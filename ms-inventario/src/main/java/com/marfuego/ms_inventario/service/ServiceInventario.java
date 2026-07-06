package com.marfuego.ms_inventario.service;

import com.marfuego.ms_inventario.dto.IngredienteRequestDTO;
import com.marfuego.ms_inventario.dto.IngredienteResponseDTO;
import com.marfuego.ms_inventario.exception.NegocioException;
import com.marfuego.ms_inventario.exception.RecursoNoEncontradoException;
import com.marfuego.ms_inventario.model.IngredienteInventario;
import com.marfuego.ms_inventario.repository.RepositoryInventario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Aquí está toda la lógica del inventario. Se encarga de pasar las entidades a
 * DTO, de validar los datos y de aplicar la regla R2 (el control del stock
 * mínimo). El controlador solo llama a esta clase y el acceso a la base de
 * datos se hace a través del RepositoryInventario.
 */
@Service
public class ServiceInventario {

    private static final Logger log = LoggerFactory.getLogger(ServiceInventario.class);

    @Autowired
    private RepositoryInventario repository;

    /**
     * Devuelve todos los ingredientes que hay guardados en el inventario.
     *
     * @return la lista de ingredientes lista para mostrar
     */
    public List<IngredienteResponseDTO> listarTodos() {
        List<IngredienteInventario> ingredientes = repository.findAll();
        List<IngredienteResponseDTO> resultado = new ArrayList<>();
        for (IngredienteInventario ingrediente : ingredientes) {
            resultado.add(toDTO(ingrediente));
        }
        return resultado;
    }

    /**
     * Devuelve solo los ingredientes que están en alerta, o sea los que ya
     * bajaron de su stock mínimo (regla R2). Sirve para saber qué hay que
     * reponer.
     *
     * @return la lista de ingredientes que quedaron bajo el mínimo
     */
    public List<IngredienteResponseDTO> listarEnAlerta() {
        List<IngredienteInventario> ingredientes = repository.findEnAlerta();
        List<IngredienteResponseDTO> resultado = new ArrayList<>();
        for (IngredienteInventario ingrediente : ingredientes) {
            resultado.add(toDTO(ingrediente));
        }
        return resultado;
    }

    /**
     * Busca un ingrediente por su id. Si no lo encuentra, lanza un error para
     * avisar que ese id no existe.
     *
     * @param id el id del ingrediente que se quiere buscar
     * @return el ingrediente encontrado
     * @throws RecursoNoEncontradoException si no existe un ingrediente con ese id
     */
    public IngredienteResponseDTO obtenerPorId(Long id) {
        IngredienteInventario ingrediente = repository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Ingrediente con id " + id + " no encontrado"));
        return toDTO(ingrediente);
    }

    /**
     * Crea un ingrediente nuevo con los datos que llegan en el DTO y lo guarda
     * en la base de datos.
     *
     * @param dto los datos del ingrediente que se quiere registrar
     * @return el ingrediente ya creado, con el id que le asignó la base
     */
    public IngredienteResponseDTO crear(IngredienteRequestDTO dto) {
        log.info("Creando ingrediente: {}", dto.getNombre());

        IngredienteInventario ingrediente = new IngredienteInventario();
        ingrediente.setNombre(dto.getNombre());
        ingrediente.setStockActual(dto.getStockActual());
        ingrediente.setStockMinimo(dto.getStockMinimo());
        ingrediente.setUnidadMedida(dto.getUnidadMedida());

        IngredienteInventario guardado = repository.save(ingrediente);
        return toDTO(guardado);
    }

    /**
     * Actualiza un ingrediente que ya existe con los datos nuevos del DTO.
     *
     * @param id  el id del ingrediente que se quiere modificar
     * @param dto los datos nuevos que se le van a poner
     * @return el ingrediente ya actualizado
     * @throws RecursoNoEncontradoException si el ingrediente no existe
     */
    public IngredienteResponseDTO actualizar(Long id, IngredienteRequestDTO dto) {
        IngredienteInventario ingrediente = repository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Ingrediente con id " + id + " no encontrado"));

        ingrediente.setNombre(dto.getNombre());
        ingrediente.setStockActual(dto.getStockActual());
        ingrediente.setStockMinimo(dto.getStockMinimo());
        ingrediente.setUnidadMedida(dto.getUnidadMedida());

        return toDTO(repository.save(ingrediente));
    }

    /**
     * Borra un ingrediente del inventario según su id.
     *
     * @param id el id del ingrediente que se quiere eliminar
     * @throws RecursoNoEncontradoException si el ingrediente no existe
     */
    public void eliminar(Long id) {
        if (!repository.existsById(id)) {
            throw new RecursoNoEncontradoException("Ingrediente con id " + id + " no encontrado");
        }
        repository.deleteById(id);
    }

    /**
     * Le descuenta stock a un ingrediente cuando se hace un pedido (regla R2).
     * Antes de restar revisa que la cantidad sea mayor a 0 y que haya stock
     * suficiente. Si después del descuento el ingrediente queda bajo su mínimo,
     * lo deja avisado en el log.
     *
     * @param id       el id del ingrediente al que se le descuenta
     * @param cantidad cuánto se quiere descontar (tiene que ser mayor a 0)
     * @return el ingrediente ya con el stock actualizado
     * @throws NegocioException             si la cantidad no sirve o no alcanza el stock
     * @throws RecursoNoEncontradoException si el ingrediente no existe
     */
    public IngredienteResponseDTO descontarStock(Long id, Double cantidad) {
        if (cantidad == null || cantidad <= 0) {
            throw new NegocioException("La cantidad a descontar debe ser mayor a 0");
        }

        IngredienteInventario ingrediente = repository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Ingrediente con id " + id + " no encontrado"));

        if (ingrediente.getStockActual() < cantidad) {
            throw new NegocioException(
                    "Stock insuficiente para " + ingrediente.getNombre() +
                    " (disponible: " + ingrediente.getStockActual() +
                    ", solicitado: " + cantidad + ")");
        }

        ingrediente.setStockActual(ingrediente.getStockActual() - cantidad);
        IngredienteInventario actualizado = repository.save(ingrediente);

        // Si quedó bajo el mínimo dejamos la alerta en los logs
        if (actualizado.getStockActual() < actualizado.getStockMinimo()) {
            log.warn("R2 ALERTA: {} bajo el stock minimo (actual={}, minimo={})",
                    actualizado.getNombre(),
                    actualizado.getStockActual(),
                    actualizado.getStockMinimo());
        }

        return toDTO(actualizado);
    }

    /**
     * Convierte una entidad de ingrediente en su DTO de respuesta. De paso
     * calcula el campo enAlerta comparando el stock actual con el mínimo (R2).
     *
     * @param ingrediente la entidad que viene de la base de datos
     * @return el DTO equivalente para devolver al cliente
     */
    private IngredienteResponseDTO toDTO(IngredienteInventario ingrediente) {
        IngredienteResponseDTO dto = new IngredienteResponseDTO();
        dto.setId(ingrediente.getId());
        dto.setNombre(ingrediente.getNombre());
        dto.setStockActual(ingrediente.getStockActual());
        dto.setStockMinimo(ingrediente.getStockMinimo());
        dto.setUnidadMedida(ingrediente.getUnidadMedida());
        dto.setEnAlerta(ingrediente.getStockActual() < ingrediente.getStockMinimo());
        return dto;
    }
}

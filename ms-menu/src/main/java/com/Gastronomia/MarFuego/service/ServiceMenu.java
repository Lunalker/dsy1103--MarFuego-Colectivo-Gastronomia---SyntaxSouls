package com.Gastronomia.MarFuego.service;

import com.Gastronomia.MarFuego.dto.PlatoRequestDTO;
import com.Gastronomia.MarFuego.dto.PlatoResponseDTO;
import com.Gastronomia.MarFuego.exception.NegocioException;
import com.Gastronomia.MarFuego.exception.RecursoNoEncontradoException;
import com.Gastronomia.MarFuego.model.PlatoMenu;
import com.Gastronomia.MarFuego.repository.RepositoryMenu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Aquí está la lógica del menú. Pasa las entidades a DTO y aplica las dos reglas
 * del dominio: la R1 (mostrar solo platos disponibles por local) y la R5 (que el
 * precio de venta sea al menos 3 veces el costo de producción).
 */
@Service
public class ServiceMenu {

    private static final Logger log = LoggerFactory.getLogger(ServiceMenu.class);

    @Autowired
    private RepositoryMenu repository;

    /**
     * Devuelve todos los platos que hay en el menú.
     *
     * @return la lista de platos lista para mostrar
     */
    public List<PlatoResponseDTO> listarTodos() {
        List<PlatoMenu> platos = repository.findAll();
        List<PlatoResponseDTO> resultado = new ArrayList<>();
        for (PlatoMenu plato : platos) {
            resultado.add(toDTO(plato));
        }
        return resultado;
    }

    /**
     * Devuelve solo los platos que están disponibles en un local (regla R1).
     *
     * @param localId el id del local que se está consultando
     * @return los platos disponibles de ese local
     */
    public List<PlatoResponseDTO> listarDisponiblesPorLocal(Long localId) {
        List<PlatoMenu> platos = repository.findByLocalIdAndDisponibleTrue(localId);
        List<PlatoResponseDTO> resultado = new ArrayList<>();
        for (PlatoMenu plato : platos) {
            resultado.add(toDTO(plato));
        }
        return resultado;
    }

    /**
     * Busca un plato por su id. Si no lo encuentra, lanza un error.
     *
     * @param id el id del plato que se quiere buscar
     * @return el plato encontrado
     * @throws RecursoNoEncontradoException si el plato no existe
     */
    public PlatoResponseDTO obtenerPorId(Long id) {
        PlatoMenu plato = repository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Plato con id " + id + " no encontrado"));
        return toDTO(plato);
    }

    /**
     * Crea un plato nuevo con los datos del DTO. Si no cumple el margen de la
     * regla R5, no lo bloquea pero deja un aviso en el log.
     *
     * @param dto los datos del plato que se quiere registrar
     * @return el plato ya creado, con su id
     */
    public PlatoResponseDTO crear(PlatoRequestDTO dto) {
        log.info("Creando plato: {}", dto.getNombre());

        PlatoMenu plato = new PlatoMenu();
        plato.setNombre(dto.getNombre());
        plato.setDescripcion(dto.getDescripcion());
        plato.setCategoria(dto.getCategoria());
        plato.setPrecioVenta(dto.getPrecioVenta());
        plato.setCostoProduccion(dto.getCostoProduccion());
        plato.setDisponible(dto.getDisponible());
        plato.setLocalId(dto.getLocalId());

        // R5: si no cumple el margen del 300% solo avisamos por log
        if (!cumpleMargen(plato)) {
            log.warn("R5: el plato {} no cumple el markup del 300%", plato.getNombre());
        }

        PlatoMenu guardado = repository.save(plato);
        return toDTO(guardado);
    }

    /**
     * Actualiza un plato que ya existe y vuelve a revisar la regla R5 del margen.
     *
     * @param id  el id del plato que se quiere modificar
     * @param dto los datos nuevos del plato
     * @return el plato ya actualizado
     * @throws RecursoNoEncontradoException si el plato no existe
     */
    public PlatoResponseDTO actualizar(Long id, PlatoRequestDTO dto) {
        PlatoMenu plato = repository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Plato con id " + id + " no encontrado"));

        plato.setNombre(dto.getNombre());
        plato.setDescripcion(dto.getDescripcion());
        plato.setCategoria(dto.getCategoria());
        plato.setPrecioVenta(dto.getPrecioVenta());
        plato.setCostoProduccion(dto.getCostoProduccion());
        plato.setDisponible(dto.getDisponible());
        plato.setLocalId(dto.getLocalId());

        if (!cumpleMargen(plato)) {
            log.warn("R5: el plato {} no cumple el markup del 300%", plato.getNombre());
        }

        return toDTO(repository.save(plato));
    }

    /**
     * Borra un plato del menú según su id.
     *
     * @param id el id del plato que se quiere eliminar
     * @throws RecursoNoEncontradoException si el plato no existe
     */
    public void eliminar(Long id) {
        if (!repository.existsById(id)) {
            throw new RecursoNoEncontradoException("Plato con id " + id + " no encontrado");
        }
        repository.deleteById(id);
    }

    /**
     * Cambia si un plato está disponible o no (regla R1). Si el plato ya estaba
     * en ese mismo estado, lanza un error porque no tiene sentido el cambio.
     *
     * @param id         el id del plato
     * @param disponible el nuevo estado de disponibilidad
     * @return el plato con la disponibilidad actualizada
     * @throws NegocioException             si el plato ya estaba en ese estado
     * @throws RecursoNoEncontradoException si el plato no existe
     */
    public PlatoResponseDTO cambiarDisponibilidad(Long id, boolean disponible) {
        PlatoMenu plato = repository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Plato con id " + id + " no encontrado"));

        // Si el plato ya está en ese estado no tiene sentido el cambio
        if (plato.getDisponible() != null && plato.getDisponible() == disponible) {
            throw new NegocioException("El plato ya esta en estado disponible=" + disponible);
        }

        plato.setDisponible(disponible);
        return toDTO(repository.save(plato));
    }

    /**
     * Revisa la regla R5: el precio de venta tiene que ser al menos 3 veces el
     * costo de producción.
     *
     * @param plato el plato que se quiere revisar
     * @return true si cumple el margen del 300%, false si no
     */
    private boolean cumpleMargen(PlatoMenu plato) {
        if (plato.getCostoProduccion() == null || plato.getPrecioVenta() == null) {
            return false;
        }
        return plato.getPrecioVenta() >= (plato.getCostoProduccion() * 3);
    }

    /**
     * Convierte una entidad de plato en su DTO de respuesta, agregando el campo
     * que dice si cumple o no la regla R5.
     *
     * @param plato la entidad que viene de la base de datos
     * @return el DTO equivalente para devolver al cliente
     */
    private PlatoResponseDTO toDTO(PlatoMenu plato) {
        PlatoResponseDTO dto = new PlatoResponseDTO();
        dto.setId(plato.getId());
        dto.setNombre(plato.getNombre());
        dto.setDescripcion(plato.getDescripcion());
        dto.setCategoria(plato.getCategoria());
        dto.setPrecioVenta(plato.getPrecioVenta());
        dto.setCostoProduccion(plato.getCostoProduccion());
        dto.setDisponible(plato.getDisponible());
        dto.setLocalId(plato.getLocalId());
        dto.setCumpleMargen(cumpleMargen(plato));
        return dto;
    }
}

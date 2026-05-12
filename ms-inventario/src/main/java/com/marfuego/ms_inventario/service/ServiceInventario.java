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

@Service
public class ServiceInventario {

    private static final Logger log = LoggerFactory.getLogger(ServiceInventario.class);

    @Autowired
    private RepositoryInventario repository;

    public List<IngredienteResponseDTO> listarTodos() {
        List<IngredienteInventario> ingredientes = repository.findAll();
        List<IngredienteResponseDTO> resultado = new ArrayList<>();
        for (IngredienteInventario ingrediente : ingredientes) {
            resultado.add(toDTO(ingrediente));
        }
        return resultado;
    }

    // R2: lista los ingredientes que estan bajo el stock minimo
    public List<IngredienteResponseDTO> listarEnAlerta() {
        List<IngredienteInventario> ingredientes = repository.findEnAlerta();
        List<IngredienteResponseDTO> resultado = new ArrayList<>();
        for (IngredienteInventario ingrediente : ingredientes) {
            resultado.add(toDTO(ingrediente));
        }
        return resultado;
    }

    public IngredienteResponseDTO obtenerPorId(Long id) {
        IngredienteInventario ingrediente = repository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Ingrediente con id " + id + " no encontrado"));
        return toDTO(ingrediente);
    }

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

    public void eliminar(Long id) {
        if (!repository.existsById(id)) {
            throw new RecursoNoEncontradoException("Ingrediente con id " + id + " no encontrado");
        }
        repository.deleteById(id);
    }

    // R2: descontar stock cuando se registra un pedido
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

        // Si quedo bajo el minimo dejamos la alerta en los logs
        if (actualizado.getStockActual() < actualizado.getStockMinimo()) {
            log.warn("R2 ALERTA: {} bajo el stock minimo (actual={}, minimo={})",
                    actualizado.getNombre(),
                    actualizado.getStockActual(),
                    actualizado.getStockMinimo());
        }

        return toDTO(actualizado);
    }

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

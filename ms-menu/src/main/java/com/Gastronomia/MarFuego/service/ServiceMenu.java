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

@Service
public class ServiceMenu {

    private static final Logger log = LoggerFactory.getLogger(ServiceMenu.class);

    @Autowired
    private RepositoryMenu repository;

    // Lista todos los platos
    public List<PlatoResponseDTO> listarTodos() {
        List<PlatoMenu> platos = repository.findAll();
        List<PlatoResponseDTO> resultado = new ArrayList<>();
        for (PlatoMenu plato : platos) {
            resultado.add(toDTO(plato));
        }
        return resultado;
    }

    // Lista los platos disponibles de un local (R1)
    public List<PlatoResponseDTO> listarDisponiblesPorLocal(Long localId) {
        List<PlatoMenu> platos = repository.findByLocalIdAndDisponibleTrue(localId);
        List<PlatoResponseDTO> resultado = new ArrayList<>();
        for (PlatoMenu plato : platos) {
            resultado.add(toDTO(plato));
        }
        return resultado;
    }

    public PlatoResponseDTO obtenerPorId(Long id) {
        PlatoMenu plato = repository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Plato con id " + id + " no encontrado"));
        return toDTO(plato);
    }

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

    public void eliminar(Long id) {
        if (!repository.existsById(id)) {
            throw new RecursoNoEncontradoException("Plato con id " + id + " no encontrado");
        }
        repository.deleteById(id);
    }

    // R1: cambiar disponibilidad del plato
    public PlatoResponseDTO cambiarDisponibilidad(Long id, boolean disponible) {
        PlatoMenu plato = repository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Plato con id " + id + " no encontrado"));

        // Si el plato ya esta en ese estado no tiene sentido el cambio
        if (plato.getDisponible() != null && plato.getDisponible() == disponible) {
            throw new NegocioException("El plato ya esta en estado disponible=" + disponible);
        }

        plato.setDisponible(disponible);
        return toDTO(repository.save(plato));
    }

    // R5: precio de venta debe ser al menos 3 veces el costo
    private boolean cumpleMargen(PlatoMenu plato) {
        if (plato.getCostoProduccion() == null || plato.getPrecioVenta() == null) {
            return false;
        }
        return plato.getPrecioVenta() >= (plato.getCostoProduccion() * 3);
    }

    // Pasa la entidad a DTO para devolver al cliente
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

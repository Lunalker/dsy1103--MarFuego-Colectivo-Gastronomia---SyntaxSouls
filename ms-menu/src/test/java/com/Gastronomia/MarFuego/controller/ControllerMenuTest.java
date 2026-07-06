package com.Gastronomia.MarFuego.controller;

import com.Gastronomia.MarFuego.dto.PlatoRequestDTO;
import com.Gastronomia.MarFuego.dto.PlatoResponseDTO;
import com.Gastronomia.MarFuego.service.ServiceMenu;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Pruebas unitarias del ControllerMenu. Se mockea el servicio y se verifican
 * las respuestas HTTP de cada endpoint.
 */
@ExtendWith(MockitoExtension.class)
class ControllerMenuTest {

    @Mock
    private ServiceMenu service;

    @InjectMocks
    private ControllerMenu controller;

    @Test
    void listar_devuelve2xx() {
        when(service.listarTodos()).thenReturn(List.of(new PlatoResponseDTO()));
        ResponseEntity<List<PlatoResponseDTO>> resp = controller.listar();
        assertTrue(resp.getStatusCode().is2xxSuccessful());
        assertEquals(1, resp.getBody().size());
    }

    @Test
    void obtener_devuelve2xx() {
        when(service.obtenerPorId(1L)).thenReturn(new PlatoResponseDTO());
        ResponseEntity<PlatoResponseDTO> resp = controller.obtener(1L);
        assertTrue(resp.getStatusCode().is2xxSuccessful());
    }

    @Test
    void listarDisponibles_devuelve2xx() {
        when(service.listarDisponiblesPorLocal(1L)).thenReturn(List.of());
        ResponseEntity<List<PlatoResponseDTO>> resp = controller.listarDisponibles(1L);
        assertTrue(resp.getStatusCode().is2xxSuccessful());
    }

    @Test
    void crear_devuelve2xx() {
        PlatoRequestDTO dto = new PlatoRequestDTO();
        when(service.crear(dto)).thenReturn(new PlatoResponseDTO());
        ResponseEntity<PlatoResponseDTO> resp = controller.crear(dto);
        assertTrue(resp.getStatusCode().is2xxSuccessful());
    }

    @Test
    void actualizar_devuelve2xx() {
        PlatoRequestDTO dto = new PlatoRequestDTO();
        when(service.actualizar(1L, dto)).thenReturn(new PlatoResponseDTO());
        ResponseEntity<PlatoResponseDTO> resp = controller.actualizar(1L, dto);
        assertTrue(resp.getStatusCode().is2xxSuccessful());
    }

    @Test
    void eliminar_devuelve2xx() {
        ResponseEntity<Void> resp = controller.eliminar(1L);
        assertTrue(resp.getStatusCode().is2xxSuccessful());
        verify(service).eliminar(1L);
    }

    @Test
    void cambiarDisponibilidad_devuelve2xx() {
        when(service.cambiarDisponibilidad(1L, false)).thenReturn(new PlatoResponseDTO());
        ResponseEntity<PlatoResponseDTO> resp = controller.cambiarDisponibilidad(1L, false);
        assertTrue(resp.getStatusCode().is2xxSuccessful());
    }
}

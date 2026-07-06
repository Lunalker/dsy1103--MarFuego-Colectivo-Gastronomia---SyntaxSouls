package com.marfuego.ms_inventario.controller;

import com.marfuego.ms_inventario.dto.IngredienteRequestDTO;
import com.marfuego.ms_inventario.dto.IngredienteResponseDTO;
import com.marfuego.ms_inventario.service.ServiceInventario;
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
 * Pruebas unitarias del ControllerInventario. Se mockea el servicio y se
 * verifican las respuestas HTTP que devuelve cada endpoint.
 */
@ExtendWith(MockitoExtension.class)
class ControllerInventarioTest {

    @Mock
    private ServiceInventario service;

    @InjectMocks
    private ControllerInventario controller;

    @Test
    void listar_devuelve2xx() {
        // given
        when(service.listarTodos()).thenReturn(List.of(new IngredienteResponseDTO()));
        // when
        ResponseEntity<List<IngredienteResponseDTO>> resp = controller.listar();
        // then
        assertTrue(resp.getStatusCode().is2xxSuccessful());
        assertEquals(1, resp.getBody().size());
    }

    @Test
    void listarAlertas_devuelve2xx() {
        // given
        when(service.listarEnAlerta()).thenReturn(List.of());
        // when
        ResponseEntity<List<IngredienteResponseDTO>> resp = controller.listarAlertas();
        // then
        assertTrue(resp.getStatusCode().is2xxSuccessful());
    }

    @Test
    void obtener_devuelve2xx() {
        // given
        when(service.obtenerPorId(1L)).thenReturn(new IngredienteResponseDTO());
        // when
        ResponseEntity<IngredienteResponseDTO> resp = controller.obtener(1L);
        // then
        assertTrue(resp.getStatusCode().is2xxSuccessful());
        assertNotNull(resp.getBody());
    }

    @Test
    void crear_devuelve2xx() {
        // given
        IngredienteRequestDTO dto = new IngredienteRequestDTO();
        when(service.crear(dto)).thenReturn(new IngredienteResponseDTO());
        // when
        ResponseEntity<IngredienteResponseDTO> resp = controller.crear(dto);
        // then
        assertTrue(resp.getStatusCode().is2xxSuccessful());
    }

    @Test
    void actualizar_devuelve2xx() {
        // given
        IngredienteRequestDTO dto = new IngredienteRequestDTO();
        when(service.actualizar(1L, dto)).thenReturn(new IngredienteResponseDTO());
        // when
        ResponseEntity<IngredienteResponseDTO> resp = controller.actualizar(1L, dto);
        // then
        assertTrue(resp.getStatusCode().is2xxSuccessful());
    }

    @Test
    void eliminar_devuelve2xxYLlamaAlServicio() {
        // when
        ResponseEntity<Void> resp = controller.eliminar(1L);
        // then
        assertTrue(resp.getStatusCode().is2xxSuccessful());
        verify(service).eliminar(1L);
    }

    @Test
    void descontar_devuelve2xx() {
        // given
        when(service.descontarStock(1L, 5.0)).thenReturn(new IngredienteResponseDTO());
        // when
        ResponseEntity<IngredienteResponseDTO> resp = controller.descontar(1L, 5.0);
        // then
        assertTrue(resp.getStatusCode().is2xxSuccessful());
    }
}

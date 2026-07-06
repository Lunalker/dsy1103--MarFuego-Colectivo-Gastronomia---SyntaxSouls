package com.marfuego.ms_pedidos.controller;

import com.marfuego.ms_pedidos.dto.PedidoRequestDTO;
import com.marfuego.ms_pedidos.dto.PedidoResponseDTO;
import com.marfuego.ms_pedidos.service.PedidoService;
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
 * Pruebas unitarias del PedidoController. Se mockea el servicio y se verifican
 * las respuestas HTTP de cada endpoint.
 */
@ExtendWith(MockitoExtension.class)
class PedidoControllerTest {

    @Mock
    private PedidoService service;

    @InjectMocks
    private PedidoController controller;

    @Test
    void listar_devuelve2xx() {
        when(service.listarTodos()).thenReturn(List.of(new PedidoResponseDTO()));
        ResponseEntity<List<PedidoResponseDTO>> resp = controller.listar();
        assertTrue(resp.getStatusCode().is2xxSuccessful());
    }

    @Test
    void obtener_devuelve2xx() {
        when(service.obtenerPorId(1L)).thenReturn(new PedidoResponseDTO());
        assertTrue(controller.obtener(1L).getStatusCode().is2xxSuccessful());
    }

    @Test
    void listarPorLocal_devuelve2xx() {
        when(service.listarPorLocal(1L)).thenReturn(List.of());
        assertTrue(controller.listarPorLocal(1L).getStatusCode().is2xxSuccessful());
    }

    @Test
    void listarPorEstado_devuelve2xx() {
        when(service.listarPorEstado("PENDIENTE")).thenReturn(List.of());
        assertTrue(controller.listarPorEstado("PENDIENTE").getStatusCode().is2xxSuccessful());
    }

    @Test
    void crear_devuelve2xx() {
        PedidoRequestDTO dto = new PedidoRequestDTO();
        when(service.crear(dto)).thenReturn(new PedidoResponseDTO());
        assertTrue(controller.crear(dto).getStatusCode().is2xxSuccessful());
    }

    @Test
    void actualizar_devuelve2xx() {
        PedidoRequestDTO dto = new PedidoRequestDTO();
        when(service.actualizar(1L, dto)).thenReturn(new PedidoResponseDTO());
        assertTrue(controller.actualizar(1L, dto).getStatusCode().is2xxSuccessful());
    }

    @Test
    void cambiarEstado_devuelve2xx() {
        when(service.cambiarEstado(1L, "ENTREGADO")).thenReturn(new PedidoResponseDTO());
        assertTrue(controller.cambiarEstado(1L, "ENTREGADO").getStatusCode().is2xxSuccessful());
    }

    @Test
    void eliminar_devuelve2xx() {
        assertTrue(controller.eliminar(1L).getStatusCode().is2xxSuccessful());
        verify(service).eliminar(1L);
    }
}

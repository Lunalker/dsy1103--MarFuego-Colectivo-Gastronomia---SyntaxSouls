package com.marfuego.ms_locales.controller;

import com.marfuego.ms_locales.dto.LocalRequestDTO;
import com.marfuego.ms_locales.dto.LocalResponseDTO;
import com.marfuego.ms_locales.dto.MesaRequestDTO;
import com.marfuego.ms_locales.dto.MesaResponseDTO;
import com.marfuego.ms_locales.model.Estado;
import com.marfuego.ms_locales.service.ServiceLocales;
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
 * Pruebas unitarias del ControllerLocales. Se mockea el servicio y se verifican
 * las respuestas HTTP de los endpoints de locales y mesas.
 */
@ExtendWith(MockitoExtension.class)
class ControllerLocalesTest {

    @Mock
    private ServiceLocales service;

    @InjectMocks
    private ControllerLocales controller;

    @Test
    void listarLocales_devuelve2xx() {
        when(service.listarLocales()).thenReturn(List.of(new LocalResponseDTO()));
        ResponseEntity<List<LocalResponseDTO>> resp = controller.listarLocales();
        assertTrue(resp.getStatusCode().is2xxSuccessful());
    }

    @Test
    void obtenerLocal_devuelve2xx() {
        when(service.obtenerLocal(1L)).thenReturn(new LocalResponseDTO());
        assertTrue(controller.obtenerLocal(1L).getStatusCode().is2xxSuccessful());
    }

    @Test
    void crearLocal_devuelve2xx() {
        LocalRequestDTO dto = new LocalRequestDTO();
        when(service.crearLocal(dto)).thenReturn(new LocalResponseDTO());
        assertTrue(controller.crearLocal(dto).getStatusCode().is2xxSuccessful());
    }

    @Test
    void actualizarLocal_devuelve2xx() {
        LocalRequestDTO dto = new LocalRequestDTO();
        when(service.actualizarLocal(1L, dto)).thenReturn(new LocalResponseDTO());
        assertTrue(controller.actualizarLocal(1L, dto).getStatusCode().is2xxSuccessful());
    }

    @Test
    void eliminarLocal_devuelve2xx() {
        assertTrue(controller.eliminarLocal(1L).getStatusCode().is2xxSuccessful());
        verify(service).eliminarLocal(1L);
    }

    @Test
    void listarMesas_devuelve2xx() {
        when(service.listarMesas()).thenReturn(List.of(new MesaResponseDTO()));
        assertTrue(controller.listarMesas().getStatusCode().is2xxSuccessful());
    }

    @Test
    void listarMesasPorLocal_devuelve2xx() {
        when(service.listarMesasPorLocal(1L)).thenReturn(List.of());
        assertTrue(controller.listarMesasPorLocal(1L).getStatusCode().is2xxSuccessful());
    }

    @Test
    void listarMesasPorEstado_devuelve2xx() {
        when(service.listarMesasPorEstado(Estado.LIBRE)).thenReturn(List.of());
        assertTrue(controller.listarMesasPorEstado(Estado.LIBRE).getStatusCode().is2xxSuccessful());
    }

    @Test
    void obtenerMesa_devuelve2xx() {
        when(service.obtenerMesa(1L)).thenReturn(new MesaResponseDTO());
        assertTrue(controller.obtenerMesa(1L).getStatusCode().is2xxSuccessful());
    }

    @Test
    void crearMesa_devuelve2xx() {
        MesaRequestDTO dto = new MesaRequestDTO();
        when(service.crearMesa(dto)).thenReturn(new MesaResponseDTO());
        assertTrue(controller.crearMesa(dto).getStatusCode().is2xxSuccessful());
    }

    @Test
    void actualizarMesa_devuelve2xx() {
        MesaRequestDTO dto = new MesaRequestDTO();
        when(service.actualizarMesa(1L, dto)).thenReturn(new MesaResponseDTO());
        assertTrue(controller.actualizarMesa(1L, dto).getStatusCode().is2xxSuccessful());
    }

    @Test
    void eliminarMesa_devuelve2xx() {
        assertTrue(controller.eliminarMesa(1L).getStatusCode().is2xxSuccessful());
        verify(service).eliminarMesa(1L);
    }

    @Test
    void reservar_devuelve2xx() {
        when(service.reservarMesa(1L)).thenReturn(new MesaResponseDTO());
        assertTrue(controller.reservar(1L).getStatusCode().is2xxSuccessful());
    }

    @Test
    void ocupar_devuelve2xx() {
        when(service.ocuparMesa(1L)).thenReturn(new MesaResponseDTO());
        assertTrue(controller.ocupar(1L).getStatusCode().is2xxSuccessful());
    }

    @Test
    void liberar_devuelve2xx() {
        when(service.liberarMesa(1L)).thenReturn(new MesaResponseDTO());
        assertTrue(controller.liberar(1L).getStatusCode().is2xxSuccessful());
    }
}

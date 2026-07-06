package com.marfuego.ms_reportes.controller;

import com.marfuego.ms_reportes.dto.reporte.RentabilidadPlatoDTO;
import com.marfuego.ms_reportes.dto.reporte.ResumenLocalDTO;
import com.marfuego.ms_reportes.dto.reporte.StockCriticoDTO;
import com.marfuego.ms_reportes.service.ServiceReportes;
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
 * Pruebas unitarias del ControllerReportes. Se mockea el servicio y se verifican
 * las respuestas HTTP de cada reporte.
 */
@ExtendWith(MockitoExtension.class)
class ControllerReportesTest {

    @Mock
    private ServiceReportes service;

    @InjectMocks
    private ControllerReportes controller;

    @Test
    void resumenLocal_devuelve2xx() {
        when(service.resumenDeLocal(1L)).thenReturn(new ResumenLocalDTO());
        ResponseEntity<ResumenLocalDTO> resp = controller.resumenLocal(1L);
        assertTrue(resp.getStatusCode().is2xxSuccessful());
    }

    @Test
    void stockCritico_devuelve2xx() {
        when(service.stockCritico()).thenReturn(List.of(new StockCriticoDTO()));
        ResponseEntity<List<StockCriticoDTO>> resp = controller.stockCritico();
        assertTrue(resp.getStatusCode().is2xxSuccessful());
    }

    @Test
    void rentabilidad_devuelve2xx() {
        when(service.rentabilidadPlatos()).thenReturn(List.of(new RentabilidadPlatoDTO()));
        ResponseEntity<List<RentabilidadPlatoDTO>> resp = controller.rentabilidad();
        assertTrue(resp.getStatusCode().is2xxSuccessful());
    }
}

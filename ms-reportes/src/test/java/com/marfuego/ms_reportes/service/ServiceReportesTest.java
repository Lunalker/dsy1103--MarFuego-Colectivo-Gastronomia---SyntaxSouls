package com.marfuego.ms_reportes.service;

import com.marfuego.ms_reportes.client.InventarioClient;
import com.marfuego.ms_reportes.client.LocalesClient;
import com.marfuego.ms_reportes.client.MenuClient;
import com.marfuego.ms_reportes.client.PedidosClient;
import com.marfuego.ms_reportes.dto.externo.IngredienteDTO;
import com.marfuego.ms_reportes.dto.externo.LocalDTO;
import com.marfuego.ms_reportes.dto.externo.PedidoDTO;
import com.marfuego.ms_reportes.dto.externo.PlatoDTO;
import com.marfuego.ms_reportes.dto.reporte.ResumenLocalDTO;
import com.marfuego.ms_reportes.dto.reporte.StockCriticoDTO;
import com.marfuego.ms_reportes.exception.ComunicacionException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ServiceReportesTest {

    @Mock
    private LocalesClient localesClient;

    @Mock
    private MenuClient menuClient;

    @Mock
    private InventarioClient inventarioClient;

    @Mock
    private PedidosClient pedidosClient;

    @InjectMocks
    private ServiceReportes service;

    private LocalDTO crearLocal() {
        LocalDTO dto = new LocalDTO();
        dto.setId(1L);
        dto.setNombre("MarFuego Centro");
        dto.setUbicacion("Puerto Montt");
        return dto;
    }

    private PlatoDTO crearPlatoDisponible() {
        PlatoDTO dto = new PlatoDTO();
        dto.setId(1L);
        dto.setNombre("Ceviche");
        dto.setLocalId(1L);
        dto.setDisponible(true);
        dto.setPrecioVenta(10000.0);
        dto.setCostoProduccion(2500.0);
        dto.setCumpleMargen(true);
        return dto;
    }

    private PlatoDTO crearPlatoNoDisponible() {
        PlatoDTO dto = new PlatoDTO();
        dto.setId(2L);
        dto.setNombre("Salmón");
        dto.setLocalId(1L);
        dto.setDisponible(false);
        dto.setPrecioVenta(12000.0);
        dto.setCostoProduccion(4000.0);
        dto.setCumpleMargen(false);
        return dto;
    }

    private PedidoDTO crearPedidoPendiente() {
        PedidoDTO dto = new PedidoDTO();
        dto.setId(1L);
        dto.setEstado("PENDIENTE");
        dto.setTotal(10000.0);
        dto.setLocalId(1L);
        return dto;
    }

    private PedidoDTO crearPedidoEntregado() {
        PedidoDTO dto = new PedidoDTO();
        dto.setId(2L);
        dto.setEstado("ENTREGADO");
        dto.setTotal(20000.0);
        dto.setLocalId(1L);
        return dto;
    }

    private IngredienteDTO crearIngrediente() {
        IngredienteDTO dto = new IngredienteDTO();
        dto.setId(1L);
        dto.setNombre("Salmón");
        dto.setStockActual(2.0);
        dto.setStockMinimo(5.0);
        dto.setUnidadMedida("kg");
        return dto;
    }

    @Test
    void debeGenerarResumenDelLocal() {

        when(localesClient.obtenerLocal(1L))
                .thenReturn(crearLocal());

        when(menuClient.listarPlatos())
                .thenReturn(List.of(
                        crearPlatoDisponible(),
                        crearPlatoNoDisponible()
                ));

        when(pedidosClient.listarPedidosPorLocal(1L))
                .thenReturn(List.of(
                        crearPedidoPendiente(),
                        crearPedidoEntregado()
                ));

        ResumenLocalDTO dto = service.resumenDeLocal(1L);

        assertNotNull(dto);
        assertEquals(1L, dto.getLocalId());
        assertEquals("MarFuego Centro", dto.getNombreLocal());
        assertEquals("Puerto Montt", dto.getUbicacion());
        assertEquals(2, dto.getTotalPlatos());
        assertEquals(1, dto.getPlatosDisponibles());
        assertEquals(2, dto.getTotalPedidos());
        assertEquals(1, dto.getPedidosPendientes());
        assertEquals(30000.0, dto.getVentasTotales());

        verify(localesClient).obtenerLocal(1L);
        verify(menuClient).listarPlatos();
        verify(pedidosClient).listarPedidosPorLocal(1L);
    }

    @Test
    void debeLanzarExcepcionCuandoFallaLocales() {

        when(localesClient.obtenerLocal(1L))
                .thenThrow(new RuntimeException());

        assertThrows(
                ComunicacionException.class,
                () -> service.resumenDeLocal(1L)
        );

        verify(localesClient).obtenerLocal(1L);
    }

    @Test
    void debeLanzarExcepcionCuandoFallaMenu() {

        when(localesClient.obtenerLocal(1L))
                .thenReturn(crearLocal());

        when(menuClient.listarPlatos())
                .thenThrow(new RuntimeException());

        assertThrows(
                ComunicacionException.class,
                () -> service.resumenDeLocal(1L)
        );

        verify(menuClient).listarPlatos();
    }

    @Test
    void debeLanzarExcepcionCuandoFallaPedidos() {

        when(localesClient.obtenerLocal(1L))
                .thenReturn(crearLocal());

        when(menuClient.listarPlatos())
                .thenReturn(List.of(crearPlatoDisponible()));

        when(pedidosClient.listarPedidosPorLocal(1L))
                .thenThrow(new RuntimeException());

        assertThrows(
                ComunicacionException.class,
                () -> service.resumenDeLocal(1L)
        );

        verify(pedidosClient).listarPedidosPorLocal(1L);
    }

    @Test
    void debeGenerarReporteStockCritico() {

        when(inventarioClient.listarAlertas())
                .thenReturn(List.of(crearIngrediente()));

        List<StockCriticoDTO> resultado = service.stockCritico();

        assertEquals(1, resultado.size());

        StockCriticoDTO dto = resultado.get(0);

        assertEquals(1L, dto.getIngredienteId());
        assertEquals("Salmón", dto.getNombreIngrediente());
        assertEquals(2.0, dto.getStockActual());
        assertEquals(5.0, dto.getStockMinimo());
        assertEquals("kg", dto.getUnidadMedida());
        assertEquals(3.0, dto.getCantidadFaltante());

        verify(inventarioClient).listarAlertas();
    }

    @Test
    void debeLanzarExcepcionCuandoFallaInventario() {

        when(inventarioClient.listarAlertas())
                .thenThrow(new RuntimeException());

        assertThrows(
                ComunicacionException.class,
                () -> service.stockCritico()
        );

        verify(inventarioClient).listarAlertas();
    }

}
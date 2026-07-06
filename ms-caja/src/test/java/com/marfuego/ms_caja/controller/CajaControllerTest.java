package com.marfuego.ms_caja.controller;

import com.marfuego.ms_caja.model.BoletaCaja;
import com.marfuego.ms_caja.model.FacturaCaja;
import com.marfuego.ms_caja.service.CajaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Pruebas unitarias del CajaController. Se mockea el servicio y se verifica que
 * cada endpoint devuelva lo que entrega la capa de negocio.
 */
@ExtendWith(MockitoExtension.class)
class CajaControllerTest {

    @Mock
    private CajaService service;

    @InjectMocks
    private CajaController controller;

    @Test
    void listarBoletas_devuelveLaLista() {
        BoletaCaja b = new BoletaCaja();
        when(service.listarBoletas()).thenReturn(List.of(b));
        List<BoletaCaja> resultado = controller.listarBoletas();
        assertEquals(1, resultado.size());
    }

    @Test
    void buscarBoletaPorId_devuelveLaBoleta() {
        BoletaCaja b = new BoletaCaja();
        when(service.buscarBoletaPorId(1L)).thenReturn(b);
        assertEquals(b, controller.buscarBoletaPorId(1L));
    }

    @Test
    void buscarBoletaPorPedido_devuelveLaBoleta() {
        BoletaCaja b = new BoletaCaja();
        when(service.buscarBoletaPorPedido(5L)).thenReturn(b);
        assertEquals(b, controller.buscarBoletaPorPedido(5L));
    }

    @Test
    void guardarBoleta_devuelveLaGuardada() {
        BoletaCaja b = new BoletaCaja();
        when(service.guardarBoleta(b)).thenReturn(b);
        assertEquals(b, controller.guardarBoleta(b));
    }

    @Test
    void listarFacturas_devuelveLaLista() {
        when(service.listarFacturas()).thenReturn(List.of(new FacturaCaja()));
        assertEquals(1, controller.listarFacturas().size());
    }

    @Test
    void buscarFacturaPorId_devuelveLaFactura() {
        FacturaCaja f = new FacturaCaja();
        when(service.buscarFacturaPorId(1L)).thenReturn(f);
        assertEquals(f, controller.buscarFacturaPorId(1L));
    }

    @Test
    void guardarFactura_devuelveLaGuardada() {
        FacturaCaja f = new FacturaCaja();
        when(service.guardarFactura(f)).thenReturn(f);
        assertEquals(f, controller.guardarFactura(f));
    }
}

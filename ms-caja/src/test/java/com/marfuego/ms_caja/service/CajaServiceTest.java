package com.marfuego.ms_caja.service;

import com.marfuego.ms_caja.model.BoletaCaja;
import com.marfuego.ms_caja.model.FacturaCaja;
import com.marfuego.ms_caja.model.MetodoPago;
import com.marfuego.ms_caja.repository.BoletaRepository;
import com.marfuego.ms_caja.repository.FacturaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Pruebas unitarias del CajaService. Se mockean los repositorios de boletas y
 * facturas para probar la logica sin base de datos, incluida la regla R4.
 */
@ExtendWith(MockitoExtension.class)
class CajaServiceTest {

    @Mock
    private BoletaRepository boletaRepository;

    @Mock
    private FacturaRepository facturaRepository;

    @InjectMocks
    private CajaService service;

    private BoletaCaja boleta(Long id, Long pedidoId) {
        BoletaCaja b = new BoletaCaja();
        b.setId(id);
        b.setPedidoId(pedidoId);
        b.setNombreCliente("Cliente");
        b.setMontoTotal(10000.0);
        b.setMetodoPago(MetodoPago.EFECTIVO);
        return b;
    }

    @Test
    void listarBoletas_devuelveLaLista() {
        // given: hay dos boletas guardadas
        when(boletaRepository.findAll()).thenReturn(List.of(boleta(1L, 5L), boleta(2L, 6L)));

        // when: se listan
        List<BoletaCaja> resultado = service.listarBoletas();

        // then: vuelven las dos
        assertEquals(2, resultado.size());
        verify(boletaRepository).findAll();
    }

    @Test
    void guardarBoleta_persisteYDevuelveLaBoleta() {
        // given: una boleta nueva
        BoletaCaja b = boleta(1L, 5L);
        when(boletaRepository.save(b)).thenReturn(b);

        // when: se guarda
        BoletaCaja resultado = service.guardarBoleta(b);

        // then: se persiste y se devuelve
        assertNotNull(resultado);
        assertEquals(5L, resultado.getPedidoId());
        verify(boletaRepository).save(b);
    }

    @Test
    void buscarBoletaPorId_cuandoNoExiste_devuelveNull() {
        // given: no existe la boleta 99
        when(boletaRepository.findById(99L)).thenReturn(Optional.empty());

        // when: se busca
        BoletaCaja resultado = service.buscarBoletaPorId(99L);

        // then: devuelve null
        assertNull(resultado);
    }

    @Test
    void buscarBoletaPorPedido_devuelveLaBoletaDelPedido() {
        // given: la regla R4 busca la boleta de un pedido para saber si se pago
        when(boletaRepository.findByPedidoId(5L)).thenReturn(boleta(1L, 5L));

        // when: se busca por el id del pedido
        BoletaCaja resultado = service.buscarBoletaPorPedido(5L);

        // then: devuelve la boleta de ese pedido
        assertNotNull(resultado);
        assertEquals(5L, resultado.getPedidoId());
        verify(boletaRepository).findByPedidoId(5L);
    }

    @Test
    void listarFacturas_devuelveLaLista() {
        // given: hay una factura guardada
        FacturaCaja f = new FacturaCaja();
        f.setId(1L);
        f.setRazonSocial("Empresa SpA");
        f.setRut("11.111.111-1");
        f.setMontoTotal(20000.0);
        f.setMetodoPago(MetodoPago.TRANSFERENCIA);
        when(facturaRepository.findAll()).thenReturn(List.of(f));

        // when: se listan
        List<FacturaCaja> resultado = service.listarFacturas();

        // then: vuelve la factura con su razon social
        assertEquals(1, resultado.size());
        assertEquals("Empresa SpA", resultado.get(0).getRazonSocial());
    }

    @Test
    void buscarFacturaPorId_cuandoExiste_devuelveLaFactura() {
        // given: existe la factura 1
        FacturaCaja f = new FacturaCaja();
        f.setId(1L);
        f.setRazonSocial("Comercio");
        when(facturaRepository.findById(1L)).thenReturn(Optional.of(f));

        // when: se busca por id
        FacturaCaja resultado = service.buscarFacturaPorId(1L);

        // then: devuelve la factura
        assertNotNull(resultado);
        assertEquals("Comercio", resultado.getRazonSocial());
    }
}

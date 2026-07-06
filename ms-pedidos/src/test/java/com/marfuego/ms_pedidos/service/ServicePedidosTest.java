package com.marfuego.ms_pedidos.service;

import com.marfuego.ms_pedidos.client.MenuClient;
import com.marfuego.ms_pedidos.dto.PedidoResponseDTO;
import com.marfuego.ms_pedidos.exception.RecursoNoEncontradoException;
import com.marfuego.ms_pedidos.model.DetallePedido;
import com.marfuego.ms_pedidos.model.Pedido;
import com.marfuego.ms_pedidos.repository.PedidoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PedidoServiceTest {

    @Mock
    private PedidoRepository repository;

    @Mock
    private MenuClient menuClient;

    @InjectMocks
    private PedidoService service;

    private Pedido crearPedido() {

        Pedido pedido = new Pedido();
        pedido.setId(1L);
        pedido.setMesaId(10L);
        pedido.setLocalId(2L);
        pedido.setEstado("PENDIENTE");
        pedido.setTipo("COMEDOR");
        pedido.setFecha(LocalDateTime.now());
        pedido.setTotal(25980.0);

        DetallePedido detalle = new DetallePedido();
        detalle.setId(1L);
        detalle.setPlatoId(5L);
        detalle.setCantidad(2);
        detalle.setPrecioUnitario(12990.0);
        detalle.setPedido(pedido);

        pedido.getDetalles().add(detalle);

        return pedido;
    }

    @Test
    void debeListarTodosLosPedidos() {

        when(repository.findAll()).thenReturn(List.of(crearPedido()));

        List<PedidoResponseDTO> resultado = service.listarTodos();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(1L, resultado.get(0).getId());
        assertEquals("PENDIENTE", resultado.get(0).getEstado());

        verify(repository).findAll();
    }

    @Test
    void debeListarPedidosPorLocal() {

        when(repository.findByLocalId(2L))
                .thenReturn(List.of(crearPedido()));

        List<PedidoResponseDTO> resultado = service.listarPorLocal(2L);

        assertEquals(1, resultado.size());
        assertEquals(2L, resultado.get(0).getLocalId());

        verify(repository).findByLocalId(2L);
    }

    @Test
    void debeObtenerPedidoExistente() {

        when(repository.findById(1L))
                .thenReturn(Optional.of(crearPedido()));

        PedidoResponseDTO resultado = service.obtenerPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals(10L, resultado.getMesaId());
        assertEquals(2L, resultado.getLocalId());
        assertEquals("COMEDOR", resultado.getTipo());
        assertEquals("PENDIENTE", resultado.getEstado());
        assertEquals(25980.0, resultado.getTotal());

        verify(repository).findById(1L);
    }

    @Test
    void debeLanzarExcepcionCuandoPedidoNoExiste() {

        when(repository.findById(1L))
                .thenReturn(Optional.empty());

        RecursoNoEncontradoException ex = assertThrows(
                RecursoNoEncontradoException.class,
                () -> service.obtenerPorId(1L)
        );

        assertEquals("Pedido con id 1 no encontrado", ex.getMessage());

        verify(repository).findById(1L);
    }

}
package com.marfuego.ms_inventario.service;

import com.marfuego.ms_inventario.dto.IngredienteRequestDTO;
import com.marfuego.ms_inventario.dto.IngredienteResponseDTO;
import com.marfuego.ms_inventario.exception.NegocioException;
import com.marfuego.ms_inventario.exception.RecursoNoEncontradoException;
import com.marfuego.ms_inventario.model.IngredienteInventario;
import com.marfuego.ms_inventario.repository.RepositoryInventario;
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
 * Pruebas unitarias del ServiceInventario. Se mockea el repositorio para probar
 * la logica del servicio sin base de datos real, incluida la regla R2.
 */
@ExtendWith(MockitoExtension.class)
class InventarioServiceTest {

    @Mock
    private RepositoryInventario repository;

    @InjectMocks
    private ServiceInventario service;

    // Helper para armar un ingrediente de prueba
    private IngredienteInventario ingrediente(Long id, String nombre, double actual, double minimo) {
        IngredienteInventario i = new IngredienteInventario();
        i.setId(id);
        i.setNombre(nombre);
        i.setStockActual(actual);
        i.setStockMinimo(minimo);
        i.setUnidadMedida("KG");
        return i;
    }

    @Test
    void listarTodos_devuelveTodosLosIngredientes() {
        // given: el repositorio tiene dos ingredientes
        when(repository.findAll()).thenReturn(List.of(
                ingrediente(1L, "Harina", 20, 5),
                ingrediente(2L, "Mantequilla", 15, 3)));

        // when: se piden todos
        List<IngredienteResponseDTO> resultado = service.listarTodos();

        // then: vuelven los dos en formato DTO
        assertEquals(2, resultado.size());
        assertEquals("Harina", resultado.get(0).getNombre());
        verify(repository).findAll();
    }

    @Test
    void listarEnAlerta_devuelveLosBajoElMinimo() {
        // given: hay un ingrediente bajo su minimo (regla R2)
        when(repository.findEnAlerta()).thenReturn(List.of(ingrediente(1L, "Sal", 2, 10)));

        // when: se piden los que estan en alerta
        List<IngredienteResponseDTO> resultado = service.listarEnAlerta();

        // then: viene uno y marcado en alerta
        assertEquals(1, resultado.size());
        assertTrue(resultado.get(0).getEnAlerta());
        verify(repository).findEnAlerta();
    }

    @Test
    void obtenerPorId_cuandoExiste_devuelveElIngrediente() {
        // given: existe el ingrediente con id 1
        when(repository.findById(1L)).thenReturn(Optional.of(ingrediente(1L, "Azucar", 30, 5)));

        // when: se busca por id
        IngredienteResponseDTO resultado = service.obtenerPorId(1L);

        // then: devuelve sus datos
        assertEquals("Azucar", resultado.getNombre());
        assertEquals(30.0, resultado.getStockActual().doubleValue());
    }

    @Test
    void obtenerPorId_cuandoNoExiste_lanzaRecursoNoEncontrado() {
        // given: el id no existe
        when(repository.findById(99L)).thenReturn(Optional.empty());

        // when / then: se espera la excepcion de recurso no encontrado
        assertThrows(RecursoNoEncontradoException.class, () -> service.obtenerPorId(99L));
    }

    @Test
    void crear_guardaYDevuelveElDTO() {
        // given: llegan los datos de un ingrediente nuevo
        IngredienteRequestDTO dto = new IngredienteRequestDTO();
        dto.setNombre("Queso");
        dto.setStockActual(10.0);
        dto.setStockMinimo(2.0);
        dto.setUnidadMedida("KG");
        when(repository.save(any(IngredienteInventario.class))).thenAnswer(inv -> inv.getArgument(0));

        // when: se crea
        IngredienteResponseDTO resultado = service.crear(dto);

        // then: se guarda y vuelve como DTO
        assertEquals("Queso", resultado.getNombre());
        verify(repository).save(any(IngredienteInventario.class));
    }

    @Test
    void eliminar_cuandoNoExiste_lanzaExcepcionYNoBorra() {
        // given: el ingrediente no existe
        when(repository.existsById(5L)).thenReturn(false);

        // when / then: lanza excepcion y nunca llama a deleteById
        assertThrows(RecursoNoEncontradoException.class, () -> service.eliminar(5L));
        verify(repository, never()).deleteById(anyLong());
    }

    @Test
    void descontarStock_conStockSuficiente_restaLaCantidad() {
        // given: hay 20 de stock y se van a descontar 5 (regla R2)
        IngredienteInventario ing = ingrediente(1L, "Harina", 20, 5);
        when(repository.findById(1L)).thenReturn(Optional.of(ing));
        when(repository.save(any(IngredienteInventario.class))).thenAnswer(inv -> inv.getArgument(0));

        // when: se descuentan 5
        IngredienteResponseDTO resultado = service.descontarStock(1L, 5.0);

        // then: queda 15
        assertEquals(15.0, resultado.getStockActual().doubleValue());
    }

    @Test
    void descontarStock_conStockInsuficiente_lanzaNegocioException() {
        // given: solo hay 3 de stock y se piden 10
        when(repository.findById(1L)).thenReturn(Optional.of(ingrediente(1L, "Sal", 3, 1)));

        // when / then: la regla R2 no permite dejar el stock negativo
        assertThrows(NegocioException.class, () -> service.descontarStock(1L, 10.0));
        verify(repository, never()).save(any());
    }

    @Test
    void descontarStock_conCantidadInvalida_lanzaNegocioException() {
        // given / when / then: descontar 0 (o menos) no es una operacion valida
        assertThrows(NegocioException.class, () -> service.descontarStock(1L, 0.0));
    }
}

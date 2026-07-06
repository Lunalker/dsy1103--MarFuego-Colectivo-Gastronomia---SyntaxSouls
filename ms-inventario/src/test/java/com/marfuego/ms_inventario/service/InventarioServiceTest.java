package com.marfuego.ms_inventario.service;

import com.marfuego.ms_inventario.model.InventarioModel;
import com.marfuego.ms_inventario.repository.InventarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InventarioServiceTest {

    private InventarioRepository repository;
    private InventarioService service;

    @BeforeEach
    void setUp() {
        repository = mock(InventarioRepository.class);
        service = new InventarioService(repository);
    }

    @Test
    void obtenerTodoInventarioDebeRetornarLista() {

        InventarioModel item1 = new InventarioModel(1L, "Harina", 20);
        InventarioModel item2 = new InventarioModel(2L, "Mantequilla", 15);

        when(repository.findAll()).thenReturn(Arrays.asList(item1, item2));

        var lista = service.obtenerTodoInventario();

        assertEquals(2, lista.size());
        assertEquals("Harina", lista.get(0).getNombre());

        verify(repository).findAll();
    }

    @Test
    void obtenerInventarioPorIdDebeRetornarInventario() {

        InventarioModel item = new InventarioModel(1L, "Azúcar", 30);

        when(repository.findById(1L)).thenReturn(Optional.of(item));

        InventarioModel resultado = service.obtenerInventarioPorId(1L);

        assertNotNull(resultado);
        assertEquals("Azúcar", resultado.getNombre());
        assertEquals(30, resultado.getStock());

        verify(repository).findById(1L);
    }

    @Test
    void guardarInventarioDebeGuardarCorrectamente() {

        InventarioModel item = new InventarioModel(1L, "Sal", 10);

        when(repository.save(item)).thenReturn(item);

        InventarioModel resultado = service.guardarInventario(item);

        assertNotNull(resultado);
        assertEquals("Sal", resultado.getNombre());

        verify(repository).save(item);
    }

    @Test
    void borrarInventarioDebeEliminarPorId() {

        service.borrarInventario(1L);

        verify(repository).deleteById(1L);
    }
}
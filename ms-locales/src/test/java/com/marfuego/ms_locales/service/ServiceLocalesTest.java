package com.marfuego.ms_locales.service;

import com.marfuego.ms_locales.dto.LocalResponseDTO;
import com.marfuego.ms_locales.exception.RecursoNoEncontradoException;
import com.marfuego.ms_locales.model.LocalLocales;
import com.marfuego.ms_locales.model.Ubicacion;
import com.marfuego.ms_locales.repository.LocalRepositoryLocales;
import com.marfuego.ms_locales.repository.MesaRepositoryLocales;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ServiceLocalesTest {

    @Mock
    private LocalRepositoryLocales localRepo;

    @Mock
    private MesaRepositoryLocales mesaRepo;

    @InjectMocks
    private ServiceLocales service;

    private LocalLocales crearLocal() {
        LocalLocales local = new LocalLocales();
        local.setId(1L);
        local.setNombre("MarFuego Centro");
        local.setDireccion("Av. Costanera 123");
        local.setUbicacion(Ubicacion.CENTRO_PUERTO);
        return local;
    }

    @Test
    void debeObtenerLocalExistente() {

        LocalLocales local = crearLocal();

        when(localRepo.findById(1L)).thenReturn(Optional.of(local));

        LocalResponseDTO resultado = service.obtenerLocal(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("MarFuego Centro", resultado.getNombre());
        assertEquals("Av. Costanera 123", resultado.getDireccion());
        assertEquals(Ubicacion.CENTRO_PUERTO, resultado.getUbicacion());

        verify(localRepo, times(1)).findById(1L);
    }

    @Test
    void debeLanzarExcepcionCuandoLocalNoExiste() {

        when(localRepo.findById(1L)).thenReturn(Optional.empty());

        RecursoNoEncontradoException exception = assertThrows(
                RecursoNoEncontradoException.class,
                () -> service.obtenerLocal(1L)
        );

        assertEquals("Local con id 1 no encontrado", exception.getMessage());

        verify(localRepo, times(1)).findById(1L);
    }

}
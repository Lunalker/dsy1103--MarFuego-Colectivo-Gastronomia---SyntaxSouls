package com.Gastronomia.MarFuego.service;

import com.Gastronomia.MarFuego.dto.PlatoRequestDTO;
import com.Gastronomia.MarFuego.dto.PlatoResponseDTO;
import com.Gastronomia.MarFuego.exception.NegocioException;
import com.Gastronomia.MarFuego.exception.RecursoNoEncontradoException;
import com.Gastronomia.MarFuego.model.PlatoMenu;
import com.Gastronomia.MarFuego.repository.RepositoryMenu;
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
 * Pruebas unitarias del ServiceMenu. Se mockea el repositorio para probar la
 * logica del menu sin base de datos, incluidas las reglas R1 y R5.
 */
@ExtendWith(MockitoExtension.class)
class ServiceMenuTest {

    @Mock
    private RepositoryMenu repository;

    @InjectMocks
    private ServiceMenu service;

    private PlatoMenu plato(Long id, double precio, double costo, boolean disponible) {
        PlatoMenu p = new PlatoMenu();
        p.setId(id);
        p.setNombre("Ceviche");
        p.setDescripcion("Fresco");
        p.setCategoria("Entrada");
        p.setPrecioVenta(precio);
        p.setCostoProduccion(costo);
        p.setDisponible(disponible);
        p.setLocalId(1L);
        return p;
    }

    private PlatoRequestDTO request(double precio, double costo) {
        PlatoRequestDTO dto = new PlatoRequestDTO();
        dto.setNombre("Ceviche");
        dto.setDescripcion("Fresco");
        dto.setCategoria("Entrada");
        dto.setPrecioVenta(precio);
        dto.setCostoProduccion(costo);
        dto.setDisponible(true);
        dto.setLocalId(1L);
        return dto;
    }

    @Test
    void listarDisponiblesPorLocal_devuelveSoloLosDisponibles() {
        // given: la regla R1 pide los platos disponibles de un local
        when(repository.findByLocalIdAndDisponibleTrue(1L))
                .thenReturn(List.of(plato(1L, 9000, 2000, true)));

        // when: se listan los disponibles del local 1
        List<PlatoResponseDTO> resultado = service.listarDisponiblesPorLocal(1L);

        // then: vuelve uno y esta disponible
        assertEquals(1, resultado.size());
        assertTrue(resultado.get(0).getDisponible());
        verify(repository).findByLocalIdAndDisponibleTrue(1L);
    }

    @Test
    void obtenerPorId_cuandoNoExiste_lanzaRecursoNoEncontrado() {
        // given: el plato no existe
        when(repository.findById(99L)).thenReturn(Optional.empty());

        // when / then: se espera la excepcion
        assertThrows(RecursoNoEncontradoException.class, () -> service.obtenerPorId(99L));
    }

    @Test
    void crear_cuandoCumpleMargen_marcaCumpleMargenTrue() {
        // given: precio 9000 y costo 2000 -> 9000 >= 3*2000, cumple la regla R5
        when(repository.save(any(PlatoMenu.class))).thenAnswer(inv -> inv.getArgument(0));

        // when: se crea el plato
        PlatoResponseDTO resultado = service.crear(request(9000.0, 2000.0));

        // then: queda marcado como que cumple el margen
        assertTrue(resultado.getCumpleMargen());
    }

    @Test
    void crear_cuandoNoCumpleMargen_igualGuardaPeroMarcaFalse() {
        // given: precio 5000 y costo 2000 -> 5000 < 3*2000, no cumple R5 (pero no bloquea)
        when(repository.save(any(PlatoMenu.class))).thenAnswer(inv -> inv.getArgument(0));

        // when: se crea el plato
        PlatoResponseDTO resultado = service.crear(request(5000.0, 2000.0));

        // then: se guarda igual, solo que marcado como que no cumple el margen
        assertFalse(resultado.getCumpleMargen());
        verify(repository).save(any(PlatoMenu.class));
    }

    @Test
    void cambiarDisponibilidad_cuandoYaEstaEnEseEstado_lanzaNegocioException() {
        // given: el plato ya esta disponible (regla R1)
        when(repository.findById(1L)).thenReturn(Optional.of(plato(1L, 9000, 2000, true)));

        // when / then: volver a marcarlo disponible=true no tiene sentido
        assertThrows(NegocioException.class, () -> service.cambiarDisponibilidad(1L, true));
    }

    @Test
    void cambiarDisponibilidad_cuandoCambia_actualizaElEstado() {
        // given: el plato esta disponible y se quiere marcar como no disponible
        when(repository.findById(1L)).thenReturn(Optional.of(plato(1L, 9000, 2000, true)));
        when(repository.save(any(PlatoMenu.class))).thenAnswer(inv -> inv.getArgument(0));

        // when: se cambia a no disponible
        PlatoResponseDTO resultado = service.cambiarDisponibilidad(1L, false);

        // then: queda no disponible
        assertFalse(resultado.getDisponible());
    }
}

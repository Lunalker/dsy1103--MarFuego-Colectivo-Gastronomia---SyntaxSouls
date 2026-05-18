package com.Gastronomia.MarFuego.controller;

import com.Gastronomia.MarFuego.dto.PlatoRequestDTO;
import com.Gastronomia.MarFuego.dto.PlatoResponseDTO;
import com.Gastronomia.MarFuego.service.ServiceMenu;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/menu")
public class ControllerMenu {

    @Autowired
    private ServiceMenu service;

    @GetMapping("/platos")
    public ResponseEntity<List<PlatoResponseDTO>> listar() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/platos/{id}")
    public ResponseEntity<PlatoResponseDTO> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    // R1: lista los platos disponibles de un local
    @GetMapping("/platos/local/{localId}/disponibles")
    public ResponseEntity<List<PlatoResponseDTO>> listarDisponibles(@PathVariable Long localId) {
        return ResponseEntity.ok(service.listarDisponiblesPorLocal(localId));
    }

    @PostMapping("/platos")
    public ResponseEntity<PlatoResponseDTO> crear(@Valid @RequestBody PlatoRequestDTO dto) {
        PlatoResponseDTO creado = service.crear(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PutMapping("/platos/{id}")
    public ResponseEntity<PlatoResponseDTO> actualizar(@PathVariable Long id,
                                                       @Valid @RequestBody PlatoRequestDTO dto) {
        return ResponseEntity.ok(service.actualizar(id, dto));
    }

    @DeleteMapping("/platos/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // R1: cambiar disponibilidad (true / false)
    @PatchMapping("/platos/{id}/disponibilidad")
    public ResponseEntity<PlatoResponseDTO> cambiarDisponibilidad(@PathVariable Long id,
                                                                  @RequestParam boolean disponible) {
        return ResponseEntity.ok(service.cambiarDisponibilidad(id, disponible));
    }
}

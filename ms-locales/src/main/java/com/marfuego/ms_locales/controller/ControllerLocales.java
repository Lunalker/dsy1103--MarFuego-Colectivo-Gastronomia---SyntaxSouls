package com.marfuego.ms_locales.controller;

import com.marfuego.ms_locales.dto.*;
import com.marfuego.ms_locales.model.Estado;
import com.marfuego.ms_locales.service.ServiceLocales;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/locales")
public class ControllerLocales {

    @Autowired
    private ServiceLocales service;

    // ===== LOCALES =====

    @GetMapping
    public ResponseEntity<List<LocalResponseDTO>> listarLocales() {
        return ResponseEntity.ok(service.listarLocales());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LocalResponseDTO> obtenerLocal(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerLocal(id));
    }

    @PostMapping
    public ResponseEntity<LocalResponseDTO> crearLocal(@Valid @RequestBody LocalRequestDTO dto) {
        LocalResponseDTO creado = service.crearLocal(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LocalResponseDTO> actualizarLocal(@PathVariable Long id,
                                                            @Valid @RequestBody LocalRequestDTO dto) {
        return ResponseEntity.ok(service.actualizarLocal(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarLocal(@PathVariable Long id) {
        service.eliminarLocal(id);
        return ResponseEntity.noContent().build();
    }

    // ===== MESAS =====

    @GetMapping("/mesas")
    public ResponseEntity<List<MesaResponseDTO>> listarMesas() {
        return ResponseEntity.ok(service.listarMesas());
    }

    @GetMapping("/{localId}/mesas")
    public ResponseEntity<List<MesaResponseDTO>> listarMesasPorLocal(@PathVariable Long localId) {
        return ResponseEntity.ok(service.listarMesasPorLocal(localId));
    }

    // Lista las mesas en un estado (LIBRE, RESERVADA, OCUPADA, LIMPIEZA)
    @GetMapping("/mesas/estado/{estado}")
    public ResponseEntity<List<MesaResponseDTO>> listarMesasPorEstado(@PathVariable Estado estado) {
        return ResponseEntity.ok(service.listarMesasPorEstado(estado));
    }

    @GetMapping("/mesas/{id}")
    public ResponseEntity<MesaResponseDTO> obtenerMesa(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerMesa(id));
    }

    @PostMapping("/mesas")
    public ResponseEntity<MesaResponseDTO> crearMesa(@Valid @RequestBody MesaRequestDTO dto) {
        MesaResponseDTO creada = service.crearMesa(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }

    @PutMapping("/mesas/{id}")
    public ResponseEntity<MesaResponseDTO> actualizarMesa(@PathVariable Long id,
                                                          @Valid @RequestBody MesaRequestDTO dto) {
        return ResponseEntity.ok(service.actualizarMesa(id, dto));
    }

    @DeleteMapping("/mesas/{id}")
    public ResponseEntity<Void> eliminarMesa(@PathVariable Long id) {
        service.eliminarMesa(id);
        return ResponseEntity.noContent().build();
    }

    // ===== R3: cambios de estado =====

    @PostMapping("/mesas/{id}/reservar")
    public ResponseEntity<MesaResponseDTO> reservar(@PathVariable Long id) {
        return ResponseEntity.ok(service.reservarMesa(id));
    }

    @PostMapping("/mesas/{id}/ocupar")
    public ResponseEntity<MesaResponseDTO> ocupar(@PathVariable Long id) {
        return ResponseEntity.ok(service.ocuparMesa(id));
    }

    @PostMapping("/mesas/{id}/liberar")
    public ResponseEntity<MesaResponseDTO> liberar(@PathVariable Long id) {
        return ResponseEntity.ok(service.liberarMesa(id));
    }
}

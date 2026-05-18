package com.marfuego.ms_inventario.controller;

import com.marfuego.ms_inventario.dto.IngredienteRequestDTO;
import com.marfuego.ms_inventario.dto.IngredienteResponseDTO;
import com.marfuego.ms_inventario.service.ServiceInventario;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/inventario")
public class ControllerInventario {

    @Autowired
    private ServiceInventario service;

    @GetMapping("/ingredientes")
    public ResponseEntity<List<IngredienteResponseDTO>> listar() {
        return ResponseEntity.ok(service.listarTodos());
    }

    // R2: ingredientes que estan bajo el stock minimo
    @GetMapping("/ingredientes/alertas")
    public ResponseEntity<List<IngredienteResponseDTO>> listarAlertas() {
        return ResponseEntity.ok(service.listarEnAlerta());
    }

    @GetMapping("/ingredientes/{id}")
    public ResponseEntity<IngredienteResponseDTO> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @PostMapping("/ingredientes")
    public ResponseEntity<IngredienteResponseDTO> crear(@Valid @RequestBody IngredienteRequestDTO dto) {
        IngredienteResponseDTO creado = service.crear(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PutMapping("/ingredientes/{id}")
    public ResponseEntity<IngredienteResponseDTO> actualizar(@PathVariable Long id,
                                                             @Valid @RequestBody IngredienteRequestDTO dto) {
        return ResponseEntity.ok(service.actualizar(id, dto));
    }

    @DeleteMapping("/ingredientes/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // R2: descontar stock (lo va a llamar ms-pedidos)
    @PostMapping("/ingredientes/{id}/descontar")
    public ResponseEntity<IngredienteResponseDTO> descontar(@PathVariable Long id,
                                                            @RequestParam Double cantidad) {
        return ResponseEntity.ok(service.descontarStock(id, cantidad));
    }
}

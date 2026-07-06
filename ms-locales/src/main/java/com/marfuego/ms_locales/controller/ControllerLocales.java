package com.marfuego.ms_locales.controller;

import com.marfuego.ms_locales.dto.*;
import com.marfuego.ms_locales.model.Estado;
import com.marfuego.ms_locales.service.ServiceLocales;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST de locales y mesas. Recibe las peticiones de
 * /api/v1/locales y le pasa el trabajo (incluida la regla R3) al ServiceLocales.
 */
@RestController
@RequestMapping("/api/v1/locales")
// @Tag: agrupa y describe los endpoints de este microservicio en Swagger
@Tag(
        name = "Locales y Mesas",
        description = "Gestion de locales y mesas (R3: estado de mesas)")
public class ControllerLocales {

    @Autowired
    private ServiceLocales service;

    // ===== LOCALES =====

    @Operation(
            summary = "Listar locales",
            description = "Obtiene todos los locales registrados")
    @ApiResponse(
            responseCode = "200",
            description = "Consulta exitosa")
    @GetMapping
    public ResponseEntity<List<LocalResponseDTO>> listarLocales() {
        return ResponseEntity.ok(service.listarLocales());
    }


    @Operation(
            summary = "Obtener local",
            description = "Obtiene un local por su ID")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Local encontrado"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Local no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<LocalResponseDTO> obtenerLocal(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerLocal(id));
    }


    @Operation(
            summary = "Crear local",
            description = "Registra un nuevo local")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Local creado"),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos invalidos")
    })
    @PostMapping
    public ResponseEntity<LocalResponseDTO> crearLocal(@Valid @RequestBody LocalRequestDTO dto) {
        LocalResponseDTO creado = service.crearLocal(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }


    @Operation(
            summary = "Actualizar local",
            description = "Actualiza un local existente")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Local actualizado"),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos invalidos"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Local no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<LocalResponseDTO> actualizarLocal(@PathVariable Long id,
                                                            @Valid @RequestBody LocalRequestDTO dto) {
        return ResponseEntity.ok(service.actualizarLocal(id, dto));
    }


    @Operation(
            summary = "Eliminar local",
            description = "Elimina un local por su ID")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Local eliminado"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Local no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarLocal(@PathVariable Long id) {
        service.eliminarLocal(id);
        return ResponseEntity.noContent().build();
    }

    // ===== MESAS =====

    @Operation(
            summary = "Listar mesas",
            description = "Obtiene todas las mesas registradas")
    @ApiResponse(
            responseCode = "200",
            description = "Consulta exitosa")
    @GetMapping("/mesas")
    public ResponseEntity<List<MesaResponseDTO>> listarMesas() {
        return ResponseEntity.ok(service.listarMesas());
    }


    @Operation(
            summary = "Listar mesas por local",
            description = "Obtiene las mesas de un local")
    @ApiResponse(
            responseCode = "200",
            description = "Consulta exitosa")
    @GetMapping("/{localId}/mesas")
    public ResponseEntity<List<MesaResponseDTO>> listarMesasPorLocal(@PathVariable Long localId) {
        return ResponseEntity.ok(service.listarMesasPorLocal(localId));
    }

    // Lista las mesas en un estado (LIBRE, RESERVADA, OCUPADA, LIMPIEZA)
    @Operation(
            summary = "Listar mesas por estado",
            description = "Obtiene las mesas en un estado (LIBRE, RESERVADA, OCUPADA, LIMPIEZA)")
    @ApiResponse(
            responseCode = "200",
            description = "Consulta exitosa")
    @GetMapping("/mesas/estado/{estado}")
    public ResponseEntity<List<MesaResponseDTO>> listarMesasPorEstado(@PathVariable Estado estado) {
        return ResponseEntity.ok(service.listarMesasPorEstado(estado));
    }

    @Operation(
            summary = "Obtener mesa",
            description = "Obtiene una mesa por su ID")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Mesa encontrada"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Mesa no encontrada")
    })
    @GetMapping("/mesas/{id}")
    public ResponseEntity<MesaResponseDTO> obtenerMesa(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerMesa(id));
    }

    @Operation(
            summary = "Crear mesa",
            description = "Registra una nueva mesa")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Mesa creada"),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos invalidos")
    })
    @PostMapping("/mesas")
    public ResponseEntity<MesaResponseDTO> crearMesa(@Valid @RequestBody MesaRequestDTO dto) {
        MesaResponseDTO creada = service.crearMesa(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }

    @Operation(
            summary = "Actualizar mesa",
            description = "Actualiza una mesa existente")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Mesa actualizada"),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos invalidos"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Mesa no encontrada")
    })
    @PutMapping("/mesas/{id}")
    public ResponseEntity<MesaResponseDTO> actualizarMesa(@PathVariable Long id,
                                                          @Valid @RequestBody MesaRequestDTO dto) {
        return ResponseEntity.ok(service.actualizarMesa(id, dto));
    }

    @Operation(
            summary = "Eliminar mesa",
            description = "Elimina una mesa por su ID")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Mesa eliminada"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Mesa no encontrada")
    })
    @DeleteMapping("/mesas/{id}")
    public ResponseEntity<Void> eliminarMesa(@PathVariable Long id) {
        service.eliminarMesa(id);
        return ResponseEntity.noContent().build();
    }


    // ===== R3: cambios de estado =====

    @Operation(
            summary = "Reservar mesa",
            description = "R3: cambia el estado de la mesa a RESERVADA")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Mesa reservada"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Mesa no encontrada")
    })
    @PostMapping("/mesas/{id}/reservar")
    public ResponseEntity<MesaResponseDTO> reservar(@PathVariable Long id) {
        return ResponseEntity.ok(service.reservarMesa(id));
    }

    @Operation(
            summary = "Ocupar mesa",
            description = "R3: cambia el estado de la mesa a OCUPADA")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Mesa ocupada"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Mesa no encontrada")
    })
    @PostMapping("/mesas/{id}/ocupar")
    public ResponseEntity<MesaResponseDTO> ocupar(@PathVariable Long id) {
        return ResponseEntity.ok(service.ocuparMesa(id));
    }

    @Operation(
            summary = "Liberar mesa",
            description = "R3: cambia el estado de la mesa a LIBRE")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Mesa liberada"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Mesa no encontrada")
    })
    @PostMapping("/mesas/{id}/liberar")
    public ResponseEntity<MesaResponseDTO> liberar(@PathVariable Long id) {
        return ResponseEntity.ok(service.liberarMesa(id));
    }
}

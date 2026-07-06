package com.marfuego.ms_inventario.controller;

import com.marfuego.ms_inventario.dto.IngredienteRequestDTO;
import com.marfuego.ms_inventario.dto.IngredienteResponseDTO;
import com.marfuego.ms_inventario.service.ServiceInventario;
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
 * Controlador REST del inventario. Recibe las peticiones HTTP que llegan a
 * /api/v1/inventario y no hace la lógica él mismo, sino que se la pasa al
 * ServiceInventario. Básicamente es la puerta de entrada del microservicio.
 */
@RestController
@RequestMapping("/api/v1/inventario")


@Tag(
        name = "Inventario",
        description = "Operaciones relacionadas con el inventario"
)
public class ControllerInventario {

    @Autowired
    private ServiceInventario service;

    @Operation(
            summary = "Listar inventario",
            description = "Obtiene todas los ingredientes")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Consulta exitosa"
            ),
            @ApiResponse( responseCode = "404",
                    description = "Recurso no encontrado"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor"
            )
    })
    @GetMapping("/ingredientes")
    public ResponseEntity<List<IngredienteResponseDTO>> listar() {
        return ResponseEntity.ok(service.listarTodos());
    }

    // R2: ingredientes que están bajo el stock mínimo
    @Operation(
            summary = "Listar ingredientes en alerta",
            description = "Obtiene todos los ingredientes que esten en stock minimo")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Consulta exitosa"
            ),
            @ApiResponse( responseCode = "404",
                    description = "Recurso no encontrado"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor"
            )
    })
    @GetMapping("/ingredientes/alertas")
    public ResponseEntity<List<IngredienteResponseDTO>> listarAlertas() {
        return ResponseEntity.ok(service.listarEnAlerta());
    }

    @Operation(
            summary = "Listar ingredientes por id",
            description = "Obtiene todos los ingredientes por su id")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Consulta exitosa"
            ),
            @ApiResponse( responseCode = "404",
                    description = "Recurso no encontrado"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor"
            )
    })
    @GetMapping("/ingredientes/{id}")
    public ResponseEntity<IngredienteResponseDTO> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }


    @Operation(
            summary = "Crear ingrediente",
            description = "Crea un ingrediente")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Consulta exitosa"
            ),
            @ApiResponse( responseCode = "404",
                    description = "Recurso no encontrado"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor"
            )
    })
    @PostMapping("/ingredientes")
    public ResponseEntity<IngredienteResponseDTO> crear(@Valid @RequestBody IngredienteRequestDTO dto) {
        IngredienteResponseDTO creado = service.crear(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }
    @Operation(
            summary = "Actualiza un ingrediente",
            description = "Actualiza la información de un ingrediente existente mediante su id")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Consulta exitosa"
            ),
            @ApiResponse( responseCode = "404",
                    description = "Recurso no encontrado"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor"
            )
    })
    @PutMapping("/ingredientes/{id}")
    public ResponseEntity<IngredienteResponseDTO> actualizar(@PathVariable Long id,
                                                             @Valid @RequestBody IngredienteRequestDTO dto) {
        return ResponseEntity.ok(service.actualizar(id, dto));
    }

    @Operation(
            summary = "elimina un ingrediente",
            description = "elimina la información de un ingrediente existente mediante su id")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Consulta exitosa"
            ),
            @ApiResponse( responseCode = "404",
                    description = "Recurso no encontrado"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor"
            )
    })
    @DeleteMapping("/ingredientes/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // R2: descontar stock (lo va a llamar ms-pedidos)
    @Operation(
            summary = "descontar stock de un ingrediente un ingrediente",
            description = "Reduce la cantidad disponible de un ingrediente")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Consulta exitosa"
            ),
            @ApiResponse( responseCode = "404",
                    description = "Recurso no encontrado"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor"
            )
    })
    @PostMapping("/ingredientes/{id}/descontar")
    public ResponseEntity<IngredienteResponseDTO> descontar(@PathVariable Long id,
                                                            @RequestParam Double cantidad) {
        return ResponseEntity.ok(service.descontarStock(id, cantidad));
    }
}

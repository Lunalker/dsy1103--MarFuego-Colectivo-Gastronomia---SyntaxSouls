package com.marfuego.ms_inventario.controller;

import com.marfuego.ms_inventario.model.InventarioModel;
import com.marfuego.ms_inventario.service.InventarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/inventario")

@Tag(
        name = "Inventario",
        description = "Operaciones relacionadas con el inventario"
)
public class InventarioController {
    private final InventarioService service;

    public InventarioController(InventarioService service){
        this.service = service;
    }

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
    @GetMapping
    public List<InventarioModel> obtenerTodo(){
        return service.obtenerTodoInventario();
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
    @GetMapping("/{id}")
    public InventarioModel obtenerPorId(@PathVariable Long id){
        return service.obtenerInventarioPorId(id);
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
    @PostMapping
    public InventarioModel crear(@Valid @RequestBody InventarioModel inventarioModel){
        return  service.guardarInventario(inventarioModel);
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
    @PutMapping("/{id}")
    public InventarioModel actualizar(@PathVariable Long id, @Valid @RequestBody InventarioModel inventarioModel){
        InventarioModel i = service.obtenerInventarioPorId(id);
        if (i != null){
            i.setNombre((inventarioModel.getNombre()));
            i.setStock(inventarioModel.getStock());
            return service.guardarInventario(i);
        }
        return null;
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
    @DeleteMapping("/{id}")
    public void borrar(@PathVariable Long id){
        service.borrarInventario(id);
    }
}

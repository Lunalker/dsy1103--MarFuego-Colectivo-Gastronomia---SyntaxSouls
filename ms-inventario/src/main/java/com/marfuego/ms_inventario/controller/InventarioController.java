package com.marfuego.ms_inventario.controller;

import com.marfuego.ms_inventario.model.InventarioModel;
import com.marfuego.ms_inventario.service.InventarioService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/inventario")

public class InventarioController {
    private final InventarioService service;

    public InventarioController(InventarioService service){
        this.service = service;
    }

    @GetMapping
    public List<InventarioModel> obtenerTodo(){
        return service.obtenerTodoInventario();
    }
    @GetMapping("/{id}")
    public InventarioModel obtenerPorId(@PathVariable Long id){
        return service.obtenerInventarioPorId(id);
    }
    @PostMapping
    public InventarioModel crear(@Valid @RequestBody InventarioModel inventarioModel){
        return  service.guardarInventario(inventarioModel);
    }
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
    @DeleteMapping("/{id}")
    public void borrar(@PathVariable Long id){
        service.borrarInventario(id);
    }
}

package com.marfuego.ms_inventario.controller;

import com.marfuego.ms_inventario.model.IngredienteInventario;
import com.marfuego.ms_inventario.service.ServiceInventario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v2/inventario")
public class ControllerInventario {
    @Autowired
    private ServiceInventario service;

    @GetMapping
    public List<IngredienteInventario> listar() {
        return service.listarTodo();
    }

    @PostMapping
    public IngredienteInventario crear(@RequestBody IngredienteInventario item) {
        return service.guardar(item);
    }
}
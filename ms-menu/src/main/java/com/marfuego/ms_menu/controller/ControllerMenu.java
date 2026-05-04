package com.marfuego.ms_menu.controller;

import com.marfuego.ms_menu.model.PlatoMenu;
import com.marfuego.ms_menu.service.ServiceMenu;
import com.marfuego.ms_menu.repository.RepositoryMenu;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/gastronomia")
public class ControllerMenu {

    @Autowired
    private ServiceMenu service;

    @Autowired
    private RepositoryMenu repository;

    @GetMapping("/platos")
    public List<PlatoMenu> listar() {
        return repository.findAll();
    }

    @PostMapping("/platos")
    public ResponseEntity<PlatoMenu> crear(@Valid @RequestBody PlatoMenu plato) {
        // Se devuelve un estado 201 al crear un nuevo plato
        PlatoMenu nuevoPlato = service.procesarReglasPlato(plato);
        return ResponseEntity.status(201).body(nuevoPlato);
    }
}
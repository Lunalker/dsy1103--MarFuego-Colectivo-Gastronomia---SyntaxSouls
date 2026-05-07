package com.Gastronomia.MarFuego.controller;

import com.Gastronomia.MarFuego.model.PlatoMenu;
import com.Gastronomia.MarFuego.service.ServiceMenu;
import com.Gastronomia.MarFuego.repository.RepositoryMenu;
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
        return ResponseEntity.ok(service.procesarReglasPlato(plato));
    }
}


package com.Gastronomia.MarFuego.controller;

import com.Gastronomia.MarFuego.model.PlatoGastronomia;
import com.Gastronomia.MarFuego.service.ServiceGastronomia;
import com.Gastronomia.MarFuego.repository.RepositoryGastronomia;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/gastronomia")
public class ControllerGastronomia {

    @Autowired
    private ServiceGastronomia service;

    @Autowired
    private RepositoryGastronomia repository;

    @GetMapping("/platos")
    public List<PlatoGastronomia> listar() {
        return repository.findAll();
    }

    @PostMapping("/platos")
    public ResponseEntity<PlatoGastronomia> crear(@Valid @RequestBody PlatoGastronomia plato) {
        return ResponseEntity.ok(service.procesarReglasPlato(plato));
    }
}


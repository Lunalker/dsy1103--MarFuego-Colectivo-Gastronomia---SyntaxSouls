package com.marfuego.ms_locales.controller;

import com.marfuego.ms_locales.model.LocalLocales;
import com.marfuego.ms_locales.service.ServiceLocales;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/locales")
public class ControllerLocales {

    @Autowired
    private ServiceLocales service;

    @GetMapping
    public List<LocalLocales> listar() {
        return service.listarTodos();
    }

    @PostMapping
    public ResponseEntity<LocalLocales> crear(@Valid @RequestBody LocalLocales local) {
        return ResponseEntity.ok(service.guardar(local));
    }
}
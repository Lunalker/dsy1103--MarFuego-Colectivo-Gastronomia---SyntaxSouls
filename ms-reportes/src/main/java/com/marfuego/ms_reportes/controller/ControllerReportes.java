package com.marfuego.ms_reportes.controller;

import com.marfuego.ms_reportes.dto.reporte.RentabilidadPlatoDTO;
import com.marfuego.ms_reportes.dto.reporte.ResumenLocalDTO;
import com.marfuego.ms_reportes.dto.reporte.StockCriticoDTO;
import com.marfuego.ms_reportes.service.ServiceReportes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Controller del ms-reportes.
// Expone endpoints que consolidan informacion de otros microservicios.
@RestController
@RequestMapping("/api/v1/reportes")
public class ControllerReportes {

    @Autowired
    private ServiceReportes service;

    // Resumen de un local: junta info de 3 MS (locales, menu, pedidos)
    @GetMapping("/resumen-local/{id}")
    public ResponseEntity<ResumenLocalDTO> resumenLocal(@PathVariable Long id) {
        return ResponseEntity.ok(service.resumenDeLocal(id));
    }

    // Lista los ingredientes con stock bajo el minimo (R2)
    @GetMapping("/stock-critico")
    public ResponseEntity<List<StockCriticoDTO>> stockCritico() {
        return ResponseEntity.ok(service.stockCritico());
    }

    // Lista la rentabilidad de cada plato (R5: margen 300%)
    @GetMapping("/rentabilidad-platos")
    public ResponseEntity<List<RentabilidadPlatoDTO>> rentabilidad() {
        return ResponseEntity.ok(service.rentabilidadPlatos());
    }
}

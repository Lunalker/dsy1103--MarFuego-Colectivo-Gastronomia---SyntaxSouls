package com.marfuego.ms_reportes.controller;

import com.marfuego.ms_reportes.dto.reporte.RentabilidadPlatoDTO;
import com.marfuego.ms_reportes.dto.reporte.ResumenLocalDTO;
import com.marfuego.ms_reportes.dto.reporte.StockCriticoDTO;
import com.marfuego.ms_reportes.service.ServiceReportes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST de reportes. Recibe las peticiones de /api/v1/reportes y le
 * pasa al ServiceReportes el armado de cada reporte.
 */
@RestController
@RequestMapping("/api/v1/reportes")
// @Tag: agrupa y describe los endpoints de este microservicio en Swagger
@Tag(name = "Reportes", description = "Reportes consolidados (consume ms-locales, ms-menu, ms-inventario y ms-pedidos)")
public class ControllerReportes {

    @Autowired
    private ServiceReportes service;

    // Resumen de un local: junta info de 3 MS (locales, menú, pedidos)
    @Operation(
            summary = "Resumen de local",
            description = "Consolida informacion de un local (locales, menu y pedidos)")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Reporte generado"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Local no encontrado")
    })
    @GetMapping("/resumen-local/{id}")
    public ResponseEntity<ResumenLocalDTO> resumenLocal(@PathVariable Long id) {
        return ResponseEntity.ok(service.resumenDeLocal(id));
    }

    // Lista los ingredientes con stock bajo el mínimo (R2)
    @Operation(
            summary = "Stock critico",
            description = "R2: lista los ingredientes con stock bajo el minimo")
    @ApiResponse(
            responseCode = "200",
            description = "Reporte generado")
    @GetMapping("/stock-critico")
    public ResponseEntity<List<StockCriticoDTO>> stockCritico() {
        return ResponseEntity.ok(service.stockCritico());
    }

    // Lista la rentabilidad de cada plato (R5: margen 300%)
    @Operation(
            summary = "Rentabilidad de platos",
            description = "R5: lista la rentabilidad y margen de cada plato")
    @ApiResponse(
            responseCode = "200",
            description = "Reporte generado")
    @GetMapping("/rentabilidad-platos")
    public ResponseEntity<List<RentabilidadPlatoDTO>> rentabilidad() {
        return ResponseEntity.ok(service.rentabilidadPlatos());
    }
}

package com.marfuego.ms_reportes.dto.reporte;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

// Reporte de rentabilidad: muestra el margen de cada plato (R5)
@Data
@Schema(
        name = "RentabilidadPlatoDTO",
        description = "Reporte de rentabilidad y margen de un plato (R5)")
public class RentabilidadPlatoDTO {

    @Schema(
            description = "Id del plato",
            example = "1")
    private Long platoId;

    @Schema(
            description = "Nombre del plato",
            example = "Ceviche mixto")
    private String nombrePlato;

    @Schema(
            description = "Categoria del plato",
            example = "Entradas")
    private String categoria;

    @Schema(
            description = "Precio de venta",
            example = "9990.0")
    private Double precioVenta;

    @Schema(
            description = "Costo de produccion",
            example = "2500.0")
    private Double costoProduccion;

    @Schema(
            description = "Margen absoluto (precioVenta - costoProduccion)",
            example = "7490.0")
    private Double margenAbsoluto;   // precioVenta - costoProduccion

    @Schema(
            description = "Margen porcentual ((precioVenta / costoProduccion) * 100)",
            example = "399.6")
    private Double margenPorcentaje; // (precioVenta / costoProduccion) * 100

    @Schema(
            description = "Indica si cumple el margen minimo de 300% (R5)",
            example = "true")
    private Boolean cumpleMargen300; // R5
}

package com.marfuego.ms_reportes.dto.reporte;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * Reporte de un ingrediente que quedó en stock crítico (bajo el mínimo, regla R2).
 */
@Data
@Schema(
        name = "StockCriticoDTO",
        description = "Reporte de ingredientes con stock bajo el minimo (R2)")
public class StockCriticoDTO {

    @Schema(
            description = "Id del ingrediente",
            example = "1")
    private Long ingredienteId;

    @Schema(
            description = "Nombre del ingrediente",
            example = "Salmon")
    private String nombreIngrediente;

    @Schema(
            description = "Stock actual",
            example = "2.0")
    private Double stockActual;

    @Schema(
            description = "Stock minimo (R2)",
            example = "5.0")
    private Double stockMinimo;

    @Schema(
            description = "Unidad de medida",
            example = "kg")
    private String unidadMedida;

    @Schema(
            description = "Cantidad faltante para alcanzar el stock minimo",
            example = "2.0")
    private Double cantidadFaltante;
}

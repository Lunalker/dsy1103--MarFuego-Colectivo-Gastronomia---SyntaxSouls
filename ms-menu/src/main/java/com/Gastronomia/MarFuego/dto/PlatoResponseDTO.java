package com.Gastronomia.MarFuego.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * DTO que se devuelve al cliente con la info de un plato. Incluye el campo que
 * indica si cumple la regla R5 del margen.
 */
@Data
@Schema(
        name = "PlatoResponseDTO",
        description = "DTO que representa la información de un plato devuelta por la API"
)
public class PlatoResponseDTO {

    @Schema(
            description = "Identificador único del plato",
            example = "1"
    )
    private Long id;

    @Schema(
            description = "Nombre del plato",
            example = "Salmón a la mantequilla"
    )
    private String nombre;

    @Schema(
            description = "Descripción detallada del plato",
            example = "Salmón grillado acompañado de verduras salteadas"
    )
    private String descripcion;

    @Schema(
            description = "Categoría gastronómica",
            example = "Pescados y Mariscos"
    )
    private String categoria;

    @Schema(
            description = "Precio de venta al público",
            example = "15990"
    )
    private Double precioVenta;

    @Schema(
            description = "Costo estimado de producción",
            example = "7500"
    )
    private Double costoProduccion;

    @Schema(
            description = "Indica si el plato está disponible actualmente",
            example = "true"
    )
    private Boolean disponible;

    @Schema(
            description = "Identificador del local al que pertenece el plato",
            example = "1"
    )
    private Long localId;

    @Schema(
            description = "Indica si el plato cumple con el margen mínimo de ganancia definido por el negocio",
            example = "true"
    )
    private Boolean cumpleMargen;
}
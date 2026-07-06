package com.marfuego.ms_inventario.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * DTO que se devuelve al cliente con la info de un ingrediente. Incluye el
 * campo enAlerta, que sale de la regla R2 (si está bajo el mínimo o no).
 */
@Data
@Schema(
        name = "IngredienteResponseDTO",
        description = "DTO que representa la información de un ingrediente devuelta por la API"
)
public class IngredienteResponseDTO {

    @Schema(
            description = "Identificador único del ingrediente",
            example = "1"
    )
    private Long id;

    @Schema(
            description = "Nombre del ingrediente",
            example = "Queso Mozzarella"
    )
    private String nombre;

    @Schema(
            description = "Cantidad disponible actualmente en el inventario",
            example = "15.5"
    )
    private Double stockActual;

    @Schema(
            description = "Cantidad mínima permitida para el ingrediente",
            example = "5.0"
    )
    private Double stockMinimo;

    @Schema(
            description = "Unidad de medida utilizada para controlar el stock",
            example = "kg"
    )
    private String unidadMedida;

    @Schema(
            description = "Indica si el ingrediente se encuentra por debajo del stock mínimo",
            example = "false"
    )
    private Boolean enAlerta; // R2
}
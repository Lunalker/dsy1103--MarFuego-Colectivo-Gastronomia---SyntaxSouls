package com.Gastronomia.MarFuego.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
@Schema(
        name = "PlatoRequestDTO",
        description = "DTO utilizado para registrar o actualizar platos del menú"
)
public class PlatoRequestDTO {

    @Schema(
            description = "Nombre del plato",
            example = "Salmón a la mantequilla",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 120, message = "El nombre debe tener entre 2 y 120 caracteres")
    private String nombre;

    @Schema(
            description = "Descripción detallada del plato",
            example = "Salmón grillado acompañado de verduras salteadas"
    )
    @Size(max = 500, message = "La descripcion no puede superar 500 caracteres")
    private String descripcion;

    @Schema(
            description = "Categoría gastronómica del plato",
            example = "Pescados y Mariscos",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotBlank(message = "La categoria es obligatoria")
    private String categoria;

    @Schema(
            description = "Precio de venta al cliente",
            example = "15990",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "El precio de venta es obligatorio")
    @Positive(message = "El precio de venta debe ser mayor a 0")
    private Double precioVenta;

    @Schema(
            description = "Costo estimado de producción",
            example = "7500",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "El costo de produccion es obligatorio")
    @PositiveOrZero(message = "El costo no puede ser negativo")
    private Double costoProduccion;

    @Schema(
            description = "Indica si el plato está disponible para la venta",
            example = "true",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "Debe indicar si esta disponible")
    private Boolean disponible;

    @Schema(
            description = "Identificador del local propietario del plato",
            example = "1",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "El localId es obligatorio")
    @Positive(message = "El localId debe ser positivo")
    private Long localId;
}
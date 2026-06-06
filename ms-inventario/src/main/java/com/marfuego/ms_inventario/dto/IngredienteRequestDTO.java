package com.marfuego.ms_inventario.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(
        name = "IngredienteRequestDTO",
        description = "DTO utilizado para registrar o actualizar un ingrediente del inventario"
)

public class IngredienteRequestDTO {

    @Schema(
            description = "Nombre del ingrediente",
            example = "Queso Mozzarella",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 120, message = "El nombre debe tener entre 2 y 120 caracteres")
    private String nombre;

    @Schema(
            description = "Cantidad disponible del ingrediente en inventario",
            example = "15.5",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "El stock actual es obligatorio")
    @PositiveOrZero(message = "El stock actual no puede ser negativo")
    private Double stockActual;

    @Schema(
            description = "Cantidad mínima permitida antes de generar una alerta de reposición",
            example = "5.0",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "El stock minimo es obligatorio")
    @PositiveOrZero(message = "El stock minimo no puede ser negativo")
    private Double stockMinimo;

    @Schema(
            description = "Unidad de medida utilizada para el ingrediente",
            example = "kg",
            allowableValues = {"KG, UNIDAD, LITRO"},
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotBlank(message = "La unidad de medida es obligatoria")
    @Size(max = 20)
    private String unidadMedida;
}
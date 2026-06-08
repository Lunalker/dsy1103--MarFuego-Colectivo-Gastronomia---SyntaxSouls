package com.marfuego.ms_locales.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
@Schema(
        name = "MesaRequestDTO",
        description = "Datos para crear o actualizar una mesa")
public class MesaRequestDTO {

    @NotNull(message = "El numero de mesa es obligatorio")
    @Min(value = 1, message = "El numero de mesa debe ser al menos 1")
    @Schema(
            description = "Numero de la mesa",
            example = "5")
    private Integer numeroMesa;

    @NotNull(message = "La capacidad es obligatoria")
    @Min(value = 1, message = "La capacidad debe ser al menos 1")
    @Schema(
            description = "Capacidad de personas",
            example = "4")
    private Integer capacidad;

    @NotNull(message = "El localId es obligatorio")
    @Positive(message = "El localId debe ser positivo")
    @Schema(
            description = "Id del local al que pertenece la mesa",
            example = "1")
    private Long localId;
}

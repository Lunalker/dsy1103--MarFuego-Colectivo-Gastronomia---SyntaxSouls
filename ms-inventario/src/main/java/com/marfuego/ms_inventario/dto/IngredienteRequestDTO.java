package com.marfuego.ms_inventario.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class IngredienteRequestDTO {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 120, message = "El nombre debe tener entre 2 y 120 caracteres")
    private String nombre;

    @NotNull(message = "El stock actual es obligatorio")
    @PositiveOrZero(message = "El stock actual no puede ser negativo")
    private Double stockActual;

    @NotNull(message = "El stock minimo es obligatorio")
    @PositiveOrZero(message = "El stock minimo no puede ser negativo")
    private Double stockMinimo;

    @NotBlank(message = "La unidad de medida es obligatoria")
    @Size(max = 20)
    private String unidadMedida;
}

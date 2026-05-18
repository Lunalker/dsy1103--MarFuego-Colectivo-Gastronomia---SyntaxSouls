package com.marfuego.ms_locales.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class MesaRequestDTO {

    @NotNull(message = "El numero de mesa es obligatorio")
    @Min(value = 1, message = "El numero de mesa debe ser al menos 1")
    private Integer numeroMesa;

    @NotNull(message = "La capacidad es obligatoria")
    @Min(value = 1, message = "La capacidad debe ser al menos 1")
    private Integer capacidad;

    @NotNull(message = "El localId es obligatorio")
    @Positive(message = "El localId debe ser positivo")
    private Long localId;
}

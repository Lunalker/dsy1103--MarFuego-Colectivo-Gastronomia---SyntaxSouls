package com.marfuego.ms_pedidos.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class DetallePedidoRequestDTO {

    @NotNull(message = "El platoId es obligatorio")
    @Positive(message = "El platoId debe ser positivo")
    private Long platoId;

    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad debe ser al menos 1")
    private Integer cantidad;

    @NotNull(message = "El precio unitario es obligatorio")
    @Positive(message = "El precio unitario debe ser mayor a 0")
    private Double precioUnitario;
}

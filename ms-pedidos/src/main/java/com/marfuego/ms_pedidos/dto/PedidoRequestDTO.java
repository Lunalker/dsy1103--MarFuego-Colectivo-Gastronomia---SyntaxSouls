package com.marfuego.ms_pedidos.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;

@Data
public class PedidoRequestDTO {

    // mesaId puede ser null si el pedido es DELIVERY
    @Positive(message = "El mesaId debe ser positivo")
    private Long mesaId;

    @NotNull(message = "El localId es obligatorio")
    @Positive(message = "El localId debe ser positivo")
    private Long localId;

    @NotBlank(message = "El tipo es obligatorio (COMEDOR o DELIVERY)")
    @Pattern(regexp = "COMEDOR|DELIVERY", message = "El tipo debe ser COMEDOR o DELIVERY")
    private String tipo;

    @NotEmpty(message = "El pedido debe tener al menos un detalle")
    @Valid
    private List<DetallePedidoRequestDTO> detalles;
}

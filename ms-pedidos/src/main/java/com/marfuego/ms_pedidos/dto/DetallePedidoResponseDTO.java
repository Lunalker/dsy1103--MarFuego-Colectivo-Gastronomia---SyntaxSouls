package com.marfuego.ms_pedidos.dto;

import lombok.Data;

@Data
public class DetallePedidoResponseDTO {
    private Long id;
    private Long platoId;
    private Integer cantidad;
    private Double precioUnitario;
    private Double subtotal; // cantidad * precioUnitario
}

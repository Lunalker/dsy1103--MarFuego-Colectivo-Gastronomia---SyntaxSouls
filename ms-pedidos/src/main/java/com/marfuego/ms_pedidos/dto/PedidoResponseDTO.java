package com.marfuego.ms_pedidos.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Schema(
        name = "PedidoResponseDTO",
        description = "DTO que representa la información de un pedido devuelta por la API"
)
public class PedidoResponseDTO {

    @Schema(
            description = "Identificador único del pedido",
            example = "1"
    )
    private Long id;

    @Schema(
            description = "Identificador de la mesa asociada al pedido. Puede ser nulo para pedidos DELIVERY",
            example = "12"
    )
    private Long mesaId;

    @Schema(
            description = "Identificador del local donde se realizó el pedido",
            example = "3"
    )
    private Long localId;

    @Schema(
            description = "Estado actual del pedido",
            example = "EN_PREPARACION",
            allowableValues = {
                    "PENDIENTE",
                    "EN_PREPARACION",
                    "ENTREGADO",
                    "CANCELADO"
            }
    )
    private String estado;

    @Schema(
            description = "Tipo de pedido",
            example = "COMEDOR",
            allowableValues = {
                    "COMEDOR",
                    "DELIVERY"
            }
    )
    private String tipo;

    @Schema(
            description = "Fecha y hora de creación del pedido",
            example = "2026-06-09T14:30:00"
    )
    private LocalDateTime fecha;

    @Schema(
            description = "Monto total del pedido",
            example = "25980"
    )
    private Double total;

    @Schema(
            description = "Listado de productos incluidos en el pedido"
    )
    private List<DetallePedidoResponseDTO> detalles;
}
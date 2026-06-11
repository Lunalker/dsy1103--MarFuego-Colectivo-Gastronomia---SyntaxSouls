package com.marfuego.ms_pedidos.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(
        name = "DetallePedidoResponseDTO",
        description = "DTO que representa un producto incluido en un pedido"
)
public class DetallePedidoResponseDTO {

    @Schema(
            description = "Identificador único del detalle",
            example = "1"
    )
    private Long id;

    @Schema(
            description = "Identificador del plato solicitado",
            example = "5"
    )
    private Long platoId;

    @Schema(
            description = "Cantidad solicitada",
            example = "2"
    )
    private Integer cantidad;

    @Schema(
            description = "Precio unitario registrado para el plato",
            example = "12990"
    )
    private Double precioUnitario;

    @Schema(
            description = "Subtotal calculado para este detalle (cantidad × precio unitario)",
            example = "25980"
    )
    private Double subtotal;
}
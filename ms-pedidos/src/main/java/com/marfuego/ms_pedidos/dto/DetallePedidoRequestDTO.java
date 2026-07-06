package com.marfuego.ms_pedidos.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

/**
 * DTO que se usa para una línea de detalle que entra en un pedido.
 */
@Data
@Schema(
        name = "DetallePedidoRequestDTO",
        description = "DTO utilizado para registrar un producto dentro de un pedido"
)
public class DetallePedidoRequestDTO {

    @Schema(
            description = "Identificador del plato solicitado",
            example = "5",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "El platoId es obligatorio")
    @Positive(message = "El platoId debe ser positivo")
    private Long platoId;

    @Schema(
            description = "Cantidad solicitada del plato",
            example = "2",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad debe ser al menos 1")
    private Integer cantidad;

    @Schema(
            description = "Precio unitario del plato al momento del pedido",
            example = "12990",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "El precio unitario es obligatorio")
    @Positive(message = "El precio unitario debe ser mayor a 0")
    private Double precioUnitario;
}
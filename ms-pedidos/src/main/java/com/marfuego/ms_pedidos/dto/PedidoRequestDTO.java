package com.marfuego.ms_pedidos.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;

@Data
@Schema(
        name = "PedidoRequestDTO",
        description = "DTO utilizado para registrar un nuevo pedido"
)
public class PedidoRequestDTO {

    @Schema(
            description = "Identificador de la mesa. Puede ser nulo para pedidos tipo DELIVERY",
            example = "12"
    )
    @Positive(message = "El mesaId debe ser positivo")
    private Long mesaId;

    @Schema(
            description = "Identificador del local donde se realiza el pedido",
            example = "3",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "El localId es obligatorio")
    @Positive(message = "El localId debe ser positivo")
    private Long localId;

    @Schema(
            description = "Tipo de pedido",
            example = "COMEDOR",
            allowableValues = {
                    "COMEDOR",
                    "DELIVERY"
            },
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotBlank(message = "El tipo es obligatorio (COMEDOR o DELIVERY)")
    @Pattern(
            regexp = "COMEDOR|DELIVERY",
            message = "El tipo debe ser COMEDOR o DELIVERY"
    )
    private String tipo;

    @Schema(
            description = "Listado de productos incluidos en el pedido",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotEmpty(message = "El pedido debe tener al menos un detalle")
    @Valid
    private List<DetallePedidoRequestDTO> detalles;
}
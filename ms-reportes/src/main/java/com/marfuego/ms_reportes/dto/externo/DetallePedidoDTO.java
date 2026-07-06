package com.marfuego.ms_reportes.dto.externo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * DTO para mapear una línea de detalle de un pedido que llega desde ms-pedidos.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DetallePedidoDTO {
    private Long id;
    private Long platoId;
    private Integer cantidad;
    private Double precioUnitario;
    private Double subtotal;
}

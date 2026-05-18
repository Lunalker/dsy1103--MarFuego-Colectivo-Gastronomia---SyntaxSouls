package com.marfuego.ms_reportes.dto.externo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

// Detalle de un pedido (cada linea del pedido)
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DetallePedidoDTO {
    private Long id;
    private Long platoId;
    private Integer cantidad;
    private Double precioUnitario;
    private Double subtotal;
}

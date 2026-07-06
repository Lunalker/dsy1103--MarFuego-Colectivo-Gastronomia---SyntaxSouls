package com.marfuego.ms_reportes.dto.externo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO para mapear lo que devuelve ms-pedidos de un pedido.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PedidoDTO {
    private Long id;
    private Long mesaId;
    private Long localId;
    private String estado;
    private String tipo;
    private LocalDateTime fecha;
    private Double total;
    private List<DetallePedidoDTO> detalles;
}

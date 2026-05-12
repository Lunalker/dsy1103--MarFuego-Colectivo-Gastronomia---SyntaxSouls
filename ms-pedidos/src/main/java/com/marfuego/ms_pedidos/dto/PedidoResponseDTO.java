package com.marfuego.ms_pedidos.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PedidoResponseDTO {
    private Long id;
    private Long mesaId;
    private Long localId;
    private String estado;
    private String tipo;
    private LocalDateTime fecha;
    private Double total;
    private List<DetallePedidoResponseDTO> detalles;
}

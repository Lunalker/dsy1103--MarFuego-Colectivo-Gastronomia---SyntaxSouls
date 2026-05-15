package com.marfuego.ms_pedidos.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long mesaId;   // Referencia al MS-Locales
    private Long localId;  // Referencia al MS-Locales
    private String estado; // PENDIENTE, ENTREGADO
    private LocalDateTime fecha;
    private Double total;

    @OneToMany(cascade = CascadeType.ALL)
    private List<DetallePedido> detalles;
}
package com.marfuego.ms_pedidos.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetallePedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long platoId; // viene de ms-menu

    private Integer cantidad;

    private Double precio;

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    @JsonBackReference // 🔥 SOLUCIÓN
    private Pedido pedido;
}
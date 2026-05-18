package com.marfuego.ms_pedidos.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pedido")
@Data
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Referencias a otros microservicios (solo guardamos el id)
    @Column(name = "mesa_id")
    private Long mesaId;

    @Column(name = "local_id", nullable = false)
    private Long localId;

    // PENDIENTE, EN_PREPARACION, ENTREGADO, CANCELADO
    @Column(nullable = false, length = 30)
    private String estado;

    // COMEDOR o DELIVERY (R4 aplica para DELIVERY)
    @Column(nullable = false, length = 20)
    private String tipo;

    @Column(nullable = false)
    private LocalDateTime fecha;

    @Column(nullable = false)
    private Double total;

    // Un pedido tiene varios detalles. Cascade ALL para que se guarden / borren juntos.
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<DetallePedido> detalles = new ArrayList<>();
}

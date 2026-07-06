package com.marfuego.ms_pedidos.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidad que representa la cabecera de un pedido y agrupa sus líneas de detalle.
 * Se guarda en la tabla pedido.
 */
@Entity
@Table(name = "pedido")
@Data
@Schema(
        name = "Pedido",
        description = "Representa un pedido realizado por un cliente"
)
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(
            description = "Identificador único del pedido",
            example = "1"
    )
    private Long id;

    @Column(name = "mesa_id")
    @Schema(
            description = "Identificador de la mesa asociada al pedido",
            example = "12"
    )
    private Long mesaId;

    @Column(name = "local_id", nullable = false)
    @Schema(
            description = "Identificador del local donde se realizó el pedido",
            example = "3"
    )
    private Long localId;

    @Column(nullable = false, length = 30)
    @Schema(
            description = "Estado actual del pedido",
            example = "PENDIENTE",
            allowableValues = {
                    "PENDIENTE",
                    "EN_PREPARACION",
                    "ENTREGADO",
                    "CANCELADO"
            }
    )
    private String estado;

    @Column(nullable = false, length = 20)
    @Schema(
            description = "Tipo de atención asociada al pedido",
            example = "COMEDOR",
            allowableValues = {
                    "COMEDOR",
                    "DELIVERY"
            }
    )
    private String tipo;

    @Column(nullable = false)
    @Schema(
            description = "Fecha y hora de creación del pedido",
            example = "2026-06-09T14:30:00"
    )
    private LocalDateTime fecha;

    @Column(nullable = false)
    @Schema(
            description = "Monto total del pedido",
            example = "25980"
    )
    private Double total;

    @OneToMany(mappedBy = "pedido",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @JsonManagedReference
    @Schema(
            description = "Listado de productos incluidos en el pedido"
    )
    private List<DetallePedido> detalles = new ArrayList<>();
}
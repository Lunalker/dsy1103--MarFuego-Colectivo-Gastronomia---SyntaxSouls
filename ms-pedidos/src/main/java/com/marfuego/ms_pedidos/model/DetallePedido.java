package com.marfuego.ms_pedidos.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

/**
 * Entidad que representa una línea de un pedido (un plato, su cantidad y su
 * precio). Se guarda en la tabla detalle_pedido.
 */
@Entity
@Table(name = "detalle_pedido")
@Data
@Schema(
        name = "DetallePedido",
        description = "Representa un producto incluido dentro de un pedido"
)
public class DetallePedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(
            description = "Identificador único del detalle del pedido",
            example = "1"
    )
    private Long id;

    @Column(name = "plato_id", nullable = false)
    @Schema(
            description = "Identificador del plato registrado en el microservicio de menú",
            example = "5"
    )
    private Long platoId;

    @Column(nullable = false)
    @Schema(
            description = "Cantidad solicitada del plato",
            example = "2"
    )
    private Integer cantidad;

    @Column(name = "precio_unitario", nullable = false)
    @Schema(
            description = "Precio unitario del plato al momento de realizar el pedido",
            example = "12990"
    )
    private Double precioUnitario;

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    @JsonBackReference
    @Schema(
            description = "Pedido al que pertenece este detalle"
    )
    private Pedido pedido;
}
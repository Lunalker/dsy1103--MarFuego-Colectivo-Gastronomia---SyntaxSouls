package com.Gastronomia.MarFuego.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "plato_menu")
@Data
@Schema(
        name = "PlatoMenu",
        description = "Representa un plato disponible dentro del menú gastronómico"
)
public class PlatoMenu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(
            description = "Identificador único del plato",
            example = "1"
    )
    private Long id;

    @Column(nullable = false, length = 120)
    @Schema(
            description = "Nombre del plato",
            example = "Salmón a la mantequilla"
    )
    private String nombre;

    @Column(length = 500)
    @Schema(
            description = "Descripción detallada del plato",
            example = "Salmón grillado acompañado de verduras salteadas y salsa de mantequilla"
    )
    private String descripcion;

    @Column(length = 60)
    @Schema(
            description = "Categoría a la que pertenece el plato",
            example = "Pescados y Mariscos"
    )
    private String categoria;

    @Column(name = "precio_venta", nullable = false)
    @Schema(
            description = "Precio de venta al cliente",
            example = "15990"
    )
    private Double precioVenta;

    @Column(name = "costo_produccion", nullable = false)
    @Schema(
            description = "Costo estimado de producción del plato",
            example = "7500"
    )
    private Double costoProduccion;

    // R1: indica si el plato está disponible hoy en el local
    @Column(nullable = false)
    @Schema(
            description = "Indica si el plato se encuentra disponible para su venta",
            example = "true"
    )
    private Boolean disponible;

    @Column(name = "local_id", nullable = false)
    @Schema(
            description = "Identificador del local al que pertenece el plato",
            example = "3"
    )
    private Long localId;
}
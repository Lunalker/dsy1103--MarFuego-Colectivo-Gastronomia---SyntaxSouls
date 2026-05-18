package com.Gastronomia.MarFuego.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "plato_menu")
@Data
public class PlatoMenu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String nombre;

    @Column(length = 500)
    private String descripcion;

    @Column(length = 60)
    private String categoria;

    @Column(name = "precio_venta", nullable = false)
    private Double precioVenta;

    @Column(name = "costo_produccion", nullable = false)
    private Double costoProduccion;

    // R1: indica si el plato esta disponible hoy en el local
    @Column(nullable = false)
    private Boolean disponible;

    @Column(name = "local_id", nullable = false)
    private Long localId;
}

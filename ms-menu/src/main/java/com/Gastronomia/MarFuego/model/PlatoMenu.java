package com.Gastronomia.MarFuego.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

@Entity
@Table(name = "plato_menu")
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

    // Constructor vacío requerido por JPA
    public PlatoMenu() {
    }

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Double getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(Double precioVenta) {
        this.precioVenta = precioVenta;
    }

    public Double getCostoProduccion() {
        return costoProduccion;
    }

    public void setCostoProduccion(Double costoProduccion) {
        this.costoProduccion = costoProduccion;
    }

    public Boolean getDisponible() {
        return disponible;
    }

    public void setDisponible(Boolean disponible) {
        this.disponible = disponible;
    }

    public Long getLocalId() {
        return localId;
    }

    public void setLocalId(Long localId) {
        this.localId = localId;
    }
}
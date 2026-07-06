package com.Gastronomia.MarFuego.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(
        name = "PlatoResponseDTO",
        description = "DTO que representa la información de un plato devuelta por la API"
)
public class PlatoResponseDTO {

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

    public Boolean getCumpleMargen() {
        return cumpleMargen;
    }

    public void setCumpleMargen(Boolean cumpleMargen) {
        this.cumpleMargen = cumpleMargen;
    }

    @Schema(
            description = "Identificador único del plato",
            example = "1"
    )
    private Long id;

    @Schema(
            description = "Nombre del plato",
            example = "Salmón a la mantequilla"
    )
    private String nombre;

    @Schema(
            description = "Descripción detallada del plato",
            example = "Salmón grillado acompañado de verduras salteadas"
    )
    private String descripcion;

    @Schema(
            description = "Categoría gastronómica",
            example = "Pescados y Mariscos"
    )
    private String categoria;

    @Schema(
            description = "Precio de venta al público",
            example = "15990"
    )
    private Double precioVenta;

    @Schema(
            description = "Costo estimado de producción",
            example = "7500"
    )
    private Double costoProduccion;

    @Schema(
            description = "Indica si el plato está disponible actualmente",
            example = "true"
    )
    private Boolean disponible;

    @Schema(
            description = "Identificador del local al que pertenece el plato",
            example = "1"
    )
    private Long localId;

    @Schema(
            description = "Indica si el plato cumple con el margen mínimo de ganancia definido por el negocio",
            example = "true"
    )
    private Boolean cumpleMargen;
}
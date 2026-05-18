package com.Gastronomia.MarFuego.dto;

import lombok.Data;

@Data
public class PlatoResponseDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private String categoria;
    private Double precioVenta;
    private Double costoProduccion;
    private Boolean disponible;
    private Long localId;
    private Boolean cumpleMargen; // R5
}

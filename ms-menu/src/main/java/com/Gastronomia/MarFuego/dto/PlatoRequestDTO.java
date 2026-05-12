package com.Gastronomia.MarFuego.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class PlatoRequestDTO {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 120, message = "El nombre debe tener entre 2 y 120 caracteres")
    private String nombre;

    @Size(max = 500, message = "La descripcion no puede superar 500 caracteres")
    private String descripcion;

    @NotBlank(message = "La categoria es obligatoria")
    private String categoria;

    @NotNull(message = "El precio de venta es obligatorio")
    @Positive(message = "El precio de venta debe ser mayor a 0")
    private Double precioVenta;

    @NotNull(message = "El costo de produccion es obligatorio")
    @PositiveOrZero(message = "El costo no puede ser negativo")
    private Double costoProduccion;

    @NotNull(message = "Debe indicar si esta disponible")
    private Boolean disponible;

    @NotNull(message = "El localId es obligatorio")
    @Positive(message = "El localId debe ser positivo")
    private Long localId;
}

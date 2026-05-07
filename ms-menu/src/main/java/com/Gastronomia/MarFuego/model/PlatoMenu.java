package com.Gastronomia.MarFuego.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

@Entity
@Data

public class PlatoMenu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @Min(value = 0, message = "El precio no puede ser negativo")
    private Double precioVenta;

    private Double costoProduccion;

    private Boolean disponible; // R1: Disponibilidad en tiempo real

    // R5: Lógica de margen mínimo
    public boolean cumpleMargenMinimo() {
        return this.precioVenta >= (this.costoProduccion * 3);
    }
}

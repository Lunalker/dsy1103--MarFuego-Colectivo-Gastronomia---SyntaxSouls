package com.Gastronomia.MarFuego.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data // Lombok genera los getters y setters automáticamente
public class PlatoMenu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private Double precioVenta;
    private Double costoProduccion;
    private Boolean disponible;

    // ESTE ES EL MÉTODO QUE FALTA O TIENE ERROR DE NOMBRE
    public boolean cumpleMargenMinimo() {
        if (this.costoProduccion == null || this.precioVenta == null) {
            return false;
        }
        return this.precioVenta >= (this.costoProduccion * 3);
    }
}

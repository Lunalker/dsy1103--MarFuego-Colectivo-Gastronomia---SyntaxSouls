package com.Gastronomia.MarFuego.model;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
class IngredienteMenu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private Double stockActual;
    private Double stockMinimo; // R2: Para alertas de reabastecimiento
}
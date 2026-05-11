package com.marfuego.ms_inventario.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class IngredienteInventario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre; // Ej: "Cordero", "Papa Chilota"
    private Double stockActual;
    private Double stockMinimo;
    private String unidadMedida; // Ej: "Kg", "Unidades"
}
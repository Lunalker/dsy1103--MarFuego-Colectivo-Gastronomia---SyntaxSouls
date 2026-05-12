package com.marfuego.ms_inventario.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "ingrediente_inventario")
@Data
public class IngredienteInventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String nombre;

    @Column(name = "stock_actual", nullable = false)
    private Double stockActual;

    // R2: si el stockActual baja de stockMinimo se considera "en alerta"
    @Column(name = "stock_minimo", nullable = false)
    private Double stockMinimo;

    @Column(name = "unidad_medida", nullable = false, length = 20)
    private String unidadMedida; // KG, UNIDAD, LITRO
}

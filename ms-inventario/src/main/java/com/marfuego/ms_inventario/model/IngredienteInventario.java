package com.marfuego.ms_inventario.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "ingrediente_inventario")
@Data

@Schema(
        name = "Ingrediente",
        description = "Representa los ingredientes del inventario"
)
public class IngredienteInventario {


    @Schema(
            description = "Identificador único",
            example = "1"
    )
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(
            description = "Nombre del ingrediente",
            example = "Mantequilla"
    )
    @Column(nullable = false, length = 120)
    private String nombre;

    @Schema(
            description = "Stock actual del ingrediente",
            example = "50.0"
    )
    @Column(name = "stock_actual", nullable = false)
    private Double stockActual;

    // R2: si el stockActual baja de stockMinimo se considera "en alerta"
    @Schema(
            description = "Stock minimo permitido del ingrediente",
            example = "10.0"
    )
    @Column(name = "stock_minimo", nullable = false)
    private Double stockMinimo;

    @Schema(
            description = "Unidad de medida del ingrediente",
            example = "KG"
    )
    @Column(name = "unidad_medida", nullable = false, length = 20)
    private String unidadMedida; // KG, UNIDAD, LITRO
}

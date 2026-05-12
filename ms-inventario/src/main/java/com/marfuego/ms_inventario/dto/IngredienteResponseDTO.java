package com.marfuego.ms_inventario.dto;

import lombok.Data;

@Data
public class IngredienteResponseDTO {
    private Long id;
    private String nombre;
    private Double stockActual;
    private Double stockMinimo;
    private String unidadMedida;
    private Boolean enAlerta; // R2
}

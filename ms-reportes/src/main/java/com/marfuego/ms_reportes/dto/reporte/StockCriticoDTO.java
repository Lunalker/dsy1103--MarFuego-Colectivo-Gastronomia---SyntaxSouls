package com.marfuego.ms_reportes.dto.reporte;

import lombok.Data;

// Reporte de stock critico: ingredientes que estan bajo el minimo (R2)
@Data
public class StockCriticoDTO {
    private Long ingredienteId;
    private String nombreIngrediente;
    private Double stockActual;
    private Double stockMinimo;
    private String unidadMedida;
    private Double cantidadFaltante;
}

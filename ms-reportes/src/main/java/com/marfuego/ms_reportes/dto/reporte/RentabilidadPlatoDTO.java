package com.marfuego.ms_reportes.dto.reporte;

import lombok.Data;

// Reporte de rentabilidad: muestra el margen de cada plato (R5)
@Data
public class RentabilidadPlatoDTO {
    private Long platoId;
    private String nombrePlato;
    private String categoria;
    private Double precioVenta;
    private Double costoProduccion;
    private Double margenAbsoluto;   // precioVenta - costoProduccion
    private Double margenPorcentaje; // (precioVenta / costoProduccion) * 100
    private Boolean cumpleMargen300; // R5
}

package com.marfuego.ms_reportes.dto.externo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * DTO para mapear lo que devuelve ms-menu de un plato.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlatoDTO {
    private Long id;
    private String nombre;
    private String categoria;
    private Double precioVenta;
    private Double costoProduccion;
    private Boolean disponible;
    private Long localId;
    private Boolean cumpleMargen;
}

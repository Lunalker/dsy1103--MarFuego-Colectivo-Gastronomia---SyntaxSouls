package com.marfuego.ms_reportes.dto.externo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

// Mapea la respuesta de ms-inventario para un ingrediente
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class IngredienteDTO {
    private Long id;
    private String nombre;
    private Double stockActual;
    private Double stockMinimo;
    private String unidadMedida;
    private Boolean enAlerta;
}

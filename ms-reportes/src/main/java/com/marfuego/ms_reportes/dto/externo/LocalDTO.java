package com.marfuego.ms_reportes.dto.externo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * DTO para mapear lo que devuelve ms-locales de un local. Con @JsonIgnoreProperties ignora campos de más que no se usan.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LocalDTO {
    private Long id;
    private String nombre;
    private String direccion;
    private String ubicacion;
}

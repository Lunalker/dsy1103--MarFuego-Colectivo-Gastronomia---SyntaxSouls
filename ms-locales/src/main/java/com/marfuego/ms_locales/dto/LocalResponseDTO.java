package com.marfuego.ms_locales.dto;

import com.marfuego.ms_locales.model.Ubicacion;
import lombok.Data;

@Data
public class LocalResponseDTO {
    private Long id;
    private String nombre;
    private String direccion;
    private Ubicacion ubicacion;
}

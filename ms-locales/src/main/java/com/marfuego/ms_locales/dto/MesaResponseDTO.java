package com.marfuego.ms_locales.dto;

import com.marfuego.ms_locales.model.Estado;
import lombok.Data;

@Data
public class MesaResponseDTO {
    private Long id;
    private Integer numeroMesa;
    private Integer capacidad;
    private Estado estado;
    private Long localId;
}

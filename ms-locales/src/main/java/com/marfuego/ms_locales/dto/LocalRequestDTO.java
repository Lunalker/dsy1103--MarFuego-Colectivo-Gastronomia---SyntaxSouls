package com.marfuego.ms_locales.dto;

import com.marfuego.ms_locales.model.Ubicacion;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LocalRequestDTO {

    @NotBlank(message = "El nombre del local es obligatorio")
    @Size(min = 2, max = 120)
    private String nombre;

    @NotBlank(message = "La direccion es obligatoria")
    @Size(max = 200)
    private String direccion;

    @NotNull(message = "La ubicacion es obligatoria (CENTRO_PUERTO, PELLUCO, ANCUD, CASTRO)")
    private Ubicacion ubicacion;
}

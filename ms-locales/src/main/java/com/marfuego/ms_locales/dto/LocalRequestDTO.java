package com.marfuego.ms_locales.dto;

import com.marfuego.ms_locales.model.Ubicacion;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(
        name = "LocalRequestDTO",
        description = "Datos para crear o actualizar un local")
public class LocalRequestDTO {

    @NotBlank(message = "El nombre del local es obligatorio")
    @Size(min = 2, max = 120)
    @Schema(
            description = "Nombre del local",
            example = "MarFuego Pelluco")
    private String nombre;

    @NotBlank(message = "La direccion es obligatoria")
    @Size(max = 200)
    @Schema(
            description = "Direccion del local",
            example = "Av. Costanera 123")
    private String direccion;

    @NotNull(message = "La ubicacion es obligatoria (CENTRO_PUERTO, PELLUCO, ANCUD, CASTRO)")
    @Schema(
            description = "Ubicacion del local",
            example = "PELLUCO")
    private Ubicacion ubicacion;
}

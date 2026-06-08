package com.marfuego.ms_locales.dto;

import com.marfuego.ms_locales.model.Ubicacion;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(
        name = "LocalResponseDTO",
        description = "Representa un local del sistema")
public class LocalResponseDTO {

    @Schema(
            description = "Identificador unico",
            example = "1")
    private Long id;

    @Schema(
            description = "Nombre del local",
            example = "MarFuego Pelluco")
    private String nombre;

    @Schema(
            description = "Direccion del local",
            example = "Av. Costanera 123")
    private String direccion;

    @Schema(
            description = "Ubicacion del local",
            example = "PELLUCO")
    private Ubicacion ubicacion;
}

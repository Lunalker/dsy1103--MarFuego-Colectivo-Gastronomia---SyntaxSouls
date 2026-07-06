package com.marfuego.ms_locales.dto;

import com.marfuego.ms_locales.model.Estado;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * DTO que se devuelve con la info de una mesa, incluido su estado (R3).
 */
@Data
@Schema(
        name = "MesaResponseDTO",
        description = "Representa una mesa de un local")
public class MesaResponseDTO {

    @Schema(
            description = "Identificador unico",
            example = "1")
    private Long id;

    @Schema(
            description = "Numero de la mesa",
            example = "5")
    private Integer numeroMesa;

    @Schema(
            description = "Capacidad de personas",
            example = "4")
    private Integer capacidad;

    @Schema(
            description = "Estado actual de la mesa (R3)",
            example = "LIBRE")
    private Estado estado;

    @Schema(
            description = "Id del local al que pertenece la mesa",
            example = "1")
    private Long localId;
}

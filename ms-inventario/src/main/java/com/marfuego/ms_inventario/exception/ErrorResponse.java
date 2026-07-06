package com.marfuego.ms_inventario.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Es el formato estándar que tienen todas las respuestas de error de la API.
 * Trae la hora, el código, el mensaje, la ruta y el detalle de qué falló.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
    private List<String> details;
}

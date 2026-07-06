package com.marfuego.ms_reportes.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

/**
 * Maneja en un solo lugar los errores del microservicio de reportes, incluidos
 * los de comunicación cuando falla algún microservicio que se está consultando.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // 503: error al llamar a otro microservicio
    @ExceptionHandler(ComunicacionException.class)
    public ResponseEntity<ErrorResponse> handleComunicacion(ComunicacionException ex,
                                                            HttpServletRequest request) {
        log.error("Error de comunicacion entre microservicios: {}", ex.getMessage());

        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(), 503, "Servicio no disponible",
                ex.getMessage(), request.getRequestURI(), null
        );
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(error);
    }

    // 500: cualquier otro error inesperado
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenerico(Exception ex, HttpServletRequest request) {
        log.error("Error inesperado: {}", ex.getMessage(), ex);

        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(), 500, "Error interno",
                ex.getMessage(), request.getRequestURI(), null
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}

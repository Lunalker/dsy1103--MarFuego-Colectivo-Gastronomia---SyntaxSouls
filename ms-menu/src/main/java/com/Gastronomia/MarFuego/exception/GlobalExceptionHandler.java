package com.Gastronomia.MarFuego.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Maneja en un solo lugar los errores del microservicio de menú y los convierte
 * en respuestas HTTP ordenadas con el código que corresponde.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // 400: datos inválidos (Bean Validation)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidacion(MethodArgumentNotValidException ex,
                                                          HttpServletRequest request) {
        List<String> detalles = new ArrayList<>();
        for (var fieldError : ex.getBindingResult().getFieldErrors()) {
            detalles.add(fieldError.getField() + ": " + fieldError.getDefaultMessage());
        }
        log.warn("Validacion fallida en {}", request.getRequestURI());

        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(), 400, "Validacion fallida",
                "Los datos enviados no son validos", request.getRequestURI(), detalles
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    // 404: no se encontró el recurso
    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ResponseEntity<ErrorResponse> handleNoEncontrado(RecursoNoEncontradoException ex,
                                                            HttpServletRequest request) {
        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(), 404, "Recurso no encontrado",
                ex.getMessage(), request.getRequestURI(), null
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    // 422: regla de negocio violada
    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<ErrorResponse> handleNegocio(NegocioException ex,
                                                      HttpServletRequest request) {
        log.warn("Regla de negocio violada: {}", ex.getMessage());

        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(), 422, "Regla de negocio violada",
                ex.getMessage(), request.getRequestURI(), null
        );
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(error);
    }

    // 500: cualquier otro error no esperado
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

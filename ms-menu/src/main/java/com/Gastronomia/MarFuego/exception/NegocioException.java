package com.Gastronomia.MarFuego.exception;

/**
 * Se lanza cuando se rompe una regla de negocio del menú. Termina devolviendo
 * un HTTP 422.
 */
public class NegocioException extends RuntimeException {
    public NegocioException(String mensaje) {
        super(mensaje);
    }
}

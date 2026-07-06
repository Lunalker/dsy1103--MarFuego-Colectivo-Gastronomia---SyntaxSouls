package com.marfuego.ms_pedidos.exception;

/**
 * Se lanza cuando se rompe una regla de negocio de los pedidos. Termina en un
 * HTTP 422.
 */
public class NegocioException extends RuntimeException {
    public NegocioException(String mensaje) {
        super(mensaje);
    }
}

package com.marfuego.ms_inventario.exception;

/**
 * Se lanza cuando se rompe una regla de negocio, por ejemplo cuando no alcanza
 * el stock al descontar. Termina devolviendo un HTTP 422.
 */
public class NegocioException extends RuntimeException {
    public NegocioException(String mensaje) {
        super(mensaje);
    }
}

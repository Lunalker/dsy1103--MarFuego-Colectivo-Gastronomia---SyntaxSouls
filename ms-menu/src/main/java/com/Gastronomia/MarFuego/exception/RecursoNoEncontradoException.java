package com.Gastronomia.MarFuego.exception;

/**
 * Se lanza cuando se busca un plato que no existe. Termina devolviendo un
 * HTTP 404.
 */
public class RecursoNoEncontradoException extends RuntimeException {
    public RecursoNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}

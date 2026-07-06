package com.marfuego.ms_locales.exception;

/**
 * Se lanza cuando se busca un local o una mesa que no existe. Termina en un
 * HTTP 404.
 */
public class RecursoNoEncontradoException extends RuntimeException {
    public RecursoNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}

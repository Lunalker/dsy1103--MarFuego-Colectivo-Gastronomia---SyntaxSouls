package com.Gastronomia.MarFuego.exception;

// Se lanza cuando no se encuentra el recurso. Se traduce a HTTP 404.
public class RecursoNoEncontradoException extends RuntimeException {
    public RecursoNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}

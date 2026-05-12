package com.Gastronomia.MarFuego.exception;

// Se lanza cuando se viola una regla del negocio. Se traduce a HTTP 422.
public class NegocioException extends RuntimeException {
    public NegocioException(String mensaje) {
        super(mensaje);
    }
}

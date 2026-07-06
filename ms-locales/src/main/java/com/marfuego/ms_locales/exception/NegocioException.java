package com.marfuego.ms_locales.exception;

/**
 * Se lanza cuando se rompe una regla de negocio de locales o mesas, por ejemplo
 * un cambio de estado que la R3 no permite. Termina en un HTTP 422.
 */
public class NegocioException extends RuntimeException {
    public NegocioException(String mensaje) {
        super(mensaje);
    }
}

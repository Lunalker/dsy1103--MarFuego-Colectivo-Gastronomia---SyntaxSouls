package com.marfuego.ms_reportes.exception;

/**
 * Se lanza cuando falla la comunicación con otro microservicio (se cae, tarda
 * demasiado o devuelve un error). Termina en un HTTP 503.
 */
public class ComunicacionException extends RuntimeException {

    public ComunicacionException(String mensaje) {
        super(mensaje);
    }

    public ComunicacionException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}

package com.marfuego.ms_reportes.exception;

// Se lanza cuando falla la comunicacion con otro microservicio
// (por ejemplo: timeout, MS caido, error 500, etc).
// Traducida a HTTP 503 (Service Unavailable).
public class ComunicacionException extends RuntimeException {

    public ComunicacionException(String mensaje) {
        super(mensaje);
    }

    public ComunicacionException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}

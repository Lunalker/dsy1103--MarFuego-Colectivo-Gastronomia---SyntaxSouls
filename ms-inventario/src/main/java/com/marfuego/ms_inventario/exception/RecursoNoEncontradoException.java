package com.marfuego.ms_inventario.exception;

/**
 * Se lanza cuando se busca algo que no existe (por ejemplo un ingrediente con
 * un id que no está). Termina devolviendo un HTTP 404.
 */
public class RecursoNoEncontradoException extends RuntimeException {
    public RecursoNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}

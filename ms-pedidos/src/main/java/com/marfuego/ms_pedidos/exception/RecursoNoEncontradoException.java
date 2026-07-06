package com.marfuego.ms_pedidos.exception;

/**
 * Se lanza cuando se busca un pedido que no existe. Termina en un HTTP 404.
 */
public class RecursoNoEncontradoException extends RuntimeException {
    public RecursoNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}

package com.fiap.pedidos.exceptions.entities;

public class EmailInvalidoException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public EmailInvalidoException() {
        super("E-mail inválido");
    }
}

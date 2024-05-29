package com.fiap.pedidos.exceptions.entities;

public class ClienteInformacoesInvalidasException  extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ClienteInformacoesInvalidasException() {
        super("Informações não conferem, verifique");
    }
}

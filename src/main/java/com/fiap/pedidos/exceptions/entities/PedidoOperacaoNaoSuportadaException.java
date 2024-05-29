package com.fiap.pedidos.exceptions.entities;

public class PedidoOperacaoNaoSuportadaException  extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public PedidoOperacaoNaoSuportadaException(String msg) {
        super(msg);
    }
}

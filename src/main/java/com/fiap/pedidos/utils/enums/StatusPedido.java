package com.fiap.pedidos.utils.enums;

public enum StatusPedido {
    A("Aberto"),
    R("Recebido"),
    E("Em preparação"),
    P("Pronto"),
    F("Finalizado");

    private final String descricao;

    StatusPedido(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}

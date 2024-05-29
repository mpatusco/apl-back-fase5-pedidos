package com.fiap.pedidos.utils.enums;

public enum TipoAtualizacao {

    F("FILA"),
    P("PAGAMENTO"),
    I("ITEM"),
    C("CHECKOUT");

    private final String descricao;

    TipoAtualizacao(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}

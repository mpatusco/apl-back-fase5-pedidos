package com.fiap.pedidos.sqslisteners.request;

import com.fiap.pedidos.utils.enums.StatusPedido;
import com.fiap.pedidos.utils.enums.TipoAtualizacao;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class AtualizaPedidoFila {

    private UUID idPedido;
    private StatusPedido statusPedido;
    private TipoAtualizacao tipoAtualizacao;
}

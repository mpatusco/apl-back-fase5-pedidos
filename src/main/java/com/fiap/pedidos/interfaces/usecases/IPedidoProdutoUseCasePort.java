package com.fiap.pedidos.interfaces.usecases;


import com.fiap.pedidos.entities.Pedido;
import com.fiap.pedidos.entities.PedidoProduto;
import com.fiap.pedidos.entities.Produto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IPedidoProdutoUseCasePort {

    Pedido adicionarItemNoPedido(PedidoProduto pedidoProduto);
    Pedido removerItemDoPedido(PedidoProduto pedidoProduto);
}

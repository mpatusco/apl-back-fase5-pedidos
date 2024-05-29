package com.fiap.pedidos.interfaces.gateways;

import com.fiap.pedidos.entities.Pedido;
import com.fiap.pedidos.entities.PedidoProduto;
import com.fiap.pedidos.entities.Produto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IPedidoProdutoRepositoryPort {

    Optional<PedidoProduto> buscarPorId(UUID id);

    List<Produto> obterTodosOsProdutosAssociadosAoPedidoPeloIdPedido(UUID id);

    PedidoProduto adicionarPedidoProduto(Pedido pedido, Produto produto, PedidoProduto pedidoProduto);
    void excluirPedidoProduto(UUID idPedido, UUID idProduto);
}

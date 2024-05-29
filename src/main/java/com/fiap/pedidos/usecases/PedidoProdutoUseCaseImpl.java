package com.fiap.pedidos.usecases;

import com.fiap.pedidos.entities.Pedido;
import com.fiap.pedidos.entities.PedidoProduto;
import com.fiap.pedidos.entities.Produto;
import com.fiap.pedidos.exceptions.entities.PedidoNaoEncontradoException;
import com.fiap.pedidos.exceptions.entities.PedidoOperacaoNaoSuportadaException;
import com.fiap.pedidos.exceptions.entities.ProdutoNaoEncontradoException;
import com.fiap.pedidos.interfaces.gateways.IPedidoProdutoRepositoryPort;
import com.fiap.pedidos.interfaces.gateways.IPedidoRepositoryPort;
import com.fiap.pedidos.interfaces.gateways.IProdutoRepositoryPort;
import com.fiap.pedidos.interfaces.usecases.IPedidoProdutoUseCasePort;
import com.fiap.pedidos.utils.enums.StatusPedido;
import lombok.RequiredArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class PedidoProdutoUseCaseImpl implements IPedidoProdutoUseCasePort {

    private final IPedidoProdutoRepositoryPort pedidoProdutoRepositoryPort;
    private final IPedidoRepositoryPort pedidoRepositoryPort;
    private final IProdutoRepositoryPort produtoRepositoryPort;

    @Override
    public Pedido adicionarItemNoPedido(PedidoProduto pedidoProduto) {
        Pedido pedido = this.buscarPorId(pedidoProduto.getPedidoId());
        validarPedido(pedido);

        Optional<Produto> optionalProduto = produtoRepositoryPort.buscarPorId(pedidoProduto.getProdutoId());
        validarProduto(optionalProduto);

        Produto produto = optionalProduto.get();

        pedido.setDataAtualizacao(new Date());
        pedido.setValorPedido(
                pedido.getValorPedido()
                        .add(produto.getValorProduto().getValorProduto())
        );

        pedidoProdutoRepositoryPort.adicionarPedidoProduto(pedido, produto, pedidoProduto);

        List<Produto> produtoList = this.pedidoProdutoRepositoryPort.obterTodosOsProdutosAssociadosAoPedidoPeloIdPedido(pedido.getIdPedido());

        pedido = pedidoRepositoryPort.atualizarPedido(pedido);

        pedido.setProdutos(produtoList);

        return pedido;
    }

    @Override
    public Pedido removerItemDoPedido(PedidoProduto pedidoProduto) {
        Pedido pedido = this.buscarPorId(pedidoProduto.getPedidoId());
        validarPedido(pedido);

        Optional<Produto> optionalProduto = produtoRepositoryPort.buscarPorId(pedidoProduto.getProdutoId());
        validarProduto(optionalProduto);

        List<Produto> produtoList = this.pedidoProdutoRepositoryPort
                .obterTodosOsProdutosAssociadosAoPedidoPeloIdPedido(pedido.getIdPedido());

        if(produtoList.isEmpty())
            return pedido;

        pedidoProdutoRepositoryPort.excluirPedidoProduto(pedidoProduto.getPedidoId(), pedidoProduto.getProdutoId());

        Produto produto = optionalProduto.get();

        var novoValorPedido = pedido.getValorPedido()
                .subtract(produto.getValorProduto().getValorProduto());

        pedido.setValorPedido(novoValorPedido);
        pedido.setDataAtualizacao(new Date());

        pedidoRepositoryPort.atualizarPedido(pedido);

        pedido.setProdutos(produtoList);

        return pedido;
    }

    private Pedido buscarPorId(UUID id) {
        var pedidoOptional = pedidoRepositoryPort.buscarPorId(id);

        if (pedidoOptional.isEmpty()) {
            throw new PedidoNaoEncontradoException();
        }

        return pedidoOptional.get();
    }

    private void validarPedido(Pedido optionalPedido) {
        if (optionalPedido.getStatusPedido() != StatusPedido.A) {
            throw new PedidoOperacaoNaoSuportadaException("Pedido não está aberto para edição.");
        }
    }

    private void validarProduto(Optional<Produto> optionalProduto) {
        if (optionalProduto.isEmpty()) {
            throw new ProdutoNaoEncontradoException();
        }
    }
}

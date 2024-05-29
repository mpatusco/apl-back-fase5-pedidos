package com.fiap.pedidos.gateways;

import com.fiap.pedidos.entities.Pedido;
import com.fiap.pedidos.entities.PedidoProduto;
import com.fiap.pedidos.entities.Produto;
import com.fiap.pedidos.exceptions.entities.PedidoNaoEncontradoException;
import com.fiap.pedidos.exceptions.entities.PedidoOperacaoNaoSuportadaException;
import com.fiap.pedidos.gateways.entities.PedidoEntity;
import com.fiap.pedidos.gateways.entities.PedidoProdutoEntity;
import com.fiap.pedidos.gateways.entities.ProdutoEntity;
import com.fiap.pedidos.interfaces.gateways.IPedidoProdutoRepositoryPort;
import com.fiap.pedidos.interfaces.repositories.PedidoProdutoRepository;
import com.fiap.pedidos.interfaces.repositories.PedidoRepository;
import com.fiap.pedidos.interfaces.repositories.ProdutoRepository;
import com.fiap.pedidos.utils.enums.StatusPedido;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PedidoProdutoRepositoryAdapter implements IPedidoProdutoRepositoryPort {

    private final PedidoProdutoRepository pedidoProdutoRepository;
    private final PedidoRepository pedidoRepository;
    private final ProdutoRepository produtoRepository;

    @Override
    @Transactional(readOnly = true)
    public Optional<PedidoProduto> buscarPorId(UUID id) {
        return this.pedidoProdutoRepository.findById(id)
                .map(PedidoProdutoEntity::to);
    }

    @Override
    @Transactional
    public PedidoProduto adicionarPedidoProduto(Pedido pedido, Produto produto, PedidoProduto pedidoProduto) {

        ProdutoEntity existProdutoEntity = new ProdutoEntity().from(produto, false);
        PedidoEntity pedidoEntity = new PedidoEntity().from(pedido, false);

        PedidoProdutoEntity pedidoProdutoEntity = PedidoProdutoEntity.builder()
                .pedido(pedidoEntity)
                .produto(existProdutoEntity)
                .build();

        return PedidoProdutoEntity.to(this.pedidoProdutoRepository.save(pedidoProdutoEntity));
    }

    @Override
    @Transactional
    public void excluirPedidoProduto(UUID idPedido, UUID idProduto) {
        Optional<PedidoEntity> pedidoEntityOptional = pedidoRepository.findById(idPedido);

        if(pedidoEntityOptional.isEmpty()){
            throw new PedidoNaoEncontradoException();
        }

        if(pedidoEntityOptional.get().getStatusPedido() != StatusPedido.A) {
            throw new PedidoOperacaoNaoSuportadaException("Pedido status is not A, id: " + pedidoEntityOptional.get().getIdPedido());
        }

        pedidoProdutoRepository.deleteByIdPedidoAndIdProduto(idPedido, idProduto);
    }


    @Override
    @Transactional(readOnly = true)
    public List<Produto> obterTodosOsProdutosAssociadosAoPedidoPeloIdPedido(UUID idPedido) {
        var produtoEntityList = produtoRepository.findAllProductsByIdPedido(idPedido);

        return produtoEntityList.map(produtoEntities -> produtoEntities
                .stream()
                .map(ProdutoEntity::to)
                .collect(Collectors.toList())).orElseGet(ArrayList::new);

    }
}

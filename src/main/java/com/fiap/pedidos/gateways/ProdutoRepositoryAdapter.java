package com.fiap.pedidos.gateways;

import com.fiap.pedidos.entities.Produto;
import com.fiap.pedidos.exceptions.entities.ProdutoNaoEncontradoException;
import com.fiap.pedidos.gateways.entities.ProdutoEntity;
import com.fiap.pedidos.interfaces.gateways.IProdutoRepositoryPort;
import com.fiap.pedidos.interfaces.repositories.ProdutoRepository;
import com.fiap.pedidos.utils.enums.TipoProduto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProdutoRepositoryAdapter implements IProdutoRepositoryPort {

    private final ProdutoRepository produtoRepository;


    @Override
    @Transactional(readOnly = true)
    public Optional<Produto> buscarPorId(UUID idProduto) {
        return this.produtoRepository.findById(idProduto)
                .map(ProdutoEntity::to);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Produto> listarProdutosPorTipo(TipoProduto tipoProduto) {
        final var produtoList = new ArrayList<Produto>();
        final Optional<List<ProdutoEntity>> produtoEntityList = this.produtoRepository
                .findAllByTipoProdutoAndAtivo(tipoProduto.getCodigo(), true);

        produtoEntityList.ifPresent(
                produtoEntities ->
                        produtoEntities.forEach(
                                produtoEntity -> produtoList.add(produtoEntity.to())
                        )
        );

        return produtoList;
    }

    @Override
    @Transactional()
    public Produto criarProduto(Produto produto) {
        ProdutoEntity produtoEntity = new ProdutoEntity().from(produto, true);
        return this.produtoRepository.save(produtoEntity).to();
    }

    @Override
    @Transactional()
    public void deletarProduto(UUID idProduto) {
        Optional<ProdutoEntity> produto = this.produtoRepository.findById(idProduto);
        if (produto.isEmpty())
            throw new ProdutoNaoEncontradoException();
        produto.get().setAtivo(false);
        this.produtoRepository.save(produto.get());
    }
}
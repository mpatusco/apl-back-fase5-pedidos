package com.fiap.pedidos.usecases;

import com.fiap.pedidos.entities.Produto;
import com.fiap.pedidos.interfaces.gateways.IProdutoRepositoryPort;
import com.fiap.pedidos.interfaces.usecases.IProdutoUseCasePort;
import com.fiap.pedidos.utils.enums.TipoProduto;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class ProdutoUseCaseImpl implements IProdutoUseCasePort {

    private final IProdutoRepositoryPort produtoRepositoryPort;

    @Override
    public List<Produto> listarProdutosPorTipoProduto(TipoProduto tipoProduto) {
        return produtoRepositoryPort.listarProdutosPorTipo(tipoProduto);
    }

    @Override
    public Produto criarProduto(Produto produto) {
        return produtoRepositoryPort.criarProduto(produto);
    }

    @Override
    public void deletarProduto(UUID idProduto) {
        produtoRepositoryPort.deletarProduto(idProduto);
    }

    @Override
    public Optional<Produto> buscarPorId(UUID idProduto) {
       return produtoRepositoryPort.buscarPorId(idProduto);
    }
}

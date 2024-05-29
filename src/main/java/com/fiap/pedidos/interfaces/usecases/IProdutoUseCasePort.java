package com.fiap.pedidos.interfaces.usecases;

import com.fiap.pedidos.entities.Produto;
import com.fiap.pedidos.utils.enums.TipoProduto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IProdutoUseCasePort {
    List<Produto> listarProdutosPorTipoProduto(TipoProduto tipoProduto);
    Produto criarProduto(Produto produto);
    void deletarProduto(UUID idProduto);
    Optional<Produto> buscarPorId(UUID idProduto);

}
package com.fiap.pedidos.interfaces.gateways;

import com.fiap.pedidos.entities.Produto;
import com.fiap.pedidos.utils.enums.TipoProduto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IProdutoRepositoryPort {
    List<Produto> listarProdutosPorTipo(TipoProduto tipoProduto);
    Produto criarProduto(Produto produto);
    void deletarProduto(UUID idProduto);
    Optional<Produto> buscarPorId(UUID idProduto);

}

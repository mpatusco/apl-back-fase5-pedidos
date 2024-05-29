package com.fiap.pedidos.interfaces.repositories;

import com.fiap.pedidos.gateways.entities.ProdutoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProdutoRepository extends JpaRepository<ProdutoEntity, UUID> {
    Optional<List<ProdutoEntity>> findAllByTipoProdutoAndAtivo(String tipoProduto, Boolean ativo);

    @Query(value = "SELECT produtos.* FROM produtos " +
            "JOIN pedido_produtos ON produtos.id_produto = pedido_produtos.id_produto " +
            "JOIN pedidos ON pedidos.id = pedido_produtos.id_pedido " +
            "WHERE pedidos.id = ?1", nativeQuery = true)
    Optional<List<ProdutoEntity>> findAllProductsByIdPedido(UUID idPedido);
}

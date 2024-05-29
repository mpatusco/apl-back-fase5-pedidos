package com.fiap.pedidos.interfaces.repositories;

import com.fiap.pedidos.entities.Pedido;
import com.fiap.pedidos.gateways.entities.PedidoProdutoEntity;
import com.fiap.pedidos.gateways.entities.ProdutoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PedidoProdutoRepository extends JpaRepository<PedidoProdutoEntity, UUID> {

    @Query(value = "SELECT * FROM pedido_produtos WHERE pedido.id = ?1",
            nativeQuery = true)
    Optional<List<PedidoProdutoEntity>> findByIdPedido(UUID idPedido);

    @Modifying
    @Query(value = "DELETE FROM pedido_produtos WHERE id IN (SELECT id FROM pedido_produtos " +
                    "WHERE id_pedido = ?1 AND id_produto = ?2 LIMIT 1)", nativeQuery = true)
    void deleteByIdPedidoAndIdProduto(UUID idPedido, UUID idProduto);
}

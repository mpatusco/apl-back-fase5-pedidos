package com.fiap.pedidos.entities;

import org.junit.jupiter.api.Test;
import java.util.UUID;
import static org.assertj.core.api.Assertions.assertThat;

class PedidoProdutoTest {

    @Test
    void testEqualsAndHashCode() {
        UUID id = UUID.randomUUID();
        UUID pedidoId = UUID.randomUUID();
        UUID produtoId = UUID.randomUUID();

        PedidoProduto pedidoProduto1 = new PedidoProduto(id, pedidoId, produtoId);
        PedidoProduto pedidoProduto2 = new PedidoProduto(id, pedidoId, produtoId);

        assertThat(pedidoProduto1).isEqualTo(pedidoProduto2);
        assertThat(pedidoProduto1.hashCode()).isEqualTo(pedidoProduto2.hashCode());
    }

    @Test
    void testInicializarVazio() {
        PedidoProduto pedidoProduto = new PedidoProduto();
        assertThat(pedidoProduto.getPedidoId()).isNull();
        assertThat(pedidoProduto.getProdutoId()).isNull();
        assertThat(pedidoProduto.getId()).isNull();
    }

    @Test
    void testToString() {
        UUID id = UUID.randomUUID();
        UUID pedidoId = UUID.randomUUID();
        UUID produtoId = UUID.randomUUID();

        PedidoProduto pedidoProduto = new PedidoProduto(id, pedidoId, produtoId);

        assertThat(pedidoProduto.toString()).contains(id.toString(), pedidoId.toString(), produtoId.toString());
    }
}

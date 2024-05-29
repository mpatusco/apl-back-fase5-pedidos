package com.fiap.pedidos.gateways.entities;
import com.fiap.pedidos.entities.PedidoProduto;
import org.junit.jupiter.api.Test;
import java.util.UUID;
import static org.assertj.core.api.Assertions.assertThat;

class PedidoProdutoEntityTest {

    @Test
    void testCreatePedidoProdutoEntity() {
        UUID id = UUID.randomUUID();
        PedidoEntity pedidoEntity = new PedidoEntity();
        ProdutoEntity produtoEntity = new ProdutoEntity();

        PedidoProdutoEntity pedidoProdutoEntity = PedidoProdutoEntity.builder()
                .id(id)
                .pedido(pedidoEntity)
                .produto(produtoEntity)
                .build();

        assertThat(pedidoProdutoEntity.getId()).isEqualTo(id);
        assertThat(pedidoProdutoEntity.getPedido()).isEqualTo(pedidoEntity);
        assertThat(pedidoProdutoEntity.getProduto()).isEqualTo(produtoEntity);
    }
    @Test
    void testConstrutorVazio() {
        PedidoProdutoEntity pedidoProdutoEntity = new PedidoProdutoEntity();
        assertThat(pedidoProdutoEntity.getId()).isNull();
        assertThat(pedidoProdutoEntity.getPedido()).isNull();
        assertThat(pedidoProdutoEntity.getProduto()).isNull();

        pedidoProdutoEntity.setProduto(new ProdutoEntity());
        pedidoProdutoEntity.setPedido(new PedidoEntity());

        assertThat(pedidoProdutoEntity.getPedido()).isNotNull();
        assertThat(pedidoProdutoEntity.getProduto()).isNotNull();
    }


    @Test
    void testEqualsAndHashCode() {
        PedidoEntity pedidoEntity = new PedidoEntity();
        ProdutoEntity produtoEntity = new ProdutoEntity();
        var uuid = UUID.randomUUID();

        PedidoProdutoEntity pedidoProdutoEntity = PedidoProdutoEntity.builder()
                .id(uuid)
                .pedido(pedidoEntity)
                .produto(produtoEntity)
                .build();

        PedidoProdutoEntity pedidoProdutoEntity1 = PedidoProdutoEntity.builder()
                .id(uuid)
                .pedido(pedidoEntity)
                .produto(produtoEntity)
                .build();

        assertThat(pedidoProdutoEntity.canEqual(pedidoProdutoEntity1)).isTrue();
        assertThat(pedidoProdutoEntity.toString()).isEqualTo(pedidoProdutoEntity1.toString());
        assertThat(pedidoProdutoEntity.equals(pedidoProdutoEntity1)).isTrue();
        assertThat(pedidoProdutoEntity.hashCode()).isEqualTo(pedidoProdutoEntity1.hashCode());

    }
}

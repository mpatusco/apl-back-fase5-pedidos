package com.fiap.pedidos.gateways.entities;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import com.fiap.pedidos.entities.Cliente;
import com.fiap.pedidos.entities.Pedido;
import com.fiap.pedidos.entities.PedidoProduto;
import com.fiap.pedidos.gateways.entities.ClienteEntity;
import com.fiap.pedidos.gateways.entities.PedidoEntity;
import com.fiap.pedidos.gateways.entities.PedidoProdutoEntity;
import com.fiap.pedidos.utils.enums.StatusPedido;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import static org.assertj.core.api.Assertions.assertThat;

class PedidoEntityTest {

    @Test
    void testCreatePedidoEntity() {
        UUID idPedido = UUID.randomUUID();
        ClienteEntity clienteEntity = new ClienteEntity();
        StatusPedido statusPedido = StatusPedido.A;
        BigDecimal valorPedido = new BigDecimal("100.00");
        Date dataInclusao = new Date();
        Date dataAtualizacao = new Date();
        List<PedidoProdutoEntity> produtos = List.of(new PedidoProdutoEntity());

        PedidoEntity pedidoEntity = PedidoEntity.builder()
                .idPedido(idPedido)
                .cliente(clienteEntity)
                .statusPedido(statusPedido)
                .valorPedido(valorPedido)
                .dataInclusao(dataInclusao)
                .dataAtualizacao(dataAtualizacao)
                .produtos(produtos)
                .build();

        assertThat(pedidoEntity.getIdPedido()).isEqualTo(idPedido);
        assertThat(pedidoEntity.getCliente()).isEqualTo(clienteEntity);
        assertThat(pedidoEntity.getStatusPedido()).isEqualTo(statusPedido);
        assertThat(pedidoEntity.getValorPedido()).isEqualTo(valorPedido);
        assertThat(pedidoEntity.getDataInclusao()).isEqualTo(dataInclusao);
        assertThat(pedidoEntity.getDataAtualizacao()).isEqualTo(dataAtualizacao);
        assertThat(pedidoEntity.getProdutos()).isEqualTo(produtos);
    }

    @Test
    void testEqualsAndHashCode() {
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();

        PedidoEntity pedidoEntity1 = PedidoEntity.builder()
                .idPedido(id1)
                .cliente(new ClienteEntity())
                .statusPedido(StatusPedido.A)
                .valorPedido(new BigDecimal("100.00"))
                .dataInclusao(new Date())
                .dataAtualizacao(new Date())
                .produtos(List.of(new PedidoProdutoEntity()))
                .build();

        PedidoEntity pedidoEntity2 = PedidoEntity.builder()
                .idPedido(id1)
                .cliente(new ClienteEntity())
                .statusPedido(StatusPedido.A)
                .valorPedido(new BigDecimal("100.00"))
                .dataInclusao(new Date())
                .dataAtualizacao(new Date())
                .produtos(List.of(new PedidoProdutoEntity()))
                .build();

        PedidoEntity pedidoEntity3 = PedidoEntity.builder()
                .idPedido(id2)
                .cliente(new ClienteEntity())
                .statusPedido(StatusPedido.A)
                .valorPedido(new BigDecimal("100.00"))
                .dataInclusao(new Date())
                .dataAtualizacao(new Date())
                .produtos(List.of(new PedidoProdutoEntity()))
                .build();

        assertThat(pedidoEntity1.canEqual(pedidoEntity2)).isTrue();
        assertThat(pedidoEntity1).isNotEqualTo(pedidoEntity3);

    }
}

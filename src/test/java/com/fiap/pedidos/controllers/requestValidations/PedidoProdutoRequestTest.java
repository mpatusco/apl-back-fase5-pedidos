package com.fiap.pedidos.controllers.requestValidations;

import com.fiap.pedidos.controllers.requestValidations.PedidoProdutoRequest;
import com.fiap.pedidos.entities.PedidoProduto;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class PedidoProdutoRequestTest {

    @Nested
    class CriarPedidoProdutoRequest {
        @Test
        @Severity(SeverityLevel.CRITICAL)
        @DisplayName("Deve criar um PedidoProduto a partir de um PedidoProdutoRequest válido")
        void deveCriarPedidoProdutoAPartirDePedidoProdutoRequestValido() {
            PedidoProdutoRequest pedidoProdutoRequest = new PedidoProdutoRequest();
            UUID idProduto = UUID.randomUUID();
            pedidoProdutoRequest.setIdProduto(idProduto);
            PedidoProduto pedidoProduto = pedidoProdutoRequest.from(pedidoProdutoRequest, UUID.randomUUID());
            assertThat(pedidoProduto).isNotNull();
            assertThat(pedidoProduto.getPedidoId()).isNotNull();
            assertThat(pedidoProduto.getProdutoId()).isEqualTo(idProduto);
        }

        @Test
        @Severity(SeverityLevel.CRITICAL)
        @DisplayName("Deve criar um PedidoProduto a partir de um PedidoProdutoRequest com idProduto nulo")
        void deveCriarPedidoProdutoAPartirDePedidoProdutoRequestComIdProdutoNulo() {
            PedidoProdutoRequest pedidoProdutoRequest = new PedidoProdutoRequest();
            pedidoProdutoRequest.setIdProduto(null);
            PedidoProduto pedidoProduto = pedidoProdutoRequest.from(pedidoProdutoRequest, UUID.randomUUID());
            assertThat(pedidoProduto).isNotNull();
            assertThat(pedidoProduto.getPedidoId()).isNotNull();
            assertThat(pedidoProduto.getProdutoId()).isNull();
        }
    }
}
package com.fiap.pedidos.controllers.requestValidations;

import com.fiap.pedidos.entities.DescricaoProduto;
import com.fiap.pedidos.entities.NomeProduto;
import com.fiap.pedidos.entities.Produto;
import com.fiap.pedidos.entities.ValorProduto;
import com.fiap.pedidos.exceptions.entities.*;
import com.fiap.pedidos.utils.enums.TipoProduto;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProdutoRequestTest {

    @Nested
    class CriarProdutoRequest {
        @Test
        @Severity(SeverityLevel.CRITICAL)
        @DisplayName("Deve criar um Produto a partir de um ProdutoRequest válido")
        void deveCriarProdutoAPartirDeProdutoRequestValido() {
            ProdutoRequest produtoRequest = new ProdutoRequest();
            produtoRequest.setNome("Nome do Produto");
            produtoRequest.setDescricao("Descrição do Produto");
            produtoRequest.setTipo(TipoProduto.BEBIDA.getCodigo());
            produtoRequest.setValor(BigDecimal.TEN);

            Produto produto = produtoRequest.from(produtoRequest);

            assertThat(produto).isNotNull();
            assertThat(produto.getNomeProduto()).isEqualTo(new NomeProduto("Nome do Produto"));
            assertThat(produto.getDescricaoProduto()).isEqualTo(new DescricaoProduto("Descrição do Produto"));
            assertThat(produto.getTipoProduto()).isEqualTo(TipoProduto.BEBIDA);
            assertThat(produto.getValorProduto()).isEqualTo(new ValorProduto(BigDecimal.TEN));
        }

        @Test
        @Severity(SeverityLevel.NORMAL)
        @DisplayName("Deve falhar ao criar um Produto a partir de um ProdutoRequest com descricacao maior que o permitido")
        void deveFalhar_AoCriarProdutoAPartirDeProdutoRequestComDescricaoInvalida() {
            ProdutoRequest produtoRequest = new ProdutoRequest();
            produtoRequest.setNome("Nome do Produto");
            produtoRequest.setDescricao("Descrição do Produtodlsfjdasklfjaslkdjfalkdjfksaldjflksdajfklsdajfasdflkj" +
                    "asldkfjlaskdjflksajflaksdjfalskjflasdjflksadfjlasdjfalskdfjlasdfjsldkfjaslkfjslkfjdlkasdjfl" +
                    "lsakdf;lsakfas;ldfksad;lfksd;lfkasd;lkfl;sdfksdfsdfsdfsdfsdfsdfd;lfka;lfdskassdfsdfsdfsdfsdf");
            produtoRequest.setTipo(TipoProduto.BEBIDA.getCodigo());
            produtoRequest.setValor(BigDecimal.TEN);

            assertThatThrownBy(() ->  produtoRequest.from(produtoRequest))
                    .isInstanceOf(DescricaoProdutoInvalidoException.class)
                    .hasMessage("Descriçao do produto muito grande, tamanho maximo permitido: 255 caracteres");
        }

        @Test
        @Severity(SeverityLevel.CRITICAL)
        @DisplayName("Deve falhar ao criar um Produto a partir de um ProdutoRequest com valor inválido")
        void deveFalhar_AoCriarProdutoAPartirDeProdutoRequestComValorInvalido() {
            ProdutoRequest produtoRequest = new ProdutoRequest();
            produtoRequest.setNome("Nome do Produto");
            produtoRequest.setDescricao("Descrição do Produto");
            produtoRequest.setTipo(TipoProduto.BEBIDA.getCodigo());
            produtoRequest.setValor(null);

            assertThatThrownBy(() ->  produtoRequest.from(produtoRequest))
                    .isInstanceOf(ValorProdutoInvalidoException.class)
                    .hasMessage("Valor do produto não pode ser nulo");
        }

        @Test
        @Severity(SeverityLevel.CRITICAL)
        @DisplayName("Deve falhar ao criar um Produto a partir de um ProdutoRequest com tipo do produto inválido")
        void deveFalhar_AoCriarProdutoAPartirDeProdutoRequestComTipoProdutoInvalido() {
            ProdutoRequest produtoRequest = new ProdutoRequest();
            produtoRequest.setNome("Nome do Produto");
            produtoRequest.setDescricao("Descrição do Produto");
            produtoRequest.setTipo("7");
            produtoRequest.setValor(BigDecimal.TEN);

            assertThatThrownBy(() ->  produtoRequest.from(produtoRequest))
                    .isInstanceOf(TipoProdutoInexistenteException.class)
                    .hasMessage("Tipo produto 7 não existe");
        }

        @Test
        @Severity(SeverityLevel.CRITICAL)
        @DisplayName("Deve falhar ao criar um Produto a partir de um ProdutoRequest com nome inválido")
        void deveFalhar_AoCriarProdutoAPartirDeProdutoRequestComNomeInvalido() {
            ProdutoRequest produtoRequest = new ProdutoRequest();
            produtoRequest.setNome("Nome do Produtoddfsfsd");
            produtoRequest.setDescricao("Descrição do Produto");
            produtoRequest.setTipo(TipoProduto.BEBIDA.getCodigo());
            produtoRequest.setValor(BigDecimal.TEN);

            assertThatThrownBy(() ->  produtoRequest.from(produtoRequest))
                    .isInstanceOf(NomeProdutoInvalidoException.class)
                    .hasMessage("Nome do produto deve ter entre 4 e 20 caracteres");
        }
    }
}

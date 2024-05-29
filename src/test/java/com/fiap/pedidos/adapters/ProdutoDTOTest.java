package com.fiap.pedidos.adapters;

import com.fiap.pedidos.entities.Produto;
import com.fiap.pedidos.helpers.Helper;
import com.fiap.pedidos.utils.enums.TipoProduto;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class ProdutoDTOTest {

    @Nested
    class ConverterParaProdutoDto {

        @Test
        @Severity(SeverityLevel.CRITICAL)
        @Description("Converter Produto para DTO")
        void converterProdutoParaDto() {
            Produto produto = Helper.gerarProdutoLanche();
            var uuid = UUID.randomUUID();
            ProdutoDTO produtoDTO = ProdutoDTO.from(produto);

            ProdutoDTO produtoDTO1 = ProdutoDTO.from(produto);

            produtoDTO.setNome("nome 1");
            produtoDTO.setId(uuid);
            produtoDTO.setValor(BigDecimal.TEN);
            produtoDTO.setDescricao("Descricao 1");
            produtoDTO.setTipo(TipoProduto.BEBIDA.getCodigo());


            produtoDTO1.setNome("nome 1");
            produtoDTO1.setId(uuid);
            produtoDTO1.setValor(BigDecimal.TEN);
            produtoDTO1.setDescricao("Descricao 1");
            produtoDTO1.setTipo(TipoProduto.BEBIDA.getCodigo());

            String dto = ProdutoDTO.builder()
                    .nome("Cliente B")
                    .valor(BigDecimal.TEN)
                    .descricao("clienteB@example.com").toString();


            assertThat(dto).contains("Cliente B", BigDecimal.TEN.toString(), "clienteB@example.com");
            assertThat(produtoDTO).isNotNull();
            assertThat(produtoDTO.getId()).isEqualTo(produtoDTO1.getId());
            assertThat(produtoDTO.getNome()).isEqualTo(produtoDTO1.getNome());
            assertThat(produtoDTO.getDescricao()).isEqualTo(produtoDTO1.getDescricao());
            assertThat(produtoDTO.getTipo()).isEqualTo(produtoDTO1.getTipo());
            assertThat(produtoDTO.getValor()).isEqualTo(produtoDTO1.getValor());
            assertThat(produtoDTO.toString()).contains("nome 1", "Descricao 1", BigDecimal.TEN.toString());
            assertThat(produtoDTO.equals(produtoDTO1)).isTrue();
            assertThat(produtoDTO.canEqual(produtoDTO1)).isTrue();
            assertThat(produtoDTO.hashCode()).isEqualTo(produtoDTO1.hashCode());
        }

        @Test
        @Severity(SeverityLevel.CRITICAL)
        void testarConstrutoVazio() {
            ProdutoDTO produto = new ProdutoDTO();
            ProdutoDTO produto1 = new ProdutoDTO();

            assertThat(produto).isNotNull();
            assertThat(produto.getDescricao()).isNull();
            assertThat(produto.getNome()).isNull();
            assertThat(produto.getTipo()).isNull();
            assertThat(produto.getValor()).isNull();

            assertThat( produto.equals(produto1)).isTrue();


        }
    }
}
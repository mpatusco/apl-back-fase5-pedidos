package com.fiap.pedidos.entities;

import com.fiap.pedidos.utils.enums.TipoProduto;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class ProdutoTest {

    @Test
    void testEqualsAndHashCode() {
        UUID idProduto = UUID.randomUUID();
        NomeProduto nomeProduto = new NomeProduto("Produto A");
        DescricaoProduto descricaoProduto = new DescricaoProduto("Descrição do Produto A");
        TipoProduto tipoProduto = TipoProduto.BEBIDA;
        ValorProduto valorProduto = new ValorProduto(BigDecimal.TEN);
        Boolean ativo = true;

        Produto produto1 = new Produto(idProduto, nomeProduto, descricaoProduto, tipoProduto, valorProduto, ativo);
        Produto produto2 = new Produto(idProduto, nomeProduto, descricaoProduto, tipoProduto, valorProduto, ativo);

        assertThat(produto1).isEqualTo(produto2);
        assertThat(produto1.hashCode()).isEqualTo(produto2.hashCode());
    }

    @Test
    void testInicializarVazio() {
        Produto produto = new Produto();
        assertThat(produto.getDescricaoProduto()).isNull();
        assertThat(produto.getValorProduto()).isNull();
        assertThat(produto.getNomeProduto()).isNull();
        assertThat(produto.getIdProduto()).isNull();
    }

    @Test
    void testToString() {
        UUID idProduto = UUID.randomUUID();
        NomeProduto nomeProduto = new NomeProduto("Produto B");
        DescricaoProduto descricaoProduto = new DescricaoProduto("Descrição do Produto B");
        TipoProduto tipoProduto = TipoProduto.BEBIDA;
        ValorProduto valorProduto = new ValorProduto(BigDecimal.TEN);
        Boolean ativo = false;

        Produto produto = new Produto(idProduto, nomeProduto, descricaoProduto, tipoProduto, valorProduto, ativo);

        assertThat(produto.toString()).contains(idProduto.toString(), nomeProduto.toString(), descricaoProduto.toString(), tipoProduto.toString(), valorProduto.toString(), ativo.toString());
    }

}

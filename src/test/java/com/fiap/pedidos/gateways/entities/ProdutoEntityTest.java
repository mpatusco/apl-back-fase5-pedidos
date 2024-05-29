package com.fiap.pedidos.gateways.entities;

import com.fiap.pedidos.entities.DescricaoProduto;
import com.fiap.pedidos.entities.NomeProduto;
import com.fiap.pedidos.entities.ValorProduto;
import com.fiap.pedidos.utils.enums.TipoProduto;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class ProdutoEntityTest {

    @Test
    void testCreateProdutoEntity() {
        UUID idProduto = UUID.randomUUID();
        NomeProduto nomeProduto = new NomeProduto("Produto Teste");
        DescricaoProduto descricaoProduto = new DescricaoProduto("Descrição do Produto Teste");
        TipoProduto tipoProduto = TipoProduto.BEBIDA;
        ValorProduto valorProduto = new ValorProduto(new BigDecimal("50.00"));
        Date dataAtualizacao = new Date();
        Date dataCriacao = new Date();
        Boolean ativo = true;

        ProdutoEntity produtoEntity = ProdutoEntity.builder()
                .idProduto(idProduto)
                .nomeProduto(nomeProduto)
                .descricaoProduto(descricaoProduto)
                .tipoProduto(tipoProduto.getCodigo())
                .valorProduto(valorProduto)
                .dataAtualizacao(dataAtualizacao)
                .dataCriacao(dataCriacao)
                .ativo(ativo)
                .build();

        assertThat(produtoEntity.getIdProduto()).isEqualTo(idProduto);
        assertThat(produtoEntity.getNomeProduto()).isEqualTo(nomeProduto);
        assertThat(produtoEntity.getDescricaoProduto()).isEqualTo(descricaoProduto);
        assertThat(produtoEntity.getTipoProduto()).isEqualTo(tipoProduto.getCodigo());
        assertThat(produtoEntity.getValorProduto()).isEqualTo(valorProduto);
        assertThat(produtoEntity.getDataAtualizacao()).isEqualTo(dataAtualizacao);
        assertThat(produtoEntity.getDataCriacao()).isEqualTo(dataCriacao);
        assertThat(produtoEntity.getAtivo()).isEqualTo(ativo);
    }

    @Test
    void testEqualsAndHashCode() {
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();

        ProdutoEntity produtoEntity1 = ProdutoEntity.builder()
                .idProduto(id1)
                .nomeProduto(new NomeProduto("Produto1"))
                .descricaoProduto(new DescricaoProduto("Descrição1"))
                .tipoProduto(TipoProduto.BEBIDA.getCodigo())
                .valorProduto(new ValorProduto(new BigDecimal("50.00")))
                .dataAtualizacao(new Date())
                .dataCriacao(new Date())
                .ativo(true)
                .build();

        ProdutoEntity produtoEntity2 = ProdutoEntity.builder()
                .idProduto(id1)
                .nomeProduto(new NomeProduto("Produto1"))
                .descricaoProduto(new DescricaoProduto("Descrição1"))
                .tipoProduto(TipoProduto.BEBIDA.getCodigo())
                .valorProduto(new ValorProduto(new BigDecimal("50.00")))
                .dataAtualizacao(new Date())
                .dataCriacao(new Date())
                .ativo(true)
                .build();

        ProdutoEntity produtoEntity3 = ProdutoEntity.builder()
                .idProduto(id2)
                .nomeProduto(new NomeProduto("Produto2"))
                .descricaoProduto(new DescricaoProduto("Descrição2"))
                .tipoProduto(TipoProduto.BEBIDA.getCodigo())
                .valorProduto(new ValorProduto(new BigDecimal("100.00")))
                .dataAtualizacao(new Date())
                .dataCriacao(new Date())
                .ativo(false)
                .build();

        assertThat(produtoEntity1).isEqualTo(produtoEntity2);
        assertThat(produtoEntity1.hashCode()).isEqualTo(produtoEntity2.hashCode());

        assertThat(produtoEntity1).isNotEqualTo(produtoEntity3);
        assertThat(produtoEntity1.hashCode()).isNotEqualTo(produtoEntity3.hashCode());
    }
}


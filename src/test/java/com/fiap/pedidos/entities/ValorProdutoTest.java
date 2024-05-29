package com.fiap.pedidos.entities;
import com.fiap.pedidos.exceptions.entities.ValorProdutoInvalidoException;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ValorProdutoTest {

    @Test
    void testEqualsAndHashCode() {
        ValorProduto produto1 = new ValorProduto(new BigDecimal("10.50"));
        ValorProduto produto2 = new ValorProduto(new BigDecimal("10.50"));

        assertThat(produto1).isEqualTo(produto2);
        assertThat(produto1.hashCode()).isEqualTo(produto2.hashCode());
    }

    @Test
    void testToString() {
        ValorProduto produto = new ValorProduto(new BigDecimal("25.75"));
        assertThat(produto.toString()).contains("25.75");
    }

    @Test
    void testInicializarVazio() {
        ValorProduto produto = new ValorProduto();
        assertThat(produto.getValorProduto()).isNull();
    }


    @Test
    void testConstrutorComValorValido() {
        ValorProduto produto = new ValorProduto(new BigDecimal("30.00"));
        assertThat(produto.getValorProduto()).isEqualByComparingTo(new BigDecimal("30.00"));
    }

    @Test
    void testConstrutorComValorNulo() {
        assertThrows(ValorProdutoInvalidoException.class, () -> {
            new ValorProduto(null);
        });
    }
}

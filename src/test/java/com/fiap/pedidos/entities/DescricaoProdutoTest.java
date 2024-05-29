package com.fiap.pedidos.entities;

import com.fiap.pedidos.exceptions.entities.DescricaoProdutoInvalidoException;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DescricaoProdutoTest {

    @Test
    void testEqualsAndHashCode() {
        DescricaoProduto descricaoProduto1 = new DescricaoProduto("Produto A");
        DescricaoProduto descricaoProduto2 = new DescricaoProduto("Produto A");

        assertThat(descricaoProduto1).isEqualTo(descricaoProduto2);
        assertThat(descricaoProduto1.hashCode()).isEqualTo(descricaoProduto2.hashCode());
    }

    @Test
    void testToString() {
        DescricaoProduto descricaoProduto = new DescricaoProduto("Produto B");
        assertThat(descricaoProduto.toString()).contains("Produto B");
    }

    @Test
    void testInicializarVazio() {
        DescricaoProduto descricaoProduto = new DescricaoProduto();
        assertThat(descricaoProduto.getDescricao()).isNull();
    }

    @Test
    void testConstrutorDescricaoValida() {
        DescricaoProduto descricaoProduto = new DescricaoProduto("Produto C");
        assertThat(descricaoProduto.getDescricao()).isEqualTo("Produto C");
    }

    @Test
    void testConstrutorDescricaoInvalida() {
        assertThrows(DescricaoProdutoInvalidoException.class, () -> {
            new DescricaoProduto("Descriçao do produto muito grande, tamanho maximo permitido: 255 caracteres" +
                    "\"Descriçao do produto muito grande, tamanho maximo permitido: 255 caracteres\"" +
                    "\"Descriçao do produto muito grande, tamanho maximo permitido: 255 caracteres\"" +
                    "\"Descriçao do produto muito grande, tamanho maximo permitido: 255 caracteres\"" +
                    "\"Descriçao do produto muito grande, tamanho maximo permitido: 255 caracteres\"" +
                    "\"Descriçao do produto muito grande, tamanho maximo permitido: 255 caracteres\"");
        });
    }
}


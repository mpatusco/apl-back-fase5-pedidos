package com.fiap.pedidos.entities;

import com.fiap.pedidos.exceptions.entities.NomeProdutoInvalidoException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class NomeProdutoTest {

    @Test
    void testEqualsAndHashCode() {
        NomeProduto nomeProduto1 = new NomeProduto("Produto A");
        NomeProduto nomeProduto2 = new NomeProduto("Produto A");

        assertThat(nomeProduto1).isEqualTo(nomeProduto2);
        assertThat(nomeProduto1.hashCode()).isEqualTo(nomeProduto2.hashCode());
    }

    @Test
    void testInicializarVazio() {
        NomeProduto nomeProduto = new NomeProduto();
        assertThat(nomeProduto.getNome()).isNull();
    }

    @Test
    void testToString() {
        NomeProduto nomeProduto = new NomeProduto("Produto B");
        assertThat(nomeProduto.toString()).contains("Produto B");
    }

    @Test
    void testConstrutorComNomeValido() {
        NomeProduto nomeProduto = new NomeProduto("Produto C");
        assertThat(nomeProduto.getNome()).isEqualTo("Produto C");
    }

    @Test
    void testConstrutorComNomeNulo() {
        assertThrows(NomeProdutoInvalidoException.class, () -> {
            new NomeProduto(null);
        });
    }

    @Test
    void testConstrutorComNomeCurto() {
        assertThrows(NomeProdutoInvalidoException.class, () -> {
            new NomeProduto("ABC");
        });
    }

    @Test
    void testConstrutorComNomeLongo() {
        assertThrows(NomeProdutoInvalidoException.class, () -> {
            new NomeProduto("NomeProdutoComMaisDeVinteCaracteres");
        });
    }
}

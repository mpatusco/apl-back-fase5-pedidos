package com.fiap.pedidos.gateways.entities;

import com.fiap.pedidos.entities.Cpf;
import com.fiap.pedidos.entities.Email;
import com.fiap.pedidos.entities.Nome;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class ClienteEntityTest {

    @Test
    void testCreateClienteEntity() {
        UUID id = UUID.randomUUID();
        Nome nome = new Nome("Primeiro 1");
        Cpf cpf = new Cpf("11111111111");
        Email email = new Email("cliente@example.com");

        ClienteEntity clienteEntity = ClienteEntity.builder()
                .id(id)
                .nome(nome)
                .cpf(cpf)
                .email(email)
                .build();

        assertThat(clienteEntity.getId()).isEqualTo(id);
        assertThat(clienteEntity.getNome()).isEqualTo(nome);
        assertThat(clienteEntity.getCpf()).isEqualTo(cpf);
        assertThat(clienteEntity.getEmail()).isEqualTo(email);
    }

    @Test
    void testEqualsAndHashCode() {
        ClienteEntity clienteEntity1 = ClienteEntity.builder()
                .id(UUID.randomUUID())
                .nome(new Nome("Primeiro 1"))
                .cpf(new Cpf("11111111111"))
                .email(new Email("clienteA@example.com"))
                .build();

        ClienteEntity clienteEntity2 = ClienteEntity.builder()
                .id(clienteEntity1.getId())
                .nome(clienteEntity1.getNome())
                .cpf(clienteEntity1.getCpf())
                .email(clienteEntity1.getEmail())
                .build();

        assertThat(clienteEntity1).isEqualTo(clienteEntity2);
        assertThat(clienteEntity1.hashCode()).isEqualTo(clienteEntity2.hashCode());
    }

    @Test
    void testToString() {
        ClienteEntity clienteEntity = ClienteEntity.builder()
                .id(UUID.randomUUID())
                .nome(new Nome("Primeiro 1"))
                .cpf(new Cpf("11111111111"))
                .email(new Email("clienteB@example.com"))
                .build();

        assertThat(clienteEntity.toString()).contains(clienteEntity.getId().toString(), clienteEntity.getNome().toString(), clienteEntity.getCpf().toString(), clienteEntity.getEmail().toString());
    }
}

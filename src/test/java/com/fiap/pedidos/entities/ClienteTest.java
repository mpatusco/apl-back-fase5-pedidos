package com.fiap.pedidos.entities;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.UUID;

class ClienteTest {

    @Test
    void testEqualsAndHashCode() {
        UUID id = UUID.randomUUID();
        Nome nome = new Nome("Sobrenome 123");
        Cpf cpf = new Cpf("11111111111");
        Email email = new Email("cliente@example.com");

        Cliente cliente1 = new Cliente(id, nome, cpf, email);
        Cliente cliente2 = new Cliente(id, nome, cpf, email);

        assertThat(cliente1).isEqualTo(cliente2);
        assertThat(cliente1.hashCode()).isEqualTo(cliente2.hashCode());
        assertThat(cliente1.equals(cliente2)).isTrue();
        assertThat(cliente1.getCpf().equals(cliente2.getCpf())).isTrue();
    }

    @Test
    void testToString() {
        UUID id = UUID.randomUUID();
        Nome nome = new Nome("Primeiro 123");
        Cpf cpf = new Cpf("11111111111");
        Email email = new Email("cliente@example.com");

        Cliente cliente = new Cliente(id, nome, cpf, email);

        assertThat(cliente.toString()).contains(id.toString(), nome.toString(), cpf.toString(), email.toString());
    }
}

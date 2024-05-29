package com.fiap.pedidos.adapters;

import com.fiap.pedidos.entities.Cliente;
import com.fiap.pedidos.helpers.Helper;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class ClienteDTOTest {

    @Nested
    class ConverterParaClienteDto {
        @Test
        @Severity(SeverityLevel.CRITICAL)
        @Description("Converter Cliente para DTO")
        void converterClienteParaDto() {
            Cliente cliente = Helper.gerarClienteComTodosDados();
            ClienteDTO clienteDTO = new ClienteDTO().from(cliente);
            ClienteDTO clienteDTO1 = new ClienteDTO().from(cliente);

            clienteDTO.setNome("Nome 1");
            clienteDTO.setCpf("11111111111");
            clienteDTO.setEmail("aaaaaa@gmail.com");

            clienteDTO1.setNome("Nome 1");
            clienteDTO1.setCpf("11111111111");
            clienteDTO1.setEmail("aaaaaa@gmail.com");

            assertThat(clienteDTO).isNotNull();
            assertThat(clienteDTO.getId()).isEqualTo(clienteDTO1.getId());
            assertThat(clienteDTO.getNome()).isEqualTo(clienteDTO1.getNome());
            assertThat(clienteDTO.getCpf()).isEqualTo(clienteDTO1.getCpf());
            assertThat(clienteDTO.getEmail()).isEqualTo(clienteDTO1.getEmail());
            assertThat(clienteDTO.toString()).contains(clienteDTO1.getNome(),clienteDTO1.getCpf(),
                    clienteDTO1.getEmail());
            assertThat(clienteDTO.hashCode()).isEqualTo(clienteDTO1.hashCode());
            assertThat(clienteDTO.equals(clienteDTO1)).isTrue();
        }

        @Test
        void testDoBuilder() {
            UUID id1 = UUID.randomUUID();
            UUID id2 = UUID.randomUUID();

            ClienteDTO clienteDTO1 = ClienteDTO.builder()
                    .id(id1)
                    .nome("Cliente A")
                    .cpf("123.456.789-01")
                    .email("clienteA@example.com")
                    .build();

            ClienteDTO clienteDTO2 = ClienteDTO.builder()
                    .id(id1)
                    .nome("Cliente A")
                    .cpf("123.456.789-01")
                    .email("clienteA@example.com")
                    .build();

            ClienteDTO clienteDTO3 = ClienteDTO.builder()
                    .id(id2)
                    .nome("Cliente B")
                    .cpf("987.654.321-09")
                    .email("clienteB@example.com")
                    .build();

            String dto = ClienteDTO.builder()
                    .id(id2)
                    .nome("Cliente B")
                    .cpf("987.654.321-09")
                    .email("clienteB@example.com").toString();

            assertThat(dto).contains("Cliente B", "987.654.321-09", "clienteB@example.com");

            assertThat(clienteDTO1).isEqualTo(clienteDTO2);
            assertThat(clienteDTO1.hashCode()).isEqualTo(clienteDTO2.hashCode());

            assertThat(clienteDTO1).isNotEqualTo(clienteDTO3);
            assertThat(clienteDTO1.hashCode()).isNotEqualTo(clienteDTO3.hashCode());

        }

        @Test
        @Severity(SeverityLevel.CRITICAL)
        @Description("Converter Cliente vazio para DTO")
        void criandoClienteDtoComClienteVazio() {
            Cliente cliente = new Cliente();
            ClienteDTO clienteDTO = new ClienteDTO().from(cliente);
            ClienteDTO clienteDTO1 = new ClienteDTO().from(cliente);

            clienteDTO.setNome("Nome 1");
            clienteDTO.setCpf("11111111111");
            clienteDTO.setEmail("aaaaaa@gmail.com");

            clienteDTO1.setNome("Nome 1");
            clienteDTO1.setCpf("11111111111");
            clienteDTO1.setEmail("aaaaaa@gmail.com");

            assertThat(clienteDTO).isNotNull();
            assertThat(clienteDTO.getId()).isEqualTo(clienteDTO1.getId());
            assertThat(clienteDTO.getNome()).isEqualTo(clienteDTO1.getNome());
            assertThat(clienteDTO.getCpf()).isEqualTo(clienteDTO1.getCpf());
            assertThat(clienteDTO.getEmail()).isEqualTo(clienteDTO1.getEmail());
            assertThat(clienteDTO.toString()).contains(clienteDTO1.getNome(),clienteDTO1.getCpf(),
                    clienteDTO1.getEmail());
            assertThat(clienteDTO.hashCode()).isEqualTo(clienteDTO1.hashCode());
            assertThat(clienteDTO.equals(clienteDTO1)).isTrue();
        }

        @Test
        @Severity(SeverityLevel.CRITICAL)
        @Description("Criando Cliente com construtor vazio")
        void criandoClienteComConstrutorVazio() {
            ClienteDTO clienteDTO = new ClienteDTO();
            ClienteDTO clienteDTO1 = new ClienteDTO();

            assertThat(clienteDTO.hashCode()).isEqualTo(clienteDTO1.hashCode());
            assertThat(clienteDTO.equals(clienteDTO1)).isTrue();
        }
    }
}


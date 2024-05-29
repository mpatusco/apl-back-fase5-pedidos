package com.fiap.pedidos.controllers.requestValidations;

import com.fiap.pedidos.entities.Cliente;
import com.fiap.pedidos.entities.Cpf;
import com.fiap.pedidos.entities.Email;
import com.fiap.pedidos.entities.Nome;
import com.fiap.pedidos.exceptions.entities.CpfExistenteException;
import com.fiap.pedidos.exceptions.entities.CpfInvalidoException;
import com.fiap.pedidos.exceptions.entities.EmailInvalidoException;
import com.fiap.pedidos.exceptions.entities.NomeInvalidoException;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.*;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ClienteRequestTest {

    private Validator validator;
    private ValidatorFactory factory;

    @BeforeEach
    void setup() {
        factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @AfterEach
    void tearDown() throws Exception {
        factory.close();
    }

    @Nested
    class CriarClienteRequest {
        @Test
        @Severity(SeverityLevel.CRITICAL)
        @DisplayName("Deve criar um Cliente a partir de um ClienteRequest válido")
        void deveCriarClienteAPartirDeClienteRequestValido() {
            ClienteRequest clienteRequest = new ClienteRequest();
            clienteRequest.setNome("John Doe");
            clienteRequest.setCpf("11111111111");
            clienteRequest.setEmail("john.doe@example.com");

            Cliente cliente = clienteRequest.from(clienteRequest);

            assertThat(cliente).isNotNull();
            assertThat(cliente.getNome()).isEqualToComparingFieldByField(new Nome("John Doe"));
            assertThat(cliente.getCpf()).isEqualToComparingFieldByField(new Cpf("11111111111"));
            assertThat(cliente.getEmail()).isEqualToComparingFieldByField(new Email("john.doe@example.com"));

            assertThat(cliente.getEmail().hashCode()).isEqualTo(new Email("john.doe@example.com").hashCode());
            assertThat(cliente.getEmail().equals(new Email("john.doe@example.com"))).isTrue();

            assertThat(cliente.getCpf().hashCode()).isEqualTo(new Cpf("11111111111").hashCode());
            assertThat(cliente.getCpf().equals(new Cpf("11111111111"))).isTrue();
        }

        @Test
        @Severity(SeverityLevel.NORMAL)
        @DisplayName("Deve falhar ao criar um Cliente a partir de um ClienteRequest inválido")
        void deveFalharAoCriarClienteAPartirDeClienteRequestInvalido() {
            ClienteRequest clienteRequest = new ClienteRequest();
            clienteRequest.setNome("");
            clienteRequest.setCpf("");
            clienteRequest.setEmail("");

            Set<ConstraintViolation<ClienteRequest>> violations = validator.validate(clienteRequest);

            assertThat(violations).hasSize(3);
            assertThat(violations).anyMatch(violation -> violation.getMessage().contains("nome não pode estar vazio"));
            assertThat(violations).anyMatch(violation -> violation.getMessage().contains("cpf não pode estar vazio"));
            assertThat(violations).anyMatch(violation -> violation.getMessage().contains("email não pode estar vazio"));
        }

        @Test
        @Severity(SeverityLevel.NORMAL)
        @DisplayName("Deve falhar ao criar um Cliente a partir de um ClienteRequest com cpf inválido")
        void deveFalhar_AoCriarClienteAPartirDeClienteRequestComCPFInvalido() {
            ClienteRequest clienteRequest = new ClienteRequest();
            clienteRequest.setNome("John Doe");
            clienteRequest.setCpf("12345678");
            clienteRequest.setEmail("john.doe@example.com");

            assertThatThrownBy(() ->  clienteRequest.from(clienteRequest))
                    .isInstanceOf(CpfInvalidoException.class)
                    .hasMessage("CPF inválido");
        }

        @Test
        @Severity(SeverityLevel.NORMAL)
        @DisplayName("Deve falhar ao criar um Cliente a partir de um ClienteRequest com email inválido")
        void deveFalhar_AoCriarClienteAPartirDeClienteRequestComEmailInvalido() {
            ClienteRequest clienteRequest = new ClienteRequest();
            clienteRequest.setNome("John Doe");
            clienteRequest.setCpf("11111111111");
            clienteRequest.setEmail("john.doe");

            assertThatThrownBy(() ->  clienteRequest.from(clienteRequest))
                    .isInstanceOf(EmailInvalidoException.class)
                    .hasMessage("E-mail inválido");
        }

        @Test
        @Severity(SeverityLevel.NORMAL)
        @DisplayName("Deve falhar ao criar um Cliente a partir de um ClienteRequest com nome inválido")
        void deveFalhar_AoCriarClienteAPartirDeClienteRequestComNomeInvalido() {
            ClienteRequest clienteRequest = new ClienteRequest();
            clienteRequest.setNome(null);
            clienteRequest.setCpf("11111111111");
            clienteRequest.setEmail("john.doe@gmail.com");

            assertThatThrownBy(() ->  clienteRequest.from(clienteRequest))
                    .isInstanceOf(NomeInvalidoException.class)
                    .hasMessage("Nome inválido");
        }
    }
}


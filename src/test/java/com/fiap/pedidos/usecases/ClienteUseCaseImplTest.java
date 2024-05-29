package com.fiap.pedidos.usecases;

import com.fiap.pedidos.entities.Cliente;
import com.fiap.pedidos.entities.Cpf;
import com.fiap.pedidos.exceptions.entities.CpfExistenteException;
import com.fiap.pedidos.helpers.Helper;
import com.fiap.pedidos.interfaces.gateways.IClienteRepositoryPort;
import com.fiap.pedidos.interfaces.usecases.IClienteUseCasePort;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ClienteUseCaseImplTest {
    private IClienteUseCasePort clienteUseCaseImpl;
    @Mock
    private IClienteRepositoryPort clienteRepositoryPort;
    AutoCloseable mock;

    @BeforeEach
    void setup() {
        mock = MockitoAnnotations.openMocks(this);
        clienteUseCaseImpl = new ClienteUseCaseImpl(clienteRepositoryPort);
    }

    @AfterEach
    void tearDown() throws Exception {
        mock.close();
    }

    @Nested
    class CadastrarCliente {
        @Test
        @Severity(SeverityLevel.BLOCKER)
        @Description("Cadastrar cliente sem cadastro existente")
        void deveCadastrarCliente() {
            var cliente = Helper.gerarClienteComTodosDados();

            when(clienteRepositoryPort.buscarPorCpf(any(Cpf.class))).thenReturn(Optional.empty());
            when(clienteRepositoryPort.cadastrar(any(Cliente.class))).thenReturn(cliente);

            var clienteSalvo = clienteUseCaseImpl.cadastrar(cliente);

            assertThat(clienteSalvo).isNotNull();
            assertThat(clienteSalvo).isInstanceOf(Cliente.class);
            assertThat(clienteSalvo.getNome().getNome()).isEqualTo(cliente.getNome().getNome());
            assertThat(clienteSalvo.getEmail().getEmail()).isEqualTo(cliente.getEmail().getEmail());
            assertThat(clienteSalvo.getCpf().getCpf()).isEqualTo(cliente.getCpf().getCpf());

            verify(clienteRepositoryPort, times(1)).buscarPorCpf(any(Cpf.class));
            verify(clienteRepositoryPort, times(1)).cadastrar(any(Cliente.class));
        }

        @Test
        @Severity(SeverityLevel.NORMAL)
        @Description("Cadastrar cliente com cadastro existente")
        void deveGerarExcecao_QuandoCadastrarClienteJaCadastrado() {
            var cliente = Helper.gerarClienteComTodosDados();

            when(clienteRepositoryPort.buscarPorCpf(any(Cpf.class))).thenReturn(Optional.of(cliente));
            when(clienteRepositoryPort.cadastrar(any(Cliente.class))).thenReturn(cliente);

            assertThatThrownBy(() -> clienteUseCaseImpl.cadastrar(cliente))
                    .isInstanceOf(CpfExistenteException.class)
                    .hasMessage("CPF já existente");

            verify(clienteRepositoryPort, never()).cadastrar(any(Cliente.class));
        }
    }

    @Nested
    class IdentificarCliente {
        @Test
        @Severity(SeverityLevel.CRITICAL)
        @Description("Identificar cliente sem cadastro existente por CPF")
        void deveIdentificarClienteSemCadastroExistentePorCPF() {
            var cliente = Helper.gerarClienteComTodosDados();

            when(clienteRepositoryPort.cadastrar(any(Cliente.class))).thenReturn(cliente);
            when(clienteRepositoryPort.buscarPorCpf(any(Cpf.class))).thenReturn(Optional.empty());

            var clienteSalvo = clienteUseCaseImpl.identificarPorCpf(cliente.getCpf());

            assertThat(clienteSalvo).isNotNull();
            assertThat(clienteSalvo).isInstanceOf(Cliente.class);
            assertThat(clienteSalvo.getNome().getNome()).isEqualTo(cliente.getNome().getNome());
            assertThat(clienteSalvo.getEmail().getEmail()).isEqualTo(cliente.getEmail().getEmail());
            assertThat(clienteSalvo.getCpf().getCpf()).isEqualTo(cliente.getCpf().getCpf());

            verify(clienteRepositoryPort, times(1)).cadastrar(any(Cliente.class));
        }

        @Test
        @Severity(SeverityLevel.NORMAL)
        @Description("Identificar cliente com cadastro existente por CPF")
        void deveIdentificarClienteComCadastroExistentePorCPF() {
            var cliente = Helper.gerarClienteComTodosDados();

            when(clienteRepositoryPort.cadastrar(any(Cliente.class))).thenReturn(cliente);
            when(clienteRepositoryPort.buscarPorCpf(any(Cpf.class))).thenReturn(Optional.of(cliente));

            var clienteSalvo = clienteUseCaseImpl.identificarPorCpf(cliente.getCpf());

            assertThat(clienteSalvo).isNotNull();
            assertThat(clienteSalvo).isInstanceOf(Cliente.class);
            assertThat(clienteSalvo.getNome().getNome()).isEqualTo(cliente.getNome().getNome());
            assertThat(clienteSalvo.getEmail().getEmail()).isEqualTo(cliente.getEmail().getEmail());
            assertThat(clienteSalvo.getCpf().getCpf()).isEqualTo(cliente.getCpf().getCpf());

            verify(clienteRepositoryPort, never()).cadastrar(any(Cliente.class));
        }


        @Test
        @Severity(SeverityLevel.NORMAL)
        @Description("Gerar id para identificar cliente não cadastrado")
        void deveGerarIdParaIdentificarClienteNaoCadastrado() {
            var uuid = UUID.randomUUID();

            when(clienteRepositoryPort.gerarId()).thenReturn(uuid);

            var id = clienteUseCaseImpl.gerarId();

            assertThat(id).isNotNull();
            assertThat(id).isInstanceOf(UUID.class);
            assertThat(id).isEqualTo(uuid);

            verify(clienteRepositoryPort, times(1)).gerarId();
        }
    }
}

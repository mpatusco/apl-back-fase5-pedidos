package com.fiap.pedidos.gateways;

import com.fiap.pedidos.entities.Cliente;
import com.fiap.pedidos.entities.Cpf;
import com.fiap.pedidos.gateways.entities.ClienteEntity;
import com.fiap.pedidos.helpers.Helper;
import com.fiap.pedidos.interfaces.gateways.IClienteRepositoryPort;
import com.fiap.pedidos.interfaces.repositories.ClienteRepository;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ClienteRepositoryAdapterTest {

    private IClienteRepositoryPort clienteRepositoryAdapter;

    @Mock
    private ClienteRepository clienteRepository;

    AutoCloseable mock;

    @BeforeEach
    void setup(){
        mock = MockitoAnnotations.openMocks(this);
        clienteRepositoryAdapter = new ClienteRepositoryAdapter(clienteRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        mock.close();
    }

    @Nested class CadastrarCliente{
        @Test
        @Severity(SeverityLevel.BLOCKER)
        @Description("Cadastrar cliente com nome, cpf e email")
        void deveCadastrarClienteUtilizandoNomeCPFeEmail(){
            var cliente = Helper.gerarClienteComTodosDados();
            var clienteEntity = new ClienteEntity().from(cliente);
            clienteEntity.setId(UUID.randomUUID());

            when(clienteRepository.save(any(ClienteEntity.class))).thenReturn(clienteEntity);

            var clienteSalvo = clienteRepositoryAdapter.cadastrar(cliente);

            assertThat(clienteSalvo).isNotNull();
            assertThat(clienteSalvo).isInstanceOf(Cliente.class);
            assertThat(clienteSalvo.getId()).isNotNull();
            assertThat(clienteSalvo.getNome().getNome()).isEqualTo(cliente.getNome().getNome());
            assertThat(clienteSalvo.getEmail().getEmail()).isEqualTo(cliente.getEmail().getEmail());
            assertThat(clienteSalvo.getCpf().getCpf()).isEqualTo(cliente.getCpf().getCpf());

            verify(clienteRepository, times(1)).save(any(ClienteEntity.class));
        }

        @Test
        @Severity(SeverityLevel.BLOCKER)
        @Description("Cadastrar cliente somente com cpf")
        void deveCadastrarClienteUtilizandoSomenteCPF(){
            var cliente = Helper.gerarClienteSomenteComCPF();
            var clienteEntity = new ClienteEntity().from(cliente);
            clienteEntity.setId(UUID.randomUUID());

            when(clienteRepository.save(any(ClienteEntity.class))).thenReturn(clienteEntity);

            var clienteSalvo = clienteRepositoryAdapter.cadastrar(cliente);

            assertThat(clienteSalvo).isNotNull();
            assertThat(clienteSalvo).isInstanceOf(Cliente.class);
            assertThat(clienteSalvo.getId()).isNotNull();
            assertThat(clienteSalvo.getNome()).isNull();
            assertThat(clienteSalvo.getEmail()).isNull();
            assertThat(clienteSalvo.getCpf().getCpf()).isEqualTo(cliente.getCpf().getCpf());

            verify(clienteRepository, times(1)).save(any(ClienteEntity.class));
        }

        @Test
        @Severity(SeverityLevel.BLOCKER)
        @Description("Cadastrar cliente que não optou por se identificar")
        void deveCadastrarClienteNaoIdentificado(){
            var clienteEntity = new ClienteEntity().from(new Cliente());
            clienteEntity.setId(UUID.randomUUID());

            when(clienteRepository.save(any(ClienteEntity.class))).thenReturn(clienteEntity);

            var id = clienteRepositoryAdapter.gerarId();

            assertThat(id).isNotNull();
            assertThat(id).isInstanceOf(UUID.class);
            assertThat(id).isNotNull();
            verify(clienteRepository, times(1)).save(any(ClienteEntity.class));
        }
    }

    @Nested class BuscarCliente{
        @Test
        @Severity(SeverityLevel.CRITICAL)
        @Description("Buscar cliente pelo cpf")
        void deveBuscarClientePorCpf(){
            var cpf = "11111111111";
            var clienteEntity = new ClienteEntity().from(Helper.gerarClienteComTodosDados());
            clienteEntity.setId(UUID.randomUUID());

            when(clienteRepository.findAllByCpf(any(Cpf.class))).thenReturn(Optional.of(clienteEntity));

            var cliente = clienteRepositoryAdapter.buscarPorCpf(new Cpf(cpf)).get();

            assertThat(cliente).isNotNull();
            assertThat(cliente).isInstanceOf(Cliente.class);
            assertThat(cliente.getCpf().getCpf()).isEqualTo(cpf);
            verify(clienteRepository, times(1)).findAllByCpf(any(Cpf.class));
        }

        @Test
        @Severity(SeverityLevel.CRITICAL)
        @Description("Buscar todos os clientes")
        void deveBuscarTodosOsCliente(){
            var clienteComTodosDados = Helper.gerarClienteComTodosDados();
            var clienteSomenteComCpf = Helper.gerarClienteSomenteComCPF();
            var clienteEntityList = new ArrayList<ClienteEntity>();

            clienteEntityList.add(new ClienteEntity().from(clienteComTodosDados));
            clienteEntityList.get(0).setId(UUID.randomUUID());
            clienteEntityList.add(new ClienteEntity().from(clienteSomenteComCpf));
            clienteEntityList.get(1).setId(UUID.randomUUID());

            when(clienteRepository.findAll()).thenReturn(clienteEntityList);

            var clienteList = clienteRepositoryAdapter.bucarTodos();

            assertThat(clienteList.isEmpty()).isFalse();
            assertThat(clienteList.size()).isEqualTo(2);
            assertThat(clienteList.get(0).getNome().getNome()).isEqualTo(clienteComTodosDados.getNome().getNome());
            assertThat(clienteList.get(0).getCpf().getCpf()).isEqualTo(clienteComTodosDados.getCpf().getCpf());
            assertThat(clienteList.get(0).getEmail().getEmail()).isEqualTo(clienteComTodosDados.getEmail().getEmail());

            assertThat(clienteList.get(1).getCpf().getCpf()).isEqualTo(clienteComTodosDados.getCpf().getCpf());
            assertThat(clienteList.get(1).getNome()).isNull();
            assertThat(clienteList.get(1).getEmail()).isNull();

            verify(clienteRepository, times(1)).findAll();
        }
    }
}

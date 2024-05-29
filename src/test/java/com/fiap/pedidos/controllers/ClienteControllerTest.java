package com.fiap.pedidos.controllers;

import com.fiap.pedidos.entities.Cliente;
import com.fiap.pedidos.entities.Cpf;
import com.fiap.pedidos.helpers.Helper;
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ClienteControllerTest {
    private MockMvc mockMvc;
    @Mock
    private IClienteUseCasePort clienteUseCasePort;
    AutoCloseable mock;

    @BeforeEach
    void setup() {
        mock = MockitoAnnotations.openMocks(this);
        ClienteController clienteController = new ClienteController(clienteUseCasePort);
        mockMvc = MockMvcBuilders.standaloneSetup(clienteController).addFilter((request, response, chain) -> {
            response.setCharacterEncoding("UTF-8");
            chain.doFilter(request, response);
        }).build();
    }

    @AfterEach
    void tearDown() throws Exception {
        mock.close();
    }

    @Nested
    class IdentificarCliente {

        @Test
        @Severity(SeverityLevel.CRITICAL)
        @Description("Identificar cliente pelo CPF")
        void deveIdentificarClientePeloCPF() throws Exception {
            var cliente = Helper.gerarClienteComTodosDadosEID();

            when(clienteUseCasePort.identificarPorCpf(any(Cpf.class))).thenReturn(cliente);

            mockMvc.perform(post("/clientes/{cpf}", cliente.getCpf()))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.cpf").value(cliente.getCpf().getCpf()));;

            verify(clienteUseCasePort, times(1)).identificarPorCpf(any(Cpf.class));
        }

        @Test
        @Severity(SeverityLevel.CRITICAL)
        @Description("Identificar cliente e gerar id")
        void deveIdentificarClienteEGerarId() throws Exception {
            var uuid = UUID.randomUUID();
            when(clienteUseCasePort.gerarId()).thenReturn(uuid);

            mockMvc.perform(post("/clientes/id"))
                    .andExpect(status().isCreated());

            verify(clienteUseCasePort, times(1)).gerarId();
        }
    }

    @Nested
    class BuscarCliente {
        @Test
        @Severity(SeverityLevel.CRITICAL)
        @Description("Buscar cliente pelo ID do cliente")
        void deveBuscarClientePeloIDCliente() throws Exception {
            var cliente = Helper.gerarClienteComTodosDadosEID();
            when(clienteUseCasePort.buscarPorId(any(UUID.class))).thenReturn(Optional.of(cliente));

            mockMvc.perform(get("/clientes/{id}", cliente.getId()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(cliente.getId().toString()))
                    .andExpect(jsonPath("$.cpf").value(cliente.getCpf().getCpf()))
                    .andExpect(jsonPath("$.nome").value(cliente.getNome().getNome()))
                    .andExpect(jsonPath("$.email").value(cliente.getEmail().getEmail()));

            verify(clienteUseCasePort, times(1)).buscarPorId(any(UUID.class));
        }

        @Test
        @Severity(SeverityLevel.NORMAL)
        @Description("Buscar todos os clientes")
        void deveBuscarTodosOsClientes() throws Exception {
            var cliente = Helper.gerarClienteComTodosDadosEID();
            when(clienteUseCasePort.bucarTodos()).thenReturn(List.of(cliente));

            mockMvc.perform(get("/clientes", cliente.getId()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.[0].id").value(cliente.getId().toString()))
                    .andExpect(jsonPath("$.[0].cpf").value(cliente.getCpf().getCpf()))
                    .andExpect(jsonPath("$.[0].nome").value(cliente.getNome().getNome()))
                    .andExpect(jsonPath("$.[0].email").value(cliente.getEmail().getEmail()));

            verify(clienteUseCasePort, times(1)).bucarTodos();
        }
    }


    @Nested
    class CadastrarCliente {
        @Test
        @Severity(SeverityLevel.CRITICAL)
        @Description("Cadastrar cliente")
        void deveCadastrarCliente() throws Exception {
            var clienteRequest = Helper.gerarClienteRequest();
            var cliente = Helper.gerarClienteAPartirDeUmClienteRequest(clienteRequest);

            when(clienteUseCasePort.cadastrar(any(Cliente.class))).thenReturn(cliente);

            mockMvc.perform(post("/clientes")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(Helper.asJsonString(clienteRequest)))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.cpf").value(cliente.getCpf().getCpf()))
                    .andExpect(jsonPath("$.nome").value(cliente.getNome().getNome()))
                    .andExpect(jsonPath("$.email").value(cliente.getEmail().getEmail()));

            verify(clienteUseCasePort, times(1)).cadastrar(any(Cliente.class));
        }

    }

}

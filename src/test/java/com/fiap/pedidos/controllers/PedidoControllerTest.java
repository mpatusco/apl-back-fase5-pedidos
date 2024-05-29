package com.fiap.pedidos.controllers;

import com.fiap.pedidos.controllers.requestValidations.PedidoRequest;
import com.fiap.pedidos.entities.Cliente;
import com.fiap.pedidos.entities.Pedido;
import com.fiap.pedidos.entities.PedidoProduto;
import com.fiap.pedidos.helpers.Helper;
import com.fiap.pedidos.interfaces.facade.IServiceAsyncProcessWebhook;
import com.fiap.pedidos.interfaces.usecases.IClienteUseCasePort;
import com.fiap.pedidos.interfaces.usecases.IPedidoProdutoUseCasePort;
import com.fiap.pedidos.interfaces.usecases.IPedidoUseCasePort;
import com.fiap.pedidos.utils.enums.TipoAtualizacao;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PedidoControllerTest {

    @Mock
    private IPedidoUseCasePort pedidoUseCasePort;

    @Mock
    private IPedidoProdutoUseCasePort pedidoProdutoUseCasePort;

    @Mock
    private IClienteUseCasePort clienteUseCasePort;

    @Mock
    private IServiceAsyncProcessWebhook serviceAsyncProcessWebhook;

    @InjectMocks
    private PedidoController pedidoController;

    private MockMvc mockMvc;

    AutoCloseable mock;

    @BeforeEach
    void setUp() {
        mock = MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(pedidoController).addFilter((request, response, chain) -> {
            response.setCharacterEncoding("UTF-8");
            chain.doFilter(request, response);
        }).build();
    }

    @AfterEach
    void tearDown() throws Exception {
        mock.close();
    }

    @Nested
    class IniciarPedido {

        @Test
        @Severity(SeverityLevel.BLOCKER)
        @Description("Iniciar pedido")
        void deveIniciarPedidoComSucesso() throws Exception {
            PedidoRequest pedidoRequest = new PedidoRequest();
            pedidoRequest.setIdCliente(UUID.randomUUID());

            var pedido = Helper.gerarPedidoComIDComClienteComIDEProdutos();

            Cliente cliente = Helper.gerarClienteComTodosDadosEID();

            when(clienteUseCasePort.buscarPorId(any(UUID.class))).thenReturn(Optional.of(cliente));
            when(pedidoUseCasePort.iniciarPedido(any(Pedido.class))).thenReturn(pedido);

            mockMvc.perform(post("/pedidos")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(Helper.asJsonString(pedidoRequest)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.idCliente").value(pedido.getCliente().getId().toString()));

            verify(clienteUseCasePort, times(1)).buscarPorId(any(UUID.class));
            verify(pedidoUseCasePort, times(1)).iniciarPedido(any(Pedido.class));

        }
    }

    @Nested
    class AdicionarItem {

        @Test
        @Severity(SeverityLevel.BLOCKER)
        @Description("Adicionar item ao pedido")
        void deveAdicionarItemAoPedidoComSucesso() throws Exception {
            UUID idPedido = UUID.randomUUID();

            PedidoRequest pedidoRequest = new PedidoRequest();
            pedidoRequest.setIdCliente(UUID.randomUUID());
            var pedido = Helper.gerarPedidoComIDComClienteComIDEProdutos();

            var pedidoProduto =  Helper.gerarPedidoProduto();

            when(pedidoProdutoUseCasePort.adicionarItemNoPedido(any(PedidoProduto.class)))
                    .thenReturn(pedido);

            mockMvc.perform(post("/pedidos/{idPedido}", idPedido)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(Helper.asJsonString(pedidoProduto)))
                    .andExpect(status().isOk());

            verify(pedidoProdutoUseCasePort, times(1))
                    .adicionarItemNoPedido(any(PedidoProduto.class));

        }
    }

    @Nested
    class RemoverItem {

        @Test
        @Severity(SeverityLevel.BLOCKER)
        @Description("Remover item do pedido")
        void deveRemoverItemDoPedidoComSucesso() throws Exception {
            UUID idPedido = UUID.randomUUID();

            PedidoRequest pedidoRequest = new PedidoRequest();
            pedidoRequest.setIdCliente(UUID.randomUUID());
            var pedido = Helper.gerarPedidoComIDComClienteComIDEProdutos();

            var pedidoProduto =  Helper.gerarPedidoProduto();

            when(pedidoProdutoUseCasePort.removerItemDoPedido(any(PedidoProduto.class)))
                    .thenReturn(pedido);

            mockMvc.perform(delete("/pedidos/{idPedido}", idPedido)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(Helper.asJsonString(pedidoProduto)))
                    .andExpect(status().isOk());

            verify(pedidoProdutoUseCasePort, times(1))
                    .removerItemDoPedido(any(PedidoProduto.class));

        }
    }

    @Nested
    class FinalizarPedido {

        @Test
        @Severity(SeverityLevel.BLOCKER)
        @Description("Finalizar pedido")
        void deveFinalizarPedidoComSucesso() throws Exception {
            UUID idPedido = UUID.randomUUID();

            PedidoRequest pedidoRequest = new PedidoRequest();
            pedidoRequest.setIdCliente(UUID.randomUUID());
            var pedido = Helper.gerarPedidoComIDComClienteComIDEProdutos();

            var pedidoProduto =  Helper.gerarPedidoProduto();

            when(pedidoUseCasePort.atualizarPedido(
                    any(UUID.class),
                    any(TipoAtualizacao.class),
                    any(),
                    any())
            ).thenReturn(pedido);

            mockMvc.perform(post("/pedidos/checkout/{idPedido}", idPedido)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(Helper.asJsonString(pedidoProduto)))
                    .andExpect(status().isOk());

            verify(pedidoUseCasePort, times(1)).atualizarPedido(
                    any(UUID.class),
                    any(TipoAtualizacao.class),
                    any(),
                    any());

        }
    }


    @Nested
    class BuscarPedido {

        @Test
        @Severity(SeverityLevel.BLOCKER)
        @Description("Buscar todos os pedidos")
        void deveFinalizarPedidoComSucesso() throws Exception {
            UUID idPedido = UUID.randomUUID();

            PedidoRequest pedidoRequest = new PedidoRequest();
            pedidoRequest.setIdCliente(UUID.randomUUID());
            var pedido = Helper.gerarPedidoComIDComClienteComIDEProdutos();

            when(pedidoUseCasePort.buscarTodos(anyInt(), anyInt())).thenReturn(List.of(pedido));

            mockMvc.perform(get("/pedidos"))
                    .andExpect(status().isOk());

            verify(pedidoUseCasePort, times(1)).buscarTodos(anyInt(),anyInt());
        }

        @Test
        @Severity(SeverityLevel.BLOCKER)
        @Description("Buscar pedido por id")
        void deveBuscarPedidoPeloIDComSucesso() throws Exception {
            UUID idPedido = UUID.randomUUID();

            PedidoRequest pedidoRequest = new PedidoRequest();
            pedidoRequest.setIdCliente(UUID.randomUUID());
            var pedido = Helper.gerarPedidoComIDComClienteComIDEProdutos();

            when(pedidoUseCasePort.buscarPorId(any(UUID.class))).thenReturn(pedido);

            mockMvc.perform(get("/pedidos/{idPedido}", idPedido))
                    .andExpect(status().isOk());

            verify(pedidoUseCasePort, times(1)).buscarPorId(any(UUID.class));
        }
    }
}


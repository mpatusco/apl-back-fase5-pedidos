package com.fiap.pedidos.facade;

import com.fiap.pedidos.interfaces.gateways.*;
import com.fiap.pedidos.interfaces.usecases.IClienteUseCasePort;
import com.fiap.pedidos.interfaces.usecases.IPedidoProdutoUseCasePort;
import com.fiap.pedidos.interfaces.usecases.IPedidoUseCasePort;
import com.fiap.pedidos.interfaces.usecases.IProdutoUseCasePort;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;

class ConfigurationContextFacadeTest {

    @Mock
    private IProdutoRepositoryPort produtoRepositoryPort;

    @Mock
    private IPedidoProdutoRepositoryPort pedidoProdutoRepositoryPort;

    @Mock
    private IPedidoRepositoryPort pedidoRepositoryPort;

    @Mock
    private IPagamentoRepositoryPort pagamentoRepositoryPort;

    @Mock
    private IFilaRepositoryPort filaRepositoryPort;

    @Mock
    private IClienteRepositoryPort clienteRepositoryPort;


    AutoCloseable mock;

    @BeforeEach
    void setup(){
        mock = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        mock.close();
    }

    @Nested
    class InicializarBeans {
        @Test
        @Severity(SeverityLevel.CRITICAL)
        @Description("Inicializar ProdutoUseCase")
        void inicializarProdutoUseCasePort() {
            IProdutoUseCasePort produtoUseCasePort = new ConfigurationContext().produtoUseCasePort(produtoRepositoryPort);
            assertThat(produtoUseCasePort).isNotNull();
        }

        @Test
        @Severity(SeverityLevel.CRITICAL)
        @Description("Inicializar PedidoUseCase")
        void inicializarPedidoUseCasePort() {
            IPedidoUseCasePort pedidoUseCasePort = new ConfigurationContext()
                    .pedidoUseCasePort(pedidoProdutoRepositoryPort, pedidoRepositoryPort, pagamentoRepositoryPort, filaRepositoryPort);
            assertThat(pedidoUseCasePort).isNotNull();
        }

        @Test
        @Severity(SeverityLevel.CRITICAL)
        @Description("Inicializar PedidoProdutoUseCase")
        void inicializarPedidoProdutoUseCasePort() {
            IPedidoProdutoUseCasePort pedidoProdutoUseCasePort = new ConfigurationContext()
                    .pedidoProdutoUseCasePort(pedidoRepositoryPort, pedidoProdutoRepositoryPort, produtoRepositoryPort);
            assertThat(pedidoProdutoUseCasePort).isNotNull();
        }

        @Test
        @Severity(SeverityLevel.CRITICAL)
        @Description("Inicializar ClienteUseCase")
        void inicializarClienteUseCasePort() {
            IClienteUseCasePort clienteUseCasePort = new ConfigurationContext().clienteUseCasePort(clienteRepositoryPort);
            assertThat(clienteUseCasePort).isNotNull();
        }
    }
}

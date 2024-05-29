package com.fiap.pedidos.usecases;

import com.fiap.pedidos.entities.Pedido;
import com.fiap.pedidos.exceptions.entities.PedidoOperacaoNaoSuportadaException;
import com.fiap.pedidos.helpers.Helper;
import com.fiap.pedidos.interfaces.gateways.IFilaRepositoryPort;
import com.fiap.pedidos.interfaces.gateways.IPagamentoRepositoryPort;
import com.fiap.pedidos.interfaces.gateways.IPedidoProdutoRepositoryPort;
import com.fiap.pedidos.interfaces.gateways.IPedidoRepositoryPort;
import com.fiap.pedidos.utils.enums.StatusPedido;
import com.fiap.pedidos.utils.enums.TipoAtualizacao;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PedidoUseCaseImplTest {
    @InjectMocks
    private PedidoUseCaseImpl pedidoUseCaseImpl;
    @Mock
    private IPedidoProdutoRepositoryPort pedidoProdutoRepositoryPort;
    @Mock
    private IPedidoRepositoryPort pedidoRepositoryPort;
    @Mock
    private IPagamentoRepositoryPort pagamentoRepositoryPort;
    @Mock
    private IFilaRepositoryPort filaRepositoryPort;
    AutoCloseable mock;

    @BeforeEach
    void setup() {
        mock = MockitoAnnotations.openMocks(this);
    }

    @Nested
    class IniciarPedido {
        @Test
        @Severity(SeverityLevel.CRITICAL)
        @Description("Iniciar novo pedido para cliente sem pedidos ativos")
        void deveIniciarNovoPedido() {
            Pedido pedido = Helper.gerarPedidoComClienteComIDEProdutos();

            when(pedidoRepositoryPort.buscarPedidosPorClienteEStatus(any(UUID.class), any(StatusPedido.class)))
                    .thenReturn(List.of());
            when(pedidoRepositoryPort.cadastrar(any(Pedido.class))).thenReturn(pedido);

            Pedido pedidoIniciado = pedidoUseCaseImpl.iniciarPedido(pedido);

            assertThat(pedidoIniciado).isNotNull();
            assertThat(pedidoIniciado.getIdPedido()).isEqualTo(pedido.getIdPedido());
            assertThat(pedidoIniciado.getValorPedido()).isEqualTo(pedido.getValorPedido());

            verify(pedidoRepositoryPort, times(1)).buscarPedidosPorClienteEStatus(any(UUID.class), any(StatusPedido.class));
            verify(pedidoRepositoryPort, times(1)).cadastrar(any(Pedido.class));
        }
    }

    @Nested
    class AtualizarPedido {
        @Test
        @Severity(SeverityLevel.CRITICAL)
        @Description("Atualizar status do pedido para entregue")
        void deveAtualizarStatusDoPedidoParaEntregue() {
            Pedido pedidoExistente = Helper.gerarPedidoComIDComClienteComIDEProdutos();


            when(pedidoRepositoryPort.buscarPorId(any(UUID.class))).thenReturn(Optional.of(pedidoExistente));
            when(pedidoRepositoryPort.atualizarPedido(any(Pedido.class))).thenReturn(pedidoExistente);

            Pedido pedidoAtualizado = pedidoUseCaseImpl.atualizarPedido(
                    pedidoExistente.getIdPedido(),
                    TipoAtualizacao.F,
                    null,
                    StatusPedido.R);

            assertThat(pedidoAtualizado).isNotNull();
            assertThat(pedidoAtualizado.getStatusPedido()).isEqualTo(StatusPedido.R);

            verify(pedidoRepositoryPort, times(1)).buscarPorId(any(UUID.class));
            verify(pedidoRepositoryPort, times(1)).atualizarPedido(any(Pedido.class));
        }
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Description("Tentar atualizar pedido com operação não suportada")
    void deveLancarExcecao_QuandoAtualizarPedidoComOperacaoNaoSuportada() {
        Pedido pedidoExistente = Helper.gerarPedidoComIDComClienteComIDEProdutosEStatusEmPreparacao();

        when(pedidoRepositoryPort.buscarPorId(any(UUID.class))).thenReturn(Optional.of(pedidoExistente));

        assertThatThrownBy(() -> pedidoUseCaseImpl.atualizarPedido(
                pedidoExistente.getIdPedido(),
                TipoAtualizacao.C,
                null,
                StatusPedido.R))
                .isInstanceOf(PedidoOperacaoNaoSuportadaException.class)
                .hasMessage("Pedido não está aberto para edição.");

        verify(pedidoRepositoryPort, times(1)).buscarPorId(any(UUID.class));
        verify(pedidoRepositoryPort, never()).atualizarPedido(any(Pedido.class));
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("Atualizar pedido apos checkout")
    void atualizarPedidoAposCheckout() {
        Pedido pedidoExistente = Helper.gerarPedidoComIDComClienteComIDEProdutos();

        when(pedidoRepositoryPort.buscarPorId(any(UUID.class))).thenReturn(Optional.of(pedidoExistente));
        when(pedidoRepositoryPort.atualizarPedido(any(Pedido.class))).thenReturn(pedidoExistente);

        var pedidoAtualizado = pedidoUseCaseImpl.atualizarPedido(
                pedidoExistente.getIdPedido(),
                TipoAtualizacao.C,
                null,
                StatusPedido.A);

        assertThat(pedidoAtualizado).isNotNull();
        assertThat(pedidoAtualizado.getStatusPedido()).isEqualTo(StatusPedido.A);

        verify(pedidoRepositoryPort, times(1)).buscarPorId(any(UUID.class));
        verify(pedidoRepositoryPort, times(1)).atualizarPedido(any(Pedido.class));
    }


    @Nested
    class AtualizarPedidoComDetalhes {
        @Test
        @Severity(SeverityLevel.CRITICAL)
        @Description("Atualizar detalhes do pedido")
        void deveAtualizarDetalhesDoPedido() {
            Pedido pedidoExistente = Helper.gerarPedidoComIDComClienteComIDEProdutos();
            Pedido pedidoRequest = Helper.gerarPedidoComIDComClienteComIDEProdutos();
            pedidoRequest.setProdutos(null);

            when(pedidoRepositoryPort.buscarPorId(any(UUID.class))).thenReturn(Optional.of(pedidoExistente));
            when(pedidoRepositoryPort.atualizarPedido(any(Pedido.class))).thenReturn(pedidoExistente);
            when(pedidoProdutoRepositoryPort.obterTodosOsProdutosAssociadosAoPedidoPeloIdPedido(any(UUID.class)))
                    .thenReturn(List.of(pedidoExistente.getProdutos().get(0)));

            Pedido pedidoAtualizado = pedidoUseCaseImpl.atualizarPedido(
                    pedidoExistente.getIdPedido(),
                    TipoAtualizacao.I,
                    pedidoRequest,
                    StatusPedido.A);

            assertThat(pedidoAtualizado).isNotNull();
            assertThat(pedidoAtualizado.getIdPedido()).isEqualTo(pedidoExistente.getIdPedido());
            assertThat(pedidoAtualizado.getProdutos()).isNotEmpty();
            assertThat(pedidoAtualizado.getValorPedido()).isEqualTo(pedidoRequest.getValorPedido());

            verify(pedidoRepositoryPort, times(1)).buscarPorId(any(UUID.class));
            verify(pedidoRepositoryPort, times(1)).atualizarPedido(any(Pedido.class));
            verify(pedidoProdutoRepositoryPort, times(2))
                    .obterTodosOsProdutosAssociadosAoPedidoPeloIdPedido(any(UUID.class));
        }

        @Test
        @Severity(SeverityLevel.NORMAL)
        @Description("Tentar atualizar pedido com operação não suportada")
        void deveLancarExcecao_QuandoAtualizarPedidoComOperacaoNaoSuportada() {
            Pedido pedidoExistente = Helper.gerarPedidoComIDComClienteComIDEProdutosEStatusEmPreparacao();
            Pedido pedidoRequest = Helper.gerarPedidoComIDComClienteComIDEProdutosEStatusEmPreparacao();

            when(pedidoRepositoryPort.buscarPorId(any(UUID.class))).thenReturn(Optional.of(pedidoExistente));
            when(pedidoProdutoRepositoryPort.obterTodosOsProdutosAssociadosAoPedidoPeloIdPedido(any(UUID.class)))
                    .thenReturn(List.of(pedidoExistente.getProdutos().get(0)));
            when(pedidoRepositoryPort.atualizarPedido(any(Pedido.class))).thenReturn(pedidoExistente);

            assertThatThrownBy(() -> pedidoUseCaseImpl.atualizarPedido(
                    pedidoExistente.getIdPedido(),
                    TipoAtualizacao.I,
                    pedidoRequest,
                    null))
                    .isInstanceOf(PedidoOperacaoNaoSuportadaException.class)
                    .hasMessage("Pedido não está aberto para edição.");

            verify(pedidoRepositoryPort, times(1)).buscarPorId(any(UUID.class));
            verify(pedidoRepositoryPort, never()).atualizarPedido(any(Pedido.class));
        }
    }

    @Nested
    class AtualizarStatusPagamento {
        @Test
        @Severity(SeverityLevel.CRITICAL)
        @Description("Atualizar status do pagamento e inserir na fila")
        void deveAtualizarStatusDoPagamentoEInserirNaFila() {
            Pedido pedidoExistente = Helper.gerarPedidoComIDComClienteComIDEProdutosEStatusEmPreparacao();
            Pedido pedidoRequest = Helper.gerarPedidoComIDComClienteComIDEProdutosEStatusEmPreparacao();

            when(pagamentoRepositoryPort.consultaPagamento(any(UUID.class))).thenReturn(true);
            doNothing().when(filaRepositoryPort).inserePedidoNaFila(any(UUID.class), any(UUID.class));
            when(pedidoProdutoRepositoryPort.obterTodosOsProdutosAssociadosAoPedidoPeloIdPedido(any(UUID.class)))
                    .thenReturn(List.of(pedidoExistente.getProdutos().get(0)));
            when(pedidoRepositoryPort.buscarPorId(any(UUID.class))).thenReturn(Optional.of(pedidoExistente));
            when(pedidoRepositoryPort.atualizarPedido(any(Pedido.class))).thenReturn(pedidoExistente);

            Pedido pedidoAtualizado = pedidoUseCaseImpl.atualizarPedido(
                    pedidoExistente.getIdPedido(),
                    TipoAtualizacao.P,
                    pedidoRequest,
                    null);

            assertThat(pedidoAtualizado.getStatusPedido()).isEqualTo(StatusPedido.R);

            verify(pagamentoRepositoryPort, times(1)).consultaPagamento(any(UUID.class));
            verify(filaRepositoryPort, times(1)).inserePedidoNaFila(any(UUID.class), any(UUID.class));
        }
    }

    @Nested
    class BuscarPorId {
        @Test
        @Severity(SeverityLevel.CRITICAL)
        @Description("Buscar pedido por ID com sucesso")
        void deveBuscarPedidoPorIdComSucesso() {
            Pedido pedidoExistente = Helper.gerarPedidoComIDComClienteComIDEProdutos();

            when(pedidoRepositoryPort.buscarPorId(any(UUID.class))).thenReturn(Optional.of(pedidoExistente));
            when(pedidoProdutoRepositoryPort.obterTodosOsProdutosAssociadosAoPedidoPeloIdPedido(any(UUID.class)))
                    .thenReturn(List.of(pedidoExistente.getProdutos().get(0)));

            Pedido pedidoEncontrado = pedidoUseCaseImpl.buscarPorId(pedidoExistente.getIdPedido());

            assertThat(pedidoEncontrado).isNotNull();
            assertThat(pedidoEncontrado.getIdPedido()).isEqualTo(pedidoExistente.getIdPedido());

            verify(pedidoRepositoryPort, times(1)).buscarPorId(any(UUID.class));
            verify(pedidoProdutoRepositoryPort, times(1))
                    .obterTodosOsProdutosAssociadosAoPedidoPeloIdPedido(any(UUID.class));
        }
    }
}

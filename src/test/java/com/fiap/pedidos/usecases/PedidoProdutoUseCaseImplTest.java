package com.fiap.pedidos.usecases;

import com.fiap.pedidos.entities.Pedido;
import com.fiap.pedidos.entities.PedidoProduto;
import com.fiap.pedidos.entities.Produto;
import com.fiap.pedidos.entities.ValorProduto;
import com.fiap.pedidos.gateways.entities.PedidoEntity;
import com.fiap.pedidos.gateways.entities.PedidoProdutoEntity;
import com.fiap.pedidos.helpers.Helper;
import com.fiap.pedidos.interfaces.gateways.IPedidoProdutoRepositoryPort;
import com.fiap.pedidos.interfaces.gateways.IPedidoRepositoryPort;
import com.fiap.pedidos.interfaces.gateways.IProdutoRepositoryPort;
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

import java.math.BigDecimal;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PedidoProdutoUseCaseImplTest {

    @InjectMocks
    private PedidoProdutoUseCaseImpl pedidoProdutoUseCase;

    @Mock
    private IPedidoProdutoRepositoryPort pedidoProdutoRepositoryPort;

    @Mock
    private IPedidoRepositoryPort pedidoRepositoryPort;

    @Mock
    private IProdutoRepositoryPort produtoRepositoryPort;

    AutoCloseable mock;

    @BeforeEach
    void setup() {
        mock = MockitoAnnotations.openMocks(this);
        pedidoProdutoUseCase = new PedidoProdutoUseCaseImpl(
                pedidoProdutoRepositoryPort,
                pedidoRepositoryPort,
                produtoRepositoryPort
        );
    }

    @AfterEach
    void tearDown() throws Exception {
        mock.close();
    }

    @Nested
    class AdicionarItem {

        @Test
        @Severity(SeverityLevel.BLOCKER)
        @Description("Adicionar item no pedido")
        void deveCadastrarPedidoProduto() {
            var uuidPedido = UUID.randomUUID();
            var uuidProduto = UUID.randomUUID();

            var pedido = Helper.gerarPedidoComIDComClienteComIDEProdutos();
            pedido.setIdPedido(uuidPedido);

            var produto = Helper.gerarProdutoLanche();
            produto.setIdProduto(uuidProduto);

            var pedidoProduto = Helper.gerarPedidoProduto();
            pedidoProduto.setPedidoId(UUID.randomUUID());

            when(pedidoRepositoryPort.buscarPorId(any(UUID.class))).thenReturn(Optional.of(pedido));
            when(produtoRepositoryPort.buscarPorId(any(UUID.class))).thenReturn(Optional.of(produto));
            when(pedidoProdutoRepositoryPort
                    .obterTodosOsProdutosAssociadosAoPedidoPeloIdPedido(any(UUID.class)))
                    .thenReturn(List.of(produto));
            when(pedidoProdutoRepositoryPort.adicionarPedidoProduto(
                    any(Pedido.class),
                    any(Produto.class),
                    any(PedidoProduto.class))).thenReturn(pedidoProduto);
            when(pedidoRepositoryPort.atualizarPedido(any(Pedido.class))).thenReturn(pedido);

            var pedidoSalvo = pedidoProdutoUseCase
                    .adicionarItemNoPedido(pedidoProduto);

            assertThat(pedidoSalvo).isNotNull();
            assertThat(pedidoSalvo).isInstanceOf(Pedido.class);
            assertThat(pedidoSalvo.getIdPedido()).isEqualTo(pedidoSalvo.getIdPedido());
            assertThat(pedidoSalvo.getProdutos().get(0).getTipoProduto())
                    .isEqualTo(pedidoSalvo.getProdutos().get(0).getTipoProduto());

            verify(pedidoRepositoryPort, times(1)).buscarPorId(any(UUID.class));
            verify(produtoRepositoryPort, times(1)).buscarPorId(any(UUID.class));
            verify(pedidoProdutoRepositoryPort, times(1))
                    .obterTodosOsProdutosAssociadosAoPedidoPeloIdPedido(any(UUID.class));
            verify(pedidoProdutoRepositoryPort, times(1))
                    .adicionarPedidoProduto(
                            any(Pedido.class),
                            any(Produto.class),
                            any(PedidoProduto.class));

        }
    }

    @Nested
    class RemoverItem {
        @Test
        void deveRemoverItemDoPedido() {
            UUID pedidoId = UUID.randomUUID();
            UUID produtoId = UUID.randomUUID();

            Pedido pedido = Helper.gerarPedidoComIDComClienteComIDEProdutos();
            pedido.setIdPedido(pedidoId);
            Produto produto = Helper.gerarProdutoLanche();
            produto.setIdProduto(produtoId);

            PedidoProduto pedidoProduto = Helper.gerarPedidoProduto();
            pedidoProduto.setPedidoId(pedidoId);
            pedidoProduto.setProdutoId(produtoId);

            List<Produto> produtosAssociadosAoPedido = Collections.singletonList(produto);

            when(pedidoRepositoryPort.buscarPorId(pedidoId)).thenReturn(Optional.of(pedido));
            when(produtoRepositoryPort.buscarPorId(produtoId)).thenReturn(Optional.of(produto));
            when(pedidoProdutoRepositoryPort.obterTodosOsProdutosAssociadosAoPedidoPeloIdPedido(pedidoId))
                    .thenReturn(produtosAssociadosAoPedido);

            Pedido pedidoAtualizado = Helper.gerarPedidoComIDComClienteComIDEProdutos();
            pedidoAtualizado.setIdPedido(pedidoId);
            pedidoAtualizado.setDataAtualizacao(new Date());

            doNothing().when(pedidoProdutoRepositoryPort).excluirPedidoProduto(pedidoId, produtoId);
            when(pedidoRepositoryPort.atualizarPedido(any(Pedido.class))).thenReturn(pedidoAtualizado);

            Pedido resultado = pedidoProdutoUseCase.removerItemDoPedido(pedidoProduto);

            assertThat(resultado).isNotNull();
            assertThat(resultado.getIdPedido()).isEqualTo(pedidoId);

            verify(pedidoRepositoryPort, times(1)).buscarPorId(pedidoId);
            verify(produtoRepositoryPort, times(1)).buscarPorId(produtoId);
            verify(pedidoProdutoRepositoryPort, times(1)).obterTodosOsProdutosAssociadosAoPedidoPeloIdPedido(pedidoId);
            verify(pedidoProdutoRepositoryPort, times(1)).excluirPedidoProduto(pedidoId, produtoId);
            verify(pedidoRepositoryPort, times(1)).atualizarPedido(any(Pedido.class));
        }
    }
}

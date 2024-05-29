package com.fiap.pedidos.gateways;

import com.fiap.pedidos.entities.Pedido;
import com.fiap.pedidos.gateways.entities.PedidoEntity;
import com.fiap.pedidos.helpers.Helper;
import com.fiap.pedidos.interfaces.gateways.IPedidoRepositoryPort;
import com.fiap.pedidos.interfaces.repositories.PedidoProdutoRepository;
import com.fiap.pedidos.interfaces.repositories.PedidoRepository;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PedidoRepositoryAdapterTest {

    private IPedidoRepositoryPort pedidoRepositoryPort;

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private PedidoProdutoRepository pedidoProdutoRepository;

    AutoCloseable mock;

    @BeforeEach
    void setup() {
        mock = MockitoAnnotations.openMocks(this);
        pedidoRepositoryPort = new PedidoRepositoryAdapter(pedidoRepository, pedidoProdutoRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        mock.close();
    }

    @Nested
    class CadastrarPedido {

        @Test
        @Severity(SeverityLevel.BLOCKER)
        @Description("Cadastrar pedido")
        void deveCadastrarPedido() {
            var pedido = Helper.gerarPedidoComCliente();
            var pedidoEntity = new PedidoEntity().from(pedido, true);
            pedidoEntity.setIdPedido(UUID.randomUUID());

            when(pedidoRepository.save(any(PedidoEntity.class))).thenReturn(pedidoEntity);

            var pedidoSalvo = pedidoRepositoryPort.cadastrar(pedido);

            assertThat(pedidoSalvo).isNotNull();
            assertThat(pedidoSalvo).isInstanceOf(Pedido.class);
            assertThat(pedidoSalvo.getIdPedido()).isNotNull();
            assertThat(pedidoSalvo.getValorPedido()).isEqualTo(new BigDecimal("0.0"));
            verify(pedidoRepository, times(1)).save(any(PedidoEntity.class));
        }
    }

    @Nested
    class AtualizarPedido {

        @Test
        @Severity(SeverityLevel.BLOCKER)
        @Description("Atualizar pedido")
        void deveAtualizarPedido() {
            var pedido = Helper.gerarPedidoComCliente();
            var pedidoEntity = new PedidoEntity().from(pedido, false);
            pedidoEntity.setIdPedido(UUID.randomUUID());

            when(pedidoRepository.save(any(PedidoEntity.class))).thenReturn(pedidoEntity);

            var pedidoSalvo = pedidoRepositoryPort.atualizarPedido(pedido);

            assertThat(pedidoSalvo).isNotNull();
            assertThat(pedidoSalvo).isInstanceOf(Pedido.class);
            assertThat(pedidoSalvo.getIdPedido()).isNotNull();
            verify(pedidoRepository, times(1)).save(any(PedidoEntity.class));
        }
    }

    @Nested
    class BuscarPedido {

        @Test
        @Severity(SeverityLevel.CRITICAL)
        @Description("Buscar pedido")
        void deveBuscarPedido() {
            var pedido = Helper.gerarPedidoComCliente();
            var uuid = UUID.randomUUID();
            var pedidoEntity = new PedidoEntity().from(pedido, false);
            pedidoEntity.setIdPedido(uuid);

            when(pedidoRepository.findById(any(UUID.class))).thenReturn(Optional.of(pedidoEntity));

            var pedidoSalvo = pedidoRepositoryPort.buscarPorId(uuid);

            assertThat(pedidoSalvo.isEmpty()).isFalse();
            assertThat(pedidoSalvo.get()).isInstanceOf(Pedido.class);
            assertThat(pedidoSalvo.get().getIdPedido()).isEqualTo(uuid);
            verify(pedidoRepository, times(1)).findById(any(UUID.class));
        }

        @Test
        @Severity(SeverityLevel.CRITICAL)
        @Description("Buscar todos os pedidos excluindo os status finalizado")
        void deveBuscarTodosOsPedidos() {
            var pedido = Helper.gerarPedidoComCliente();
            var uuidPedido1 = UUID.randomUUID();
            var uuidPedido2 = UUID.randomUUID();

            var pedidoEntityList = new ArrayList<PedidoEntity>();

            pedidoEntityList.add(new PedidoEntity().from(pedido, false));
            pedidoEntityList.add(new PedidoEntity().from(pedido, false));
            pedidoEntityList.get(0).setIdPedido(uuidPedido1);
            pedidoEntityList.get(1).setIdPedido(uuidPedido2);

            when(pedidoRepository
                    .listagemOrdenadaPorStatusExcluindoFinalizados(any(Pageable.class)))
                    .thenReturn(pedidoEntityList);

            var pedidoSalvo = pedidoRepositoryPort.buscarTodos(0, 100);

            assertThat(pedidoSalvo.isEmpty()).isFalse();

            assertThat(pedidoSalvo.get(0)).isInstanceOf(Pedido.class);
            assertThat(pedidoSalvo.get(0).getIdPedido()).isEqualTo(uuidPedido1);

            assertThat(pedidoSalvo.get(1)).isInstanceOf(Pedido.class);
            assertThat(pedidoSalvo.get(1).getIdPedido()).isEqualTo(uuidPedido2);


            verify(pedidoRepository, times(1))
                    .listagemOrdenadaPorStatusExcluindoFinalizados(any(Pageable.class));
        }

        @Test
        @Severity(SeverityLevel.CRITICAL)
        @Description("Remover pedido sem produto associado")
        void deveRemoverPedidoSemProduto() {
            var pedido = Helper.gerarPedidoComCliente();
            var uuid = UUID.randomUUID();
            var pedidoEntity = new PedidoEntity().from(pedido, false);
            pedidoEntity.setIdPedido(uuid);

            when(pedidoRepository.findById(any(UUID.class))).thenReturn(Optional.of(pedidoEntity));
            doNothing().when(pedidoRepository).deleteById(any(UUID.class));
            doNothing().when(pedidoProdutoRepository).deleteById(any(UUID.class));

            pedidoRepositoryPort.remover(uuid);

            verify(pedidoProdutoRepository, never()).deleteById(any(UUID.class));
            verify(pedidoRepository, times(1)).delete(any(PedidoEntity.class));
            verify(pedidoRepository, times(1)).findById(any(UUID.class));
        }

        @Test
        @Severity(SeverityLevel.CRITICAL)
        @Description("Remover pedido com produto associado")
        void deveRemoverPedidoComProduto() {
            var pedido = Helper.gerarPedidoComClienteEProdutos();
            var uuid = UUID.randomUUID();
            var pedidoEntity = new PedidoEntity().from(pedido, false);
            var pedidoProdutoEntity = Helper.gerarPedidoProdutoEntity();
            pedidoProdutoEntity.setId(uuid);

            pedidoEntity.setProdutos(List.of(pedidoProdutoEntity));
            pedidoEntity.setIdPedido(uuid);

            when(pedidoRepository.findById(any(UUID.class))).thenReturn(Optional.of(pedidoEntity));
            doNothing().when(pedidoRepository).deleteById(any(UUID.class));
            doNothing().when(pedidoProdutoRepository).deleteById(any(UUID.class));

            pedidoRepositoryPort.remover(uuid);

            verify(pedidoProdutoRepository, times(1)).deleteById(any(UUID.class));
            verify(pedidoRepository, times(1)).delete(any(PedidoEntity.class));
            verify(pedidoRepository, times(1)).findById(any(UUID.class));
        }
    }
}






package com.fiap.pedidos.usecases;

import com.fiap.pedidos.entities.Produto;
import com.fiap.pedidos.gateways.entities.ProdutoEntity;
import com.fiap.pedidos.helpers.Helper;
import com.fiap.pedidos.interfaces.gateways.IProdutoRepositoryPort;
import com.fiap.pedidos.interfaces.usecases.IProdutoUseCasePort;
import com.fiap.pedidos.utils.enums.TipoProduto;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProdutoUseCaseImplTest {

    private IProdutoUseCasePort produtoUseCaseImpl;

    @Mock
    private IProdutoRepositoryPort produtoRepositoryPort;

    AutoCloseable mock;

    @BeforeEach
    void setup() {
        mock = MockitoAnnotations.openMocks(this);
        produtoUseCaseImpl = new ProdutoUseCaseImpl(produtoRepositoryPort);
    }

    @AfterEach
    void tearDown() throws Exception {
        mock.close();
    }

    @Nested
    class CadastrarProduto {
        //        @Override
//        public Produto criarProduto(Produto produto) {
//            return produtoRepositoryPort.criarProduto(produto);
//        }
        @Test
        @Severity(SeverityLevel.BLOCKER)
        @Description("Cadastrar bebida")
        void deveCadastrarProdutoBebida() {
            var produto = Helper.gerarProdutoBebida();
            produto.setIdProduto(UUID.randomUUID());

            when(produtoRepositoryPort.criarProduto(any(Produto.class))).thenReturn(produto);

            var produtoSalvo = produtoUseCaseImpl.criarProduto(produto);

            assertThat(produtoSalvo).isNotNull();
            assertThat(produtoSalvo).isInstanceOf(Produto.class);
            assertThat(produtoSalvo.getIdProduto()).isNotNull();
            assertThat(produtoSalvo.getTipoProduto().getCodigo()).isEqualTo(TipoProduto.BEBIDA.getCodigo());
            assertThat(produtoSalvo.getNomeProduto().getNome()).isEqualTo(produto.getNomeProduto().getNome());
            assertThat(produtoSalvo.getValorProduto().getValorProduto()).isEqualTo(produto.getValorProduto().getValorProduto());
            assertThat(produtoSalvo.getDescricaoProduto().getDescricao()).isEqualTo(produto.getDescricaoProduto().getDescricao());

            verify(produtoRepositoryPort, times(1)).criarProduto(any(Produto.class));
        }

        @Test
        @Severity(SeverityLevel.BLOCKER)
        @Description("Cadastrar Acompanhamento")
        void deveCadastrarProdutoAcompanhamento() {
            var produto = Helper.gerarProdutoAcompanhamento();

            produto.setIdProduto(UUID.randomUUID());

            when(produtoRepositoryPort.criarProduto(any(Produto.class))).thenReturn(produto);

            var produtoSalvo = produtoUseCaseImpl.criarProduto(produto);

            assertThat(produtoSalvo).isNotNull();
            assertThat(produtoSalvo).isInstanceOf(Produto.class);
            assertThat(produtoSalvo.getIdProduto()).isNotNull();
            assertThat(produtoSalvo.getTipoProduto().getCodigo()).isEqualTo(TipoProduto.ACOMPANHAMENTO.getCodigo());
            assertThat(produtoSalvo.getNomeProduto().getNome()).isEqualTo(produto.getNomeProduto().getNome());
            assertThat(produtoSalvo.getValorProduto().getValorProduto()).isEqualTo(produto.getValorProduto().getValorProduto());
            assertThat(produtoSalvo.getDescricaoProduto().getDescricao()).isEqualTo(produto.getDescricaoProduto().getDescricao());

            verify(produtoRepositoryPort, times(1)).criarProduto(any(Produto.class));
        }

        @Test
        @Severity(SeverityLevel.BLOCKER)
        @Description("Cadastrar Lanche")
        void deveCadastrarProdutoLanche() {
            var produto = Helper.gerarProdutoLanche();

            produto.setIdProduto(UUID.randomUUID());

            when(produtoRepositoryPort.criarProduto(any(Produto.class))).thenReturn(produto);

            var produtoSalvo = produtoUseCaseImpl.criarProduto(produto);

            assertThat(produtoSalvo).isNotNull();
            assertThat(produtoSalvo).isInstanceOf(Produto.class);
            assertThat(produtoSalvo.getIdProduto()).isNotNull();
            assertThat(produtoSalvo.getTipoProduto().getCodigo()).isEqualTo(TipoProduto.LANCHE.getCodigo());
            assertThat(produtoSalvo.getNomeProduto().getNome()).isEqualTo(produto.getNomeProduto().getNome());
            assertThat(produtoSalvo.getValorProduto().getValorProduto()).isEqualTo(produto.getValorProduto().getValorProduto());
            assertThat(produtoSalvo.getDescricaoProduto().getDescricao()).isEqualTo(produto.getDescricaoProduto().getDescricao());

            verify(produtoRepositoryPort, times(1)).criarProduto(any(Produto.class));
        }

        @Test
        @Severity(SeverityLevel.BLOCKER)
        @Description("Cadastrar Sobremesa")
        void deveCadastrarProdutoSobremesa() {
            var produto = Helper.gerarProdutoSobremesa();

            produto.setIdProduto(UUID.randomUUID());

            when(produtoRepositoryPort.criarProduto(any(Produto.class))).thenReturn(produto);

            var produtoSalvo = produtoUseCaseImpl.criarProduto(produto);

            assertThat(produtoSalvo).isNotNull();
            assertThat(produtoSalvo).isInstanceOf(Produto.class);
            assertThat(produtoSalvo.getIdProduto()).isNotNull();
            assertThat(produtoSalvo.getTipoProduto().getCodigo()).isEqualTo(TipoProduto.SOBREMESA.getCodigo());
            assertThat(produtoSalvo.getNomeProduto().getNome()).isEqualTo(produto.getNomeProduto().getNome());
            assertThat(produtoSalvo.getValorProduto().getValorProduto()).isEqualTo(produto.getValorProduto().getValorProduto());
            assertThat(produtoSalvo.getDescricaoProduto().getDescricao()).isEqualTo(produto.getDescricaoProduto().getDescricao());

            verify(produtoRepositoryPort, times(1)).criarProduto(any(Produto.class));
        }
    }

    @Nested
    class RemoverProduto {

        @Test
        @Severity(SeverityLevel.BLOCKER)
        @Description("Remover produto")
        void deveRemoverProduto() {
            var produto = Helper.gerarProdutoSobremesa();
            var produtoEntity = new ProdutoEntity().from(produto, true);
            var idRandom = UUID.randomUUID();
            produtoEntity.setIdProduto(idRandom);

            doNothing().when(produtoRepositoryPort).deletarProduto(any(UUID.class));

            produtoRepositoryPort.deletarProduto(idRandom);

            verify(produtoRepositoryPort, times(1)).deletarProduto(any(UUID.class));
        }
    }

    @Nested
    class BuscarProduto {

        @Test
        @Severity(SeverityLevel.CRITICAL)
        @Description("Buscar Produto por idproduto")
        void deveBuscarProdutoPorIDProduto() {
            var produto = Helper.gerarProdutoSobremesa();
            var idRandom = UUID.randomUUID();

            when(produtoRepositoryPort.buscarPorId(any(UUID.class))).thenReturn(Optional.of(produto));

            var produtoBuscado = produtoUseCaseImpl.buscarPorId(idRandom);

            assertThat(produtoBuscado.isEmpty()).isFalse();
            assertThat(produtoBuscado.get()).isInstanceOf(Produto.class);
            assertThat(produtoBuscado.get().getTipoProduto().getCodigo()).isEqualTo(TipoProduto.SOBREMESA.getCodigo());
            assertThat(produtoBuscado.get().getNomeProduto().getNome()).isEqualTo(produto.getNomeProduto().getNome());
            assertThat(produtoBuscado.get().getValorProduto().getValorProduto()).isEqualTo(produto.getValorProduto().getValorProduto());
            assertThat(produtoBuscado.get().getDescricaoProduto().getDescricao()).isEqualTo(produto.getDescricaoProduto().getDescricao());

            verify(produtoRepositoryPort, times(1)).buscarPorId(any(UUID.class));
        }

        @Test
        @Severity(SeverityLevel.CRITICAL)
        @Description("Buscar Produto sobremesa")
        void deveBuscarProdutoPorTipoProdutoSobremesa() {
            var produto = Helper.gerarProdutoSobremesa();

            when(produtoRepositoryPort.listarProdutosPorTipo(any(TipoProduto.class)))
                    .thenReturn(List.of(produto));

            var produtoBuscado = produtoRepositoryPort.listarProdutosPorTipo(TipoProduto.SOBREMESA);

            assertThat(produtoBuscado.isEmpty()).isFalse();
            assertThat(produtoBuscado.size()).isEqualTo(1);
            assertThat(produtoBuscado.get(0).getTipoProduto().getCodigo()).isEqualTo(TipoProduto.SOBREMESA.getCodigo());
            assertThat(produtoBuscado.get(0).getNomeProduto().getNome()).isEqualTo(produto.getNomeProduto().getNome());
            assertThat(produtoBuscado.get(0).getValorProduto().getValorProduto()).isEqualTo(produto.getValorProduto().getValorProduto());
            assertThat(produtoBuscado.get(0).getDescricaoProduto().getDescricao()).isEqualTo(produto.getDescricaoProduto().getDescricao());

            verify(produtoRepositoryPort, times(1)).listarProdutosPorTipo(any(TipoProduto.class));
        }

        @Test
        @Severity(SeverityLevel.CRITICAL)
        @Description("Buscar Produto Lanche")
        void deveBuscarProdutoPorTipoProdutoLanche() {
            var produto = Helper.gerarProdutoLanche();

            when(produtoRepositoryPort.listarProdutosPorTipo(any(TipoProduto.class)))
                    .thenReturn(List.of(produto));

            var produtoBuscado = produtoRepositoryPort.listarProdutosPorTipo(TipoProduto.LANCHE);

            assertThat(produtoBuscado.isEmpty()).isFalse();
            assertThat(produtoBuscado.size()).isEqualTo(1);
            assertThat(produtoBuscado.get(0).getTipoProduto().getCodigo()).isEqualTo(TipoProduto.LANCHE.getCodigo());
            assertThat(produtoBuscado.get(0).getNomeProduto().getNome()).isEqualTo(produto.getNomeProduto().getNome());
            assertThat(produtoBuscado.get(0).getValorProduto().getValorProduto()).isEqualTo(produto.getValorProduto().getValorProduto());
            assertThat(produtoBuscado.get(0).getDescricaoProduto().getDescricao()).isEqualTo(produto.getDescricaoProduto().getDescricao());

            verify(produtoRepositoryPort, times(1)).listarProdutosPorTipo(any(TipoProduto.class));
        }

        @Test
        @Severity(SeverityLevel.CRITICAL)
        @Description("Buscar Produto Bebida")
        void deveBuscarProdutoPorTipoProdutoBebida() {
            var produto = Helper.gerarProdutoBebida();

            when(produtoRepositoryPort.listarProdutosPorTipo(any(TipoProduto.class)))
                    .thenReturn(List.of(produto));

            var produtoBuscado = produtoRepositoryPort.listarProdutosPorTipo(TipoProduto.BEBIDA);

            assertThat(produtoBuscado.isEmpty()).isFalse();
            assertThat(produtoBuscado.size()).isEqualTo(1);
            assertThat(produtoBuscado.get(0).getTipoProduto().getCodigo()).isEqualTo(TipoProduto.BEBIDA.getCodigo());
            assertThat(produtoBuscado.get(0).getNomeProduto().getNome()).isEqualTo(produto.getNomeProduto().getNome());
            assertThat(produtoBuscado.get(0).getValorProduto().getValorProduto()).isEqualTo(produto.getValorProduto().getValorProduto());
            assertThat(produtoBuscado.get(0).getDescricaoProduto().getDescricao()).isEqualTo(produto.getDescricaoProduto().getDescricao());

            verify(produtoRepositoryPort, times(1)).listarProdutosPorTipo(any(TipoProduto.class));
        }

        @Test
        @Severity(SeverityLevel.CRITICAL)
        @Description("Buscar Produto Acompanhamento")
        void deveBuscarProdutoPorTipoProdutoAcomponhamento() {
            var produto = Helper.gerarProdutoAcompanhamento();

            when(produtoRepositoryPort.listarProdutosPorTipo(any(TipoProduto.class)))
                    .thenReturn(List.of(produto));

            var produtoBuscado = produtoRepositoryPort.listarProdutosPorTipo(TipoProduto.ACOMPANHAMENTO);

            assertThat(produtoBuscado.isEmpty()).isFalse();
            assertThat(produtoBuscado.size()).isEqualTo(1);
            assertThat(produtoBuscado.get(0).getTipoProduto().getCodigo()).isEqualTo(TipoProduto.ACOMPANHAMENTO.getCodigo());
            assertThat(produtoBuscado.get(0).getNomeProduto().getNome()).isEqualTo(produto.getNomeProduto().getNome());
            assertThat(produtoBuscado.get(0).getValorProduto().getValorProduto()).isEqualTo(produto.getValorProduto().getValorProduto());
            assertThat(produtoBuscado.get(0).getDescricaoProduto().getDescricao()).isEqualTo(produto.getDescricaoProduto().getDescricao());

            verify(produtoRepositoryPort, times(1)).listarProdutosPorTipo(any(TipoProduto.class));
        }
    }

    @Nested
    class DeletarProduto {
        @Test
        @Severity(SeverityLevel.CRITICAL)
        @Description("Deletar Produto")
        void deveDeletar() {
            var produto = Helper.gerarProdutoAcompanhamento();

            doNothing().when(produtoRepositoryPort).deletarProduto(any(UUID.class));

            produtoUseCaseImpl.deletarProduto(UUID.randomUUID());

            verify(produtoRepositoryPort, times(1)).deletarProduto(any(UUID.class));
        }
    }
}




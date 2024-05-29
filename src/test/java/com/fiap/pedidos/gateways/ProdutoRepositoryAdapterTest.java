package com.fiap.pedidos.gateways;

import com.fiap.pedidos.entities.Produto;
import com.fiap.pedidos.exceptions.entities.NomeInvalidoException;
import com.fiap.pedidos.exceptions.entities.ProdutoNaoEncontradoException;
import com.fiap.pedidos.gateways.entities.ProdutoEntity;
import com.fiap.pedidos.helpers.Helper;
import com.fiap.pedidos.interfaces.gateways.IProdutoRepositoryPort;
import com.fiap.pedidos.interfaces.repositories.ProdutoRepository;
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

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProdutoRepositoryAdapterTest {

    private IProdutoRepositoryPort produtoRepositoryPort;

    @Mock
    private ProdutoRepository produtoRepository;

    AutoCloseable mock;

    @BeforeEach
    void setup() {
        mock = MockitoAnnotations.openMocks(this);
        produtoRepositoryPort = new ProdutoRepositoryAdapter(produtoRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        mock.close();
    }

    @Nested
    class CadastrarProduto {

        @Test
        @Severity(SeverityLevel.BLOCKER)
        @Description("Cadastrar bebida")
        void deveCadastrarProdutoBebida() {
            var produto = Helper.gerarProdutoBebida();
            var produtoEntity = new ProdutoEntity().from(produto, true);
            produtoEntity.setIdProduto(UUID.randomUUID());

            when(produtoRepository.save(any(ProdutoEntity.class))).thenReturn(produtoEntity);

            var produtoSalvo = produtoRepositoryPort.criarProduto(produto);

            assertThat(produtoSalvo).isNotNull();
            assertThat(produtoSalvo).isInstanceOf(Produto.class);
            assertThat(produtoSalvo.getIdProduto()).isNotNull();
            assertThat(produtoSalvo.getTipoProduto().getCodigo()).isEqualTo(TipoProduto.BEBIDA.getCodigo());
            assertThat(produtoSalvo.getNomeProduto().getNome()).isEqualTo(produto.getNomeProduto().getNome());
            assertThat(produtoSalvo.getValorProduto().getValorProduto()).isEqualTo(produto.getValorProduto().getValorProduto());
            assertThat(produtoSalvo.getDescricaoProduto().getDescricao()).isEqualTo(produto.getDescricaoProduto().getDescricao());

            verify(produtoRepository, times(1)).save(any(ProdutoEntity.class));
        }

        @Test
        @Severity(SeverityLevel.BLOCKER)
        @Description("Cadastrar Acompanhamento")
        void deveCadastrarProdutoAcompanhamento() {
            var produto = Helper.gerarProdutoAcompanhamento();
            var produtoEntity = new ProdutoEntity().from(produto, true);
            produtoEntity.setIdProduto(UUID.randomUUID());

            when(produtoRepository.save(any(ProdutoEntity.class))).thenReturn(produtoEntity);

            var produtoSalvo = produtoRepositoryPort.criarProduto(produto);

            assertThat(produtoSalvo).isNotNull();
            assertThat(produtoSalvo).isInstanceOf(Produto.class);
            assertThat(produtoSalvo.getIdProduto()).isNotNull();
            assertThat(produtoSalvo.getTipoProduto().getCodigo()).isEqualTo(TipoProduto.ACOMPANHAMENTO.getCodigo());
            assertThat(produtoSalvo.getNomeProduto().getNome()).isEqualTo(produto.getNomeProduto().getNome());
            assertThat(produtoSalvo.getValorProduto().getValorProduto()).isEqualTo(produto.getValorProduto().getValorProduto());
            assertThat(produtoSalvo.getDescricaoProduto().getDescricao()).isEqualTo(produto.getDescricaoProduto().getDescricao());

            verify(produtoRepository, times(1)).save(any(ProdutoEntity.class));
        }

        @Test
        @Severity(SeverityLevel.BLOCKER)
        @Description("Cadastrar Lanche")
        void deveCadastrarProdutoLanche() {
            var produto = Helper.gerarProdutoLanche();
            var produtoEntity = new ProdutoEntity().from(produto, true);
            produtoEntity.setIdProduto(UUID.randomUUID());

            when(produtoRepository.save(any(ProdutoEntity.class))).thenReturn(produtoEntity);

            var produtoSalvo = produtoRepositoryPort.criarProduto(produto);

            assertThat(produtoSalvo).isNotNull();
            assertThat(produtoSalvo).isInstanceOf(Produto.class);
            assertThat(produtoSalvo.getIdProduto()).isNotNull();
            assertThat(produtoSalvo.getTipoProduto().getCodigo()).isEqualTo(TipoProduto.LANCHE.getCodigo());
            assertThat(produtoSalvo.getNomeProduto().getNome()).isEqualTo(produto.getNomeProduto().getNome());
            assertThat(produtoSalvo.getValorProduto().getValorProduto()).isEqualTo(produto.getValorProduto().getValorProduto());
            assertThat(produtoSalvo.getDescricaoProduto().getDescricao()).isEqualTo(produto.getDescricaoProduto().getDescricao());

            verify(produtoRepository, times(1)).save(any(ProdutoEntity.class));
        }

        @Test
        @Severity(SeverityLevel.BLOCKER)
        @Description("Cadastrar Sobremesa")
        void deveCadastrarProdutoSobremesa() {
            var produto = Helper.gerarProdutoSobremesa();
            var produtoEntity = new ProdutoEntity().from(produto, true);
            produtoEntity.setIdProduto(UUID.randomUUID());

            when(produtoRepository.save(any(ProdutoEntity.class))).thenReturn(produtoEntity);

            var produtoSalvo = produtoRepositoryPort.criarProduto(produto);

            assertThat(produtoSalvo).isNotNull();
            assertThat(produtoSalvo).isInstanceOf(Produto.class);
            assertThat(produtoSalvo.getIdProduto()).isNotNull();
            assertThat(produtoSalvo.getTipoProduto().getCodigo()).isEqualTo(TipoProduto.SOBREMESA.getCodigo());
            assertThat(produtoSalvo.getNomeProduto().getNome()).isEqualTo(produto.getNomeProduto().getNome());
            assertThat(produtoSalvo.getValorProduto().getValorProduto()).isEqualTo(produto.getValorProduto().getValorProduto());
            assertThat(produtoSalvo.getDescricaoProduto().getDescricao()).isEqualTo(produto.getDescricaoProduto().getDescricao());

            verify(produtoRepository, times(1)).save(any(ProdutoEntity.class));
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

            when(produtoRepository.findById(any(UUID.class))).thenReturn(Optional.of(produtoEntity));
            when(produtoRepository.save(any(ProdutoEntity.class))).thenReturn(produtoEntity);

            produtoRepositoryPort.deletarProduto(idRandom);

            verify(produtoRepository, times(1)).save(any(ProdutoEntity.class));
            verify(produtoRepository, times(1)).findById(any(UUID.class));
        }

        @Test
        @Severity(SeverityLevel.BLOCKER)
        @Description("Remover produto que não existe")
        void deveFalhar_AoTentaRemoverProdutoQueNaoExiste() {
            var produto = Helper.gerarProdutoSobremesa();
            var produtoEntity = new ProdutoEntity().from(produto, true);
            var idRandom = UUID.randomUUID();
            produtoEntity.setIdProduto(idRandom);

            when(produtoRepository.findById(any(UUID.class))).thenReturn(Optional.empty());
            when(produtoRepository.save(any(ProdutoEntity.class))).thenReturn(produtoEntity);

            assertThatThrownBy(() -> produtoRepositoryPort.deletarProduto(idRandom))
                    .isInstanceOf(ProdutoNaoEncontradoException.class)
                            .hasMessage("Produto não encontrado");

            verify(produtoRepository, times(1)).findById(any(UUID.class));
        }

    }

    @Nested
    class BuscarProduto {

        @Test
        @Severity(SeverityLevel.BLOCKER)
        @Description("Buscar Produto por idproduto")
        void deveBuscarProdutoPorIDProduto() {
            var produto = Helper.gerarProdutoSobremesa();
            var produtoEntity = new ProdutoEntity().from(produto, true);
            var idRandom = UUID.randomUUID();
            produtoEntity.setIdProduto(idRandom);

            when(produtoRepository.findById(any(UUID.class))).thenReturn(Optional.of(produtoEntity));

            var produtoBuscado = produtoRepositoryPort.buscarPorId(idRandom);


            assertThat(produtoBuscado.isEmpty()).isFalse();
            assertThat(produtoBuscado.get()).isInstanceOf(Produto.class);
            assertThat(produtoBuscado.get().getIdProduto()).isNotNull();
            assertThat(produtoBuscado.get().getTipoProduto().getCodigo()).isEqualTo(TipoProduto.SOBREMESA.getCodigo());
            assertThat(produtoBuscado.get().getNomeProduto().getNome()).isEqualTo(produto.getNomeProduto().getNome());
            assertThat(produtoBuscado.get().getValorProduto().getValorProduto()).isEqualTo(produto.getValorProduto().getValorProduto());
            assertThat(produtoBuscado.get().getDescricaoProduto().getDescricao()).isEqualTo(produto.getDescricaoProduto().getDescricao());

            verify(produtoRepository, times(1)).findById(any(UUID.class));
        }

        @Test
        @Severity(SeverityLevel.CRITICAL)
        @Description("Buscar Produto sobremesa")
        void deveBuscarProdutoPorTipoProdutoSobremesa() {
            var produtoSobremesa = Helper.gerarProdutoSobremesa();

            var produtoEntityList = new ArrayList<ProdutoEntity>();
            produtoEntityList.add( new ProdutoEntity().from(produtoSobremesa, true));

            var idRandomSobremesa = UUID.randomUUID();

            produtoEntityList.get(0).setIdProduto(idRandomSobremesa);


            when(produtoRepository.findAllByTipoProdutoAndAtivo(any(String.class), any(Boolean.class)))
                    .thenReturn(Optional.of(produtoEntityList));

            var produtoBuscado = produtoRepositoryPort.listarProdutosPorTipo(TipoProduto.SOBREMESA);

            assertThat(produtoBuscado.isEmpty()).isFalse();
            assertThat(produtoBuscado.size()).isEqualTo(1);
            assertThat(produtoBuscado.get(0).getIdProduto()).isNotNull();
            assertThat(produtoBuscado.get(0).getTipoProduto().getCodigo()).isEqualTo(TipoProduto.SOBREMESA.getCodigo());
            assertThat(produtoBuscado.get(0).getNomeProduto().getNome()).isEqualTo(produtoSobremesa.getNomeProduto().getNome());
            assertThat(produtoBuscado.get(0).getValorProduto().getValorProduto()).isEqualTo(produtoSobremesa.getValorProduto().getValorProduto());
            assertThat(produtoBuscado.get(0).getDescricaoProduto().getDescricao()).isEqualTo(produtoSobremesa.getDescricaoProduto().getDescricao());

            verify(produtoRepository, times(1)).findAllByTipoProdutoAndAtivo(any(String.class), any(Boolean.class));
        }

        @Test
        @Severity(SeverityLevel.CRITICAL)
        @Description("Buscar Produto Lanche")
        void deveBuscarProdutoPorTipoProdutoLanche() {
            var produtoLanche = Helper.gerarProdutoLanche();

            var produtoEntityList = new ArrayList<ProdutoEntity>();
            produtoEntityList.add( new ProdutoEntity().from(produtoLanche, true));

            var idRandomLanche = UUID.randomUUID();

            produtoEntityList.get(0).setIdProduto(idRandomLanche);

            when(produtoRepository.findAllByTipoProdutoAndAtivo(any(String.class), any(Boolean.class)))
                    .thenReturn(Optional.of(produtoEntityList));

            var produtoBuscado = produtoRepositoryPort.listarProdutosPorTipo(TipoProduto.LANCHE);

            assertThat(produtoBuscado.isEmpty()).isFalse();
            assertThat(produtoBuscado.size()).isEqualTo(1);
            assertThat(produtoBuscado.get(0).getIdProduto()).isNotNull();
            assertThat(produtoBuscado.get(0).getTipoProduto().getCodigo()).isEqualTo(TipoProduto.LANCHE.getCodigo());
            assertThat(produtoBuscado.get(0).getNomeProduto().getNome()).isEqualTo(produtoLanche.getNomeProduto().getNome());
            assertThat(produtoBuscado.get(0).getValorProduto().getValorProduto()).isEqualTo(produtoLanche.getValorProduto().getValorProduto());
            assertThat(produtoBuscado.get(0).getDescricaoProduto().getDescricao()).isEqualTo(produtoLanche.getDescricaoProduto().getDescricao());

            verify(produtoRepository, times(1)).findAllByTipoProdutoAndAtivo(any(String.class), any(Boolean.class));
        }

        @Test
        @Severity(SeverityLevel.CRITICAL)
        @Description("Buscar Produto Bebida")
        void deveBuscarProdutoPorTipoProdutoBebida() {
            var produtoBebida = Helper.gerarProdutoBebida();

            var produtoEntityList = new ArrayList<ProdutoEntity>();
            produtoEntityList.add( new ProdutoEntity().from(produtoBebida, true));
            var idRandomBebida = UUID.randomUUID();
            produtoEntityList.get(0).setIdProduto(idRandomBebida);

            when(produtoRepository.findAllByTipoProdutoAndAtivo(any(String.class), any(Boolean.class)))
                    .thenReturn(Optional.of(produtoEntityList));

            var produtoBuscado = produtoRepositoryPort.listarProdutosPorTipo(TipoProduto.BEBIDA);

            assertThat(produtoBuscado.isEmpty()).isFalse();
            assertThat(produtoBuscado.size()).isEqualTo(1);
            assertThat(produtoBuscado.get(0).getIdProduto()).isNotNull();
            assertThat(produtoBuscado.get(0).getTipoProduto().getCodigo()).isEqualTo(TipoProduto.BEBIDA.getCodigo());
            assertThat(produtoBuscado.get(0).getNomeProduto().getNome()).isEqualTo(produtoBebida.getNomeProduto().getNome());
            assertThat(produtoBuscado.get(0).getValorProduto().getValorProduto()).isEqualTo(produtoBebida.getValorProduto().getValorProduto());
            assertThat(produtoBuscado.get(0).getDescricaoProduto().getDescricao()).isEqualTo(produtoBebida.getDescricaoProduto().getDescricao());

            verify(produtoRepository, times(1)).findAllByTipoProdutoAndAtivo(any(String.class), any(Boolean.class));
        }

        @Test
        @Severity(SeverityLevel.CRITICAL)
        @Description("Buscar Produto Acompanhamento")
        void deveBuscarProdutoPorTipoProdutoAcomponhamento() {
            var produtoAcompanhamento = Helper.gerarProdutoAcompanhamento();

            var produtoEntityList = new ArrayList<ProdutoEntity>();
            produtoEntityList.add( new ProdutoEntity().from(produtoAcompanhamento, true));

            var idRandomAcompanhamento = UUID.randomUUID();

            produtoEntityList.get(0).setIdProduto(idRandomAcompanhamento);

            when(produtoRepository.findAllByTipoProdutoAndAtivo(any(String.class), any(Boolean.class)))
                    .thenReturn(Optional.of(produtoEntityList));

            var produtoBuscado = produtoRepositoryPort.listarProdutosPorTipo(TipoProduto.ACOMPANHAMENTO);

            assertThat(produtoBuscado.isEmpty()).isFalse();
            assertThat(produtoBuscado.size()).isEqualTo(1);
            assertThat(produtoBuscado.get(0).getIdProduto()).isNotNull();
            assertThat(produtoBuscado.get(0).getTipoProduto().getCodigo()).isEqualTo(TipoProduto.ACOMPANHAMENTO.getCodigo());

            verify(produtoRepository, times(1)).findAllByTipoProdutoAndAtivo(any(String.class), any(Boolean.class));
        }
    }
}




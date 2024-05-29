package com.fiap.pedidos.controllers;

import com.fiap.pedidos.adapters.ProdutoDTO;
import com.fiap.pedidos.controllers.requestValidations.ProdutoRequest;
import com.fiap.pedidos.entities.Produto;
import com.fiap.pedidos.helpers.Helper;
import com.fiap.pedidos.interfaces.usecases.IClienteUseCasePort;
import com.fiap.pedidos.interfaces.usecases.IProdutoUseCasePort;
import com.fiap.pedidos.utils.enums.TipoProduto;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ProdutoControllerTest {
    private MockMvc mockMvc;
    @Mock
    private IProdutoUseCasePort produtoUseCasePort;
    AutoCloseable mock;

    @BeforeEach
    void setup() {
        mock = MockitoAnnotations.openMocks(this);
        ProdutoController produtoController = new ProdutoController(produtoUseCasePort);
        mockMvc = MockMvcBuilders.standaloneSetup(produtoController).addFilter((request, response, chain) -> {
            response.setCharacterEncoding("UTF-8");
            chain.doFilter(request, response);
        }).build();
    }

    @AfterEach
    void tearDown() throws Exception {
        mock.close();
    }

    @Nested
    class BuscarProduto {
        @Test
        @Severity(SeverityLevel.BLOCKER)
        @Description("Buscar todos os produtos")
        void deveBuscarTodosOsProdutos() throws Exception {
            var produto = Helper.gerarProdutoBebida();

            when(produtoUseCasePort.listarProdutosPorTipoProduto(any(TipoProduto.class)))
                    .thenReturn(List.of(produto));

            mockMvc.perform(get("/produtos")
                            .queryParam("tipo_produto", TipoProduto.BEBIDA.getCodigo()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.[0].descricao").value(produto.getDescricaoProduto().getDescricao()))
                    .andExpect(jsonPath("$.[0].nome").value(produto.getNomeProduto().getNome()))
                    .andExpect(jsonPath("$.[0].tipo").value(produto.getTipoProduto().name()));

            verify(produtoUseCasePort, times(1))
                    .listarProdutosPorTipoProduto(any(TipoProduto.class));
        }
    }

    @Nested
    class CadastrarProduto {
        @Test
        @Severity(SeverityLevel.BLOCKER)
        @Description("Cadastrar produto lanche")
        void deveCadastrarProdutoLanche() throws Exception {
            var produtoRequest = Helper.gerarProdutoRequest(TipoProduto.LANCHE);
            var produto = Helper.gerarProdutoAPartirDeUmProdutoRequest(produtoRequest);

            when(produtoUseCasePort.criarProduto(any(Produto.class))).thenReturn(produto);

            mockMvc.perform(post("/produtos")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(Helper.asJsonString(produtoRequest)))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.descricao").value(produto.getDescricaoProduto().getDescricao()))
                    .andExpect(jsonPath("$.nome").value(produto.getNomeProduto().getNome()))
                    .andExpect(jsonPath("$.tipo").value(produto.getTipoProduto().name()));

            verify(produtoUseCasePort, times(1)).criarProduto(any(Produto.class));

        }

        @Test
        @Severity(SeverityLevel.BLOCKER)
        @Description("Cadastrar produto bebida")
        void deveCadastrarProdutoBebida() throws Exception {
            var produtoRequest = Helper.gerarProdutoRequest(TipoProduto.BEBIDA);
            var produto = Helper.gerarProdutoAPartirDeUmProdutoRequest(produtoRequest);

            when(produtoUseCasePort.criarProduto(any(Produto.class))).thenReturn(produto);

            mockMvc.perform(post("/produtos")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(Helper.asJsonString(produtoRequest)))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.descricao").value(produto.getDescricaoProduto().getDescricao()))
                    .andExpect(jsonPath("$.nome").value(produto.getNomeProduto().getNome()))
                    .andExpect(jsonPath("$.tipo").value(produto.getTipoProduto().name()));

            verify(produtoUseCasePort, times(1)).criarProduto(any(Produto.class));

        }

        @Test
        @Severity(SeverityLevel.BLOCKER)
        @Description("Cadastrar produto sobremesa")
        void deveCadastrarProdutoSobremesa() throws Exception {
            var produtoRequest = Helper.gerarProdutoRequest(TipoProduto.SOBREMESA);
            var produto = Helper.gerarProdutoAPartirDeUmProdutoRequest(produtoRequest);

            when(produtoUseCasePort.criarProduto(any(Produto.class))).thenReturn(produto);

            mockMvc.perform(post("/produtos")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(Helper.asJsonString(produtoRequest)))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.descricao").value(produto.getDescricaoProduto().getDescricao()))
                    .andExpect(jsonPath("$.nome").value(produto.getNomeProduto().getNome()))
                    .andExpect(jsonPath("$.tipo").value(produto.getTipoProduto().name()));

            verify(produtoUseCasePort, times(1)).criarProduto(any(Produto.class));

        }

        @Test
        @Severity(SeverityLevel.BLOCKER)
        @Description("Cadastrar produto acompanhamento")
        void deveCadastrarProdutoAcompanhamento() throws Exception {
            var produtoRequest = Helper.gerarProdutoRequest(TipoProduto.ACOMPANHAMENTO);
            var produto = Helper.gerarProdutoAPartirDeUmProdutoRequest(produtoRequest);

            when(produtoUseCasePort.criarProduto(any(Produto.class))).thenReturn(produto);

            mockMvc.perform(post("/produtos")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(Helper.asJsonString(produtoRequest)))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.descricao").value(produto.getDescricaoProduto().getDescricao()))
                    .andExpect(jsonPath("$.nome").value(produto.getNomeProduto().getNome()))
                    .andExpect(jsonPath("$.tipo").value(produto.getTipoProduto().name()));

            verify(produtoUseCasePort, times(1)).criarProduto(any(Produto.class));

        }
    }

    @Nested
    class DeletarProduto {
        @Test
        @Severity(SeverityLevel.CRITICAL)
        @Description("Deletar produto")
        void deveCadastrarProdutoAcompanhamento() throws Exception {
            doNothing().when(produtoUseCasePort).deletarProduto(any(UUID.class));

            mockMvc.perform(delete("/produtos/{id}", UUID.randomUUID()))
                    .andExpect(status().isNoContent());

            verify(produtoUseCasePort, times(1)).deletarProduto(any(UUID.class));

        }
    }
}

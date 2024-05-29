package com.fiap.pedidos.helpers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiap.pedidos.controllers.requestValidations.ClienteRequest;
import com.fiap.pedidos.controllers.requestValidations.ProdutoRequest;
import com.fiap.pedidos.entities.*;
import com.fiap.pedidos.gateways.entities.PedidoEntity;
import com.fiap.pedidos.gateways.entities.PedidoProdutoEntity;
import com.fiap.pedidos.gateways.entities.ProdutoEntity;
import com.fiap.pedidos.utils.enums.StatusPedido;
import com.fiap.pedidos.utils.enums.TipoProduto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.UUID;

public class Helper {

    public static Cliente gerarClienteComTodosDados() {
        return Cliente.builder()
                .email(new Email("cliente1@gmail.com"))
                .nome(new Nome("Cliente 1"))
                .cpf(new Cpf("11111111111"))
                .build();
    }

    public static Cliente gerarClienteComTodosDadosEID() {
        return Cliente.builder()
                .email(new Email("cliente1@gmail.com"))
                .nome(new Nome("Cliente 1"))
                .cpf(new Cpf("11111111111"))
                .id(UUID.randomUUID())
                .build();
    }

    public static Cliente gerarClienteSomenteComCPF() {
        return Cliente.builder()
                .cpf(new Cpf("11111111111"))
                .build();
    }

    public static Produto gerarProdutoBebida() {
        return Produto.builder().nomeProduto(new NomeProduto(TipoProduto.BEBIDA.name()))
                .descricaoProduto(new DescricaoProduto("Descricao produto: "))
                .tipoProduto(TipoProduto.BEBIDA)
                .valorProduto(new ValorProduto(new BigDecimal(5.0))).build();
    }

    public static Produto gerarProdutoAcompanhamento() {
        return Produto.builder().nomeProduto(new NomeProduto(TipoProduto.ACOMPANHAMENTO.name()))
                .descricaoProduto(new DescricaoProduto("Descricao produto: "))
                .tipoProduto(TipoProduto.ACOMPANHAMENTO)
                .valorProduto(new ValorProduto(new BigDecimal("5.0"))).build();
    }

    public static Produto gerarProdutoLanche() {
        return Produto.builder().nomeProduto(new NomeProduto(TipoProduto.LANCHE.name()))
                .descricaoProduto(new DescricaoProduto("Descricao produto: "))
                .tipoProduto(TipoProduto.LANCHE)
                .valorProduto(new ValorProduto(new BigDecimal("5.0"))).build();
    }

    public static Produto gerarProdutoSobremesa() {
        return Produto.builder().nomeProduto(new NomeProduto(TipoProduto.SOBREMESA.name()))
                .descricaoProduto(new DescricaoProduto("Descricao produto: "))
                .tipoProduto(TipoProduto.SOBREMESA)
                .valorProduto(new ValorProduto(new BigDecimal("5.0"))).build();
    }

    public static Pedido gerarPedidoComCliente(){
        return Pedido.builder()
                .cliente(gerarClienteComTodosDados())
                .valorPedido(new BigDecimal("0.0"))
                .build();
    }

    public static Pedido gerarPedidoComClienteEProdutos(){
        var produtosList = new ArrayList<Produto>();
        produtosList.add(gerarProdutoLanche());
        produtosList.add(gerarProdutoBebida());
        return Pedido.builder()
                .cliente(gerarClienteComTodosDados())
                .valorPedido(new BigDecimal("0.0"))
                .statusPedido(StatusPedido.A)
                .produtos(produtosList)
                .build();
    }

    public static Pedido gerarPedidoComClienteComIDEProdutos(){
        var produtosList = new ArrayList<Produto>();
        produtosList.add(gerarProdutoLanche());
        produtosList.add(gerarProdutoBebida());
        return Pedido.builder()
                .cliente(gerarClienteComTodosDadosEID())
                .valorPedido(new BigDecimal("0.0"))
                .statusPedido(StatusPedido.A)
                .produtos(produtosList)
                .build();
    }

    public static Pedido gerarPedidoComIDComClienteComIDEProdutos(){
        var produtosList = new ArrayList<Produto>();
        produtosList.add(gerarProdutoLanche());
        produtosList.add(gerarProdutoBebida());
        return Pedido.builder()
                .idPedido(UUID.randomUUID())
                .cliente(gerarClienteComTodosDadosEID())
                .valorPedido(new BigDecimal("0.0"))
                .statusPedido(StatusPedido.A)
                .produtos(produtosList)
                .build();
    }

    public static Pedido gerarPedidoComIDComClienteComIDEProdutosEStatusEmPreparacao(){
        var produtosList = new ArrayList<Produto>();
        produtosList.add(gerarProdutoLanche());
        produtosList.add(gerarProdutoBebida());
        return Pedido.builder()
                .idPedido(UUID.randomUUID())
                .cliente(gerarClienteComTodosDadosEID())
                .valorPedido(new BigDecimal("0.0"))
                .statusPedido(StatusPedido.E)
                .produtos(produtosList)
                .build();
    }

    public static PedidoProduto gerarPedidoProduto(){
        return PedidoProduto.builder()
                .pedidoId(UUID.randomUUID())
                .produtoId(UUID.randomUUID())
                .build();
    }

    public static PedidoProdutoEntity gerarPedidoProdutoEntity(){
        var uuidPedido = UUID.randomUUID();
        var uuidProduto = UUID.randomUUID();

        var pedido = Helper.gerarPedidoComCliente();
        pedido.setIdPedido(uuidPedido);

        var produto = Helper.gerarProdutoLanche();
        produto.setIdProduto(uuidProduto);

        return PedidoProdutoEntity.builder()
                .produto(new ProdutoEntity().from(produto, true))
                .pedido(new PedidoEntity().from(pedido, true))
                .build();
    }

    public static ProdutoEntity gerarProdutoEntity(){
        var uuidPedido = UUID.randomUUID();
        var uuidProduto = UUID.randomUUID();

        var pedido = Helper.gerarPedidoComCliente();
        pedido.setIdPedido(uuidPedido);

        var produto = Helper.gerarProdutoLanche();
        produto.setIdProduto(uuidProduto);

        return new ProdutoEntity().from(produto, true);
    }

    public static ClienteRequest gerarClienteRequest() {
        var clienteRequest = new ClienteRequest();
                clienteRequest.setEmail("cliente1@gmail.com");
                clienteRequest.setNome("Cliente 1");
                clienteRequest.setCpf("11111111111");
        return clienteRequest;
    }

    public static ClienteRequest gerarClienteComCPFParametrizadoRequest(String cpf) {
        var clienteRequest = new ClienteRequest();
        clienteRequest.setEmail("cliente1@gmail.com");
        clienteRequest.setNome("Cliente 1");
        clienteRequest.setCpf(cpf);
        return clienteRequest;
    }

    public static ProdutoRequest gerarProdutoRequest(TipoProduto tipoProduto) {
        var produtoRequest = new ProdutoRequest();
        produtoRequest.setDescricao("Descricao 1");
        produtoRequest.setNome("Produto 1");
        produtoRequest.setTipo(tipoProduto.getCodigo());
        produtoRequest.setValor(new BigDecimal("10.00"));

        return produtoRequest;
    }

    public static Cliente gerarClienteAPartirDeUmClienteRequest(ClienteRequest clienteRequest) {
        return new ClienteRequest().from(clienteRequest);
    }

    public static Produto gerarProdutoAPartirDeUmProdutoRequest(ProdutoRequest produtoRequest) {
        return new ProdutoRequest().from(produtoRequest);
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

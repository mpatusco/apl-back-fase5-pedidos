package com.fiap.pedidos.controllers.requestValidations;

import com.fiap.pedidos.entities.DescricaoProduto;
import com.fiap.pedidos.entities.NomeProduto;
import com.fiap.pedidos.entities.Produto;
import com.fiap.pedidos.entities.ValorProduto;
import com.fiap.pedidos.utils.enums.TipoProduto;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProdutoRequest {

    @NotEmpty(message = "nome não pode estar vazio")
    private String nome;
    @NotEmpty(message = "descricao não pode estar vazio")
    private String descricao;
    @NotEmpty(message = "tipo não pode estar vazio")
    private String tipo;
    @NotNull(message = "valor não pode ser nulo")
    private BigDecimal valor;

    public Produto from(ProdutoRequest request) {
        return Produto.builder()
                .nomeProduto(new NomeProduto(request.getNome()))
                .tipoProduto(TipoProduto.fromCodigo(request.getTipo()))
                .descricaoProduto(new DescricaoProduto(request.getDescricao()))
                .valorProduto(new ValorProduto(request.getValor()))
                .build();
    }
}
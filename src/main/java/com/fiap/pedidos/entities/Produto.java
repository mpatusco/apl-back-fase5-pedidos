package com.fiap.pedidos.entities;

import com.fiap.pedidos.utils.enums.TipoProduto;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
public final class Produto {
    private UUID idProduto;
    private NomeProduto nomeProduto;
    private DescricaoProduto descricaoProduto;
    private TipoProduto tipoProduto;
    private ValorProduto valorProduto;
    private Boolean ativo;
}

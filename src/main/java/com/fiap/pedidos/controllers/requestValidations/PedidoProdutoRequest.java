package com.fiap.pedidos.controllers.requestValidations;

import com.fiap.pedidos.entities.PedidoProduto;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PedidoProdutoRequest {
    private UUID idProduto;

    public PedidoProduto from(PedidoProdutoRequest request, UUID idPedido) {
        return PedidoProduto.builder()
                .pedidoId(idPedido)
                .produtoId(request.getIdProduto())
                .build();
    }
}

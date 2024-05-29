package com.fiap.pedidos.entities;

import lombok.*;

import java.util.UUID;

@EqualsAndHashCode
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public final class PedidoProduto {

    private UUID id;
    private UUID pedidoId;
    private UUID produtoId;

}

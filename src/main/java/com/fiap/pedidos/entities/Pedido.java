package com.fiap.pedidos.entities;

import com.fiap.pedidos.utils.enums.StatusPedido;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@EqualsAndHashCode
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Pedido {

    private UUID idPedido;
    private Cliente cliente;
    private List<Produto> produtos;
    private StatusPedido statusPedido;
    private BigDecimal valorPedido;
    private Date dataInclusao;
    private Date dataAtualizacao;

}

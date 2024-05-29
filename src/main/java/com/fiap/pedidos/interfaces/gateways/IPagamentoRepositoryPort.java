package com.fiap.pedidos.interfaces.gateways;

import java.util.UUID;

public interface IPagamentoRepositoryPort {
    boolean consultaPagamento(UUID idPedido);
}

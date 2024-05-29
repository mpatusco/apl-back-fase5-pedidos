package com.fiap.pedidos.interfaces.gateways;

import java.util.UUID;

public interface IFilaRepositoryPort {
    void inserePedidoNaFila(UUID idPedido, UUID idCliente);
}

package com.fiap.pedidos.interfaces.gateways;

import com.fiap.pedidos.entities.Pedido;
import com.fiap.pedidos.utils.enums.StatusPedido;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IPedidoRepositoryPort {
    Pedido cadastrar(Pedido pedido);
    Pedido atualizarPedido(Pedido pedido);
    void remover(UUID idPedido);
    List<Pedido> buscarTodos(int pageNumber, int pageSize);
    Optional<Pedido> buscarPorId(UUID idPedido);
    List<Pedido> buscarPedidosPorClienteEStatus(UUID idCliente, StatusPedido statusPedido);
}

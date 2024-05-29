package com.fiap.pedidos.interfaces.gateways;

import com.fiap.pedidos.entities.Cliente;
import com.fiap.pedidos.entities.Cpf;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IClienteRepositoryPort {
    Cliente cadastrar(Cliente cliente);
    UUID gerarId();
    Optional<Cliente> buscarPorCpf(Cpf cpf);
    List<Cliente> bucarTodos();
    Optional<Cliente> buscarPorId(UUID uuid);

    void excluir(Cliente cliente);
}

package com.fiap.pedidos.interfaces.usecases;

import com.fiap.pedidos.entities.Cliente;
import com.fiap.pedidos.entities.Cpf;
import com.fiap.pedidos.exceptions.entities.ClienteInformacoesInvalidasException;
import com.fiap.pedidos.exceptions.entities.CpfExistenteException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IClienteUseCasePort {
    Cliente cadastrar(Cliente cliente) throws CpfExistenteException;
    Cliente identificarPorCpf(Cpf cpf);
    UUID gerarId();
    Optional<Cliente> buscarPorCpf(Cpf cpf);
    List<Cliente> bucarTodos();
    Optional<Cliente> buscarPorId(UUID uuid);

    Boolean excluir(Cliente cliente) throws ClienteInformacoesInvalidasException;
}

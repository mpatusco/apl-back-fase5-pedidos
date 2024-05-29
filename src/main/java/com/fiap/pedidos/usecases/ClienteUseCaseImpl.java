package com.fiap.pedidos.usecases;

import com.fiap.pedidos.entities.Cliente;
import com.fiap.pedidos.entities.Cpf;
import com.fiap.pedidos.exceptions.entities.ClienteInformacoesInvalidasException;
import com.fiap.pedidos.exceptions.entities.CpfExistenteException;
import com.fiap.pedidos.gateways.entities.ClienteEntity;
import com.fiap.pedidos.interfaces.gateways.IClienteRepositoryPort;
import com.fiap.pedidos.interfaces.usecases.IClienteUseCasePort;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class ClienteUseCaseImpl implements IClienteUseCasePort {

    private final IClienteRepositoryPort clienteRepositoryPort;

    @Override
    public Cliente cadastrar(Cliente cliente) throws CpfExistenteException {
        Optional<Cliente> clienteDb = buscarPorCpf(cliente.getCpf());
        if (clienteDb.isPresent())
            throw new CpfExistenteException();
        return clienteRepositoryPort.cadastrar(cliente);
    }

    @Override
    public Cliente identificarPorCpf(Cpf cpf) {
        Optional<Cliente> clienteDb = buscarPorCpf(cpf);
        return clienteDb
                .orElseGet(() -> clienteRepositoryPort.cadastrar(Cliente.builder().cpf(cpf).build()));
    }

    @Override
    public List<Cliente> bucarTodos() { // TODO - temp remover posteriormente
        return clienteRepositoryPort.bucarTodos();
    }

    @Override
    public UUID gerarId() {
        return clienteRepositoryPort.gerarId();
    }

    @Override
    public Optional<Cliente> buscarPorCpf(Cpf cpf) {
        return clienteRepositoryPort.buscarPorCpf(cpf);
    }

    @Override
    public Optional<Cliente> buscarPorId(UUID uuid) {
        return clienteRepositoryPort.buscarPorId(uuid);
    }

    @Override
    public Boolean excluir(Cliente cliente) throws ClienteInformacoesInvalidasException {
        if (Objects.isNull(cliente))
            throw new ClienteInformacoesInvalidasException();

        Optional<Cliente> clienteDBBefore = buscarPorCpf(cliente.getCpf());
        if (clienteDBBefore.isEmpty())
            throw new ClienteInformacoesInvalidasException();

        clienteRepositoryPort.excluir(clienteDBBefore.get());
        Optional<Cliente> clienteDBAfter = buscarPorCpf(cliente.getCpf());
        return clienteDBAfter.isEmpty();
    }
}

package com.fiap.pedidos.gateways;

import com.fiap.pedidos.entities.Cliente;
import com.fiap.pedidos.entities.Cpf;
import com.fiap.pedidos.exceptions.entities.ClienteInformacoesInvalidasException;
import com.fiap.pedidos.gateways.entities.ClienteEntity;
import com.fiap.pedidos.interfaces.gateways.IClienteRepositoryPort;
import com.fiap.pedidos.interfaces.repositories.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClienteRepositoryAdapter implements IClienteRepositoryPort {

    private final ClienteRepository clienteRepository;

    @Override
    public Cliente cadastrar(Cliente cliente) {
        ClienteEntity clienteEntity = new ClienteEntity().from(cliente);
        var clienteSalvo = clienteRepository.save(clienteEntity);
        return clienteSalvo.to(clienteSalvo);
    }

    @Override
    public UUID gerarId() {
        return clienteRepository.save(new ClienteEntity()).getId();
    }

    @Override
    public Optional<Cliente> buscarPorCpf(Cpf cpf) {
        return clienteRepository.findAllByCpf(cpf).map(obj -> obj.to(obj));
    }

    @Override
    public List<Cliente> bucarTodos() {
        List<ClienteEntity> clientesEntity = clienteRepository.findAll();
        return clientesEntity
                .stream()
                .map(clienteEntity -> clienteEntity.to(clienteEntity))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Cliente> buscarPorId(UUID uuid) {
        return clienteRepository.findById(uuid).map(obj -> obj.to(obj));
    }

    @Override
    public void excluir(Cliente cliente) {
        clienteRepository.deleteById(cliente.getId());
    }
}

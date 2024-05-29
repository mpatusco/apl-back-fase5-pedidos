package com.fiap.pedidos.interfaces.repositories;

import com.fiap.pedidos.entities.Cpf;
import com.fiap.pedidos.gateways.entities.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClienteRepository extends JpaRepository<ClienteEntity, UUID> {
    Optional<ClienteEntity> findAllByCpf(Cpf cpf);
}

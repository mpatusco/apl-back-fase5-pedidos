package com.fiap.pedidos.gateways.entities;

import com.fiap.pedidos.entities.Cliente;
import com.fiap.pedidos.entities.Cpf;
import com.fiap.pedidos.entities.Email;
import com.fiap.pedidos.entities.Nome;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "clientes")
public class ClienteEntity {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "txt_nome")
    @Embedded
    private Nome nome;

    @Column(name = "txt_cpf")
    @Embedded
    private Cpf cpf;

    @Column(name = "txt_email")
    @Embedded
    private Email email;

    public Cliente to(ClienteEntity clienteEntity) {
        return Cliente.builder()
                .id(clienteEntity.getId())
                .nome(clienteEntity.getNome())
                .cpf(clienteEntity.getCpf())
                .email(clienteEntity.getEmail())
                .build();

    }

    public ClienteEntity from(Cliente cliente) {
        return ClienteEntity.builder()
                .id(cliente.getId())
                .nome(cliente.getNome())
                .cpf(cliente.getCpf())
                .email(cliente.getEmail())
                .build();
    }

}

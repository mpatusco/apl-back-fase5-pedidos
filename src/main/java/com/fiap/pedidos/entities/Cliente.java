package com.fiap.pedidos.entities;

import lombok.*;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public final class Cliente {
    private UUID id;
    private Nome nome;
    private Cpf cpf;
    private Email email;
}

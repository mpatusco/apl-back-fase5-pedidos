package com.fiap.pedidos.controllers.requestValidations;

import com.fiap.pedidos.entities.Cliente;
import com.fiap.pedidos.entities.Pedido;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PedidoRequest {

    @NotNull(message = "id cliente não pode estar vazio")
    private UUID idCliente;

    public Pedido from(PedidoRequest request, Cliente cliente) {

        return Pedido.builder()
                .cliente(cliente)
                .build();
    }
}

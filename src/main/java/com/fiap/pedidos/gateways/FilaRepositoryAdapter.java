package com.fiap.pedidos.gateways;

import com.fiap.pedidos.interfaces.gateways.IFilaRepositoryPort;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FilaRepositoryAdapter implements IFilaRepositoryPort {

    private final SqsTemplate sqsTemplate;

    @Value("${queue.fila.pedido}")
    private String nomeDaFila;

    @Override
    public void inserePedidoNaFila(UUID idPedido, UUID idCliente) {
        this.sqsTemplate.send(nomeDaFila,
                MessageBuilder
                        .withPayload("{\"idPedido\":\"" + idPedido + "\", \"idCliente\":\"" + idCliente + "\"}")
                        .build());
    }
}

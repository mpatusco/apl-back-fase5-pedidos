package com.fiap.pedidos.sqslisteners;

import com.fiap.pedidos.interfaces.usecases.IPedidoUseCasePort;
import com.fiap.pedidos.sqslisteners.request.AtualizaPedidoFila;
import com.fiap.pedidos.utils.enums.TipoAtualizacao;
import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class AtualizaPedidoListener {

    private final IPedidoUseCasePort pedidoUseCasePort;

    @SqsListener(queueNames = "${queue.update.pedido}", factory = "defaultSqsListenerContainerFactory")
    public void listenStatusUpdate(AtualizaPedidoFila message) {
        System.out.println(message.getTipoAtualizacao());
        if(message.getTipoAtualizacao().equals(TipoAtualizacao.P)){
            System.out.println("aqui");
            this.pedidoUseCasePort.atualizarPedido(
                    message.getIdPedido(),
                    TipoAtualizacao.P,
                    null,
                    null);
        } else if(message.getTipoAtualizacao().equals(TipoAtualizacao.F)) {
            this.pedidoUseCasePort.atualizarPedido(
                    message.getIdPedido(),
                    TipoAtualizacao.F,
                    null,
                    message.getStatusPedido());
        }
    }
}

package com.fiap.pedidos.interfaces.facade;

import java.util.UUID;

public interface IServiceAsyncProcessWebhook {

    void processarWebhook(UUID idPedido);
}

package com.fiap.pedidos.interfaces.repositories;

import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "consulta-pagamento", url = "${pagamentos.service.url}")
public interface PagamentoRepository {
    @GetMapping("/{idPedido}")
    Response consultarPagamento(@PathVariable("idPedido")UUID idPedido);
}

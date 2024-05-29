package com.fiap.pedidos.gateways;

import com.fiap.pedidos.interfaces.gateways.IPagamentoRepositoryPort;
import com.fiap.pedidos.interfaces.repositories.PagamentoRepository;
import feign.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PagamentoRepositoryAdapter implements IPagamentoRepositoryPort {

    private final PagamentoRepository pagamentoRepository;
    @Override
    public boolean consultaPagamento(UUID idPedido) {Response response = this.pagamentoRepository.consultarPagamento(idPedido);
       return response.status() == HttpStatus.OK.value();
    }
}

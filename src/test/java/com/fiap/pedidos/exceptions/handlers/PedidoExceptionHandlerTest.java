package com.fiap.pedidos.exceptions.handlers;
import com.fiap.pedidos.exceptions.entities.*;
import com.fiap.pedidos.exceptions.handlers.PedidoExceptionHandler;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.assertj.core.api.Assertions.assertThat;

class PedidoExceptionHandlerTest {

    private final PedidoExceptionHandler handler = new PedidoExceptionHandler();

    @Test
    void deveGerarExcecao_QuandoPedidoNaoEncontrado() {
        var exception = new PedidoNaoEncontradoException();
        var req = new MockHttpServletRequest();
        var res = handler.pedidoNaoEncontrado(exception, req);

        assertThat(res).isInstanceOf(ResponseEntity.class);
        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void deveGerarExcecao_QuandoPedidoOperacaoNaoSuportada() {
        var exception = new PedidoOperacaoNaoSuportadaException("Pedido não está aberto para edição.");
        var req = new MockHttpServletRequest();
        var res = handler.pedidoOperacaoNaoSuportada(exception, req);

        assertThat(res).isInstanceOf(ResponseEntity.class);
        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}

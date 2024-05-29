package com.fiap.pedidos.exceptions.handlers;

import com.fiap.pedidos.exceptions.entities.CpfExistenteException;
import com.fiap.pedidos.exceptions.entities.CpfInvalidoException;
import com.fiap.pedidos.exceptions.entities.EmailInvalidoException;
import com.fiap.pedidos.exceptions.entities.NomeInvalidoException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.assertj.core.api.Assertions.assertThat;

class ClienteExceptionHandlerTest {

    private final ClienteExceptionHandler handler = new ClienteExceptionHandler();

    @Test
    void deveGerarExcecao_QuandoCpfExistente() {
        var exception = new CpfExistenteException();
        var req = new MockHttpServletRequest();
        var res = handler.cpfExistente(exception, req);

        assertThat(res).isInstanceOf(ResponseEntity.class);
        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void deveGerarExcecao_QuandoCpfInvalido() {
        var exception = new CpfInvalidoException();
        var req = new MockHttpServletRequest();
        var res = handler.cpfInvalido(exception, req);

        assertThat(res).isInstanceOf(ResponseEntity.class);
        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void deveGerarExcecao_QuandoEmailInvalido() {
        var exception = new EmailInvalidoException();
        var req = new MockHttpServletRequest();
        var res = handler.emailInvalido(exception, req);

        assertThat(res).isInstanceOf(ResponseEntity.class);
        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void deveGerarExcecao_QuandoNomeInvalido() {
        var exception = new NomeInvalidoException();
        var req = new MockHttpServletRequest();
        var res = handler.nomeInvalido(exception, req);

        assertThat(res).isInstanceOf(ResponseEntity.class);
        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}

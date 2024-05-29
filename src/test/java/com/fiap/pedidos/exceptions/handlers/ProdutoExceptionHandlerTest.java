package com.fiap.pedidos.exceptions.handlers;

import com.fiap.pedidos.exceptions.entities.DescricaoProdutoInvalidoException;
import com.fiap.pedidos.exceptions.entities.NomeProdutoInvalidoException;
import com.fiap.pedidos.exceptions.entities.TipoProdutoInexistenteException;
import com.fiap.pedidos.exceptions.entities.ValorProdutoInvalidoException;
import org.junit.jupiter.api.Test;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static org.assertj.core.api.Assertions.assertThat;

class ProdutoExceptionHandlerTest {

    private final ProdutoExceptionHandler handler = new ProdutoExceptionHandler();

    @Test
    void deveGerarExcecao_QuandoDescricaoProdutoInvalido() {
        var exception = new DescricaoProdutoInvalidoException();
        var req = new MockHttpServletRequest();
        var res = handler.descricaoProdutoInvalido(exception, req);

        assertThat(res).isInstanceOf(ResponseEntity.class);
        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void deveGerarExcecao_QuandoNomeProdutoInvalido() {
        var exception = new NomeProdutoInvalidoException();
        var req = new MockHttpServletRequest();
        var res = handler.nomeProdutoInvalidoException(exception, req);

        assertThat(res).isInstanceOf(ResponseEntity.class);
        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void deveGerarExcecao_QuandoTipoProdutoInexistente() {
        var exception = new TipoProdutoInexistenteException("7");
        var req = new MockHttpServletRequest();
        var res = handler.tipoProdutoInexistenteException(exception, req);

        assertThat(res).isInstanceOf(ResponseEntity.class);
        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void deveGerarExcecao_QuandoValorProdutoInvalido() {
        var exception = new ValorProdutoInvalidoException();
        var req = new MockHttpServletRequest();
        var res = handler.valorProdutoInvalidoException(exception, req);

        assertThat(res).isInstanceOf(ResponseEntity.class);
        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void deveGerarExcecao_QuandoEmptyResultDataAccessException() {
        var exception = new EmptyResultDataAccessException(1);
        var req = new MockHttpServletRequest();
        var res = handler.emptyResultDataAccessException(exception, req);

        assertThat(res).isInstanceOf(ResponseEntity.class);
        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void deveGerarExcecao_QuandoMethodArgumentNotValidException() {
        var fieldError = new FieldError("objectName", "fieldName", "error message");
        var bindingResult = new org.springframework.validation.BeanPropertyBindingResult(new Object(), "objectName");
        bindingResult.addError(fieldError);
        var exception = new MethodArgumentNotValidException(null, bindingResult);
        var req = new MockHttpServletRequest();
        var res = handler.emptyResultDataAccessException(exception, req);

        assertThat(res).isInstanceOf(ResponseEntity.class);
        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}

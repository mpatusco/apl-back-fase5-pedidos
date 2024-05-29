package com.fiap.pedidos.exceptions.handlers;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

class StandardErrorTest {

    @Test
    void deveCriarStandardErrorVazio() {
        var erro = new StandardError();
        assertThat(erro).isNotNull();
        assertThat(erro).isInstanceOf(StandardError.class);
    }
    @Test
    void deveCriarStandardErrorBuilder() {
        var erro = StandardError.builder().build();
        assertThat(erro).isNotNull();
        assertThat(erro).isInstanceOf(StandardError.class);
    }
    @Test
    void deveCriarStandardError() {
        var date = new Date().getTime();
        var erro = new StandardError();
        erro.setTimeStamp(date);
        erro.setStatus(500);
        erro.setError("erro");
        erro.setMessage("message");
        erro.setPath("/");

        var erro1 = new StandardError();
        erro1.setTimeStamp(date);
        erro1.setStatus(500);
        erro1.setError("erro");
        erro1.setMessage("message");
        erro1.setPath("/");

        assertThat(erro).isNotNull();
        assertThat(erro.getTimeStamp()).isNotNull();
        assertThat(erro.getStatus()).isNotNull();
        assertThat(erro.getError()).isNotNull();
        assertThat(erro.getMessage()).isNotNull();
        assertThat(erro.getPath()).isNotNull();
        assertThat(erro.equals(erro1)).isTrue();
        assertThat(erro.hashCode()).isEqualTo(erro1.hashCode());
        assertThat(erro.canEqual(erro1)).isTrue();
        assertThat(erro.toString()).isEqualTo(erro1.toString());

    }

}

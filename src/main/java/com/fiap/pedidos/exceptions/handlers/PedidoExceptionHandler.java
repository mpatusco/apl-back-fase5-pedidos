package com.fiap.pedidos.exceptions.handlers;

import com.fiap.pedidos.controllers.PedidoController;
import com.fiap.pedidos.exceptions.entities.PedidoNaoEncontradoException;
import com.fiap.pedidos.exceptions.entities.PedidoOperacaoNaoSuportadaException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(assignableTypes = {PedidoController.class})
public class PedidoExceptionHandler {
    @ExceptionHandler(PedidoNaoEncontradoException.class)
    public ResponseEntity<StandardError> pedidoNaoEncontrado(PedidoNaoEncontradoException e, HttpServletRequest request){
        StandardError err = new StandardError(System.currentTimeMillis(), HttpStatus.NOT_FOUND.value(), "Pedido não encontrado", e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }

    @ExceptionHandler(PedidoOperacaoNaoSuportadaException.class)
    public ResponseEntity<StandardError> pedidoOperacaoNaoSuportada(PedidoOperacaoNaoSuportadaException e, HttpServletRequest request){
        StandardError err = new StandardError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(), "Operação não suportada, verifique o status do pedido", e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }
}

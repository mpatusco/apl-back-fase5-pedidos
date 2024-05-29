package com.fiap.pedidos.exceptions.handlers;

import com.fiap.pedidos.controllers.ClienteController;
import com.fiap.pedidos.exceptions.entities.CpfExistenteException;
import com.fiap.pedidos.exceptions.entities.CpfInvalidoException;
import com.fiap.pedidos.exceptions.entities.EmailInvalidoException;
import com.fiap.pedidos.exceptions.entities.NomeInvalidoException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(assignableTypes = {ClienteController.class})
public class ClienteExceptionHandler {
    @ExceptionHandler(CpfExistenteException.class)
    public ResponseEntity<StandardError> cpfExistente(CpfExistenteException e, HttpServletRequest request){
        StandardError err = new StandardError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(), "CPF já cadastrado", e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler(CpfInvalidoException.class)
    public ResponseEntity<StandardError> cpfInvalido(CpfInvalidoException e, HttpServletRequest request){
        StandardError err = new StandardError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(), "CPF inválido", e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler(EmailInvalidoException.class)
    public ResponseEntity<StandardError> emailInvalido(EmailInvalidoException e, HttpServletRequest request){
        StandardError err = new StandardError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(), "Email inválido", e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler(NomeInvalidoException.class)
    public ResponseEntity<StandardError> nomeInvalido(NomeInvalidoException e, HttpServletRequest request){
        StandardError err = new StandardError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(), "Nome inválido", e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

}

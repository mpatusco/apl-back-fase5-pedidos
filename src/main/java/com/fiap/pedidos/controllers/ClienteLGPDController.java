package com.fiap.pedidos.controllers;

import com.fiap.pedidos.adapters.ClienteDTO;
import com.fiap.pedidos.controllers.requestValidations.ClienteLGPDExclusaoRequest;
import com.fiap.pedidos.controllers.requestValidations.ClienteRequest;
import com.fiap.pedidos.entities.Cliente;
import com.fiap.pedidos.interfaces.usecases.IClienteUseCasePort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteLGPDController {

    private final IClienteUseCasePort clienteUseCasePort;

    @DeleteMapping(value = {"/lgpd/", ""}, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> excluirCliente(@RequestBody @Validated ClienteLGPDExclusaoRequest clienteLGPDExclusaoRequest) {
        if (Objects.nonNull(clienteLGPDExclusaoRequest)) {
            Boolean clienteExcluido = clienteUseCasePort.excluir(clienteLGPDExclusaoRequest.from(clienteLGPDExclusaoRequest));
            return clienteExcluido ? ResponseEntity.ok().build() : ResponseEntity.internalServerError().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}

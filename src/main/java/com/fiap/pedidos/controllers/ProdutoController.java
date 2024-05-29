package com.fiap.pedidos.controllers;

import com.fiap.pedidos.adapters.ProdutoDTO;
import com.fiap.pedidos.controllers.requestValidations.ProdutoRequest;
import com.fiap.pedidos.entities.Produto;
import com.fiap.pedidos.interfaces.usecases.IProdutoUseCasePort;
import com.fiap.pedidos.utils.enums.TipoProduto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/produtos")
@RequiredArgsConstructor
public class ProdutoController {

    private final IProdutoUseCasePort produtoUseCasePort;

    @GetMapping(value = {"/", ""}, produces = "application/json")
    public ResponseEntity<List<ProdutoDTO>> buscarProdutos(
            @RequestParam(value="tipo_produto") @Validated @NotBlank String tipoProduto) {

        List<Produto> produtoArrayList = this.produtoUseCasePort
                .listarProdutosPorTipoProduto(TipoProduto.fromCodigo(tipoProduto));

        final var produtoDTOList = new ArrayList<ProdutoDTO>();

        produtoArrayList.forEach(produto -> produtoDTOList.add(ProdutoDTO.from(produto)));

        if(produtoDTOList.isEmpty())
            return new ResponseEntity<>(produtoDTOList, HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(produtoDTOList, HttpStatus.OK);
    }

    @PostMapping(value = {"/", ""}, produces = "application/json")
    public ResponseEntity<?> criarProduto(@RequestBody @Validated ProdutoRequest request) {
        Produto produto = this.produtoUseCasePort.criarProduto(request.from(request));
        return new ResponseEntity<>(ProdutoDTO.from(produto), HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<?> deletarProduto(@PathVariable @NotNull UUID id) {
        this.produtoUseCasePort.deletarProduto(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
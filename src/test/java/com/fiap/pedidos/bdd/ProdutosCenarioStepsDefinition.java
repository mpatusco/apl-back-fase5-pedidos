package com.fiap.pedidos.bdd;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiap.pedidos.adapters.ProdutoDTO;
import com.fiap.pedidos.helpers.Helper;
import com.fiap.pedidos.utils.enums.TipoProduto;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import io.restassured.response.Response;
import org.springframework.http.MediaType;

import java.util.Objects;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.fail;

public class ProdutosCenarioStepsDefinition {

    private Response response;
    private ProdutoDTO produtoCadastrado;
    private final String BASE_URI = "http://localhost:9091";
    private final String BASE_PATH = "/tech-challenge/produtos";

    ObjectMapper objectMapper = new ObjectMapper();

    @Quando("cadastrar o produto de tipo {string}")
    public void cadastrarProduto(String tipoProduto){
        var produtoRequest = Helper.gerarProdutoRequest(TipoProduto.fromCodigo(tipoProduto));
        response = given()
                .baseUri(BASE_URI)
                .basePath(BASE_PATH)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .body(produtoRequest)
                .post("/");
    }

    @E("buscar o produto cadastrado")
    public void buscarProduto(){
        var tipoProduto = TipoProduto.valueOf(response.getBody().jsonPath().getString("tipo"));
        var idProduto = response.getBody().jsonPath().getString("id");

        response = given()
                .baseUri(BASE_URI)
                .basePath(BASE_PATH)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .queryParam("tipo_produto", tipoProduto.getCodigo())
                .get("/");

        System.out.println(response.getBody().jsonPath());
        var retorno = response.getBody().jsonPath().getList("");

        if(!retorno.isEmpty()) {
            retorno.forEach(res -> {
                ProdutoDTO produtoDTO = objectMapper.convertValue(res, ProdutoDTO.class);
                if (produtoDTO.getId().toString().equalsIgnoreCase(idProduto))
                    produtoCadastrado = produtoDTO;
            });
        }
    }

    @Entao("o produto e cadastrado com sucesso")
    public void validarProdutoCadastrado(){
        if(Objects.isNull(produtoCadastrado))
            fail("Produto não foi cadastrado");
    }
}

package com.fiap.pedidos.bdd;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiap.pedidos.adapters.ClienteDTO;
import com.fiap.pedidos.helpers.Helper;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import io.restassured.response.Response;
import org.springframework.http.MediaType;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.fail;

public class ClientesCenarioStepsDefinition {
    private Response response;
    private String cpf;
    private final String BASE_URI = "http://localhost:9091";
    private final String BASE_PATH = "/tech-challenge/clientes";

    ObjectMapper objectMapper = new ObjectMapper();

    @Dado("que o cliente de CPF {string} não está cadastrado")
    public void buscarTodosClientes(String cpf){
        this.cpf = cpf;
        response = given()
                .baseUri(BASE_URI)
                .basePath(BASE_PATH)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/");
        var retorno = response.getBody().jsonPath().getList("");

        if(!retorno.isEmpty()) {
            retorno.forEach(res -> {
                ClienteDTO clienteDTO = objectMapper.convertValue(res, ClienteDTO.class);
                if (clienteDTO.getCpf().equalsIgnoreCase(cpf)) fail("CPF já existe.");
            });
        }
    }

    @Quando("o cliente decide se cadastrar")
    public void cadastrarCliente(){
        var clienteRequest = Helper.gerarClienteComCPFParametrizadoRequest(this.cpf);
        response = given()
                .baseUri(BASE_URI)
                .basePath(BASE_PATH)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .body(clienteRequest)
                .post("/");
    }

    @Entao("o cliente é cadastrado com sucesso")
    public void buscarCliente(){
        var id = response.getBody().jsonPath().get("id");

        response = given()
                .baseUri(BASE_URI)
                .basePath(BASE_PATH)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/{id}", id);
        var resposta = response.then().extract().as(ClienteDTO.class);
        if(!resposta.getCpf().equalsIgnoreCase(this.cpf))
            fail("Erro ao buscar cliente. Cliente com o CPF " + cpf + "não existe na base.");
    }

    @Quando("o cliente decide se identificar pelo CPF")
    public void identificarPorCpf(){
        response = given()
                .baseUri(BASE_URI)
                .basePath(BASE_PATH)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/{cpf}", cpf);
    }

    @Quando("o cliente inicia sem se identificar")
    public void iniciarSemSeIdentificar(){
        response = given()
                .baseUri(BASE_URI)
                .basePath(BASE_PATH)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/id");
    }

    @Quando("o identificador do cliente é gerado com sucesso")
    public void buscarClientePeloIdentificador(){
        var id = response.getBody().jsonPath().getString("id");
        response = given()
                .baseUri(BASE_URI)
                .basePath(BASE_PATH)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/{id}", id);
        var resposta = response.then().extract().as(ClienteDTO.class);
        if(!resposta.getId().toString().equalsIgnoreCase(id))
            fail("Erro ao buscar cliente pelo identificador. Cliente com o id " + id + "não existe na base.");
    }
}

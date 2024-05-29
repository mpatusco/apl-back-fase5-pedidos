package com.fiap.pedidos.controllers.requestValidations;

import com.fiap.pedidos.entities.Cliente;
import com.fiap.pedidos.entities.Cpf;
import com.fiap.pedidos.entities.Email;
import com.fiap.pedidos.entities.Nome;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteLGPDExclusaoRequest {
    @NotEmpty(message = "nome não pode estar vazio")
    private String nome;
    @NotEmpty(message = "cpf não pode estar vazio")
    private String cpf;
    @NotEmpty(message = "email não pode estar vazio")
    private String email;
    private String endereco;
    private String numeroTelefone;

    public Cliente from(ClienteLGPDExclusaoRequest request) {
        return Cliente.builder()
                .nome(new Nome(request.getNome()))
                .cpf(new Cpf(request.getCpf()))
                .email(new Email(request.getEmail()))
                .build();
    }

}

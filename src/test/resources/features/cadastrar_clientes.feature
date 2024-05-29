# language: pt
Funcionalidade: Cadastrar Cliente

  Cenario: Cadastrar Cliente
    Dado que o cliente de CPF "88888888888" não está cadastrado
    Quando o cliente decide se cadastrar
    Entao o cliente é cadastrado com sucesso

  Cenario: Cadastrar Cliente identificado pelo CPF
    Dado que o cliente de CPF "99999999999" não está cadastrado
    Quando o cliente decide se identificar pelo CPF
    Entao o cliente é cadastrado com sucesso

  Cenario: Gerar identificador para cliente não identificado
    Quando o cliente inicia sem se identificar
    Entao o identificador do cliente é gerado com sucesso
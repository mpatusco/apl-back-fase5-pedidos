# language: pt
Funcionalidade: Cadastrar Produto

  Cenario: Cadastrar Produto - Lanche
    Quando cadastrar o produto de tipo "1"
    E buscar o produto cadastrado
    Entao o produto e cadastrado com sucesso

  Cenario: Cadastrar Produto - Bebida
    Quando cadastrar o produto de tipo "2"
    E buscar o produto cadastrado
    Entao o produto e cadastrado com sucesso

  Cenario: Cadastrar Produto - Sobremesa
    Quando cadastrar o produto de tipo "3"
    E buscar o produto cadastrado
    Entao o produto e cadastrado com sucesso

  Cenario: Cadastrar Produto - Acompanhamento
    Quando cadastrar o produto de tipo "4"
    E buscar o produto cadastrado
    Entao o produto e cadastrado com sucesso
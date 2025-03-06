# Ada Commerce - E-Commerce

## Descrição do Projeto

A Ada Tech pretende realizar a venda de produtos através de um E-Commerce e nos contratou para desenvolver todo o fluxo necessário. Este projeto consiste em criar um sistema de E-Commerce que atenda às seguintes necessidades:

### Funcionalidades Principais

- **Clientes**:
    - Cadastrar, listar e atualizar clientes na base de dados.
    - Não é necessário excluir clientes, pois eles devem permanecer na base como histórico.

- **Produtos**:
    - Cadastrar, listar e atualizar produtos na base de dados.
    - Não é necessário excluir produtos, pois eles devem permanecer na base como histórico.

- **Vendas**:
    - Criar uma venda para um cliente.
    - Adicionar item (produto) com quantidade e preço.
    - Remover item (produto).
    - Alterar quantidade do item (produto).
    - Realizar o pagamento e a entrega.

### Regras de Negócio

- **Clientes**:
    - Todo cliente cadastrado precisa ter um documento de identificação.

- **Pedidos**:
    - Todo pedido deve ter a data em que foi criado.
    - Todo pedido deve iniciar com o status igual a "aberto".
    - Pedidos com status igual a "aberto" podem receber itens (produto), alterar quantidade e remover item.
    - Os produtos adicionados ao pedido devem ter um valor de venda informado, pois esse valor pode ser diferente do valor do produto.

- **Finalização do Pedido**:
    - Para finalizar o pedido, o pedido deve ter ao menos um item e o valor deve ser maior que zero.
    - O status do pagamento deve ser alterado para "Aguardando pagamento" e o cliente deve ser notificado via e-mail.

- **Pagamento**:
    - O pagamento deve acontecer apenas sobre vendas que estejam com o status igual a "Aguardando pagamento".
    - Após o pagamento ser realizado com sucesso, o status do pagamento deve ser alterado para "Pago" e o cliente deve ser notificado.

- **Entrega**:
    - Após o pagamento, o pedido pode ser entregue ao cliente e o status deve ser alterado para "Finalizado".
    - O cliente deve ser notificado sobre a entrega.

### Dicas para Desenvolvimento

- Utilize os conceitos aprendidos em sala de aula, como os princípios de orientação a objetos e SOLID.
- Neste momento, podemos trabalhar com uma base de dados em memória para simplificar o exercício. Porém, se desejar, faça a persistência em arquivos (item bônus, não obrigatório).

## Entrega

- **Data de Entrega**: 06/03/2025 – Até 23:00
- **Forma de Entrega**: Entregar o link do repositório do projeto no GitHub.


## Como Contribuir

1. Faça um fork deste repositório.
2. Crie uma branch para sua feature (`git checkout -b feature/AmazingFeature`).
3. Commit suas mudanças (`git commit -m 'Add some AmazingFeature'`).
4. Push para a branch (`git push origin feature/AmazingFeature`).
5. Abra um Pull Request.



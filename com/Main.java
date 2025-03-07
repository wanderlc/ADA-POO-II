package com.adacommerce;

import com.adacommerce.clientes.Cliente;
import com.adacommerce.clientes.GerenciadorClientes;
import com.adacommerce.produtos.GerenciadorProdutos;
import com.adacommerce.produtos.Produto;
import com.adacommerce.vendas.GerenciadorVendas;
import com.adacommerce.vendas.ItemVenda;
import com.adacommerce.vendas.Venda;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors; // Import Collectors

public class Main {
    private static GerenciadorClientes gClientes = new GerenciadorClientes();
    private static GerenciadorProdutos gProdutos = new GerenciadorProdutos();
    private static GerenciadorVendas gVendas = new GerenciadorVendas(gClientes, gProdutos);
    private static Scanner scanner = new Scanner(System.in);
    private static Venda vendaAtual = null;

    public static void main(String[] args) {
        int opcao = -1;

        do {
            exibirMenuPrincipal();
            opcao = lerOpcao();

            switch (opcao) {
                case 1:
                    menuClientes();
                    break;
                case 2:
                    menuProdutos();
                    break;
                case 3:
                    menuVendas();
                    break;
                case 4:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 4);

        scanner.close();
    }

    private static void exibirMenuPrincipal() {
        System.out.println("\n=== Menu Ada Commerce ===");
        System.out.println("1. Clientes");
        System.out.println("2. Produtos");
        System.out.println("3. Vendas");
        System.out.println("4. Sair");
        System.out.print("Escolha uma opção: ");
    }

    private static int lerOpcao() {
        try {
            return scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Digite um número.");
            scanner.next();
            return -1;
        } finally {
            scanner.nextLine();
        }
    }

    private static void menuClientes() {
        int opcao;
        do {
            System.out.println("\n=== Submenu Clientes ===");
            System.out.println("1. Cadastrar Cliente");
            System.out.println("2. Listar Clientes");
            System.out.println("3. Atualizar Cliente");
            System.out.println("4. Voltar");
            System.out.print("Escolha uma opção: ");
            opcao = lerOpcao();

            switch (opcao) {
                case 1:
                    cadastrarCliente();
                    break;
                case 2:
                    listarClientes();
                    break;
                case 3:
                    atualizarCliente();
                    break;
                case 4:
                    System.out.println("Voltando ao menu principal...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
            aguardarEnter();
        } while (opcao != 4);
    }

    private static void menuProdutos() {
        int opcao;
        do {
            System.out.println("\n=== Submenu Produtos ===");
            System.out.println("1. Cadastrar Produto");
            System.out.println("2. Listar Produtos");
            System.out.println("3. Atualizar Produto");
            System.out.println("4. Voltar");
            System.out.print("Escolha uma opção: ");
            opcao = lerOpcao();

            switch (opcao) {
                case 1:
                    cadastrarProduto();
                    break;
                case 2:
                    listarProdutos();
                    break;
                case 3:
                    atualizarProduto();
                    break;
                case 4:
                    System.out.println("Voltando ao menu principal...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
            aguardarEnter();
        } while (opcao != 4);
    }

    private static void menuVendas() {
        int opcao;
        do {
            System.out.println("\n=== Submenu Vendas ===");
            System.out.println("1. Criar Venda");
            System.out.println("2. Adicionar Item à Venda");
            System.out.println("3. Remover Item da Venda");
            System.out.println("4. Alterar Quantidade do Item na Venda");
            System.out.println("5. Finalizar Venda");
            System.out.println("6. Realizar Pagamento de Venda");
            System.out.println("7. Entregar Venda");
            System.out.println("8. Listar Vendas Detalhado");
            System.out.println("9. Voltar");
            System.out.print("Escolha uma opção: ");
            opcao = lerOpcao();

            switch (opcao) {
                case 1:
                    criarVenda();
                    break;
                case 2:
                    adicionarItemVenda();
                    break;
                case 3:
                    removerItemVenda();
                    break;
                case 4:
                    alterarQuantidadeItemVenda();
                    break;
                case 5:
                    finalizarVendaComConfirmacao();
                    break;
                case 6:
                    realizarPagamento();
                    break;
                case 7:
                    entregarVenda();
                    break;
                case 8:
                    listarVendasDetalhado();
                    break;
                case 9:
                    System.out.println("Voltando ao menu principal...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }

            aguardarEnter();
        } while (opcao != 9);
    }

    private static void cadastrarCliente() {
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Documento: ");
        String doc = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        gClientes.cadastrarCliente(nome, doc, email);
    }

    private static void listarClientes() {
        gClientes.listarClientes();
    }

    private static void atualizarCliente() {
        System.out.print("ID do Cliente a ser atualizado: ");
        int idCliente = lerOpcao();
        if (idCliente <= 0) return;

        Cliente clienteExistente = gClientes.buscarClientePorId(idCliente);
        if (clienteExistente == null) {
            System.out.println("Cliente não encontrado.");
            return;
        }

        System.out.print("Novo Nome (" + clienteExistente.getNome() + "): ");
        String nome = scanner.nextLine();
        if (nome.isEmpty()) {
            nome = clienteExistente.getNome();
        }

        System.out.print("Novo Documento (" + clienteExistente.getDocumento() + "): ");
        String doc = scanner.nextLine();
        if (doc.isEmpty()) {
            doc = clienteExistente.getDocumento();
        }

        System.out.print("Novo Email (" + clienteExistente.getEmail() + "): ");
        String email = scanner.nextLine();
        if (email.isEmpty()) {
            email = clienteExistente.getEmail();
        }

        gClientes.atualizarCliente(idCliente, nome, doc, email);
    }

    private static void cadastrarProduto() {
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Preço: ");
        String precoStr = scanner.nextLine();
        double preco = 0.0;

        try {
            preco = Double.parseDouble(precoStr);
        } catch (NumberFormatException e) {
            System.out.println("Preço inválido. O preço deve ser um número.");
            return;
        }
        gProdutos.cadastrarProduto(nome, preco);
    }

    private static void listarProdutos() {
        gProdutos.listarProdutos();
    }

    private static void atualizarProduto() {
        System.out.print("ID do Produto a ser atualizado: ");
        int idProduto = lerOpcao();
        if (idProduto <= 0) return;

        Produto produtoExistente = gProdutos.buscarProdutoPorId(idProduto);
        if (produtoExistente == null) {
            System.out.println("Produto não encontrado.");
            return;
        }

        System.out.print("Novo Nome (" + produtoExistente.getNome() + "): ");
        String nome = scanner.nextLine();
        if (nome.isEmpty()) nome = produtoExistente.getNome();

        System.out.print("Novo Preço (" + produtoExistente.getPreco() + "): ");
        double preco;

        String precoStr = scanner.nextLine();
        if (precoStr.isEmpty()) {
            preco = produtoExistente.getPreco();
        } else {
            try {
                preco = Double.parseDouble(precoStr);
            } catch (NumberFormatException e) {
                System.out.println("Preço inválido. Usando o valor anterior.");
                preco = produtoExistente.getPreco();
            }
        }
        gProdutos.atualizarProduto(idProduto, nome, preco);
    }

    private static void criarVenda() {
        if (vendaAtual != null && vendaAtual.getStatus().equals("aberto")) {
            System.out.println("Já existe uma venda aberta. Finalize-a ou adicione itens a ela.");
            return;
        }

        Cliente clienteSelecionado = selecionarCliente();
        if (clienteSelecionado != null) {
            vendaAtual = new Venda(gVendas.getProximoIdVenda(), clienteSelecionado);
            gVendas.adicionarVenda(vendaAtual);
            System.out.println("Venda criada para o cliente: " + vendaAtual.getCliente().getNome() + ". ID da Venda: " + vendaAtual.getId());
        } else {
            System.out.println("Criação de venda cancelada.");
            vendaAtual = null;
        }
    }

    private static void adicionarItemVenda() {
        if (vendaAtual == null || !vendaAtual.getStatus().equals("aberto")) {
            System.out.println("Nenhuma venda aberta para adicionar itens. Crie uma venda primeiro.");
            return;
        }

        Produto produtoSelecionado = selecionarProduto();
        if (produtoSelecionado != null) {
            System.out.println("Produto selecionado: " + produtoSelecionado.getNome() + ", Preço: R$" + produtoSelecionado.getPreco());

            int quantidade = 1;
            System.out.print("Quantidade desejada (Enter para 1): ");
            String quantidadeStr = scanner.nextLine();
            if (!quantidadeStr.isEmpty()) {
                try {
                    quantidade = Integer.parseInt(quantidadeStr);
                } catch (NumberFormatException e) {
                    System.out.println("Quantidade inválida. Usando 1.");
                }
            }

            double precoVenda = produtoSelecionado.getPreco();
            System.out.print("Preço de venda (Enter para R$" + precoVenda + "): ");
            String precoVendaStr = scanner.nextLine();

            if (!precoVendaStr.isEmpty()) {
                try {
                    precoVenda = Double.parseDouble(precoVendaStr);
                } catch (NumberFormatException e) {
                    System.out.println("Preço de venda inválido. Usando o preço padrão do produto.");
                }
            }

            gVendas.adicionarItemVenda(vendaAtual.getId(), produtoSelecionado, quantidade, precoVenda);
            System.out.println("Item adicionado à venda.");
        } else {
            System.out.println("Item não adicionado à venda.");
        }
    }

    private static void removerItemVenda() {
        if (vendaAtual == null || !vendaAtual.getStatus().equals("aberto")) {
            System.out.println("Nenhuma venda aberta para remover itens.");
            return;
        }
        System.out.print("Digite o ID do produto a remover da venda: ");
        int produtoIdRemover = lerOpcao();
        if (produtoIdRemover <= 0) return;

        gVendas.removerItemVenda(vendaAtual.getId(), produtoIdRemover);
    }

    private static void alterarQuantidadeItemVenda() {
        if (vendaAtual == null || !vendaAtual.getStatus().equals("aberto")) {
            System.out.println("Nenhuma venda aberta para alterar a quantidade de itens.");
            return;
        }

        System.out.print("Digite o ID do produto para alterar a quantidade: ");
        int produtoIdAlterar = lerOpcao();
        if (produtoIdAlterar <= 0) return;

        System.out.print("Digite a nova quantidade: ");
        int novaQuantidade = 1;
        String novaQuantidadeStr = scanner.nextLine();
        if (!novaQuantidadeStr.isEmpty()) {
            try {
                novaQuantidade = Integer.parseInt(novaQuantidadeStr);
            } catch (NumberFormatException e) {
                System.out.println("Quantidade inválida. Usando 1.");
            }
        }
        gVendas.alterarQuantidadeItemVenda(vendaAtual.getId(), produtoIdAlterar, novaQuantidade);

    }
    private static void finalizarVendaComConfirmacao() {
        if (vendaAtual == null || !vendaAtual.getStatus().equals("aberto")) {
            System.out.println("Nenhuma venda aberta para finalizar.");
            return;
        }

        if (vendaAtual.getItens().isEmpty()) {
            System.out.println("A venda não possui itens. Adicione itens antes de finalizar.");
            return;
        }

        System.out.println("\n=== Resumo do Pedido ===");
        System.out.println("Cliente: " + vendaAtual.getCliente().getNome());
        System.out.println("Itens do Pedido:");
        for (ItemVenda item : vendaAtual.getItens()) {
            System.out.println("  - " + item.getProduto().getNome() + ", Quantidade: " + item.getQuantidade() +
                    ", Preço Unitário: R$" + item.getPrecoVenda() + ", Subtotal: R$" + (item.getPrecoVenda() * item.getQuantidade()));
        }
        System.out.printf("Valor Total: R$ %.2f\n", vendaAtual.calcularTotalVenda());
        System.out.print("Confirmar finalização da venda (S/N)? ");
        String confirmacao = scanner.nextLine();

        if (confirmacao.equalsIgnoreCase("S")) {
            gVendas.finalizarVenda(vendaAtual.getId());
            System.out.println("Venda " + vendaAtual.getId() + " finalizada. Status: Aguardando Pagamento.");
            vendaAtual = null; // Reseta a venda atual
        } else {
            System.out.println("Finalização da venda cancelada.");
        }
    }
    private static void finalizarVenda() {
        if (vendaAtual == null || !vendaAtual.getStatus().equals("aberto")) {
            System.out.println("Nenhuma venda aberta para finalizar.");
            return;
        }
        if (vendaAtual.getItens().isEmpty()) {
            System.out.println("A venda não possui itens. Adicione itens antes de finalizar.");
            return;
        }
        gVendas.finalizarVenda(vendaAtual.getId());
        System.out.println("Venda " + vendaAtual.getId() + " finalizada para o cliente " + vendaAtual.getCliente().getNome() + ".");
        vendaAtual = null;
    }

    private static void listarVendasDetalhado() {
        List<Venda> vendas = gVendas.getVendas();
        if (vendas.isEmpty()) {
            System.out.println("Nenhuma venda cadastrada.");
            return;
        }

        System.out.println("\n=== Vendas Cadastradas (Detalhado) ===");
        for (Venda venda : vendas) {
            System.out.println("-------------------------");
            System.out.println("ID da Venda: " + venda.getId());
            System.out.println("Cliente: " + venda.getCliente().getNome());
            System.out.println("Data: " + venda.getData());
            System.out.println("Status: " + venda.getStatus());
            System.out.println("Status Pagamento: " + venda.getStatusPagamento());
            System.out.printf("Valor Total: R$ %.2f\n", venda.calcularTotalVenda());

            if (!venda.getItens().isEmpty()) {
                System.out.println("Itens:");
                for (ItemVenda item : venda.getItens()) {
                    System.out.println("  - Produto: " + item.getProduto().getNome() +
                            ", Quantidade: " + item.getQuantidade() +
                            ", Preço Unitário: R$" + item.getPrecoVenda());
                }
            } else {
                System.out.println("  Nenhum item nesta venda.");
            }

            System.out.println("-------------------------");
        }
    }

    private static void realizarPagamento() {
        List<Venda> vendasAguardandoPagamento = gVendas.getVendas().stream()
                .filter(v -> "Aguardando pagamento".equals(v.getStatusPagamento()))
                .collect(Collectors.toList());

        if (vendasAguardandoPagamento.isEmpty()) {
            System.out.println("Não há vendas aguardando pagamento.");
            return;
        }

        System.out.println("\n=== Vendas Aguardando Pagamento ===");
        for (int i = 0; i < vendasAguardandoPagamento.size(); i++) {
            Venda venda = vendasAguardandoPagamento.get(i);
            System.out.println((i + 1) + ". Venda ID: " + venda.getId() + ", Cliente: " + venda.getCliente().getNome() + ", Valor Total: R$" + venda.calcularTotalVenda());
        }

        System.out.print("Escolha o número da venda para realizar o pagamento (ou 0 para cancelar): ");
        int escolhaVenda = lerOpcao();

        if (escolhaVenda > 0 && escolhaVenda <= vendasAguardandoPagamento.size()) {
            Venda vendaParaPagar = vendasAguardandoPagamento.get(escolhaVenda - 1);
            gVendas.realizarPagamentoVenda(vendaParaPagar.getId());
            if(vendaAtual != null && vendaAtual.getId() == vendaParaPagar.getId()){
                vendaAtual = null;
            }

        } else if (escolhaVenda != 0) {
            System.out.println("Opção inválida.");
        }
    }

    private static void entregarVenda() {
        if (vendaAtual == null) {
            System.out.println("Nenhuma venda selecionada.");
            return;
        }

        if ("Pago".equals(vendaAtual.getStatusPagamento()) && "fechado".equals(vendaAtual.getStatus())) {
            gVendas.entregarVenda(vendaAtual.getId());
            vendaAtual = null;
        } else {
            System.out.println("A venda atual não pode ser entregue. Verifique se o pagamento foi realizado e a venda foi finalizada.");
        }
    }

    private static Cliente selecionarCliente() {
        List<Cliente> clientes = gClientes.getClientes();
        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado. Cadastre um cliente primeiro.");
            return null;
        }

        System.out.println("\n=== Clientes Cadastrados ===");
        for (int i = 0; i < clientes.size(); i++) {
            Cliente cliente = clientes.get(i);
            System.out.println((i + 1) + ". " + cliente.getNome() + " (ID: " + cliente.getId() + ")");
        }
        System.out.print("Escolha o número do cliente para a venda (ou 0 para cancelar): ");

        int escolhaCliente = lerOpcao();
        if (escolhaCliente == 0) {
            return null;
        }
        if (escolhaCliente < 0 || escolhaCliente > clientes.size()) {
            System.out.println("Opção inválida.");
            return null;
        }


        return clientes.get(escolhaCliente - 1);

    }

    private static Produto selecionarProduto() {
        List<Produto> produtos = gProdutos.getProdutos();
        if (produtos.isEmpty()) {
            System.out.println("Nenhum produto cadastrado. Cadastre um produto primeiro.");
            return null;
        }

        System.out.println("\n=== Produtos Cadastrados ===");
        for (int i = 0; i < produtos.size(); i++) {
            Produto produto = produtos.get(i);
            System.out.println((i + 1) + ". " + produto.getNome() + " (ID: " + produto.getId() + ", Preço: R$" + produto.getPreco() + ")");
        }
        System.out.print("Escolha o número do produto para adicionar à venda (ou 0 para cancelar): ");

        int escolhaProduto = lerOpcao();
        if (escolhaProduto == 0) {
            return null;
        }
        if (escolhaProduto < 0 || escolhaProduto > produtos.size()) {
            System.out.println("Opção inválida.");
            return null;
        }

        return produtos.get(escolhaProduto - 1);
    }

    private static void aguardarEnter() {
        System.out.print("Pressione Enter para continuar...");
        scanner.nextLine();
    }
}
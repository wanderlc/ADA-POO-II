// 8. Main.java (Menu corrigido para aceitar entrada 3.4 e similares)
package com.adacommerce;

import com.adacommerce.clientes.Cliente;
import com.adacommerce.clientes.GerenciadorClientes;
import com.adacommerce.produtos.GerenciadorProdutos;
import com.adacommerce.produtos.Produto;
import com.adacommerce.vendas.GerenciadorVendas;
import com.adacommerce.vendas.Venda;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static GerenciadorClientes gClientes = new GerenciadorClientes();
    private static GerenciadorProdutos gProdutos = new GerenciadorProdutos();
    private static GerenciadorVendas gVendas = new GerenciadorVendas(gClientes, gProdutos);
    private static Scanner scanner = new Scanner(System.in);
    private static Venda vendaAtual = null;

    public static void main(String[] args) {
        int opcao = -1; // Inicializa com um valor inválido
        do {
            System.out.println("\n=== Menu Ada Commerce ===");
            System.out.println("1. Clientes");
            System.out.println("   1.1. Cadastrar Cliente");
            System.out.println("   1.2. Listar Clientes");
            System.out.println("2. Produtos");
            System.out.println("   2.1. Cadastrar Produto");
            System.out.println("   2.2. Listar Produtos");
            System.out.println("3. Vendas");
            System.out.println("   3.1. Criar Venda");
            System.out.println("   3.2. Adicionar Item à Venda");
            System.out.println("   3.3. Finalizar Venda");
            System.out.println("   3.4. Realizar Pagamento de Venda");
            System.out.println("   3.5. Listar Vendas Detalhado");
            System.out.println("4. Sair");
            System.out.print("Escolha uma opção: ");

            String opcaoStr = scanner.nextLine(); // Lê a entrada como String
            try {
                opcao = Integer.parseInt(opcaoStr.replace(".", "")); // Tenta converter para int, removendo o ponto
            } catch (NumberFormatException e) {
                opcao = -1; // Mantém como -1 se não for um número válido
            }

            switch (opcao) {
                case 1:
                case 11: cadastrarCliente(); break; // Opção 1 ou 11
                case 12: listarClientes(); break;
                case 2:
                case 21: cadastrarProduto(); break; // Opção 2 ou 21
                case 22: listarProdutos(); break;
                case 3:
                case 31: criarVenda(); break; // Opção 3 ou 31
                case 32: adicionarItemVenda(); break;
                case 33: finalizarVenda(); break;
                case 34: realizarPagamentoVenda(); break;
                case 35: listarVendasDetalhado(); break;
                case 4: System.out.println("Saindo..."); break;
                default: System.out.println("Opção inválida!");
            }
        } while (opcao != 4);
        scanner.close();
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

    private static void cadastrarProduto() {
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Preço: ");
        double preco = scanner.nextDouble();
        scanner.nextLine();
        gProdutos.cadastrarProduto(nome, preco);
    }

    private static void listarProdutos() {
        gProdutos.listarProdutos();
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
            System.out.print("Quantidade desejada: ");
            int quantidade = scanner.nextInt();
            scanner.nextLine();
            double precoVenda = produtoSelecionado.getPreco();
            System.out.print("Preço de venda (R$" + precoVenda + " - padrão, ou digite novo preço): ");
            System.out.println("Main: Adicionando item - Produto: " + produtoSelecionado.getNome() + ", Quantidade: " + quantidade + ", Preço Venda: " + precoVenda); // ADICIONADO
            if (scanner.hasNextDouble()) {
                precoVenda = scanner.nextDouble();
                scanner.nextLine();
            } else {
                scanner.nextLine();
                System.out.println("Usando preço padrão do produto.");
            }

            gVendas.adicionarItemVenda(vendaAtual.getId(), produtoSelecionado, quantidade, precoVenda);
            System.out.println("Item adicionado à venda.");
        } else {
            System.out.println("Item não adicionado à venda.");
        }
    }


    private static void finalizarVenda() {
        if (vendaAtual == null || !vendaAtual.getStatus().equals("aberto")) {
            System.out.println("Nenhuma venda aberta para finalizar.");
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
            System.out.println("-------------------------");
        }
    }


    private static void realizarPagamentoVenda() {
        System.out.print("Digite o ID da venda para realizar o pagamento: ");
        if (scanner.hasNextInt()) {
            int vendaIdPagamento = scanner.nextInt();
            scanner.nextLine();
            gVendas.realizarPagamentoVenda(vendaIdPagamento);
        } else {
            System.out.println("ID de venda inválido.");
            scanner.nextLine();
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

        int escolhaCliente;
        if (scanner.hasNextInt()) {
            escolhaCliente = scanner.nextInt();
            scanner.nextLine();
        } else {
            System.out.println("Opção inválida. Cancelando.");
            scanner.nextLine();
            return null;
        }

        if (escolhaCliente == 0) {
            return null;
        }

        if (escolhaCliente > 0 && escolhaCliente <= clientes.size()) {
            return clientes.get(escolhaCliente - 1);
        } else {
            System.out.println("Número de cliente inválido. Cancelando.");
            return null;
        }
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

        int escolhaProduto;
        if (scanner.hasNextInt()) {
            escolhaProduto = scanner.nextInt();
            scanner.nextLine();
        } else {
            System.out.println("Opção inválida. Cancelando.");
            scanner.nextLine();
            return null;
        }

        if (escolhaProduto == 0) {
            return null;
        }

        if (escolhaProduto > 0 && escolhaProduto <= produtos.size()) {
            return produtos.get(escolhaProduto - 1);
        } else {
            System.out.println("Número de produto inválido. Cancelando.");
            return null;
        }
    }
}
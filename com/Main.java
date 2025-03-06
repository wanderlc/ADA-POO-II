package com.adacommerce;

import com.adacommerce.clientes.Cliente;
import com.adacommerce.clientes.GerenciadorClientes;
import com.adacommerce.produtos.GerenciadorProdutos;
import com.adacommerce.produtos.Produto;
import com.adacommerce.vendas.GerenciadorVendas;
import java.util.Scanner;

public class Main {
    private static GerenciadorClientes gClientes = new GerenciadorClientes();
    private static GerenciadorProdutos gProdutos = new GerenciadorProdutos();
    private static GerenciadorVendas gVendas = new GerenciadorVendas(gClientes, gProdutos);
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int opcao;
        do {
            System.out.println("\n=== Menu Ada Commerce ===");
            System.out.println("1. Cadastrar Cliente");
            System.out.println("2. Listar Clientes");
            System.out.println("3. Cadastrar Produto");
            System.out.println("4. Listar Produtos");
            System.out.println("5. Criar Venda");
            System.out.println("6. Adicionar Item à Venda");
            System.out.println("7. Finalizar Venda");
            System.out.println("8. Sair");
            System.out.print("Escolha uma opção: ");

            opcao = scanner.nextInt();
            scanner.nextLine(); // Limpar buffer

            switch (opcao) {
                case 1: cadastrarCliente(); break;
                case 2: listarClientes(); break;
                case 3: cadastrarProduto(); break;
                case 4: listarProdutos(); break;
                case 5: criarVenda(); break;
                case 6: adicionarItemVenda(); break;
                case 7: finalizarVenda(); break;
                case 8:
                    System.out.println("Saindo...");
                    break;
                default: System.out.println("Opção inválida!");
            }
        } while (opcao != 8);
        scanner.close();
    }

    private static void cadastrarCliente() {
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Documento: ");
        String doc = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        gClientes.cadastrarCliente(nome, doc, email); // ID é gerado automaticamente
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
        gProdutos.cadastrarProduto(nome, preco); // ID é gerado automaticamente
    }

    private static void listarProdutos() {
        gProdutos.listarProdutos();
    }

    private static void criarVenda() {
        System.out.print("ID do Cliente: ");
        int idCliente = scanner.nextInt();
        scanner.nextLine();

        Cliente cliente = gClientes.buscarClientePorId(idCliente); // Busca cliente pelo ID
        if (cliente != null) {
            gVendas.criarVenda(cliente); // ID da venda é gerado automaticamente
        } else {
            System.out.println("Cliente não encontrado!");
        }
    }

    private static void adicionarItemVenda() {
        System.out.print("ID da Venda: ");
        int idVenda = scanner.nextInt();
        scanner.nextLine();
        System.out.print("ID do Produto: ");
        int idProduto = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Quantidade: ");
        int qtd = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Preço de Venda: ");
        double preco = scanner.nextDouble();
        scanner.nextLine();

        Produto produto = gProdutos.buscarProdutoPorId(idProduto); // Busca produto pelo ID
        if (produto != null) {
            gVendas.adicionarItemVenda(idVenda, produto, qtd, preco);
        } else {
            System.out.println("Produto não encontrado!");
        }
    }

    private static void finalizarVenda() {
        System.out.print("ID da Venda: ");
        int idVenda = scanner.nextInt();
        scanner.nextLine();
        gVendas.finalizarVenda(idVenda); // Correção: Usar idVenda (variável local correta)
    }
}
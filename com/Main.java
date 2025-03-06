package com.adacommerce;

import com.adacommerce.clientes.Cliente;
import com.adacommerce.clientes.GerenciadorClientes;
import com.adacommerce.produtos.GerenciadorProdutos;
import com.adacommerce.produtos.Produto;
import com.adacommerce.vendas.GerenciadorVendas;

public class Main {
    public static void main(String[] args) {
        // Exemplo de uso
        GerenciadorClientes gerenciadorClientes = new GerenciadorClientes();
        GerenciadorProdutos gerenciadorProdutos = new GerenciadorProdutos();
        GerenciadorVendas gerenciadorVendas = new GerenciadorVendas();

        // Cadastrar cliente
        gerenciadorClientes.cadastrarCliente(1, "João Silva", "123.456.789-00", "joao@example.com");

        // Cadastrar produtos
        gerenciadorProdutos.cadastrarProduto(1, "Notebook", 3500.00);
        gerenciadorProdutos.cadastrarProduto(2, "Mouse", 50.00);

        // Criar venda
        Cliente cliente = new Cliente(1, "João Silva", "123.456.789-00", "joao@example.com");
        gerenciadorVendas.criarVenda(1, cliente);

        // Adicionar itens à venda
        Produto notebook = new Produto(1, "Notebook", 3500.00);
        Produto mouse = new Produto(2, "Mouse", 50.00);
        gerenciadorVendas.adicionarItemVenda(1, notebook, 1, 3500.00);
        gerenciadorVendas.adicionarItemVenda(1, mouse, 2, 45.00);

        // Finalizar venda
        gerenciadorVendas.finalizarVenda(1);
    }
}
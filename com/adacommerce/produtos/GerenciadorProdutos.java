package com.adacommerce.produtos;

import java.util.ArrayList;
import java.util.List;

public class GerenciadorProdutos {
    private List<Produto> produtos = new ArrayList<>();

    public void cadastrarProduto(int id, String nome, double preco) {
        Produto produto = new Produto(id, nome, preco);
        produtos.add(produto);
        System.out.println("Produto " + nome + " cadastrado com sucesso.");
    }

    public void listarProdutos() {
        for (Produto produto : produtos) {
            System.out.println("ID: " + produto.getId() + ", Nome: " + produto.getNome() +
                    ", Preço: R$" + produto.getPreco());
        }
    }

    public void atualizarProduto(int id, String nome, double preco) {
        for (Produto produto : produtos) {
            if (produto.getId() == id) {
                if (nome != null) produto.setNome(nome);
                if (preco > 0) produto.setPreco(preco);
                System.out.println("Produto " + id + " atualizado com sucesso.");
                return;
            }
        }
        System.out.println("Produto " + id + " não encontrado.");
    }
}
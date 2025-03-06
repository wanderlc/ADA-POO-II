public class ItemVenda {
}
package com.adacommerce.vendas;

import com.adacommerce.produtos.Produto;

public class ItemVenda {
    private Produto produto;
    private int quantidade;
    private double precoVenda;

    public ItemVenda(Produto produto, int quantidade, double precoVenda) {
        this.produto = produto;
        this.quantidade = quantidade;
        this.precoVenda = precoVenda;
    }

    // Getters e Setters
    public Produto getProduto() {
        return produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getPrecoVenda() {
        return precoVenda;
    }

    public void setPrecoVenda(double precoVenda) {
        this.precoVenda = precoVenda;
    }
}
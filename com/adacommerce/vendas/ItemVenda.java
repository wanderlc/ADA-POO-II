// 5. ItemVenda.java (No changes needed)
package com.adacommerce.vendas;

import com.adacommerce.produtos.Produto;

public class ItemVenda {
    private Produto produto;
    private int quantidade;
    private double precoVenda;

    public ItemVenda() {
        // Construtor vazio se precisar no futuro
    }

    public ItemVenda(Produto produto, int quantidade, double precoVenda) {
        this.produto = produto;
        this.quantidade = quantidade;
        this.precoVenda = precoVenda;
    }

    // Getters
    public Produto getProduto() { return produto; }
    public int getQuantidade() { return quantidade; }
    public double getPrecoVenda() { return precoVenda; }

    // Setters (Adicionados se precisar no futuro)
    public void setProduto(Produto produto) { this.produto = produto; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }
    public void setPrecoVenda(double precoVenda) { this.precoVenda = precoVenda; }
}
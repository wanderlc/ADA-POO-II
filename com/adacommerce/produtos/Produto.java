// 3. Produto.java (No changes needed)
package com.adacommerce.produtos;

public class Produto {
    private int id;
    private String nome;
    private double preco;

    public Produto() {
        // Construtor vazio necessário para CSV loading
    }

    public Produto(int id, String nome, double preco) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
    }

    // Getters
    public int getId() { return id; }
    public String getNome() { return nome; }
    public double getPreco() { return preco; }

    // Setters
    public void setId(int id) { this.id = id; } // Setter para ID necessário para Jackson
    public void setNome(String nome) { this.nome = nome; }
    public void setPreco(double preco) { this.preco = preco; }
}
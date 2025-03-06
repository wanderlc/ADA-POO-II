package com.adacommerce.clientes;

public class Cliente {
    private int id;
    private String nome;
    private String documento;
    private String email;

    public Cliente() {
    }

    public Cliente(int id, String nome, String documento, String email) {
        this.id = id;
        this.nome = nome;
        this.documento = documento;
        this.email = email;
    }

    public int getId() { return id; }
    public String getNome() { return nome; }
    public String getDocumento() { return documento; }
    public String getEmail() { return email; }

    public void setNome(String nome) { this.nome = nome; }
    public void setDocumento(String documento) { this.documento = documento; }
    public void setEmail(String email) { this.email = email; }
}
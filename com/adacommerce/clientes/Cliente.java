package Clientes;

public class Cliente {
    private int id;
    private String nome;
    private String documento;
    private String email;

    public Cliente(int id, String nome, String documento, String email) {
        this.id = id;
        this.nome = nome;
        this.documento = documento;
        this.email = email;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
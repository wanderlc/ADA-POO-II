package Clientes;

import java.util.ArrayList;
import java.util.List;

public class GerenciadorClientes {
    private List<Cliente> clientes = new ArrayList<>();

    public void cadastrarCliente(int id, String nome, String documento, String email) {
        Cliente cliente = new Cliente(id, nome, documento, email);
        clientes.add(cliente);
        System.out.println("Cliente " + nome + " cadastrado com sucesso.");
    }

    public void listarClientes() {
        for (Cliente cliente : clientes) {
            System.out.println("ID: " + cliente.getId() + ", Nome: " + cliente.getNome() +
                    ", Documento: " + cliente.getDocumento() + ", Email: " + cliente.getEmail());
        }
    }

    public void atualizarCliente(int id, String nome, String documento, String email) {
        for (Cliente cliente : clientes) {
            if (cliente.getId() == id) {
                if (nome != null) cliente.setNome(nome);
                if (documento != null) cliente.setDocumento(documento);
                if (email != null) cliente.setEmail(email);
                System.out.println("Cliente " + id + " atualizado com sucesso.");
                return;
            }
        }
        System.out.println("Cliente " + id + " não encontrado.");
    }
}
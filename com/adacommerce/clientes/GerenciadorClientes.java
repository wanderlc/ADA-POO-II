package com.adacommerce.clientes;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.File;

public class GerenciadorClientes {
    private List<Cliente> clientes = new ArrayList<>();
    private static final String CLIENTES_FILE = "clientes.csv";
    private static int proximoIdCliente = 1;

    public GerenciadorClientes() {
        carregarClientesDeCsv();
        if (!clientes.isEmpty()) {
            proximoIdCliente = clientes.stream()
                    .mapToInt(Cliente::getId)
                    .max()
                    .orElse(0) + 1;
        }
    }

    public int getProximoIdCliente() {
        return proximoIdCliente;
    }

    public void cadastrarCliente(String nome, String documento, String email) {
        int id = proximoIdCliente++;
        Cliente novoCliente = new Cliente(id, nome, documento, email);
        clientes.add(novoCliente);
        salvarClientesParaCsv();
        System.out.println("Cliente cadastrado com sucesso! ID: " + id);
    }

    public void listarClientes() {
        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado.");
            return;
        }
        for (Cliente cliente : clientes) {
            System.out.println(
                    "ID: " + cliente.getId() +
                            ", Nome: " + cliente.getNome() +
                            ", Documento: " + cliente.getDocumento() +
                            ", Email: " + cliente.getEmail()
            );
        }
    }

    public Cliente buscarClientePorId(int id) {
        for (Cliente cliente : clientes) {
            if (cliente.getId() == id) {
                return cliente;
            }
        }
        return null;
    }

    public void atualizarCliente(int id, String nome, String documento, String email) {
        Cliente clienteExistente = buscarClientePorId(id);
        if (clienteExistente != null) {
            clienteExistente.setNome(nome);
            clienteExistente.setDocumento(documento);
            clienteExistente.setEmail(email);
            salvarClientesParaCsv();
            System.out.println("Cliente ID " + id + " atualizado com sucesso!");
        } else {
            System.out.println("Cliente ID " + id + " não encontrado.");
        }
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    private void carregarClientesDeCsv() {
        File file = new File(CLIENTES_FILE);
        if (!file.exists()) {
            clientes = new ArrayList<>();
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(CLIENTES_FILE))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] campos = linha.split(",");
                if (campos.length == 4) {
                    try {
                        int id = Integer.parseInt(campos[0]);
                        String nome = campos[1];
                        String documento = campos[2];
                        String email = campos[3];
                        Cliente cliente = new Cliente(id, nome, documento, email);
                        clientes.add(cliente);
                    } catch (NumberFormatException e) {
                        System.err.println("Erro ao ler ID do cliente no CSV: " + linha);
                    }
                } else {
                    System.err.println("Formato inválido de linha no CSV de clientes: " + linha);
                }
            }
            System.out.println("Clientes carregados do arquivo CSV.");
        } catch (IOException e) {
            System.err.println("Erro ao carregar clientes do CSV: " + e.getMessage());
            clientes = new ArrayList<>();
        }
    }

    private void salvarClientesParaCsv() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CLIENTES_FILE))) {
            for (Cliente cliente : clientes) {
                bw.write(cliente.getId() + "," + cliente.getNome() + "," + cliente.getDocumento() + "," + cliente.getEmail());
                bw.newLine();
            }
            System.out.println("Clientes salvos no arquivo CSV.");
        } catch (IOException e) {
            System.err.println("Erro ao salvar clientes para CSV: " + e.getMessage());
        }
    }
}
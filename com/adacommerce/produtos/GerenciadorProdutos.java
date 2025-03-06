package com.adacommerce.produtos;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.File; // Importação adicionada para a classe File

public class GerenciadorProdutos {
    private List<Produto> produtos = new ArrayList<>();
    private static final String PRODUTOS_FILE = "produtos.csv"; // Nome do arquivo CSV
    private static int proximoIdProduto = 1;

    public GerenciadorProdutos() {
        carregarProdutosDeCsv(); // Carrega os produtos do CSV ao iniciar
        if (!produtos.isEmpty()) {
            proximoIdProduto = produtos.stream()
                    .mapToInt(Produto::getId)
                    .max()
                    .orElse(0) + 1;
        }
    }

    public void cadastrarProduto(String nome, double preco) {
        int id = proximoIdProduto++;
        Produto novoProduto = new Produto(id, nome, preco);
        produtos.add(novoProduto);
        salvarProdutosParaCsv(); // Salva no CSV após cadastrar
        System.out.println("Produto cadastrado com sucesso! ID: " + id);
    }

    public void listarProdutos() {
        if (produtos.isEmpty()) {
            System.out.println("Nenhum produto cadastrado.");
            return;
        }
        for (Produto produto : produtos) {
            System.out.println(
                    "ID: " + produto.getId() +
                            ", Nome: " + produto.getNome() +
                            ", Preço: R$" + produto.getPreco()
            );
        }
    }

    public Produto buscarProdutoPorId(int id) {
        for (Produto produto : produtos) {
            if (produto.getId() == id) {
                return produto;
            }
        }
        return null;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    private void carregarProdutosDeCsv() {
        File file = new File(PRODUTOS_FILE);
        if (!file.exists()) {
            produtos = new ArrayList<>();
            return; // Se o arquivo não existe, inicia com lista vazia
        }

        try (BufferedReader br = new BufferedReader(new FileReader(PRODUTOS_FILE))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] campos = linha.split(",");
                if (campos.length == 3) {
                    try {
                        int id = Integer.parseInt(campos[0]);
                        String nome = campos[1];
                        double preco = Double.parseDouble(campos[2]);
                        Produto produto = new Produto(id, nome, preco);
                        produtos.add(produto);
                    } catch (NumberFormatException e) {
                        System.err.println("Erro ao ler ID ou Preço do produto no CSV: " + linha);
                        // Ignora a linha com erro de formato
                    }
                } else {
                    System.err.println("Formato inválido de linha no CSV de produtos: " + linha);
                    // Ignora a linha com formato inválido
                }
            }
            System.out.println("Produtos carregados do arquivo CSV.");
        } catch (IOException e) {
            System.err.println("Erro ao carregar produtos do CSV: " + e.getMessage());
            produtos = new ArrayList<>(); // Garante que a lista não seja null em caso de erro
        }
    }

    private void salvarProdutosParaCsv() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(PRODUTOS_FILE))) {
            for (Produto produto : produtos) {
                bw.write(produto.getId() + "," + produto.getNome() + "," + produto.getPreco());
                bw.newLine();
            }
            System.out.println("Produtos salvos no arquivo CSV.");
        } catch (IOException e) {
            System.err.println("Erro ao salvar produtos para CSV: " + e.getMessage());
        }
    }
}
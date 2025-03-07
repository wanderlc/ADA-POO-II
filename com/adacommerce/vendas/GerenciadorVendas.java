// 7. GerenciadorVendas.java (Adicionado métodos removerItemVenda, alterarQuantidadeItemVenda e entregarVenda)
package com.adacommerce.vendas;

import com.adacommerce.clientes.Cliente;
import com.adacommerce.clientes.GerenciadorClientes;
import com.adacommerce.produtos.GerenciadorProdutos;
import com.adacommerce.produtos.Produto;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.File; // Importação adicionada para a classe File

public class GerenciadorVendas {
    private List<Venda> vendas = new ArrayList<>();
    private static final String VENDAS_FILE = "vendas.csv"; // Nome do arquivo CSV
    private static int proximoIdVenda = 1;

    private final GerenciadorClientes gerenciadorClientes; // Necessário para buscar cliente por ID ao carregar vendas
    private final GerenciadorProdutos gerenciadorProdutos; // Não usado na persistencia CSV simplificada de Vendas

    public GerenciadorVendas(GerenciadorClientes gerenciadorClientes, GerenciadorProdutos gerenciadorProdutos) {
        this.gerenciadorClientes = gerenciadorClientes;
        this.gerenciadorProdutos = gerenciadorProdutos;
        carregarVendasDeCsv(); // Carrega as vendas do CSV ao iniciar
        if (!vendas.isEmpty()) {
            proximoIdVenda = vendas.stream()
                    .mapToInt(Venda::getId)
                    .max()
                    .orElse(0) + 1;
        }
    }

    // Getter para proximoIdVenda
    public int getProximoIdVenda() {
        return proximoIdVenda;
    }

    public void adicionarVenda(Venda venda) {
        vendas.add(venda);
        salvarVendasParaCsv(); // Salva no CSV após adicionar venda
    }

    public void criarVenda(Cliente cliente) {
        int id = proximoIdVenda++;
        Venda novaVenda = new Venda(id, cliente);
        vendas.add(novaVenda);
        salvarVendasParaCsv(); // Salva no CSV após criar
        System.out.println("Venda criada com sucesso! ID: " + id);
    }

    public void adicionarItemVenda(int vendaId, Produto produto, int quantidade, double precoVenda) {
        Venda venda = buscarVendaPorId(vendaId);
        if (venda != null) {
            System.out.println("GerenciadorVendas: Venda encontrada, adicionando item...");
            venda.adicionarItem(new ItemVenda(produto, quantidade, precoVenda));
            salvarVendasParaCsv();
        } else {
            System.out.println("Venda não encontrada!");
        }
    }

    public void removerItemVenda(int vendaId, int produtoId) {
        Venda venda = buscarVendaPorId(vendaId);
        if (venda != null) {
            venda.removerItem(produtoId);
            salvarVendasParaCsv();
        } else {
            System.out.println("Venda não encontrada!");
        }
    }

    public void alterarQuantidadeItemVenda(int vendaId, int produtoId, int quantidade) {
        Venda venda = buscarVendaPorId(vendaId);
        if (venda != null) {
            venda.alterarQuantidadeItem(produtoId, quantidade);
            salvarVendasParaCsv();
        } else {
            System.out.println("Venda não encontrada!");
        }
    }

    public void finalizarVenda(int vendaId) {
        Venda venda = buscarVendaPorId(vendaId);
        if (venda != null) {
            venda.finalizarPedido();
            salvarVendasParaCsv();
        } else {
            System.out.println("Venda não encontrada!");
        }
    }

    public void realizarPagamentoVenda(int vendaId) {
        Venda venda = buscarVendaPorId(vendaId);
        if (venda != null) {
            if ("Aguardando pagamento".equals(venda.getStatusPagamento())) {
                venda.setStatusPagamento("Pago");
                salvarVendasParaCsv();
                System.out.println("Pagamento da Venda " + vendaId + " realizado com sucesso.");
            } else {
                System.out.println("Venda " + vendaId + " não está aguardando pagamento ou já foi paga.");
            }
        } else {
            System.out.println("Venda não encontrada!");
        }
    }

    public void entregarVenda(int vendaId) {
        Venda venda = buscarVendaPorId(vendaId);
        if (venda != null) {
            venda.entregarPedido();
            salvarVendasParaCsv();
        } else {
            System.out.println("Venda não encontrada!");
        }
    }


    public Venda buscarVendaPorId(int id) {
        for (Venda venda : vendas) {
            if (venda.getId() == id) {
                return venda;
            }
        }
        return null;
    }

    public List<Venda> getVendas() {
        return vendas;
    }

    private void carregarVendasDeCsv() {
        File file = new File(VENDAS_FILE);
        if (!file.exists()) {
            vendas = new ArrayList<>();
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(VENDAS_FILE))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] campos = linha.split(",");
                if (campos.length >= 6) {
                    try {
                        int id = Integer.parseInt(campos[0]);
                        int clienteId = Integer.parseInt(campos[1]);
                        String data = campos[2];
                        String status = campos[3];
                        String statusPagamento = campos[4];
                        String totalVendaStr = campos[5];

                        Cliente cliente = gerenciadorClientes.buscarClientePorId(clienteId);
                        if (cliente != null) {
                            Venda venda = new Venda(id, cliente);
                            venda.setData(data);
                            venda.setStatus(status);
                            venda.setStatusPagamento(statusPagamento);
                            vendas.add(venda);
                        } else {
                            System.err.println("Cliente não encontrado para venda no CSV: " + linha);
                        }

                    } catch (NumberFormatException e) {
                        System.err.println("Erro ao ler ID da venda ou clienteId no CSV: " + linha);
                    }
                } else {
                    System.err.println("Formato inválido de linha no CSV de vendas: " + linha);
                }
            }
            System.out.println("Vendas carregadas do arquivo CSV.");
        } catch (IOException e) {
            System.err.println("Erro ao carregar vendas do CSV: " + e.getMessage());
            vendas = new ArrayList<>();
        }
    }

    private void salvarVendasParaCsv() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(VENDAS_FILE))) {
            for (Venda venda : vendas) {
                int clienteId = (venda.getCliente() != null) ? venda.getCliente().getId() : 0;
                double totalVenda = venda.calcularTotalVenda();
                bw.write(venda.getId() + "," + clienteId + "," + venda.getData() + "," + venda.getStatus() + "," + venda.getStatusPagamento() + "," + totalVenda);
                bw.newLine();
            }
            System.out.println("Vendas salvas no arquivo CSV.");
        } catch (IOException e) {
            System.err.println("Erro ao salvar vendas para CSV: " + e.getMessage());
        }
    }
}
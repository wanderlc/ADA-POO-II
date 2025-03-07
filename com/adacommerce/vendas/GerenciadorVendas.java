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
import java.util.stream.Collectors;
import java.io.File;

public class GerenciadorVendas {
    private List<Venda> vendas = new ArrayList<>();
    private static final String VENDAS_FILE = "vendas.csv";
    private static int proximoIdVenda = 1;

    private final GerenciadorClientes gerenciadorClientes;
    private final GerenciadorProdutos gerenciadorProdutos;

    public GerenciadorVendas(GerenciadorClientes gerenciadorClientes, GerenciadorProdutos gerenciadorProdutos) {
        this.gerenciadorClientes = gerenciadorClientes;
        this.gerenciadorProdutos = gerenciadorProdutos;
        carregarVendasDeCsv();
        if (!vendas.isEmpty()) {
            proximoIdVenda = vendas.stream()
                    .mapToInt(Venda::getId)
                    .max()
                    .orElse(0) + 1;
        }
    }

    public int getProximoIdVenda() {
        return proximoIdVenda;
    }

    public void adicionarVenda(Venda venda) {
        vendas.add(venda);
        salvarVendasParaCsv();
    }

    public void criarVenda(Cliente cliente) {
        int id = proximoIdVenda++;
        Venda novaVenda = new Venda(id, cliente);
        vendas.add(novaVenda);
        salvarVendasParaCsv();
    }

    public void adicionarItemVenda(int vendaId, Produto produto, int quantidade, double precoVenda) {
        Venda venda = buscarVendaPorId(vendaId);
        if (venda != null && venda.getStatus().equals("aberto")) {
            venda.adicionarItem(new ItemVenda(produto, quantidade, precoVenda));
            salvarVendasParaCsv();
        }
    }

    public void removerItemVenda(int vendaId, int produtoId) {
        Venda venda = buscarVendaPorId(vendaId);

        if (venda != null && venda.getStatus().equals("aberto")) {
            venda.removerItem(produtoId);
            salvarVendasParaCsv();
        }
    }

    public void alterarQuantidadeItemVenda(int vendaId, int produtoId, int quantidade) {
        Venda venda = buscarVendaPorId(vendaId);

        if (venda != null && venda.getStatus().equals("aberto")) {
            venda.alterarQuantidadeItem(produtoId, quantidade);
            salvarVendasParaCsv();
        }
    }

    public void finalizarVenda(int vendaId) {
        Venda venda = buscarVendaPorId(vendaId);
        if (venda != null && venda.getStatus().equals("aberto")) {
            venda.finalizarPedido();
            salvarVendasParaCsv();
        }
    }

    public void realizarPagamentoVenda(int vendaId) {
        Venda venda = buscarVendaPorId(vendaId);
        if (venda != null && "Aguardando pagamento".equals(venda.getStatusPagamento())) {
            venda.setStatusPagamento("Pago");
            salvarVendasParaCsv();
        }
    }

    public void entregarVenda(int vendaId) {
        Venda venda = buscarVendaPorId(vendaId);
        if (venda != null) {
            venda.entregarPedido();
            salvarVendasParaCsv();
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

    public List<Venda> listarVendasAguardandoPagamento() {
        return vendas.stream()
                .filter(venda -> "Aguardando pagamento".equals(venda.getStatusPagamento()))
                .collect(Collectors.toList());
    }

    private void carregarVendasDeCsv() {
        File file = new File(VENDAS_FILE);
        if (!file.exists()) {
            vendas = new ArrayList<>();
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(VENDAS_FILE))) {
            String linha;
            Venda vendaAtual = null;
            while ((linha = br.readLine()) != null) {
                String[] campos = linha.split(",");
                if (campos.length >= 6 && campos[0].equals("VENDA")) {
                    try {
                        vendaAtual = lerCabecalhoVenda(campos);
                        if (vendaAtual != null) {
                            vendas.add(vendaAtual);
                        }
                    } catch (NumberFormatException e) {
                        System.err.println("Erro ao ler dados da venda do CSV (cabeçalho): " + linha);
                        vendaAtual = null;
                    }
                } else if (campos.length == 4 && campos[0].equals("ITEM") && vendaAtual != null) {
                    try {
                        lerItemVenda(campos, vendaAtual);
                    } catch (NumberFormatException e) {
                        System.err.println("Erro ao ler dados do item de venda do CSV: " + linha);
                    }
                } else {
                    System.err.println("Linha inválida ou item de venda sem cabeçalho de venda associado no CSV: " + linha);
                    vendaAtual = null;
                }
            }
            System.out.println("Vendas carregadas do arquivo CSV.");
        } catch (IOException e) {
            System.err.println("Erro ao carregar vendas do CSV: " + e.getMessage());
            vendas = new ArrayList<>();
        }
    }

    private Venda lerCabecalhoVenda(String[] campos) {
        int id = Integer.parseInt(campos[1]);
        int clienteId = Integer.parseInt(campos[2]);
        String data = campos[3];
        String status = campos[4];
        String statusPagamento = campos[5];

        Cliente cliente = gerenciadorClientes.buscarClientePorId(clienteId);
        if (cliente != null) {
            Venda venda = new Venda(id, cliente);
            venda.setData(data);
            venda.setStatus(status);
            venda.setStatusPagamento(statusPagamento);
            return venda;
        } else {
            System.err.println("Cliente não encontrado para venda ID " + id + " no CSV.");
            return null;
        }
    }

    private void lerItemVenda(String[] campos, Venda vendaAtual) {
        int produtoId = Integer.parseInt(campos[1]);
        int quantidade = Integer.parseInt(campos[2]);
        double precoVenda = Double.parseDouble(campos[3]);

        Produto produto = gerenciadorProdutos.buscarProdutoPorId(produtoId);
        if (produto != null) {
            ItemVenda item = new ItemVenda(produto, quantidade, precoVenda);
            vendaAtual.adicionarItem(item);
        } else {
            System.err.println("Produto não encontrado para o item de venda: " + campos[1]);
        }
    }


    private void salvarVendasParaCsv() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(VENDAS_FILE))) {
            for (Venda venda : vendas) {
                int clienteId = (venda.getCliente() != null) ? venda.getCliente().getId() : 0;
                double totalVenda = venda.calcularTotalVenda();
                bw.write("VENDA," + venda.getId() + "," + clienteId + "," + venda.getData() + "," + venda.getStatus() + "," + venda.getStatusPagamento() + "," + totalVenda);
                bw.newLine();
                for (ItemVenda item : venda.getItens()) {
                    bw.write("ITEM," + item.getProduto().getId() + "," + item.getQuantidade() + "," + item.getPrecoVenda());
                    bw.newLine();
                }
            }
            System.out.println("Vendas salvas no arquivo CSV.");
        } catch (IOException e) {
            System.err.println("Erro ao salvar vendas para CSV: " + e.getMessage());
        }
    }
}
package com.adacommerce.vendas;

import com.adacommerce.clientes.Cliente;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

public class Venda {
    private int id;
    private Cliente cliente;
    private List<ItemVenda> itens = new ArrayList<>();
    private String data;
    private String status = "aberto";
    private String statusPagamento; // No default value initially

    public Venda() {}

    public Venda(int id, Cliente cliente) {
        this.id = id;
        this.cliente = cliente;
        this.data = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
    }

    public int getId() { return id; }
    public Cliente getCliente() { return cliente; }
    public List<ItemVenda> getItens() { return itens; }
    public String getStatus() { return status; }
    public String getData() { return data; }
    public String getStatusPagamento() { return statusPagamento; }

    public void setId(int id) { this.id = id; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }
    public void setData(String data) { this.data = data; }
    public void setStatus(String status) { this.status = status; }
    public void setStatusPagamento(String statusPagamento) { this.statusPagamento = statusPagamento; }
    public void setItens(List<ItemVenda> itens) { this.itens = itens; }

    public void adicionarItem(ItemVenda item) {
        if (status.equals("aberto")) {
            itens.add(item);
        }
    }

    public void removerItem(int produtoId) {
        if (status.equals("aberto")) {
            Iterator<ItemVenda> iterator = itens.iterator();
            while (iterator.hasNext()) {
                ItemVenda item = iterator.next();
                if (item.getProduto().getId() == produtoId) {
                    iterator.remove();
                    return;
                }
            }
        }
    }

    public void alterarQuantidadeItem(int produtoId, int quantidade) {
        if (status.equals("aberto")) {
            for (ItemVenda item : itens) {
                if (item.getProduto().getId() == produtoId) {
                    item.setQuantidade(quantidade);
                    return;
                }
            }
        }
    }

    public void finalizarPedido() {
        if (itens.isEmpty()) {
            return;
        }
        if (calcularTotalVenda() <= 0) {
            return;
        }
        statusPagamento = "Aguardando pagamento";
        status = "fechado";
    }

    public void entregarPedido() {
        // Check for null statusPagamento before calling equals()
        if (statusPagamento != null && statusPagamento.equals("Pago")) {
            status = "finalizado";
        }
    }

    public double calcularTotalVenda() {
        double total = 0.0;
        for (ItemVenda item : itens) {
            total += item.getPrecoVenda() * item.getQuantidade();
        }
        return total;
    }
}
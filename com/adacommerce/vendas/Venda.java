package com.adacommerce.vendas;

import com.adacommerce.clientes.Cliente;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Venda {
    private int id;
    private Cliente cliente;
    private List<ItemVenda> itens = new ArrayList<>();
    private String data;
    private String status = "aberto";
    private String statusPagamento;

    public Venda() {
        // Construtor vazio necessário para CSV loading
    }

    public Venda(int id, Cliente cliente) {
        this.id = id;
        this.cliente = cliente;
        this.data = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
    }

    // Getters
    public int getId() { return id; }
    public Cliente getCliente() { return cliente; }
    public List<ItemVenda> getItens() { return itens; }
    public String getStatus() { return status; }
    public String getData() { return data; }
    public String getStatusPagamento() { return statusPagamento; }

    // Setters (Adicionados para CSV loading)
    public void setId(int id) { this.id = id; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }
    public void setData(String data) { this.data = data; }
    public void setStatus(String status) { this.status = status; }
    public void setStatusPagamento(String statusPagamento) { this.statusPagamento = statusPagamento; }


    // Métodos da venda
    public void adicionarItem(ItemVenda item) {
        if (status.equals("aberto")) {
            itens.add(item);
        }
    }

    public void finalizarPedido() {
        if (!itens.isEmpty()) {
            statusPagamento = "Aguardando pagamento";
            status = "fechado";
            System.out.println("Notificação enviada para " + cliente.getEmail() + ": Pedido finalizado!");
        }
    }
}

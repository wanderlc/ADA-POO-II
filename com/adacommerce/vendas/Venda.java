// 6. Venda.java (Adicionado métodos removerItem, alterarQuantidadeItem e entregarPedido)
package com.adacommerce.vendas;

import com.adacommerce.clientes.Cliente;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator; // Import Iterator

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

    public void removerItem(int produtoId) {
        if (status.equals("aberto")) {
            Iterator<ItemVenda> iterator = itens.iterator();
            while (iterator.hasNext()) {
                ItemVenda item = iterator.next();
                if (item.getProduto().getId() == produtoId) {
                    iterator.remove();
                    System.out.println("Item do produto ID " + produtoId + " removido da venda.");
                    return;
                }
            }
            System.out.println("Item do produto ID " + produtoId + " não encontrado na venda.");
        } else {
            System.out.println("Não é possível remover itens de uma venda " + status + ".");
        }
    }

    public void alterarQuantidadeItem(int produtoId, int quantidade) {
        if (status.equals("aberto")) {
            for (ItemVenda item : itens) {
                if (item.getProduto().getId() == produtoId) {
                    item.setQuantidade(quantidade);
                    System.out.println("Quantidade do produto ID " + produtoId + " alterada para " + quantidade + ".");
                    return;
                }
            }
            System.out.println("Item do produto ID " + produtoId + " não encontrado na venda.");
        } else {
            System.out.println("Não é possível alterar a quantidade de itens de uma venda " + status + ".");
        }
    }


    public void finalizarPedido() {
        if (itens.isEmpty()) {
            System.out.println("Não é possível finalizar um pedido sem itens.");
            return;
        }
        if (calcularTotalVenda() <= 0) {
            System.out.println("Não é possível finalizar um pedido com valor total zero ou negativo.");
            return;
        }
        statusPagamento = "Aguardando pagamento";
        status = "fechado";
        System.out.println("Notificação enviada para " + cliente.getEmail() + ": Pedido finalizado e aguardando pagamento!");
    }

    public void entregarPedido() {
        if (statusPagamento.equals("Pago")) {
            status = "finalizado"; // Alterado para "finalizado"
            System.out.println("Notificação enviada para " + cliente.getEmail() + ": Pedido entregue e finalizado!");
        } else {
            System.out.println("Não é possível entregar o pedido. Pagamento pendente.");
        }
    }

    // Novo método para calcular o total da venda
    public double calcularTotalVenda() {
        double total = 0.0;
        for (ItemVenda item : itens) {
            total += item.getPrecoVenda() * item.getQuantidade();
        }
        return total;
    }
}
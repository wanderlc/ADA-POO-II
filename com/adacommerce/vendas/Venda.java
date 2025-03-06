package com.adacommerce.vendas;

import com.adacommerce.clientes.Cliente;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Venda {
    private int id;
    private Cliente cliente;
    private List<ItemVenda> itens;
    private String data;
    private String status;
    private String statusPagamento;

    public Venda(int id, Cliente cliente) {
        this.id = id;
        this.cliente = cliente;
        this.itens = new ArrayList<>();
        this.data = obterDataAtual();
        this.status = "aberto";
        this.statusPagamento = null;
    }

    private String obterDataAtual() {
        LocalDateTime agora = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return agora.format(formatter);
    }

    public void adicionarItem(ItemVenda item) {
        if (status.equals("aberto")) {
            itens.add(item);
        } else {
            System.out.println("Não é possível adicionar itens a um pedido fechado.");
        }
    }

    public void removerItem(int produtoId) {
        if (status.equals("aberto")) {
            itens.removeIf(item -> item.getProduto().getId() == produtoId);
        } else {
            System.out.println("Não é possível remover itens de um pedido fechado.");
        }
    }

    public void alterarQuantidade(int produtoId, int novaQuantidade) {
        if (status.equals("aberto")) {
            for (ItemVenda item : itens) {
                if (item.getProduto().getId() == produtoId) {
                    item.setQuantidade(novaQuantidade);
                    break;
                }
            }
        } else {
            System.out.println("Não é possível alterar a quantidade de itens de um pedido fechado.");
        }
    }

    public void finalizarPedido() {
        if (!itens.isEmpty() && calcularTotal() > 0) {
            statusPagamento = "Aguardando pagamento";
            status = "fechado";
            notificarCliente("Seu pedido foi finalizado e está aguardando pagamento.");
        } else {
            System.out.println("Não é possível finalizar o pedido. Adicione itens e verifique o valor total.");
        }
    }

    public void pagar() {
        if (statusPagamento.equals("Aguardando pagamento")) {
            statusPagamento = "Pago";
            notificarCliente("Seu pagamento foi confirmado.");
        } else {
            System.out.println("O pedido não está aguardando pagamento.");
        }
    }

    public void entregar() {
        if (statusPagamento.equals("Pago")) {
            status = "Finalizado";
            notificarCliente("Seu pedido foi entregue.");
        } else {
            System.out.println("O pedido não pode ser entregue sem pagamento confirmado.");
        }
    }

    public double calcularTotal() {
        return itens.stream().mapToDouble(item -> item.getPrecoVenda() * item.getQuantidade()).sum();
    }

    private void notificarCliente(String mensagem) {
        System.out.println("Notificação enviada para " + cliente.getEmail() + ": " + mensagem);
    }
}
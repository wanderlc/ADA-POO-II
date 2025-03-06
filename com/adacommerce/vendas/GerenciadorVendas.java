package com.adacommerce.vendas;

import com.adacommerce.clientes.Cliente;
import com.adacommerce.produtos.Produto;

import java.util.ArrayList;
import java.util.List;

public class GerenciadorVendas {
    private List<Venda> vendas = new ArrayList<>();

    public void criarVenda(int id, Cliente cliente) {
        Venda venda = new Venda(id, cliente);
        vendas.add(venda);
        System.out.println("Venda " + id + " criada com sucesso para o cliente " + cliente.getNome() + ".");
    }

    public void adicionarItemVenda(int vendaId, Produto produto, int quantidade, double precoVenda) {
        for (Venda venda : vendas) {
            if (venda.getId() == vendaId) {
                ItemVenda item = new ItemVenda(produto, quantidade, precoVenda);
                venda.adicionarItem(item);
                System.out.println("Item " + produto.getNome() + " adicionado à venda " + vendaId + ".");
                return;
            }
        }
        System.out.println("Venda " + vendaId + " não encontrada.");
    }

    public void finalizarVenda(int vendaId) {
        for (Venda venda : vendas) {
            if (venda.getId() == vendaId) {
                venda.finalizarPedido();
                return;
            }
        }
        System.out.println("Venda " + vendaId + " não encontrada.");
    }
}
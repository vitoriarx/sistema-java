package br.edu.ifal.domain;

import java.util.List;

public class Venda {
    private int idVenda;
    private Cliente cliente;
    private Funcionario vendedor;
    private List<ItemVenda> produtos;
    private double valorTotal;

    public Venda(Cliente cliente, Funcionario vendedor, List<ItemVenda> produtos) {
        this.cliente = cliente;
        this.vendedor = vendedor;
        this.produtos = produtos;
    }

    public Venda(Cliente cliente, Funcionario vendedor, List<ItemVenda> produtos, double valorTotal) {
        this.cliente = cliente;
        this.vendedor = vendedor;
        this.produtos = produtos;
        this.valorTotal = valorTotal;
    }

    public int getIdVenda() {
        return idVenda;
    }

    public void setIdVenda(int idVenda) {
        this.idVenda = idVenda;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Funcionario getVendedor() {
        return vendedor;
    }

    public void setVendedor(Funcionario vendedor) {
        this.vendedor = vendedor;
    }

    public List<ItemVenda> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<ItemVenda> produtos) {
        this.produtos = produtos;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public void adicionarItem(Produto produto, int quantidade) {
        produtos.add(new ItemVenda(produto, quantidade));
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Venda: [ID=").append(idVenda)
               .append(", Cliente=").append(cliente.getNome())
               .append(", Vendedor=").append(vendedor.getNome())
               .append(", Valor Total=").append(valorTotal)
               .append("]\nProdutos: ");
        for (ItemVenda produto : produtos) {
            builder.append(" ").append(produto).append("\n");
        }
        return builder.toString();
    }
}
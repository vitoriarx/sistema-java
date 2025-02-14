package br.edu.ifal.domain;


public class ItemVenda {
    private Produto produto;
    private int quantidade;
    private double valorUnitario;

    // Construtor com Produto, quantidade e valorUnitario
    public ItemVenda(Produto produto, int quantidade, double valorUnitario) {
        this.produto = produto;
        this.quantidade = quantidade;
        this.valorUnitario = valorUnitario;
    }

    // Construtor com Produto e quantidade (valorUnitario é obtido do Produto)
    public ItemVenda(Produto produto, int quantidade) {
        this(produto, quantidade, produto.getValorUnitario());
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(double valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public double calcularTotal() {
        return quantidade * valorUnitario;
    }

    @Override
    public String toString() {
        return String.format(
            "ItemVenda [Produto=%s, Quantidade=%d, Valor Unitário=R$ %.2f, Total=R$ %.2f]",
            produto.getNome(), quantidade, valorUnitario, calcularTotal()
        );
    }
}
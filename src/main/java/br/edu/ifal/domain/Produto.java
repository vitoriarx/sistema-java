package br.edu.ifal.domain;

public class Produto {
    private int id;
    private String nome;
    private double valorUnitario;
    private int quantidadeEmEstoque;


    public Produto(String nome, double valorUnitario, int quantidadeEmEstoque) {
        this.nome = nome;
        this.valorUnitario = valorUnitario;
        this.quantidadeEmEstoque = quantidadeEmEstoque;
    }

  
    public Produto(int id, String nome, double valorUnitario, int quantidadeEmEstoque) {
        this(nome, valorUnitario, quantidadeEmEstoque); 
        this.id = id;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(double valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public int getQuantidadeEmEstoque() {
        return quantidadeEmEstoque;
    }

    public void setQuantidadeEmEstoque(int quantidadeEmEstoque) {
        this.quantidadeEmEstoque = quantidadeEmEstoque;
    }


    public void adicionarEstoque(int quantidade) {
        if (quantidade > 0) {
            this.quantidadeEmEstoque += quantidade;
        } else {
            throw new IllegalArgumentException("Quantidade deve ser maior que zero.");
        }
    }

    
    public void removerEstoque(int quantidade) {
        if (quantidade > 0 && quantidade <= this.quantidadeEmEstoque) {
            this.quantidadeEmEstoque -= quantidade;
        } else {
            throw new IllegalArgumentException("Quantidade inválida ou estoque insuficiente.");
        }
    }

    
    @Override
    public String toString() {
        return String.format(
            "Produto [ID=%d, Nome='%s', Valor Unitário=R$ %.2f, Estoque=%d]",
            id, nome, valorUnitario, quantidadeEmEstoque
        );
    }
}
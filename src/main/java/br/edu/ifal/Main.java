package br.edu.ifal;

import br.edu.ifal.dao.*;
import br.edu.ifal.domain.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);

    static ClienteDao clienteDao = new ClienteDao();
    static ProdutoDao produtoDao = new ProdutoDao();
    static VendaDao vendaDao = new VendaDao();
    static FuncionarioDao funcionarioDao = new FuncionarioDao();

    public static void main(String[] args) {
        int opcao = -1;
        while (opcao != 0) {
            exibirMenu();
            try {
                opcao = scanner.nextInt();
            } catch (NumberFormatException e) {
                System.out.println("Opção inválida. Tente novamente.");
            }

            switch (opcao) {
                case 1 -> cadastrarProduto();
                case 2 -> cadastrarCliente();
                case 3 -> buscarProduto();
                case 4 -> listarProdutosDisponiveis();
                case 5 -> efetuarVenda();
                case 6 -> listarVendasRealizadas();
                case 0 -> System.out.println("\nEncerrando o sistema...");
                default -> System.out.println("\nOpção inválida! Tente novamente.");
            }
        }
    }

    private static void exibirMenu() {
        System.out.println("\n===== MENU =====\n");
        System.out.println("1 - Cadastrar Produto");
        System.out.println("2 - Cadastrar Cliente");
        System.out.println("3 - Buscar Produto (por ID)");
        System.out.println("4 - Listar Produtos Disponíveis");
        System.out.println("5 - Efetuar Venda");
        System.out.println("6 - Listar Vendas Realizadas");
        System.out.println("0 - Sair");
        System.out.print("\nEscolha uma opção: ");
    }

    private static void cadastrarProduto() {
        System.out.println("\n=== Cadastrar Produto ===\n");
        System.out.print("Nome do produto: ");
        scanner.nextLine();
        String nome = scanner.nextLine();
        System.out.print("Valor unitário do produto: ");
        double valor = scanner.nextDouble();
        System.out.print("Quantidade em estoque: ");
        int quantidade = scanner.nextInt();

        Produto produto = new Produto(nome, valor, quantidade);
        produtoDao.save(produto);
        System.out.println("Produto cadastrado com sucesso!");
    }

    private static void cadastrarCliente() {
        System.out.println("\n=== Cadastrar Cliente ===\n");
        System.out.print("CPF: ");
        scanner.nextLine();
        String cpf = scanner.nextLine();
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Endereço: ");
        String endereco = scanner.nextLine();
        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();

        Cliente cliente = new Cliente(cpf, nome, endereco, telefone);
        clienteDao.save(cliente);
        System.out.println("Cliente cadastrado com sucesso!");
    }

    private static void buscarProduto() {
        System.out.println("\n=== Buscar Produto ===\n");
        System.out.print("ID do produto: ");
        int id = scanner.nextInt();
        Produto produto = produtoDao.findById(id);
        if (produto != null) {
            System.out.println("Produto encontrado: " + produto);
        } else {
            System.out.println("Produto não encontrado.");
        }
    }

    private static void listarProdutosDisponiveis() {
        System.out.println("\n=== Listar Produtos Disponíveis ===\n");
        List<Produto> produtos = produtoDao.findAll();
        for (Produto produto : produtos) {
            System.out.println(produto);
        }
    }

    private static void efetuarVenda() {
        System.out.println("\n=== Efetuar Venda ===\n");
        System.out.print("CPF do cliente: ");
        scanner.nextLine();
        String cpfCliente = scanner.nextLine();
        Cliente cliente = clienteDao.findByCpf(cpfCliente);
        if (cliente == null) {
            System.out.println("Cliente não encontrado.");
            return;
        }

        System.out.print("CPF do vendedor: ");
        String cpfVendedor = scanner.nextLine();
        Funcionario vendedor = funcionarioDao.findByCpf(cpfVendedor);
        if (vendedor == null) {
            System.out.println("Vendedor não encontrado.");
            return;
        }

        List<ItemVenda> itens = new ArrayList<>();
        boolean adicionarItem = true;

        while (adicionarItem) {
            System.out.print("Digite o ID do produto: ");
            int idProduto = scanner.nextInt();
            Produto produto = produtoDao.findById(idProduto);
            if (produto == null) {
                System.out.println("Produto não encontrado.");
            } else {
                System.out.print("Digite a quantidade: ");
                int quantidade = scanner.nextInt();
                if (quantidade > produto.getQuantidadeEmEstoque()) {
                    System.out.println("Estoque insuficiente para o produto: " + produto.getNome());
                } else {
                    itens.add(new ItemVenda(produto, quantidade));
                }
            }
            System.out.print("Deseja adicionar outro produto? (s/n): ");
            String opcao = scanner.next();
            if (!opcao.equalsIgnoreCase("s")) {
                adicionarItem = false;
            }
        }

        Venda venda = new Venda(cliente, vendedor, itens);
        vendaDao.save(venda);
        System.out.println("Venda efetuada com sucesso!");
    }

    private static void listarVendasRealizadas() {
        System.out.println("\n=== Listar Vendas Realizadas ===\n");
        List<Venda> vendas = vendaDao.findAll();
        for (Venda venda : vendas) {
            System.out.println(venda);
        }
    }
}
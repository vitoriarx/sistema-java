package br.edu.ifal.dao;


import br.edu.ifal.db.ConnectionHelper;
import br.edu.ifal.domain.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VendaDao {

    public void salvar(Venda venda) {
        double valorTotal = calcularValorTotal(venda);

        String queryPedido = "INSERT INTO PEDIDO (CPF_CLIENTE_FK, CPF_FUNCIONARIO_FK, VALOR_TOTAL) VALUES (?, ?, ?);";
        String queryItemPedido = "INSERT INTO ITEM_PEDIDO (ID_PEDIDO_FK, ID_PRODUTO_FK, QUANTIDADE, VALOR) VALUES (?, ?, ?, ?);";

        Connection conexao = null;
        PreparedStatement statementPedido = null;
        PreparedStatement statementItemPedido = null;
        ResultSet resultado = null;

        try {
            conexao = ConnectionHelper.getConnection();
            conexao.setAutoCommit(false);

            statementPedido = conexao.prepareStatement(queryPedido, Statement.RETURN_GENERATED_KEYS);
            statementPedido.setString(1, venda.getCliente().getCpf());
            statementPedido.setString(2, venda.getVendedor().getCpf());
            statementPedido.setDouble(3, valorTotal);
            statementPedido.executeUpdate();

            resultado = statementPedido.getGeneratedKeys();
            int idPedido;
            if (resultado.next()) {
                idPedido = resultado.getInt(1);
                venda.setIdVenda(idPedido);
            } else {
                throw new SQLException("Erro ao obter o ID do pedido.");
            }

            statementItemPedido = conexao.prepareStatement(queryItemPedido);
            for (ItemVenda item : venda.getProdutos()) {
                statementItemPedido.setInt(1, idPedido);
                statementItemPedido.setInt(2, item.getProduto().getId());
                statementItemPedido.setInt(3, item.getQuantidade());
                statementItemPedido.setDouble(4, item.getProduto().getValorUnitario() * item.getQuantidade());
                statementItemPedido.addBatch();
            }
            statementItemPedido.executeBatch();

            conexao.commit();
        } catch (Exception e) {
            try {
                if (conexao != null) {
                    conexao.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            throw new RuntimeException("Erro ao salvar o pedido", e);
        } finally {
            fecharRecursos(resultado, statementItemPedido, statementPedido, conexao);
        }
    }

    public List<Venda> buscarTodos() {
        List<Venda> vendas = new ArrayList<>();
        String queryPedido = "SELECT * FROM PEDIDO";
        String queryItemPedido = "SELECT * FROM ITEM_PEDIDO WHERE ID_PEDIDO_FK = ?";

        try (Connection conexao = ConnectionHelper.getConnection();
             PreparedStatement statementPedido = conexao.prepareStatement(queryPedido);
             ResultSet resultadoPedido = statementPedido.executeQuery()) {

            while (resultadoPedido.next()) {
                int idPedido = resultadoPedido.getInt("ID");
                String cpfCliente = resultadoPedido.getString("CPF_CLIENTE_FK");
                String cpfFuncionario = resultadoPedido.getString("CPF_FUNCIONARIO_FK");
                double valorTotal = resultadoPedido.getDouble("VALOR_TOTAL");

                Cliente cliente = new ClienteDao().buscarPorCpf(cpfCliente);
                Funcionario funcionario = new FuncionarioDao().buscarPorCpf(cpfFuncionario);

                List<ItemVenda> itens = buscarItensPedido(conexao, idPedido, queryItemPedido);

                Venda venda = new Venda(cliente, funcionario, itens);
                venda.setIdVenda(idPedido);
                venda.setValorTotal(valorTotal);
                vendas.add(venda);
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar pedidos", e);
        }
        return vendas;
    }

    private double calcularValorTotal(Venda venda) {
        double valorTotal = 0;
        for (ItemVenda item : venda.getProdutos()) {
            valorTotal += item.getProduto().getValorUnitario() * item.getQuantidade();
        }
        return valorTotal;
    }

    private List<ItemVenda> buscarItensPedido(Connection conexao, int idPedido, String queryItemPedido) throws SQLException {
        List<ItemVenda> itens = new ArrayList<>();
        try (PreparedStatement statementItem = conexao.prepareStatement(queryItemPedido)) {
            statementItem.setInt(1, idPedido);
            try (ResultSet resultadoItem = statementItem.executeQuery()) {
                while (resultadoItem.next()) {
                    int idProduto = resultadoItem.getInt("ID_PRODUTO_FK");
                    int quantidade = resultadoItem.getInt("QUANTIDADE");

                    Produto produto = new ProdutoDao().buscarPorId(idProduto);
                    ItemVenda item = new ItemVenda(produto, quantidade);
                    itens.add(item);
                }
            }
        }
        return itens;
    }

    private void fecharRecursos(ResultSet resultado, PreparedStatement statementItem, PreparedStatement statementPedido, Connection conexao) {
        try {
            if (resultado != null) {
                resultado.close();
            }
        } catch (SQLException _) {}
        try {
            if (statementItem != null) {
                statementItem.close();
            }
        } catch (SQLException _) {}
        try {
            if (statementPedido != null) {
                statementPedido.close();
            }
        } catch (SQLException _) {}
        try {
            if (conexao != null) {
                conexao.setAutoCommit(true);
                conexao.close();
            }
        } catch (SQLException _) {}
    }
}
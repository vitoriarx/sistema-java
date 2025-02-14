package br.edu.ifal.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import br.edu.ifal.db.ConnectionHelper;
import br.edu.ifal.domain.Produto;

public class ProdutoDao {

    public void salvar(Produto produto) {
        String query = "INSERT INTO PRODUTO (NOME, VALOR_UNIT, QUANTIDADE) VALUES (?, ?, ?)";
        try (Connection conexao = ConnectionHelper.getConnection();
             PreparedStatement statement = conexao.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, produto.getNome());
            statement.setDouble(2, produto.getValorUnitario());
            statement.setInt(3, produto.getQuantidadeEmEstoque());
            statement.executeUpdate();

            try (ResultSet resultado = statement.getGeneratedKeys()) {
                if (resultado.next()) {
                    produto.setId(resultado.getInt(1));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar produto", e);
        }
    }
}
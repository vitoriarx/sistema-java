package br.edu.ifal.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.edu.ifal.db.ConnectionHelper;
import br.edu.ifal.domain.Funcionario;

public class FuncionarioDao {

    public void save(Funcionario funcionario) {
        String sql = "INSERT INTO FUNCIONARIO (CPF, NOME, ENDERECO, TELEFONE) VALUES (?, ?, ?, ?)";

        try (
            Connection connection = ConnectionHelper.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setString(1, funcionario.getCpf());
            preparedStatement.setString(2, funcionario.getNome());
            preparedStatement.setString(3, funcionario.getEndereco());
            preparedStatement.setString(4, funcionario.getTelefone());

            preparedStatement.execute();
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException("Erro ao salvar funcionário", e);
        }
    }

    public Funcionario findByCpf(String cpf) {
        String sql = "SELECT * FROM FUNCIONARIO WHERE CPF = ?";

        try (
            Connection connection = ConnectionHelper.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setString(1, cpf);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    return new Funcionario(
                        cpf,
                        rs.getString("NOME"),
                        rs.getString("ENDERECO"),
                        rs.getString("TELEFONE")
                    );
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException("Erro ao buscar funcionário", e);
        }
        return null;
    }

    public List<Funcionario> findAll() {
        String sql = "SELECT * FROM FUNCIONARIO";
        List<Funcionario> funcionarios = new ArrayList<>();

        try (
            Connection connection = ConnectionHelper.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery()
        ) {
            while (rs.next()) {
                funcionarios.add(new Funcionario(
                    rs.getString("CPF"),
                    rs.getString("NOME"),
                    rs.getString("ENDERECO"),
                    rs.getString("TELEFONE")
                ));
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException("Erro ao buscar funcionários", e);
        }

        return funcionarios;
    }
}


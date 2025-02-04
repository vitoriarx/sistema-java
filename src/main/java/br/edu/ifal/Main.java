package br.edu.ifal;

import br.edu.ifal.dao.ClienteDao;
import br.edu.ifal.db.ConnectionHelper;
import br.edu.ifal.domain.Cliente;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        ClienteDao clienteDao = new ClienteDao();
        try {

            // Cliente cliente = new Cliente("12312312315",
            //         "José da Silva",
            //         "Endereco",
            //         "123451231");

            // new ClienteDao().save(cliente);

            List<Cliente> listaClientes = clienteDao.findAll();
            for (Cliente c : listaClientes) {
                System.out.println(c);
            }


        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}

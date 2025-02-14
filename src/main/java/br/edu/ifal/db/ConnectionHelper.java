package br.edu.ifal.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionHelper {

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        final String driver = "com.mysql.cj.jdbc.Driver";
        final String url = "jdbc:mysql://127.0.0.1:3306/Loja1";

        final String user = "root";
        final String password = "12345678";

        Class.forName(driver);
        return DriverManager.getConnection(url, user, password);
    }
}

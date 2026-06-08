package br.edu.ufersa.ProjetoHospital.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBD {

    private static Connection con;

    private ConexaoBD() {
    }

    public static Connection getConnection() throws SQLException {

        if (con == null || con.isClosed()) {

            String url = "jdbc:mysql://localhost:3306/hospital";
            String usuario = "root";
            String senha = "Familiaunida1.";

            con = DriverManager.getConnection(url, usuario, senha);
        }

        return con;
    }
}
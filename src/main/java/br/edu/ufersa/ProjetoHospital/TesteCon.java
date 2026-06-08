package br.edu.ufersa.ProjetoHospital;

import br.edu.ufersa.ProjetoHospital.DAO.ConexaoBD;
import java.sql.Connection;

public class TesteCon {

    public static void main(String[] args) {

        try {
            Connection con = ConexaoBD.getConnection();

            if (con != null) {
                System.out.println("Conectado com sucesso!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
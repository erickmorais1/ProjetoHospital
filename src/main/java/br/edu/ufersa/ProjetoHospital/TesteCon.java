package br.edu.ufersa.ProjetoHospital;

import br.edu.ufersa.ProjetoHospital.DAO.ConexaoBD;
import br.edu.ufersa.ProjetoHospital.DAO.ConsultaDAO;
import br.edu.ufersa.ProjetoHospital.DAO.MedicoDAO;
import br.edu.ufersa.ProjetoHospital.DAO.PacienteDAO;
import br.edu.ufersa.ProjetoHospital.model.entities.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TesteCon {

    public static void main(String[] args) throws SQLException {
        Connection con = ConexaoBD.getConnection();
        ConsultaDAO consultaDAO = new ConsultaDAO(con);

        Paciente paciente = new Paciente();
        paciente.setCpf("22222222222");

        Medico medico = new Medico();
        medico.setCrm("CRM12345");

        Consulta consulta = new Consulta();
        consulta.setPaciente(paciente);
        consulta.setMedico(medico);
        consulta.setDiaHora(java.time.LocalDate.now());
        consulta.setStatus("Agendada");

        Consulta consulta1 = consultaDAO.buscarConsultaPorId(1);

        consulta1.setStatus("Concluida");
        consulta1.setDiaHora(java.time.LocalDate.now());

        consultaDAO.atualizarConsulta(consulta1);

        System.out.println("Consulta atualizada!");






    }
}
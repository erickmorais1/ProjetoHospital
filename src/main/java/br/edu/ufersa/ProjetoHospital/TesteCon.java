package br.edu.ufersa.ProjetoHospital;

import br.edu.ufersa.ProjetoHospital.DAO.ConexaoBD;
import br.edu.ufersa.ProjetoHospital.DAO.ConsultaDAO;
import br.edu.ufersa.ProjetoHospital.DAO.MedicoDAO;
import br.edu.ufersa.ProjetoHospital.DAO.PacienteDAO;
import br.edu.ufersa.ProjetoHospital.Facade.HospitalFacade;
import br.edu.ufersa.ProjetoHospital.Factory.HospitalFactory;
import br.edu.ufersa.ProjetoHospital.Service.ConsultaService;
import br.edu.ufersa.ProjetoHospital.Service.MedicoService;
import br.edu.ufersa.ProjetoHospital.Service.PacienteService;
import br.edu.ufersa.ProjetoHospital.model.entities.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TesteCon {

    public static void main(String[] args) throws SQLException {
        Connection con = ConexaoBD.getConnection();

        MedicoDAO medicoDAO = new MedicoDAO(con);
        MedicoService medicoService = new MedicoService(medicoDAO);
        ConsultaDAO consultaDAO = new ConsultaDAO(con);
        ConsultaService consultaService = new ConsultaService(consultaDAO);
        PacienteDAO pacienteDAO = new PacienteDAO(con);
        PacienteService pacienteService = new PacienteService(pacienteDAO);

        HospitalFacade facade = new HospitalFacade(medicoService, consultaService, pacienteService);
        Endereco enderecoGerente = new Endereco();
        enderecoGerente.setRua("Av.Vignt Rosado");

        Gerente gerente = new Gerente("Erick","701.279.174-89",
                enderecoGerente,"0921");

        Endereco endereco = new Endereco();
        endereco.setRua("Rua da Facade");

        Medico medico = HospitalFactory.criarMedico(
                "Lucas",
                "00000000000",
                endereco,
                "CRM999",
                150.0
        );

        facade.addMedico(gerente, medico);

        System.out.println("Médico cadastrado pela Facade!");
        List<Medico> medicos = facade.listarMedicos();

        for (Medico m : medicos) {
            System.out.println("Nome: " + m.getNome());
            System.out.println("CPF: " + m.getCpf());
            System.out.println("CRM: " + m.getCrm());
            System.out.println("Valor da consulta: " + m.getValorConsulta());
            System.out.println("---------------------------");
        }
    }
}
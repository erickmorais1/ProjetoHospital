package br.edu.ufersa.ProjetoHospital.Facade;

import br.edu.ufersa.ProjetoHospital.Service.ConsultaService;
import br.edu.ufersa.ProjetoHospital.Service.MedicoService;
import br.edu.ufersa.ProjetoHospital.Service.PacienteService;
import br.edu.ufersa.ProjetoHospital.model.entities.Consulta;
import br.edu.ufersa.ProjetoHospital.model.entities.Gerente;
import br.edu.ufersa.ProjetoHospital.model.entities.Medico;
import br.edu.ufersa.ProjetoHospital.model.entities.Paciente;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class HospitalFacade {
    private MedicoService medicoService;
    private ConsultaService consultaService;
    private PacienteService pacienteService;

    public HospitalFacade(MedicoService medicoService,
                          ConsultaService consultaService,
                          PacienteService pacienteService) {

        this.medicoService = medicoService;
        this.consultaService = consultaService;
        this.pacienteService = pacienteService;
    }

    public void addMedico(Gerente gerente, Medico medico) throws SQLException {
        medicoService.addMedico(gerente, medico);
    }

    public List<Medico> listarMedicos() throws SQLException {
        return medicoService.listarMedicos();
    }
    public Paciente buscarPacientePorCpf(String cpf)
            throws PacienteService.ServicoException {

        return pacienteService.buscarPorCpf(cpf);
    }

    public void agendarConsulta(Paciente paciente,
                                Medico medico,
                                LocalDate data)
            throws SQLException {

        consultaService.agendarConsulta(paciente, medico, data);
    }

    public List<Consulta> listarTodasAsConsultas()
            throws SQLException {

        return consultaService.listarTodasAsConsultas();
    }
}

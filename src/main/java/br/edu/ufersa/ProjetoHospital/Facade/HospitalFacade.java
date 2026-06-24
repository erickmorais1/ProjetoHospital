package br.edu.ufersa.ProjetoHospital.Facade;

import br.edu.ufersa.ProjetoHospital.Service.ConsultaService;
import br.edu.ufersa.ProjetoHospital.Service.MedicoService;
import br.edu.ufersa.ProjetoHospital.Service.PacienteService;
import br.edu.ufersa.ProjetoHospital.Util.SessaoGerente;
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

    // ===== MÉDICO =====

    public void addMedico(Gerente gerente, Medico medico) throws SQLException {
        medicoService.addMedico(gerente, medico);
    }

    public void atualizarMedico(Gerente gerente, Medico medico) throws SQLException {
        if (medico == null) throw new IllegalArgumentException("Médico não pode ser nulo");
        medicoService.atualizarMedico(gerente, medico);
    }

    public void removerMedico(Gerente gerente, String crm) throws SQLException {
        if (gerente == null) throw new IllegalStateException("Apenas gerente pode remover médicos");
        medicoService.excluirMedico(gerente, crm);
    }

    public List<Medico> listarMedicos() throws SQLException {
        return medicoService.listarMedicos();
    }

    public Medico buscarMedicoPorCrm(String crm) throws SQLException {
        return medicoService.buscarMedicoPorCrm(crm);
    }

    public Medico buscarMedicoPorCpf(String cpf) throws SQLException {
        return medicoService.buscarMedicoPorCpf(cpf);
    }

    public List<Medico> buscarMedicosPorNome(String nome) throws SQLException {
        return medicoService.buscarMedicoPorNome(nome);
    }

    // ===== PACIENTE =====

    public Paciente buscarPacientePorCpf(String cpf) throws PacienteService.ServicoException {
        return pacienteService.buscarPorCpf(cpf);
    }

    public void adicionarPaciente(Paciente p) throws PacienteService.ServicoException {
        pacienteService.adicionarPaciente(p);
    }

    // ===== CONSULTA =====

    public void agendarConsulta(Paciente paciente, Medico medico, LocalDate data) throws SQLException {
        consultaService.agendarConsulta(paciente, medico, data);
    }

    public Consulta buscarConsulta(int id) throws SQLException {
        return consultaService.buscarConsulta(id);
    }

    public List<Consulta> listarTodasAsConsultas() throws SQLException {
        return consultaService.listarTodasAsConsultas();
    }

    public List<Consulta> listarConsultasPorCpf(String cpf) throws SQLException {
        return consultaService.listarConsultasPorCpf(cpf);
    }

    public List<Consulta> listarConsultasPorMedico(String crm) throws SQLException {
        return consultaService.listarConsultasPorMedico(crm);
    }

    public void removerConsulta(int id) throws SQLException {
        consultaService.removerConsulta(id);
    }

    public void adicionarObservacao(int id, String observacao) throws SQLException {
        consultaService.adicionarObservacao(id, observacao);
    }

    // ===== GERENTE =====

    public Gerente obterGerenteAtual() {
        return SessaoGerente.getGerenteLogado();
    }
}

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
    // ===== MÉTODOS PARA GERENCIAR MÉDICOS =====

    /**
     * Remove um médico do sistema
     * Apenas o gerente pode remover médicos
     */
    public void removerMedico(Gerente gerente, String crm) throws SQLException {
        if (gerente == null) {
            throw new IllegalStateException("Apenas gerente pode remover médicos");
        }
        medicoService.excluirMedico(gerente, crm);
    }

    /**
     * Busca um médico pelo CRM
     */
    public Medico buscarMedicoPorCrm(String crm) throws SQLException {
        if (crm == null || crm.isBlank()) {
            throw new IllegalArgumentException("CRM não pode ser nulo");
        }
        return medicoService.buscarMedicoPorCrm(crm);
    }

    /**
     * Busca um médico pelo CPF
     */
    public Medico buscarMedicoPorCpf(String cpf) throws SQLException {
        if (cpf == null || cpf.isBlank()) {
            throw new IllegalArgumentException("CPF não pode ser nulo");
        }
        return medicoService.buscarMedicoPorCpf(cpf);
    }

    /**
     * Busca médicos pelo nome
     */
    public List<Medico> buscarMedicosPorNome(String nome) throws SQLException {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome não pode ser nulo");
        }
        return medicoService.buscarMedicoPorNome(nome);
    }

    /**
     * Atualiza dados de um médico
     */
    public void atualizarMedico(Medico medico) throws SQLException {
        if (medico == null) {
            throw new IllegalArgumentException("Médico não pode ser nulo");
        }
        // TODO: Implementar através de MedicoService
        // medicoService.atualizarMedico(medico);
        System.out.println("TODO: Implementar atualizarMedico em MedicoService");
    }

    // ===== MÉTODOS PARA GERENCIAR GERENTE =====

    /**
     * Obtém o gerente atual da sessão
     * TODO: Implementar sistema de autenticação/sessão
     */
    public Gerente obterGerenteAtual() {
        // TODO: Retornar gerente da sessão/autenticação
        System.out.println("TODO: Implementar obterGerenteAtual com sessão");
        return null;
    }

    /**
     * Atualiza dados do gerente
     */
    public void atualizarGerente(Gerente gerente) throws SQLException {
        if (gerente == null) {
            throw new IllegalArgumentException("Gerente não pode ser nulo");
        }
        // TODO: Implementar através de GerenteService
        // gerenteService.atualizar(gerente);
        System.out.println("TODO: Implementar atualizarGerente em GerenteService");
    }

    /**
     * Altera a senha do gerente
     */
    public void alterarSenha(String cpf, String senhaAtual, String novaSenha)
            throws SQLException, Exception {

        if (cpf == null || cpf.isBlank()) {
            throw new IllegalArgumentException("CPF não pode ser nulo");
        }
        if (senhaAtual == null || senhaAtual.isBlank()) {
            throw new IllegalArgumentException("Senha atual não pode ser nula");
        }
        if (novaSenha == null || novaSenha.isBlank()) {
            throw new IllegalArgumentException("Nova senha não pode ser nula");
        }

        // TODO: Validar senha atual
        // TODO: Atualizar senha no banco de dados
        System.out.println("TODO: Implementar alterarSenha com validação");
    }

    public Consulta buscarConsulta(int id)
            throws SQLException {

        return consultaService.buscarConsulta(id);

    }

    public void removerConsulta(int id)
            throws SQLException {

        consultaService.removerConsulta(id);

    }

    public List<Consulta> listarConsultasPorCpf(String cpf)
            throws SQLException {

        return consultaService.listarConsultasPorCpf(cpf);

    }

    public List<Consulta> listarConsultasPorMedico(String crm)
            throws SQLException {

        return consultaService.listarConsultasPorMedico(crm);

    }

    public void adicionarObservacao(int id,
                                    String observacao)
            throws SQLException {

        consultaService.adicionarObservacao(
                id,
                observacao);

    }
    // Metodo salvar paciente
    public void adicionarPaciente(Paciente p) throws PacienteService.ServicoException {
        pacienteService.adicionarPaciente(p);
    }

}

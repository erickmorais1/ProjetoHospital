package br.edu.ufersa.ProjetoHospital.Service;

import br.edu.ufersa.ProjetoHospital.DAO.PacienteDAO;
import br.edu.ufersa.ProjetoHospital.model.entities.Paciente;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

// Camada de serviço responsável pelas regras de negócio da entidade {@link Paciente}

public class PacienteService {

    private static final Logger LOGGER = Logger.getLogger(PacienteService.class.getName());

    // Tamanho fixo de um CPF sem formatação (apenas dígitos). 
    private static final int CPF_LENGTH = 11;

    private final PacienteDAO pacienteDAO;

    public PacienteService(PacienteDAO pacienteDAO) {
        if (pacienteDAO == null) {
            throw new IllegalArgumentException("A instância de PacienteDAO não pode ser nula.");
        }
        this.pacienteDAO = pacienteDAO;
    }
//Cadastra um novo paciente no sistema
    public void adicionarPaciente(Paciente paciente) throws ServicoException {
        if (paciente == null) {
            throw new IllegalArgumentException("O paciente não pode ser nulo.");
        }
        try {
            pacienteDAO.addPaciente(paciente);
        } catch (SQLException e) {
            if (e.getSQLState() != null && e.getSQLState().startsWith("23")) {
                throw new ServicoException(
                    "Já existe um paciente cadastrado com o CPF: " + paciente.getCpf(), e
                );
            }
            LOGGER.log(Level.SEVERE, "Erro ao adicionar paciente com CPF: " + paciente.getCpf(), e);
            throw new ServicoException("Falha ao cadastrar o paciente. Tente novamente.", e);
        }
    }

    // Atualiza os dados de um paciente existente.

    public void atualizarPaciente(Paciente paciente) throws ServicoException {
        if (paciente == null) {
            throw new IllegalArgumentException("O paciente não pode ser nulo para atualização.");
        }
        try {
            int linhasAfetadas = pacienteDAO.atualizar(paciente);
            if (linhasAfetadas == 0) {
                throw new ServicoException(
                    "Paciente não encontrado no sistema para o CPF: " + paciente.getCpf()
                );
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Erro ao atualizar paciente com CPF: " + paciente.getCpf(), e);
            throw new ServicoException("Falha ao atualizar o paciente. Tente novamente.", e);
        }
    }

    //Remove um paciente do sistema pelo CPF.
    public void excluirPacientePorCpf(String cpf) throws ServicoException {
        validarCpf(cpf);
        try {
            int linhasAfetadas = pacienteDAO.excluirPorCpf(cpf);
            if (linhasAfetadas == 0) {
                throw new ServicoException(
                    "Não é possível excluir: paciente não encontrado com o CPF: " + cpf
                );
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Erro ao excluir paciente com CPF: " + cpf, e);
            throw new ServicoException("Falha ao excluir o paciente. Tente novamente.", e);
        }
    }

    // Busca um paciente pelo CPF.
    public Paciente buscarPorCpf(String cpf) throws ServicoException {
        validarCpf(cpf);
        try {
            return pacienteDAO.buscarPorCpf(cpf);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Erro ao buscar paciente com CPF: " + cpf, e);
            throw new ServicoException("Falha ao buscar o paciente. Tente novamente.", e);
        }
    }

    //Busca pacientes pelo nome usando correspondência parcial.

    public List<Paciente> buscarPorNome(String nome) throws ServicoException {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome para busca não pode ser nulo ou vazio.");
        }
        try {
            return pacienteDAO.buscarPorNome(nome);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Erro ao buscar pacientes com nome: " + nome, e);
            throw new ServicoException("Falha ao buscar pacientes por nome. Tente novamente.", e);
        }
    }

    /**
     * Retorna todos os pacientes cadastrados.
     *
     * @return Lista (nunca {@code null}) de todos os pacientes.
     * @throws ServicoException em caso de falha de persistência.
     */
    public List<Paciente> listarTodos() throws ServicoException {
        try {
            return pacienteDAO.listarTodos();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Erro ao listar todos os pacientes.", e);
            throw new ServicoException("Falha ao listar os pacientes. Tente novamente.", e);
        }
    }

    // Valida o CPF quanto à presença, comprimento e composição numérica.
  
    private void validarCpf(String cpf) {
        if (cpf == null || cpf.isBlank()) {
            throw new IllegalArgumentException("O CPF não pode ser nulo ou vazio.");
        }
        String cpfSomenteDigitos = cpf.replaceAll("[.\\-]", "");
        if (cpfSomenteDigitos.length() != CPF_LENGTH) {
            throw new IllegalArgumentException(
                "O CPF deve conter exatamente " + CPF_LENGTH + " dígitos numéricos. Informado: " + cpf
            );
        }
        if (!cpfSomenteDigitos.matches("\\d+")) {
            throw new IllegalArgumentException(
                "O CPF deve conter apenas dígitos numéricos. Informado: " + cpf
            );
        }
    }
  
    // Exceção de negócio que encapsula falhas da camada de serviço.

    public static class ServicoException extends Exception {

        public ServicoException(String message) {
            super(message);
        }

        public ServicoException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}

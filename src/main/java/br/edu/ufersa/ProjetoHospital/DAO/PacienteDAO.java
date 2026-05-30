package br.edu.ufersa.ProjetoHospital.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.edu.ufersa.ProjetoHospital.model.entities.Endereco;
import br.edu.ufersa.ProjetoHospital.model.entities.Paciente;
import br.edu.ufersa.ProjetoHospital.model.entities.Prontuario;


public class PacienteDAO {

    private static final Logger LOGGER = Logger.getLogger(PacienteDAO.class.getName());

    private final Connection con;

    public PacienteDAO(Connection con) {
        if (con == null) {
            throw new IllegalArgumentException("A conexão com o banco de dados não pode ser nula.");
        }
        this.con = con;
    }
    
    //Insere um novo paciente no banco de dados.
    
    public void addPaciente(Paciente paciente) throws SQLException {
        validarPaciente(paciente);

        final String sql =
            "INSERT INTO paciente (cpf, nome, endereco_rua, prontuario_observacoes, prontuario_data) " +
            "VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, paciente.getCpf());
            ps.setString(2, paciente.getNome());
            definirParametrosEndereco(ps, paciente.getEndereco(), 3);
            definirParametrosProntuario(ps, paciente.getProntuario(), 4);
            ps.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Erro ao inserir paciente com CPF: " + paciente.getCpf(), e);
            throw e;
        }
    }

    //Busca um paciente pelo CPF (chave única).
   
    public Paciente buscarPorCpf(String cpf) throws SQLException {
        validarCpf(cpf);

        final String sql = "SELECT * FROM paciente WHERE cpf = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, cpf);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return extrairPacienteDoResultSet(rs);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Erro ao buscar paciente com CPF: " + cpf, e);
            throw e;
        }
        return null;
    }

    // Busca pacientes pelo nome usando correspondência parcial (LIKE).

    public List<Paciente> buscarPorNome(String nome) throws SQLException {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome de busca não pode ser nulo ou vazio.");
        }

        final String sql = "SELECT * FROM paciente WHERE nome LIKE ?";
        final List<Paciente> lista = new ArrayList<>();

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, "%" + nome + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(extrairPacienteDoResultSet(rs));
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Erro ao buscar pacientes com nome: " + nome, e);
            throw e;
        }

        return lista.isEmpty() ? Collections.emptyList() : lista;
    }

    // Retorna todos os pacientes cadastrados.

    public List<Paciente> listarTodos() throws SQLException {
        final String sql = "SELECT * FROM paciente";
        final List<Paciente> lista = new ArrayList<>();

        // Ambos os recursos declarados no mesmo bloco try-with-resources,
        // garantindo fechamento automático na ordem inversa: rs → ps.
        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(extrairPacienteDoResultSet(rs));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Erro ao listar pacientes.", e);
            throw e;
        }

        return lista.isEmpty() ? Collections.emptyList() : lista;
    }

    //Atualiza o endereço e o prontuário de um paciente existente.

    public int atualizar(Paciente paciente) throws SQLException {
        validarPaciente(paciente);

        final String sql =
            "UPDATE paciente SET endereco_rua = ?, prontuario_observacoes = ?, prontuario_data = ? " +
            "WHERE cpf = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            definirParametrosEndereco(ps, paciente.getEndereco(), 1);
            definirParametrosProntuario(ps, paciente.getProntuario(), 2);
            ps.setString(4, paciente.getCpf());
            return ps.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Erro ao atualizar paciente com CPF: " + paciente.getCpf(), e);
            throw e;
        }
    }

    //Remove um paciente do banco de dados pelo CPF.

    public int excluirPorCpf(String cpf) throws SQLException {
        validarCpf(cpf);

        final String sql = "DELETE FROM paciente WHERE cpf = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, cpf);
            return ps.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Erro ao excluir paciente com CPF: " + cpf, e);
            throw e;
        }
    }

    // Mapeia uma linha do {@link ResultSet} para um objeto {@link Paciente}.
    // Centraliza o mapeamento e evita duplicação de código nos métodos de leitura.
    
    private Paciente extrairPacienteDoResultSet(ResultSet rs) throws SQLException {
        Paciente paciente = new Paciente();
        paciente.setCpf(rs.getString("cpf"));
        paciente.setNome(rs.getString("nome"));

        String rua = rs.getString("endereco_rua");
        if (rua != null) {
            Endereco endereco = new Endereco();
            endereco.setRua(rua);
            paciente.setEndereco(endereco);
        }

        String obs  = rs.getString("prontuario_observacoes");
        String data = rs.getString("prontuario_data");
        if (obs != null || data != null) {
            Prontuario prontuario = new Prontuario();
            prontuario.setObservacoes(obs);
            prontuario.setData(data);
            paciente.atualizarProntuario(prontuario);
        }

        return paciente;
    }

    // Define o parâmetro de endereço (rua) no {@link PreparedStatement}.

    private void definirParametrosEndereco(PreparedStatement ps, Endereco endereco, int indiceBase)
            throws SQLException {
        if (endereco != null) {
            ps.setString(indiceBase, endereco.getRua());
        } else {
            ps.setNull(indiceBase, Types.VARCHAR);
        }
    }

    // Define os parâmetros de prontuário (observações e data) no {@link PreparedStatement}.

    private void definirParametrosProntuario(PreparedStatement ps, Prontuario prontuario, int indiceBase)
            throws SQLException {
        if (prontuario != null) {
            ps.setString(indiceBase,     prontuario.getObservacoes());
            ps.setString(indiceBase + 1, prontuario.getData());
        } else {
            ps.setNull(indiceBase,     Types.VARCHAR);
            ps.setNull(indiceBase + 1, Types.VARCHAR);
        }
    }

    //Valida as regras mínimas de um {@link Paciente} antes de operações de escrita
    private void validarPaciente(Paciente paciente) {
        if (paciente == null) {
            throw new IllegalArgumentException("O paciente não pode ser nulo.");
        }
        validarCpf(paciente.getCpf());
        if (paciente.getNome() == null || paciente.getNome().isBlank()) {
            throw new IllegalArgumentException("O nome do paciente não pode ser nulo ou vazio.");
        }
    }

    // Valida o formato básico do CPF (presença e não-vazio)
    private void validarCpf(String cpf) {
        if (cpf == null || cpf.isBlank()) {
            throw new IllegalArgumentException("O CPF não pode ser nulo ou vazio.");
        }
    }
}

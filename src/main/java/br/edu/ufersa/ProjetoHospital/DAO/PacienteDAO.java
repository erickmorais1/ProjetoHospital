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

    public List<Paciente> listarTodos() throws SQLException {
        final String sql = "SELECT * FROM paciente";
        final List<Paciente> lista = new ArrayList<>();

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
        // corrigindo erro anterior e lendo a data como java.sql.Date do banco de dados
        java.sql.Date dataSql = rs.getDate("prontuario_data");

        if (obs != null || dataSql != null) {
            Prontuario prontuario = new Prontuario();
            if (obs != null) prontuario.setObservacoes(obs);

            // corrigindo erro anterior e convertendo para LocalDate
            if (dataSql != null) prontuario.setData(dataSql.toLocalDate());

            paciente.atualizarProntuario(prontuario);
        }

        return paciente;
    }

    private void definirParametrosEndereco(PreparedStatement ps, Endereco endereco, int indiceBase) throws SQLException {
        if (endereco != null) {
            ps.setString(indiceBase, endereco.getRua());
        } else {
            ps.setNull(indiceBase, Types.VARCHAR);
        }
    }

    private void definirParametrosProntuario(PreparedStatement ps, Prontuario prontuario, int indiceBase) throws SQLException {
        if (prontuario != null) {
            ps.setString(indiceBase, prontuario.getObservacoes());

            // corrigindo erro anterior e verificando e convertendo LocalDate para java.sql.Date antes de salvar
            if (prontuario.getData() != null) {
                ps.setDate(indiceBase + 1, java.sql.Date.valueOf(prontuario.getData()));
            } else {
                ps.setNull(indiceBase + 1, Types.DATE);
            }
        } else {
            ps.setNull(indiceBase,     Types.VARCHAR);
            ps.setNull(indiceBase + 1, Types.DATE);
        }
    }

    private void validarPaciente(Paciente paciente) {
        if (paciente == null) {
            throw new IllegalArgumentException("O paciente não pode ser nulo.");
        }
        validarCpf(paciente.getCpf());
        if (paciente.getNome() == null || paciente.getNome().isBlank()) {
            throw new IllegalArgumentException("O nome do paciente não pode ser nulo ou vazio.");
        }
    }

    private void validarCpf(String cpf) {
        if (cpf == null || cpf.isBlank()) {
            throw new IllegalArgumentException("O CPF não pode ser nulo ou vazio.");
        }
    }
}
package br.edu.ufersa.ProjetoHospital.DAO;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import br.edu.ufersa.ProjetoHospital.model.entities.Consulta;
import br.edu.ufersa.ProjetoHospital.model.entities.Medico;
import br.edu.ufersa.ProjetoHospital.model.entities.Paciente;

public class ConsultaDAO {
    private Connection con;

    public ConsultaDAO(Connection con) {
        this.con = con;
    }

    public void addConsulta(Consulta consulta) throws SQLException {
        String sql = "INSERT INTO consulta (paciente_cpf, medico_crm, diaHora, status) VALUES (?, ?, ?, ?)";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, consulta.getPaciente().getCpf());
            ps.setString(2, consulta.getMedico().getCrm());
            ps.setDate(3, Date.valueOf(consulta.getDiaHora()));
            ps.setString(4, consulta.getStatus());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Erro ao adicionar consulta no banco de dados.", e);
        }
    }

    public void atualizarConsulta(Consulta consulta) throws SQLException {
        String sql = "UPDATE consulta SET diaHora = ?, status = ? WHERE id = ?";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setDate(1, Date.valueOf(consulta.getDiaHora()));
            ps.setString(2, consulta.getStatus());
            ps.setInt(3, consulta.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Erro ao atualizar consulta.", e);
        }
    }

    public Consulta buscarConsultaPorId(int id) throws SQLException {
        String sql = "SELECT * FROM consulta WHERE id = ?";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapearResultSetParaConsulta(rs);
            }
        } catch (SQLException e) {
            throw e;
        }
        return null;
    }

    public List<Consulta> listarConsultasPorMedico(String crm) throws SQLException {
        List<Consulta> lista = new ArrayList<>();
        String sql = "SELECT * FROM consulta WHERE medico_crm = ?";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, crm);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                lista.add(mapearResultSetParaConsulta(rs));
            }
            return lista;
        } catch (SQLException e) {
            throw e;
        }
    }

    public List<Consulta> listarTodas() throws SQLException {
        List<Consulta> lista = new ArrayList<>();
        String sql = """
    SELECT c.*,
           p.nome AS nomePaciente,
           m.nome AS nomeMedico
    FROM consulta c
    JOIN paciente p ON c.paciente_cpf = p.cpf
    JOIN medico m ON c.medico_crm = m.crm
    """;

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                lista.add(mapearResultSetParaConsulta(rs));
            }
            return lista;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Consulta mapearResultSetParaConsulta(ResultSet rs) throws SQLException {
        Paciente paciente = new Paciente();
        paciente.setCpf(rs.getString("paciente_cpf"));
        paciente.setNome(rs.getString("nomePaciente"));

        Medico medico = new Medico();
        medico.setCrm(rs.getString("medico_crm"));
        medico.setNome(rs.getString("nomeMedico"));

        paciente.setNome(rs.getString("nomePaciente"));
        medico.setNome(rs.getString("nomeMedico"));

        LocalDate data = rs.getDate("diaHora").toLocalDate();
        int id = rs.getInt("id");

        Consulta consulta = new Consulta(id, paciente, medico, data);

        String statusBanco = rs.getString("status");
        if (!statusBanco.equals("Agendada")) {
            consulta.mudarStatus(statusBanco);
        }

        return consulta;
    }
}
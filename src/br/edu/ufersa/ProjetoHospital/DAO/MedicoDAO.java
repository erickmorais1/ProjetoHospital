package br.edu.ufersa.ProjetoHospital.DAO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import br.edu.ufersa.ProjetoHospital.model.entities.Medico;


public class MedicoDAO {
    private Connection con;
    public MedicoDAO (Connection conexao) throws SQLException {
        con = conexao;
    }

    public void addMedico(Medico medico) throws SQLException {
        String sql = "INSERT INTO medico (nome,cpf,crm,valorConsulta)" +
                "VALUES (?,?,?,?)";
        try{PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, medico.getNome());
        ps.setString(2, medico.getCpf());
        ps.setString(3, medico.getCrm());
        ps.setDouble(4, medico.getValorConsulta());
        ps.executeUpdate();}catch(SQLException e){
            throw new SQLException();
        }
    }

    public Medico buscarMedicoPorCrm(String crm) throws SQLException {
        try{
            String sql = "SELECT * FROM medico WHERE crm = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, crm);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                Medico medico = new Medico();
                medico.setNome(rs.getString("nome"));
                medico.setCpf(rs.getString("cpf"));
                medico.setCrm(rs.getString("crm"));
                medico.setValorConsulta(rs.getDouble("valorConsulta"));
                return  medico;
            }
        }catch(SQLException e){
            throw e;
        }
        return null;
    }

    public Medico buscarMedicoPorCpf(String cpf) throws SQLException {
        try{
            String sql = "SELECT * FROM medico WHERE cpf = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, cpf);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                Medico medico = new Medico();
                medico.setNome(rs.getString("nome"));
                medico.setCpf(rs.getString("cpf"));
                medico.setCrm(rs.getString("crm"));
                medico.setValorConsulta(rs.getDouble("valorConsulta"));
                return  medico;
            }
        }catch(SQLException e){
            throw e;
        }
        return null;
    }

    public List<Medico> buscarMedicoPorNome(String nome) throws SQLException {
        List<Medico> lista;
        try {
            String sql = "SELECT * FROM medico WHERE nome = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, nome);
            ResultSet rs = ps.executeQuery();
            lista = new ArrayList<>();
            while (rs.next()) {
                Medico medico = new Medico();
                medico.setNome(rs.getString("nome"));
                medico.setCpf(rs.getString("cpf"));
                medico.setCrm(rs.getString("crm"));
                medico.setValorConsulta(rs.getDouble("valorConsulta"));
                lista.add(medico);
            }
            return lista;
        } catch (SQLException e) {
            throw e;
        }
    }



}

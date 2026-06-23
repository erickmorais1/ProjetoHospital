package br.edu.ufersa.ProjetoHospital.Service;
import br.edu.ufersa.ProjetoHospital.DAO.MedicoDAO;
import br.edu.ufersa.ProjetoHospital.model.entities.Gerente;
import br.edu.ufersa.ProjetoHospital.model.entities.Medico;

import java.sql.SQLException;
import java.util.List;


public class MedicoService {
    private MedicoDAO medicoDAO;
    public MedicoService(MedicoDAO medicoDAO){
        this.medicoDAO = medicoDAO;
    }

    public void addMedico(Gerente gerente, Medico medico) throws SQLException {
        if (gerente == null) {
            throw new IllegalStateException("Somente o gerente pode adicionar um novo médico");
        }
        if (medico == null) {
            throw new IllegalArgumentException("Médico nulo não pode ser adicionado");
        }
        medicoDAO.addMedico(medico);
    }
    public void excluirMedico(Gerente gerente, String crm) throws SQLException {
        if (gerente == null) {
            throw new IllegalStateException("Somente o gerente pode remover um médico");
        }
        if (crm == null) {
            throw new IllegalArgumentException("CRM inválido");
        }
        medicoDAO.excluirMedicoPorCrm(crm);
    }

    public void atualizarMedico(Gerente gerente, Medico medico) throws SQLException {
        if (gerente == null) {
            throw new IllegalStateException("Somente o gerente pode atualizar um médico");
        }
        if (medico == null || medico.getCrm() == null) {
            throw new IllegalArgumentException("Médico inválido");
        }
        medicoDAO.atualizarMedico(medico);
    }

    public List<Medico> listarMedicos() throws SQLException {
        return medicoDAO.listarMedicos();
    }

    public Medico buscarMedicoPorCrm(String crm) throws SQLException {
        return medicoDAO.buscarMedicoPorCrm(crm);
    }
    public List<Medico> buscarMedicoPorNome(String nome) throws SQLException {
        return medicoDAO.buscarMedicoPorNome(nome);
    }
    public Medico buscarMedicoPorCpf(String cpf) throws SQLException {
        return medicoDAO.buscarMedicoPorCpf(cpf);
    }




}





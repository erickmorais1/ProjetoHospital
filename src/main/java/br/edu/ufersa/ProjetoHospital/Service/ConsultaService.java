package br.edu.ufersa.ProjetoHospital.Service;

import br.edu.ufersa.ProjetoHospital.DAO.ConsultaDAO;
import br.edu.ufersa.ProjetoHospital.model.entities.Consulta;
import br.edu.ufersa.ProjetoHospital.model.entities.Medico;
import br.edu.ufersa.ProjetoHospital.model.entities.Paciente;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class ConsultaService {
    private ConsultaDAO consultaDAO;

    public ConsultaService(ConsultaDAO consultaDAO) {
        this.consultaDAO = consultaDAO;
    }

    public void agendarConsulta(Paciente paciente, Medico medico, LocalDate diaHora) throws SQLException {
        if (paciente == null || medico == null) {
            throw new IllegalArgumentException("Paciente e Médico são obrigatórios.");
        }
        if (diaHora == null || diaHora.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Data inválida.");
        }

        Consulta consulta = new Consulta(1, paciente, medico, diaHora);
        consultaDAO.addConsulta(consulta);
    }

    public void remarcarConsulta(int idConsulta, LocalDate novaData) throws SQLException {
        Consulta consulta = consultaDAO.buscarConsultaPorId(idConsulta);

        if (consulta == null) {
            throw new IllegalArgumentException("Consulta não encontrada.");
        }
        if (novaData == null || novaData.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Data inválida.");
        }

        consulta.remarcar(novaData);
        consultaDAO.atualizarConsulta(consulta);
    }

    public void cancelarConsulta(int idConsulta) throws SQLException {
        alterarStatusConsulta(idConsulta, "Cancelada");
    }

    public void realizarConsulta(int idConsulta) throws SQLException {
        alterarStatusConsulta(idConsulta, "Realizada");
    }

    private void alterarStatusConsulta(int idConsulta, String novoStatus) throws SQLException {
        Consulta consulta = consultaDAO.buscarConsultaPorId(idConsulta);

        if (consulta == null) {
            throw new IllegalArgumentException("Consulta não encontrada.");
        }

        consulta.mudarStatus(novoStatus);
        consultaDAO.atualizarConsulta(consulta);
    }

    public Consulta buscarConsulta(int id) throws SQLException {
        if (id <= 0) {
            throw new IllegalArgumentException("ID inválido.");
        }
        return consultaDAO.buscarConsultaPorId(id);
    }

    public List<Consulta> listarConsultasDoMedico(Medico medico) throws SQLException {
        if (medico == null || medico.getCrm() == null) {
            throw new IllegalArgumentException("Médico inválido.");
        }
        return consultaDAO.listarConsultasPorMedico(medico.getCrm());
    }

    public List<Consulta> listarTodasAsConsultas() throws SQLException {
        return consultaDAO.listarTodas();
    }
}
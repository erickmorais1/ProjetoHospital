package br.edu.ufersa.ProjetoHospital.model.entities;


import java.time.LocalDate;

public class Consulta {
    private int id;
    private Paciente paciente;
    private Medico medico;
    private LocalDate diaHora;
    private String status;
    private Prontuario prontuario;

    public Consulta(int id, Paciente paciente, Medico medico, LocalDate diaHora,Prontuario prontuario) {
        setId(id);
        setPaciente(paciente);
        setMedico(medico);
        setDiaHora(diaHora);
        setProntuario(prontuario);
        this.status = "Agendada"; // Status inicial padrão
    }

    // SETTERS COM VALIDAÇÃO 
    public void setId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID inválido: deve ser maior que zero.");
        }
        this.id = id;
    }

    public void setPaciente(Paciente paciente) {
        if (paciente == null) {
            throw new IllegalArgumentException("Erro: O paciente não pode ser nulo.");
        }
        this.paciente = paciente;
    }

    public void setMedico(Medico medico) {
        if (medico == null) {
            throw new IllegalArgumentException("Erro: O médico não pode ser nulo.");
        }
        this.medico = medico;
    }

    public void setDiaHora(LocalDate diaHora) {
        if (diaHora!= null) {
            this.diaHora = diaHora;
        }
    }
    public void setProntuario(Prontuario prontuario) {
        if (prontuario != null) {
            this.prontuario = prontuario;
        }
    }

    // REGRAS DE NEGÓCIO

    public void mudarStatus(String novoStatus) {
        // Validação de integridade
        if (this.status.equals("Cancelada") || this.status.equals("Realizada")) {
            throw new IllegalStateException("Não é possível alterar o status de uma consulta já finalizada ou cancelada.");
        }

        if (novoStatus != null && (novoStatus.equals("Realizada") || novoStatus.equals("Cancelada") || novoStatus.equals("Agendada"))) {
            this.status = novoStatus;
        } else {
            throw new IllegalArgumentException("Status inválido fornecido.");
        }
    }

    public void remarcar(LocalDate novaDiaHora) {
        if (!this.status.equals("Agendada")) {
            throw new IllegalStateException("Só é possível remarcar consultas que ainda estão 'Agendadas'.");
        }
        setDiaHora(novaDiaHora); // Reutiliza a validação do setter
    }

    // GETTERS

    public int getId() { return id; }
    public Paciente getPaciente() { return paciente; }
    public Medico getMedico() { return medico; }
    public LocalDate getDiaHora() { return diaHora; }
    public String getStatus() { return status; }

    @Override
    public String toString() {
        return String.format("Consulta [ID: %d] | Paciente: %s | Médico: %s | Data: %s | Status: %s",
                id, paciente.getNome(), medico.getNome(), diaHora, status);
    }
}

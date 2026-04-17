package br.edu.ufersa.ProjetoHospital.model.entities;

public class Consulta {
  private int id; //indentificador unico
    private Paciente paciente;
    private Medico medico;
    private String diaHora;
    private String status;

    public Consulta(int id, Paciente paciente, Medico medico, String diaHora) { //acionado no momento em que você cria uma nova consulta
        this.id = id;
        this.paciente = paciente;
        this.medico = medico;
        this.diaHora = diaHora;
        this.status = "Agendada"; // Status padrão na criação
    }

    public void mudarStatus(String novoStatus) {
        // Validação de integridade: Impede transições de status ilógicas
        if (this.status.equals("Cancelada") || this.status.equals("Realizada")) {
            System.out.println("Não é possível alterar o status de uma consulta já finalizada/cancelada.");
            return;
        }
        
        if (novoStatus.equals("Realizada") || novoStatus.equals("Cancelada") || novoStatus.equals("Agendada")) {
            this.status = novoStatus;
        } else {
            System.out.println("Status inválido.");
        }
    }

    public int getId() { return id; }
    public Paciente getPaciente() { return paciente; }
    public Medico getMedico() { return medico; }
    public String getDiaHora() { return diaHora; }
    public String getStatus() { return status; }

public void remarcar(String novaDiaHora) {   //remarca consulta
        if (this.status.equals("Agendada")) {
            this.diaHora = novaDiaHora;
        } else {
            System.out.println("Só é possível remarcar consultas que estão Agendadas.");
        }
    }
@Override
    public String toString() {  //facilitar forma relatorio
        return "Consulta ID: " + id + 
               " | Paciente: " + paciente.getNome() + 
               " | Médico: " + medico.getNome() + 
               " | Data/Hora: " + diaHora + 
               " | Status: " + status;
    }
}


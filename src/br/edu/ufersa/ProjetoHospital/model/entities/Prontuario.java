package br.edu.ufersa.ProjetoHospital.model.entities;

public class Prontuario {
    private String observacoes;
    private String data;

    public Prontuario(String data) {
        if (data != null && !data.trim().isEmpty()) {
            this.data = data;
        } else {
            System.out.println("Erro: A data do prontuário não pode ser vazia.");
            this.data = "Data não informada";
        }
        this.observacoes = "Sem observações.";
    }

    // Método definido no diagrama
    public void adicionarObs(String obs) {
        if (obs != null && !obs.trim().isEmpty()) {
            if (this.observacoes.equals("Sem observações.")) {
                this.observacoes = obs;
            } else {
                this.observacoes += " | " + obs;
            }
        } else {
            System.out.println("Erro: A observação não pode ser vazia.");
        }
    }


    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        if (observacoes != null && !observacoes.trim().isEmpty()) {
            this.observacoes = observacoes;
        } else {
            System.out.println("Erro: As observações não podem ser vazias.");
        }
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        if (data != null && !data.trim().isEmpty()) {
            this.data = data;
        } else {
            System.out.println("Erro: A data não pode ser vazia.");
        }
    }

    @Override
    public String toString() {
        return "Prontuário | Data: " + data + " | Observações: " + observacoes;
    }
}
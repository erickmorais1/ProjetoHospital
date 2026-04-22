package br.edu.ufersa.ProjetoHospital.model.entities;

import java.time.LocalDate;

//mandando novamente.
public class Prontuario {
    private String observacoes;
    private LocalDate data;

    public Prontuario(LocalDate data) {
        if (data != null) {
            this.data = data;
        } else {
            System.out.println("Erro: A data do prontuário não pode ser vazia.");

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

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        if (data != null) {
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
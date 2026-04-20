package br.edu.ufersa.ProjetoHospital.model.entities;

public class Prontuario {
    private String observacoes; // [cite: 14]
    private String data; // [cite: 15]

    public Prontuario(String data) {
        this.data = data;
        this.observacoes = "Sem observações."; // Valor padrão na criação
    }

    // MAdição de obs
    public void adicionarObs(String obs) { //
        if (this.observacoes.equals("Sem observações.")) {
            this.observacoes = obs;
        } else {
            this.observacoes += " | " + obs;
        }
    }

    // Getters para encapsulamento
    public String getObservacoes() { return observacoes; }
    public String getData() { return data; }

    @Override
    public String toString() {
        return "Prontuário | Data: " + data +
                " | Observações: " + observacoes;
    }
}
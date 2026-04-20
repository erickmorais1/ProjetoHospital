package br.edu.ufersa.ProjetoHospital.model.entities;

public class Gerente {
    private String nome;
    private String cpf;
    private String endereco;

    public Gerente(String nome, String cpf, String endereco) {
        // Validação simples usando if/else
        if (nome != null && !nome.trim().isEmpty()) {
            this.nome = nome;
        } else {
            System.out.println("Erro: Nome inválido na criação do Gerente.");
            this.nome = "Não informado";
        }

        if (cpf != null && !cpf.trim().isEmpty()) {
            this.cpf = cpf;
        } else {
            System.out.println("Erro: CPF inválido na criação do Gerente.");
            this.cpf = "Não informado";
        }

        if (endereco != null && !endereco.trim().isEmpty()) {
            this.endereco = endereco;
        } else {
            System.out.println("Erro: Endereço inválido na criação do Gerente.");
            this.endereco = "Não informado";
        }
    }

    // Método do UML
    public void atualizarEndereco(String local) {
        if (local != null && !local.trim().isEmpty()) {
            this.endereco = local;
        } else {
            System.out.println("Erro: O novo endereço não pode ser vazio.");
        }
    }

    // Getters para encapsulamento
    public String getNome() { return nome; }
    public String getCpf() { return cpf; }
    public String getEndereco() { return endereco; }

    @Override
    public String toString() {
        return "Gerente | Nome: " + nome + " | CPF: " + cpf + " | Endereço: " + endereco;
    }
}
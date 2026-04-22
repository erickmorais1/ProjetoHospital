package br.edu.ufersa.ProjetoHospital.model.entities;
//mandando novamente.
public class Gerente extends Endereco {
    private String nome;
    private String cpf;
    private Endereco endereco;

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
    }

    // Método do UML


    // Getters para encapsulamento
    public String getNome() { return nome; }
    public String getCpf() { return cpf; }


    @Override
    public String toString() {
        return "Gerente | Nome: " + nome + " | CPF: " + cpf + " | Endereço: " + endereco;
    }
}
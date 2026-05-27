package br.edu.ufersa.ProjetoHospital.model.entities;

public abstract class Pessoa {

    // Atributos privados -> encapsulamento
    protected String nome;
    protected String cpf;
    protected Endereco endereco;

    // Construtor
    public Pessoa(String nome, String cpf, Endereco endereco) {
        this.setNome(nome);
        this.setCpf(cpf);
        this.setEndereco(endereco);
    }
    public Pessoa(){};

    // GETTERS

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    // SETTERS

    public void setNome(String nome) {
        if (nome != null) {
            this.nome = nome;
        }
    }

    public void setCpf(String cpf) {
        if (cpf!=null && !cpf.isBlank()) {
            this.cpf = cpf;
        }
    }

    public void setEndereco(Endereco endereco) {
        if(endereco !=  null){
            this.endereco = endereco;
        }
    }
}

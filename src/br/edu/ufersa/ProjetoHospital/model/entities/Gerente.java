package br.edu.ufersa.ProjetoHospital.model.entities;
//mandando novamente.
public class Gerente extends Pessoa{
    private String identificador;

    public Gerente(String nome, String cpf, Endereco endereco,String identificador) {
        super(nome,cpf,endereco);
        setIdentificador(identificador);
    }

    // Getters
    public String getIdentificador() {
        return identificador;
    }

    // Setters
    public void setIdentificador(String identificador) {
        if(identificador!=null){
            this.identificador=identificador;
        }
    }


    @Override
    public String toString() {
        return "Gerente | Nome: " + nome + " | CPF: " + cpf + " | Endereço: " + endereco;
    }
}
package br.edu.ufersa.ProjetoHospital.model.entities;

public class Paciente extends Pessoa {
  private Prontuario prontuario;

  public Paciente (){}
  public Paciente (String nome, Endereco endereco, String cpf, Prontuario prontuario) {
    super(nome,cpf,endereco);
    atualizarProntuario(prontuario);
  }


  public Prontuario getProntuario(){
    return prontuario;
  }

 public void atualizarProntuario(Prontuario prontuario){
  if(prontuario != null &&
     prontuario.getObservacoes() != null &&
     !prontuario.getObservacoes().isEmpty() &&
     prontuario.getData() != null){
       
    this.prontuario = prontuario;
  }
}

  
}

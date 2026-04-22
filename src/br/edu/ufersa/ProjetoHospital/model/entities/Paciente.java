package br.edu.ufersa.ProjetoHospital.model.entities;

public class Paciente extends Endereco {
  private String nome,cpf;
  private Endereco endereco;
  private Prontuario prontuario;

  public Paciente (){}
  public Paciente (String nome, Endereco endereco, String cpf){
    setNome(nome);
    setEndereco(endereco);
    setCpf(cpf);
  }

  //getters
  public String getNome(){
    return nome;
  }
  public Endereco getEndereco(){
    return endereco;
  }
  public String getCpf(){
    return cpf;
  }
  public Prontuario getProntuario(){
    return prontuario;
  }

  public void setNome(String nome){
    if(nome!=null && !nome.isEmpty()){
      this.nome = nome;
    }
  }
  public void setEndereco (Endereco endereco){
    if(endereco!=null){
      this.endereco = endereco;
    }
  }
  public void setCpf(String cpf){
    if(cpf!=null && cpf.length()==11){
      this.cpf = cpf;
    }
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

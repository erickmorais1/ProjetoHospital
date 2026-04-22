package br.edu.ufersa.ProjetoHospital.model.entities;

public class Medico extends Endereco {
  private String crm,nome,cpf;
  private Endereco endereco;
  private double valorConsulta;

  public Medico(String nome,String cpf, String crm, String endereco, double valorConsulta){
    setNome(nome);
    setCpf(cpf);
    setCrm(crm);
    setValorConsulta(valorConsulta);
  }
  public Medico(){}
  
  //getters
  public String getNome(){
    return nome;
  }

  public String getCrm(){
    return crm;
  }

  public String getCpf(){
    return cpf;
  }

  public double getValorConsulta(){
    return valorConsulta;
  }

  //setters
  public void setNome(String nome){
    if(nome!=null && !nome.isEmpty()){
      this.nome = nome;
    }
  }

  public void setCrm(String crm){
    if(crm!=null && !crm.isEmpty()){
      this.crm = crm;
    }
  }

  public void setCpf(String cpf){
    if(cpf!=null && cpf.length()==11){
      this.cpf=cpf;
    }
  }

  public void setValorConsulta(double valor){
    if(valor>0){
      this.valorConsulta=valor;
    }
  }
  
 
}

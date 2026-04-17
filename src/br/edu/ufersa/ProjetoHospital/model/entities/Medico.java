package br.edu.ufersa.ProjetoHospital.model.entities;

public class Medico {
  private String crm,nome,cpf,endereco;
  private double valorConsulta;

  public Medico(String nome,String cpf, String crm, String endereco, double valorConsulta){
    setNome(nome);
    setCpf(cpf);
    setCrm(crm);
    setEndereco(endereco);
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

  public String getEndereco(){
    return endereco;
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

  public void setEndereco(String endereco){
    if(endereco!=null && !endereco.isEmpty()){
      this.endereco = endereco;
    }
  }

  public void setValorConsulta(double valor){
    if(valor>0){
      this.valorConsulta=valor;
    }
  }
  
 
}

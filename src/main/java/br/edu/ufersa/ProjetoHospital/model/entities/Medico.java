package br.edu.ufersa.ProjetoHospital.model.entities;

public class Medico extends Pessoa {
  private String crm;
  private double valorConsulta;

  public Medico(String nome, String cpf, Endereco endereco, String crm, double valorConsulta){
    super(nome,cpf,endereco);
    setCrm(crm);
    setValorConsulta(valorConsulta);
  }
  public Medico(){
      super();
  }


  public double getValorConsulta(){
    return valorConsulta;
  }
  public String getCrm(){
    return crm;
  }

  public void setValorConsulta(double valor){
    if(valor>0){
      this.valorConsulta=valor;
    }
  }
  public void setCrm(String crm){
    if(crm != null){
      this.crm=crm;
    }
  }


}

package br.edu.ufersa.ProjetoHospital.model.entities;

public class Endereco {
    private String cep;
    private int numero;
    private String complemento;
    private String bairro;
    private String cidade;

    public  Endereco() {}

    //getters
    public String getCep() {
        return cep;
    }
    public int getNumero() {
        return numero;
    }
    public String getComplemento() {
        return complemento;
    }
    public String getBairro() {
        return bairro;
    }
    public String getCidade() {
        return cidade;
    }

    //setters

    public void setCep(String cep) {
        if(cep!=null && !cep.equals("")) {
            this.cep = cep;
        }
    }
    public void setNumero(int numero) {
        if(numero>0) {
            this.numero = numero;
        }
    }
    public void setComplemento(String complemento) {
        if(complemento!=null && !complemento.equals("")) {
            this.complemento = complemento;
        }
    }
    public void setBairro(String bairro) {
        if(bairro!=null && !bairro.equals("")) {
            this.bairro = bairro;
        }
    }
    public void setCidade(String cidade) {
        if(cidade!=null && !cidade.equals("")) {
            this.cidade = cidade;
        }
    }

    public void mudarEndereco(String cep,int numero,String complemento,String bairro,String cidade){
        if (cep!=null && !cep.equals("") && numero>0 && complemento!=null && bairro!=null && cidade!=null) {
            this.cep = cep;
            this.numero = numero;
            this.complemento = complemento;
            this.bairro = bairro;
            this.cidade = cidade;
        }
    }

}

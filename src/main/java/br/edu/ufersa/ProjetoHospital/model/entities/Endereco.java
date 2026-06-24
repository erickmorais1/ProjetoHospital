package br.edu.ufersa.ProjetoHospital.model.entities;

public class Endereco {
    private String rua; // adicionei pra funcionar melhor no PacienteDao
    private String cep;
    private int numero;
    private String complemento;
    private String bairro;
    private String cidade;

    public Endereco() {}

    // getters
    public String getRua() { return rua; }
    public String getCep() { return cep; }
    public int getNumero() { return numero; }
    public String getComplemento() { return complemento; }
    public String getBairro() { return bairro; }
    public String getCidade() { return cidade; }

    // setters
    public void setRua(String rua) {
        if(rua != null && !rua.trim().isEmpty()) {
            this.rua = rua;
        }
    }

    public void setCep(String cep) {
        if(cep != null && !cep.equals("")) {
            this.cep = cep;
        }
    }

    public void setNumero(int numero) {
        if(numero > 0) {
            this.numero = numero;
        }
    }

    public void setComplemento(String complemento) {
        if(complemento != null && !complemento.equals("")) {
            this.complemento = complemento;
        }
    }

    public void setBairro(String bairro) {
        if(bairro != null && !bairro.equals("")) {
            this.bairro = bairro;
        }
    }

    public void setCidade(String cidade) {
        if(cidade != null && !cidade.equals("")) {
            this.cidade = cidade;
        }
    }

    public void mudarEndereco(String rua, String cep, int numero, String complemento, String bairro, String cidade){
        if (rua != null && cep != null && !cep.equals("") && numero > 0 && complemento != null && bairro != null && cidade != null) {
            this.rua = rua;
            this.cep = cep;
            this.numero = numero;
            this.complemento = complemento;
            this.bairro = bairro;
            this.cidade = cidade;
        }
    }
}
package br.edu.ufersa.ProjetoHospital.Factory;

import br.edu.ufersa.ProjetoHospital.model.entities.Endereco;
import br.edu.ufersa.ProjetoHospital.model.entities.Medico;

public class HospitalFactory {
    public static Medico criarMedico(String nome, String cpf, Endereco endereco,
                                     String crm, double valorConsulta) {
        return new Medico(nome, cpf, endereco, crm, valorConsulta);
    }
}

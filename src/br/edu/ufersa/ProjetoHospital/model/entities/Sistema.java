package br.edu.ufersa.ProjetoHospital.model.entities;

import java.util.ArrayList;
import java.util.List;

public class Sistema {
private List<Medico> listaMedicos = new ArrayList<Medico>();
private List<Paciente> listaPacientes = new ArrayList<Paciente>();
private List<Consulta> listaConsultas = new ArrayList<Consulta>();



    public void addMedico(Gerente gerente,Medico medico){
    //garantir que somente gerente possa add medico
    if(gerente!=null){
        listaMedicos.add(medico);
        System.out.println("Medico adicionado com sucesso!");
    }
}
public void removerMedico(Gerente gerente, Medico medico){
    //garantir que somente gerente pode remover medico
    if(gerente!=null){
        listaMedicos.remove(medico);
        System.out.println("Medico removido.");
    }
}
public void addPaciente(Medico  medico, Paciente paciente){
   //somente medico pode add paciente
    if(medico!=null){
        listaPacientes.add(paciente);
    }
}
public void removerPaciente(Medico medico, Paciente paciente){
    //somente medico pode remover paciente
    if(medico!=null){
        listaPacientes.remove(paciente);
    }
}
public void editarPaciente(Medico medico, Paciente paciente, String novoEndereco, Prontuario novoProntuario){
//so vai ter como mudar o endereco e o prontuario, até pq ngm muda de nome direto e nem de cpf
    //so tem como editar se for medico
    if(medico!=null){
        paciente.setEndereco(novoEndereco);
        paciente.atualizarProntuario(novoProntuario);

    }
}

}

package br.edu.ufersa.ProjetoHospital.model.entities;

import java.util.ArrayList;
import java.util.List;

public class Sistema {
    private List<Medico> listaMedicos = new ArrayList<Medico>();
    private List<Paciente> listaPacientes = new ArrayList<Paciente>();
    private List<Consulta> listaConsultas = new ArrayList<Consulta>();


    public void addMedico(Gerente gerente, Medico medico) {
        //garantir que somente gerente possa add medico
        if (gerente != null) {
            listaMedicos.add(medico);
            System.out.println("Medico adicionado com sucesso!");
        }
    }

    public void removerMedico(Gerente gerente, Medico medico) {
        //garantir que somente gerente pode remover medico
        if (gerente != null) {
            listaMedicos.remove(medico);
            System.out.println("Medico removido.");
        }
    }

    public void addPaciente(Medico medico, Paciente paciente) {
        //somente medico pode add paciente
        if (medico != null) {
            listaPacientes.add(paciente);
        }
    }

    public void removerPaciente(Medico medico, Paciente paciente) {
        //somente medico pode remover paciente
        if (medico != null) {
            listaPacientes.remove(paciente);
        }
    }

    public void editarPaciente(Medico medico, Paciente paciente, String novoEndereco, Prontuario novoProntuario) {
//so vai ter como mudar o endereco e o prontuario, até pq ngm muda de nome direto e nem de cpf
        //so tem como editar se for medico
        if (medico != null) {
            paciente.setEndereco(novoEndereco);
            paciente.atualizarProntuario(novoProntuario);

        }
    }

    // Gerenciamento de Consultas
    public void cadastrarConsulta(Consulta consulta) {
        if (consulta != null) {
            listaConsultas.add(consulta);
        }
    }

    public void excluirConsulta(Consulta consulta) {
        if (consulta != null) {
            listaConsultas.remove(consulta);
        }
    }

    public Consulta editarConsulta(Consulta consulta) {
        // Retorna a consulta para edição via métodos da classe Consulta
        return consulta;
    }

    // Buscas e Relatórios de Consultas
    public List<Consulta> buscarConsultaPorMedico(Medico medico) {
        List<Consulta> resultado = new ArrayList<>();
        for (Consulta c : listaConsultas) {
            if (c.getMedico().equals(medico)) resultado.add(c);
        }
        return resultado;
    }

    public List<Consulta> buscarConsultaPorPaciente(Paciente paciente) {
        List<Consulta> resultado = new ArrayList<>();
        for (Consulta c : listaConsultas) {
            if (c.getPaciente().equals(paciente)) resultado.add(c);
        }
        return resultado;
    }

    public List<Consulta> buscarConsultaPorHorario(String diaHora) {
        List<Consulta> resultado = new ArrayList<>();
        for (Consulta c : listaConsultas) {
            if (c.getDiaHora().equals(diaHora)) resultado.add(c);
        }
        return resultado;
    }

    public List<Consulta> gerarRelatorioPorMedico(Medico medico) {
        return buscarConsultaPorMedico(medico);
    }

    // Buscas de Médicos e Pacientes
    public List<Medico> buscarMedicoCrm(String crm) {
        List<Medico> resultado = new ArrayList<>();
        for (Medico m : listaMedicos) {
            if (m.getCrm().equals(crm)) resultado.add(m);
        }
        return resultado;
    }

    public List<Medico> buscarMedicoNome(String nome) {
        List<Medico> resultado = new ArrayList<>();
        for (Medico m : listaMedicos) {
            if (m.getNome().equalsIgnoreCase(nome)) resultado.add(m);
        }
        return resultado;
    }

    public List<Paciente> buscarPacienteNome(String nome) {
        List<Paciente> resultado = new ArrayList<>();
        for (Paciente p : listaPacientes) {
            if (p.getNome().equalsIgnoreCase(nome)) resultado.add(p);
        }
        return resultado;
    }

    public List<Paciente> buscarPacienteCpf(String cpf) {
        List<Paciente> resultado = new ArrayList<>();
        for (Paciente p : listaPacientes) {
            if (p.getCpf().equals(cpf)) resultado.add(p);
        }
        return resultado;
    }

    public List<Paciente> buscarPacientePorNome(String nome) {
        return buscarPacienteNome(nome);
    }

    public List<Paciente> buscarPacientePorCpf(String cpf) {
        return buscarPacienteCpf(cpf);
    }

    public List<Consulta> buscarConsultasDoMedico(Medico medico) {
        return buscarConsultaPorMedico(medico);
    }

    public List<Consulta> buscarConsultasDoPaciente(Paciente paciente) {
        return buscarConsultaPorPaciente(paciente);
    }

    public List<Consulta> buscarConsultasPorHorario(String diaHora) {
        return buscarConsultaPorHorario(diaHora);
    }

    public List<Consulta> emitirRelatorioPorMedico(Medico medico) {
        return gerarRelatorioPorMedico(medico);
    }

}

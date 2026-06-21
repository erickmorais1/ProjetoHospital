package br.edu.ufersa.ProjetoHospital.Controller;

import br.edu.ufersa.ProjetoHospital.Facade.HospitalFacade;
import br.edu.ufersa.ProjetoHospital.Util.TrocaTela;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

/**
 * Controller para o menu principal do gerente
 * O gerente tem permissão para gerenciar apenas médicos (ADD, listar, buscar, remover)
 */
public class MenuGerenteController implements FacadeController {

    private HospitalFacade facade;

    @FXML
    public void abrirCadastrarMedico(ActionEvent event) {
        System.out.println("Abrindo cadastro de médico");
        TrocaTela.trocarTela(event,
                "/fxml/TelaCadastrarMedico.fxml",
                facade);
    }

    @FXML
    public void abrirListarMedicos(ActionEvent event) {
        System.out.println("Abrindo lista de médicos");
        TrocaTela.trocarTela(event,
                "/fxml/TelaListarMedicos.fxml",
                facade);
    }

    @FXML
    public void abrirEditarPerfil(ActionEvent event) {
        System.out.println("Abrindo edição de perfil");
        TrocaTela.trocarTela(event,
                "/fxml/TelaEditarPerfilGerente.fxml",
                facade);
    }

    @FXML
    public void sair(ActionEvent event) {
        System.out.println("Saindo do menu do gerente");
        TrocaTela.trocarTela(event,
                "/fxml/TelaLogin.fxml",
                facade);
    }

    @Override
    public void setFacade(HospitalFacade facade) {
        this.facade = facade;
        System.out.println(getClass().getSimpleName()
                + " recebeu facade: " + (facade != null));
    }
}
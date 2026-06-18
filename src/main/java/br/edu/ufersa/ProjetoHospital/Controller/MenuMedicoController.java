package br.edu.ufersa.ProjetoHospital.Controller;

import br.edu.ufersa.ProjetoHospital.Facade.HospitalFacade;
import br.edu.ufersa.ProjetoHospital.Util.TrocaTela;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class MenuMedicoController implements FacadeController {

    private HospitalFacade facade;

    @FXML
    public void abrirCadastrarConsulta(ActionEvent event) {
        System.out.println("Facade é null? " + (facade == null));
        TrocaTela.trocarTela(event,
                "/fxml/TelaCadastrarConsulta.fxml",
                facade);
    }

    @FXML
    public void abrirListarConsultas(ActionEvent event) {

        System.out.println("Facade no MenuMedicoController é null? "
                + (facade == null));

        TrocaTela.trocarTela(
                event,
                "/fxml/TelaListarConsultas.fxml",
                facade);
    }

    public void setFacade(HospitalFacade facade) {
        this.facade = facade;
        System.out.println(getClass().getSimpleName()
                + " recebeu facade: " + (facade != null));
    }

    @FXML
    public void sair(ActionEvent event) {
        System.out.println("Facade é null? " + (facade == null));
        TrocaTela.trocarTela(
                event,
                "/fxml/TelaLogin.fxml",
                facade
        );
    }
}

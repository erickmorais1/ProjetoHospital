package br.edu.ufersa.ProjetoHospital.Controller;

import br.edu.ufersa.ProjetoHospital.Facade.HospitalFacade;
import br.edu.ufersa.ProjetoHospital.Util.TrocaTela;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class TelaInicialController implements  FacadeController{

    private HospitalFacade facade;

    @FXML
    private void irParaLogin(ActionEvent event) {
        System.out.println("Facade é null? " + (facade == null));
        TrocaTela.trocarTela(
                event,
                "/fxml/TelaLogin.fxml",
                facade
        );
    }

    @FXML
    private void irParaPaciente(ActionEvent event) {
        System.out.println("Facade é null? " + (facade == null));
        TrocaTela.trocarTela(
                event,
                "/fxml/TelaPaciente.fxml",
                facade
        );
    }

    public void setFacade(HospitalFacade facade) {
        this.facade = facade;
        System.out.println(getClass().getSimpleName()
                + " recebeu facade: " + (facade != null));
    }
}
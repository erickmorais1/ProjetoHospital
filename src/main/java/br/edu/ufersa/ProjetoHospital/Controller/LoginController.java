package br.edu.ufersa.ProjetoHospital.Controller;

import br.edu.ufersa.ProjetoHospital.Facade.HospitalFacade;
import br.edu.ufersa.ProjetoHospital.Util.TrocaTela;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController implements FacadeController{

    @FXML
    private TextField txtUsuario;

    @FXML
    private PasswordField txtSenha;

    private HospitalFacade facade;

    public void setFacade(HospitalFacade facade) {
        this.facade = facade;
        System.out.println(getClass().getSimpleName()
                + " recebeu facade: " + (facade != null));
    }

    @FXML
    public void entrar(ActionEvent event) {

        String usuario = txtUsuario.getText();
        String senha = txtSenha.getText();

        if (usuario.equals("medico") && senha.equals("123")) {
            System.out.println("Facade é null? " + (facade == null));
            TrocaTela.trocarTela(
                    event,
                    "/fxml/TelaMenuMedico.fxml",
                    facade
            );

        } else {

            System.out.println("Usuário ou senha inválidos.");

        }
    }
    @FXML
    public void voltar(ActionEvent event) {

        TrocaTela.trocarTela(
                event,
                "/fxml/TelaInicial.fxml",
                facade
        );

    }
}
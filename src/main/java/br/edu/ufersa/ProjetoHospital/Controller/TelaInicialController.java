package br.edu.ufersa.ProjetoHospital.Controller;

import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.event.ActionEvent;

public class TelaInicialController {


    @FXML
    private void irParaLogin(ActionEvent event) {
        trocarTela(event, "/fxml/TelaLogin.fxml");
    }

    @FXML
    private void irParaPaciente(ActionEvent event) {
        trocarTela(event, "/fxml/TelaPaciente.fxml");
    }

    private void trocarTela(ActionEvent event, String fxml) {
        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(
                    getClass().getResource(fxml)
            );

            javafx.scene.Scene scene = new javafx.scene.Scene(loader.load());

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
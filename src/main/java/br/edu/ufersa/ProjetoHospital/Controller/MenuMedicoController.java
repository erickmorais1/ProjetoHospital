package br.edu.ufersa.ProjetoHospital.Controller;

import br.edu.ufersa.ProjetoHospital.Facade.HospitalFacade;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MenuMedicoController {

    @FXML
    public void abrirCadastrarConsulta(javafx.event.ActionEvent actionEvent) {

        try {

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/fxml/TelaCadastrarConsulta.fxml")
            );

            Scene scene = new Scene(loader.load());

            Stage stage = (Stage) ((Node) actionEvent.getSource())
                    .getScene()
                    .getWindow();

            stage.setScene(scene);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private HospitalFacade facade;

    public void setFacade(HospitalFacade facade) {
        this.facade = facade;
    }

    @FXML
    public void abrirListarConsultas(javafx.event.ActionEvent actionEvent) {

        try {

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/fxml/TelaListarConsultas.fxml")
            );

            Scene scene = new Scene(loader.load());
            ListarConsultasController controller =
                    loader.getController();

            controller.setFacade(facade);

            Stage stage = (Stage) ((Node) actionEvent.getSource())
                    .getScene()
                    .getWindow();

            stage.setScene(scene);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

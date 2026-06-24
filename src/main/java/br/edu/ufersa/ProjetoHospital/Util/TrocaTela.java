package br.edu.ufersa.ProjetoHospital.Util;

import br.edu.ufersa.ProjetoHospital.Controller.CadastrarConsultaController;
import br.edu.ufersa.ProjetoHospital.Controller.FacadeController;
import br.edu.ufersa.ProjetoHospital.Controller.ListarConsultasController;
import br.edu.ufersa.ProjetoHospital.Controller.MenuMedicoController;
import br.edu.ufersa.ProjetoHospital.Facade.HospitalFacade;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

public class TrocaTela {

    public static void trocarTela(
            ActionEvent event,
            String caminhoFXML,
            HospitalFacade facade) {

        try {

            FXMLLoader loader = new FXMLLoader(
                    TrocaTela.class.getResource(caminhoFXML));

            Scene scene = new Scene(loader.load());

            Object controller = loader.getController();

            if (controller instanceof FacadeController) {
                ((FacadeController) controller).setFacade(facade);
            }

            Stage stage = (Stage) ((Node) event.getSource())
                    .getScene()
                    .getWindow();

            boolean telaCheia = stage.isFullScreen();

            stage.setScene(scene);
            Platform.runLater(() -> {
                stage.setFullScreenExitHint("");
                stage.setFullScreen(true);
            });

            stage.setFullScreen(telaCheia);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
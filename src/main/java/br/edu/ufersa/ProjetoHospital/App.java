package br.edu.ufersa.ProjetoHospital;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/fxml/TelaLogin.fxml")
        );

        Scene scene = new Scene(loader.load());

        stage.setTitle("Sistema Hospitalar");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
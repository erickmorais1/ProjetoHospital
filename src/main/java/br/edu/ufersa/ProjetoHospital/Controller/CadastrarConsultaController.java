package br.edu.ufersa.ProjetoHospital.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class CadastrarConsultaController {
    @FXML
    private TextField txtIdConsulta;

    @FXML
    private DatePicker dpDataConsulta;

    @FXML
    private Button btnCadastrar;

    @FXML
    private Button btnVoltar;

    @FXML
    private void cadastrarConsulta() {

        String id = txtIdConsulta.getText();

        System.out.println("ID digitado: " + id);

        System.out.println("Data escolhida: " + dpDataConsulta.getValue());
    }
}

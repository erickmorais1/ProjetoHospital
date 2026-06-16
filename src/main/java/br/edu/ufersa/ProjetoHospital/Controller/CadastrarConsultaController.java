package br.edu.ufersa.ProjetoHospital.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class CadastrarConsultaController {

    @FXML
    private DatePicker dpDataConsulta;

    @FXML
    private Button btnCadastrar;

    @FXML
    private Button btnVoltar;

    @FXML
    private TextField txtCpfPaciente;

    @FXML
    private void cadastrarConsulta() {
        System.out.println("Data escolhida: " + dpDataConsulta.getValue()) ;
    }
}

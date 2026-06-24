package br.edu.ufersa.ProjetoHospital.Controller;

import br.edu.ufersa.ProjetoHospital.Facade.HospitalFacade;
import br.edu.ufersa.ProjetoHospital.Util.TrocaTela;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class RemoverConsultaController implements FacadeController {

    private HospitalFacade facade;

    @FXML
    private TextField txtId;

    @FXML
    private Label lblMensagem;

    @Override
    public void setFacade(HospitalFacade facade) {
        this.facade = facade;
    }

    @FXML
    public void removerConsulta() {
        try {
            int id = Integer.parseInt(txtId.getText());
            facade.removerConsulta(id);

            lblMensagem.setStyle("-fx-text-fill: green;");
            lblMensagem.setText("Consulta removida com sucesso!");

            mostrarAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Consulta removida com sucesso!");

        } catch (Exception e) {
            lblMensagem.setStyle("-fx-text-fill: red;");
            lblMensagem.setText("Erro ao remover consulta.");

            mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Não foi possível remover a consulta.");
        }
    }

    @FXML
    public void voltar(ActionEvent event) {
        TrocaTela.trocarTela(event, "/fxml/TelaMenuMedico.fxml", facade);
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensagem) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }
}

package br.edu.ufersa.ProjetoHospital.Controller;

import br.edu.ufersa.ProjetoHospital.Facade.HospitalFacade;
import br.edu.ufersa.ProjetoHospital.Util.TrocaTela;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ObservacaoConsultaController
        implements FacadeController {

    private HospitalFacade facade;

    @FXML
    private TextField txtId;

    @FXML
    private Label lblMensagem;

    @FXML
    private TextArea txtObservacao;

    @Override
    public void setFacade(HospitalFacade facade) {
        this.facade = facade;
    }

    @FXML
    public void salvarObservacao() {

        try {

            int id = Integer.parseInt(txtId.getText());

            facade.adicionarObservacao(id, txtObservacao.getText());

            lblMensagem.setStyle("-fx-text-fill: green;");
            lblMensagem.setText("Observação adicionada com sucesso!");

        } catch (Exception e) {

            lblMensagem.setStyle("-fx-text-fill: red;");
            lblMensagem.setText("Erro ao adicionar observação.");

        }
    }

    @FXML
    public void voltar(ActionEvent event) {

        TrocaTela.trocarTela(
                event,
                "/fxml/TelaMenuMedico.fxml",
                facade);

    }

}
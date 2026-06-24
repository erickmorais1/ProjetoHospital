package br.edu.ufersa.ProjetoHospital.Controller;

import br.edu.ufersa.ProjetoHospital.Facade.HospitalFacade;
import br.edu.ufersa.ProjetoHospital.Util.TrocaTela;
import br.edu.ufersa.ProjetoHospital.model.entities.Medico;
import br.edu.ufersa.ProjetoHospital.model.entities.Paciente;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;

public class CadastrarConsultaController implements FacadeController {

    @FXML private DatePicker dpDataConsulta;
    @FXML private TextField txtCpfPaciente;
    @FXML private TextField txtCrmMedico;
    @FXML private Label lblMensagem;
    @FXML private Button btnCadastrar;
    @FXML private Button btnVoltar;

    private HospitalFacade facade;

    @Override
    public void setFacade(HospitalFacade facade) {
        this.facade = facade;
        System.out.println(getClass().getSimpleName() + " recebeu facade: " + (facade != null));
    }

    @FXML
    private void cadastrarConsulta() {
        String cpf = txtCpfPaciente.getText().trim();
        String crm = txtCrmMedico.getText().trim();

        if (cpf.isBlank() || crm.isBlank() || dpDataConsulta.getValue() == null) {
            erro("Preencha todos os campos antes de cadastrar.");
            return;
        }

        try {
            Paciente paciente = facade.buscarPacientePorCpf(cpf);
            if (paciente == null) {
                erro("Paciente não encontrado para o CPF informado.");
                return;
            }

            Medico medico = facade.buscarMedicoPorCrm(crm);
            if (medico == null) {
                erro("Médico não encontrado para o CRM informado.");
                return;
            }

            facade.agendarConsulta(paciente, medico, dpDataConsulta.getValue());

            sucesso("Consulta cadastrada com sucesso!");
            mostrarAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Consulta cadastrada com sucesso!");
            limpar();

        } catch (Exception e) {
            erro("Erro ao cadastrar consulta.");
            mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Não foi possível cadastrar a consulta.");
            e.printStackTrace();
        }
    }

    @FXML
    public void Voltar(ActionEvent event) {
        TrocaTela.trocarTela(event, "/fxml/TelaMenuMedico.fxml", facade);
    }

    private void sucesso(String msg) {
        lblMensagem.setStyle("-fx-text-fill: green;");
        lblMensagem.setText(msg);
    }

    private void erro(String msg) {
        lblMensagem.setStyle("-fx-text-fill: red;");
        lblMensagem.setText(msg);
    }

    private void limpar() {
        txtCpfPaciente.clear();
        txtCrmMedico.clear();
        dpDataConsulta.setValue(null);
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensagem) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }
}
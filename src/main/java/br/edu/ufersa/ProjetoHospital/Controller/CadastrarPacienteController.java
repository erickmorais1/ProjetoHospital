package br.edu.ufersa.ProjetoHospital.Controller;

import br.edu.ufersa.ProjetoHospital.Facade.HospitalFacade;
import br.edu.ufersa.ProjetoHospital.Service.PacienteService;
import br.edu.ufersa.ProjetoHospital.Util.TrocaTela;
import br.edu.ufersa.ProjetoHospital.model.entities.Endereco;
import br.edu.ufersa.ProjetoHospital.model.entities.Paciente;
import br.edu.ufersa.ProjetoHospital.model.entities.Prontuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.time.LocalDate;

public class CadastrarPacienteController implements FacadeController {

    @FXML private TextField txtCpf;
    @FXML private TextField txtNome;
    @FXML private TextField txtRua;
    @FXML private DatePicker dpData;
    @FXML private TextArea  txtObservacoes;
    @FXML private Label     lblMensagem;

    private HospitalFacade facade;

    @Override
    public void setFacade(HospitalFacade facade) {
        this.facade = facade;
        System.out.println(getClass().getSimpleName() + " recebeu facade: " + (facade != null));
    }

    @FXML
    public void initialize() {
        dpData.setValue(LocalDate.now());
    }

    @FXML
    private void salvarPaciente(ActionEvent event) {
        String cpf  = txtCpf.getText().trim();
        String nome = txtNome.getText().trim();

        if (cpf.isBlank() || nome.isBlank()) {
            erro("CPF e Nome são obrigatórios.");
            mostrarAlerta(Alert.AlertType.WARNING, "Campos obrigatórios", "CPF e Nome são obrigatórios.");
            return;
        }

        if (dpData.getValue() == null) {
            erro("Informe a data de abertura do prontuário.");
            mostrarAlerta(Alert.AlertType.WARNING, "Campo obrigatório", "Informe a data de abertura do prontuário.");
            return;
        }

        Endereco endereco = null;
        String rua = txtRua.getText().trim();
        if (!rua.isBlank()) {
            endereco = new Endereco();
            endereco.setRua(rua);
        }

        Prontuario prontuario = new Prontuario(dpData.getValue());
        String obs = txtObservacoes.getText().trim();
        if (!obs.isBlank()) {
            prontuario.setObservacoes(obs);
        }

        Paciente paciente = new Paciente(nome, endereco, cpf, prontuario);

        try {
            facade.adicionarPaciente(paciente);
            sucesso("Paciente cadastrado com sucesso!");
            mostrarAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Paciente cadastrado com sucesso!");
            limparFormulario();
        } catch (PacienteService.ServicoException e) {
            erro(e.getMessage());
            mostrarAlerta(Alert.AlertType.ERROR, "Erro ao cadastrar", e.getMessage());
        } catch (IllegalArgumentException e) {
            erro("Dados inválidos: " + e.getMessage());
            mostrarAlerta(Alert.AlertType.WARNING, "Dados inválidos", e.getMessage());
        }
    }

    @FXML
    private void voltar(ActionEvent event) {
        TrocaTela.trocarTela(event, "/fxml/TelaPaciente.fxml", facade);
    }

    private void sucesso(String msg) {
        lblMensagem.setStyle("-fx-text-fill: green;");
        lblMensagem.setText(msg);
    }

    private void erro(String msg) {
        lblMensagem.setStyle("-fx-text-fill: red;");
        lblMensagem.setText(msg);
    }

    private void limparFormulario() {
        txtCpf.clear();
        txtNome.clear();
        txtRua.clear();
        txtObservacoes.clear();
        dpData.setValue(LocalDate.now());
        lblMensagem.setText("");
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensagem) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }
}

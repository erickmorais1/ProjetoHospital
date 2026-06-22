package br.edu.ufersa.ProjetoHospital.Controller;

import br.edu.ufersa.ProjetoHospital.Facade.HospitalFacade;
import br.edu.ufersa.ProjetoHospital.Service.PacienteService;
import br.edu.ufersa.ProjetoHospital.Util.TrocaTela;
import br.edu.ufersa.ProjetoHospital.model.entities.Endereco;
import br.edu.ufersa.ProjetoHospital.model.entities.Paciente;
import br.edu.ufersa.ProjetoHospital.model.entities.Prontuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.time.LocalDate;

public class CadastrarPacienteController implements FacadeController {

    // ── Campos FXML ──────────────────────────────────────────────────────────
    // CORREÇÃO Bug 2: removido "public TextArea txtObs" sem @FXML que estava
    // duplicado e causava NullPointerException em txtObservacoes.getText().
    // Todos os campos agora são @FXML private, nomes idênticos ao fx:id do FXML.

    @FXML private TextField txtCpf;
    @FXML private TextField txtNome;
    @FXML private TextField txtRua;
    @FXML private DatePicker dpData;           // CORREÇÃO Bug 3: agora presente no FXML
    @FXML private TextArea  txtObservacoes;    // CORREÇÃO Bug 2: nome único, alinhado ao FXML
    @FXML private Label     lblMensagem;       // CORREÇÃO Bug 4: agora presente no FXML

    // ── Dependência ──────────────────────────────────────────────────────────

    private HospitalFacade facade;

    @Override
    public void setFacade(HospitalFacade facade) {
        this.facade = facade;
        System.out.println(getClass().getSimpleName()
                + " recebeu facade: " + (facade != null));
    }

    // ── Inicialização FXML ───────────────────────────────────────────────────

    @FXML
    public void initialize() {
        dpData.setValue(LocalDate.now());   // seguro: dpData agora existe no FXML
    }

    // ── Handlers ─────────────────────────────────────────────────────────────

    @FXML
    private void salvarPaciente(ActionEvent event) {

        String cpf  = txtCpf.getText().trim();
        String nome = txtNome.getText().trim();

        if (cpf.isBlank() || nome.isBlank()) {
            erro("CPF e Nome são obrigatórios.");
            return;
        }

        if (dpData.getValue() == null) {
            erro("Informe a data de abertura do prontuário.");
            return;
        }

        // Endereço é opcional
        Endereco endereco = null;
        String rua = txtRua.getText().trim();
        if (!rua.isBlank()) {
            endereco = new Endereco();
            endereco.setRua(rua);
        }

        // Prontuário — observações são opcionais; construtor já define "Sem observações."
        Prontuario prontuario = new Prontuario(dpData.getValue());
        String obs = txtObservacoes.getText().trim();   // seguro: campo único e @FXML
        if (!obs.isBlank()) {
            prontuario.setObservacoes(obs);
        }

        Paciente paciente = new Paciente(nome, endereco, cpf, prontuario);

        try {
            facade.adicionarPaciente(paciente);
            sucesso("Paciente cadastrado com sucesso!");
            limparFormulario();
        } catch (PacienteService.ServicoException e) {
            erro(e.getMessage());
        } catch (IllegalArgumentException e) {
            erro("Dados inválidos: " + e.getMessage());
        }
    }

    @FXML
    private void voltar(ActionEvent event) {
        TrocaTela.trocarTela(event, "/fxml/TelaPaciente.fxml", facade);
    }

    // ── Helpers ──────────────────────────────────────────────────────────────

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
}

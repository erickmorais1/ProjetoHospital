package br.edu.ufersa.ProjetoHospital.Controller;

import br.edu.ufersa.ProjetoHospital.Facade.HospitalFacade;
import br.edu.ufersa.ProjetoHospital.Util.SessaoGerente;
import br.edu.ufersa.ProjetoHospital.Util.TrocaTela;
import br.edu.ufersa.ProjetoHospital.model.entities.Gerente;
import br.edu.ufersa.ProjetoHospital.model.entities.Medico;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import java.sql.SQLException;

public class CadastrarMedicoController implements FacadeController {

    @FXML private TextField txtNomeMedico;
    @FXML private TextField txtCpfMedico;
    @FXML private TextField txtCrmMedico;
    @FXML private TextField txtValorConsulta;
    @FXML private Button btnCadastrar;

    private HospitalFacade facade;
    private Medico medicoEmEdicao;

    public void carregarMedicoParaEdicao(Medico medico) {
        this.medicoEmEdicao = medico;
        txtNomeMedico.setText(medico.getNome());
        txtCpfMedico.setText(medico.getCpf());
        txtCrmMedico.setText(medico.getCrm());
        txtValorConsulta.setText(String.valueOf(medico.getValorConsulta()));
        // CRM é chave, não pode mudar
        txtCrmMedico.setEditable(false);
        btnCadastrar.setText("Salvar Alterações");
    }

    @FXML
    private void cadastrarMedico() {
        if (medicoEmEdicao != null) {
            salvarEdicaoMedico();
        } else {
            cadastrarNovoMedico();
        }
    }

    private void cadastrarNovoMedico() {
        try {
            if (!validarCampos()) {
                mostrarAlerta("Erro de Validação",
                        "Por favor, preencha todos os campos obrigatórios.",
                        Alert.AlertType.WARNING);
                return;
            }

            Medico medico = new Medico(
                    txtNomeMedico.getText().trim(),
                    txtCpfMedico.getText().trim(),
                    null,
                    txtCrmMedico.getText().trim(),
                    Double.parseDouble(txtValorConsulta.getText().trim())
            );

            Gerente gerente = SessaoGerente.getGerenteLogado();
            facade.addMedico(gerente, medico);

            mostrarAlerta("Sucesso", "Médico cadastrado com sucesso!", Alert.AlertType.INFORMATION);
            limparCampos();

        } catch (NumberFormatException e) {
            mostrarAlerta("Erro de Formato",
                    "O valor da consulta deve ser um número válido.",
                    Alert.AlertType.ERROR);
        } catch (SQLException e) {
            mostrarAlerta("Erro de Banco de Dados",
                    "Erro ao cadastrar médico: " + e.getMessage(),
                    Alert.AlertType.ERROR);
            e.printStackTrace();
        } catch (Exception e) {
            mostrarAlerta("Erro",
                    "Erro inesperado: " + e.getMessage(),
                    Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    private void salvarEdicaoMedico() {
        try {
            if (!validarCampos()) {
                mostrarAlerta("Erro de Validação",
                        "Por favor, preencha todos os campos obrigatórios.",
                        Alert.AlertType.WARNING);
                return;
            }

            medicoEmEdicao.setNome(txtNomeMedico.getText().trim());
            medicoEmEdicao.setCpf(txtCpfMedico.getText().trim());
            medicoEmEdicao.setValorConsulta(Double.parseDouble(txtValorConsulta.getText().trim()));

            Gerente gerente = SessaoGerente.getGerenteLogado();
            facade.atualizarMedico(gerente, medicoEmEdicao);

            mostrarAlerta("Sucesso", "Médico atualizado com sucesso!", Alert.AlertType.INFORMATION);
            voltarParaListaMedicos();

        } catch (NumberFormatException e) {
            mostrarAlerta("Erro de Formato",
                    "O valor da consulta deve ser um número válido.",
                    Alert.AlertType.ERROR);
        } catch (SQLException e) {
            mostrarAlerta("Erro de Banco de Dados",
                    "Erro ao atualizar médico: " + e.getMessage(),
                    Alert.AlertType.ERROR);
            e.printStackTrace();
        } catch (Exception e) {
            mostrarAlerta("Erro",
                    "Erro inesperado: " + e.getMessage(),
                    Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    private boolean validarCampos() {
        return !txtNomeMedico.getText().isBlank() &&
                !txtCpfMedico.getText().isBlank() &&
                !txtCrmMedico.getText().isBlank() &&
                !txtValorConsulta.getText().isBlank();
    }

    private void limparCampos() {
        txtNomeMedico.clear();
        txtCpfMedico.clear();
        txtCrmMedico.clear();
        txtValorConsulta.clear();
    }

    private void mostrarAlerta(String titulo, String mensagem, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }

    @FXML
    public void voltar(ActionEvent event) {
        if (medicoEmEdicao != null) {
            TrocaTela.trocarTela(event, "/fxml/TelaListarMedicos.fxml", facade);
        } else {
            TrocaTela.trocarTela(event, "/fxml/TelaMenuGerente.fxml", facade);
        }
    }

    private void voltarParaListaMedicos() {
        Stage stage = (Stage) btnCadastrar.getScene().getWindow();
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/fxml/TelaListarMedicos.fxml"));
            Scene scene = new Scene(loader.load());
            Object controller = loader.getController();
            if (controller instanceof FacadeController) {
                ((FacadeController) controller).setFacade(facade);
            }
            boolean telaCheia = stage.isFullScreen();
            stage.setScene(scene);
            stage.setFullScreen(telaCheia);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setFacade(HospitalFacade facade) {
        this.facade = facade;
        System.out.println(getClass().getSimpleName() + " recebeu facade: " + (facade != null));
    }
}
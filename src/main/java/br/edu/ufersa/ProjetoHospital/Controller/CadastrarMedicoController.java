package br.edu.ufersa.ProjetoHospital.Controller;

import br.edu.ufersa.ProjetoHospital.Facade.HospitalFacade;
import br.edu.ufersa.ProjetoHospital.Util.SessaoGerente;
import br.edu.ufersa.ProjetoHospital.Util.TrocaTela;
import br.edu.ufersa.ProjetoHospital.model.entities.Endereco;
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

    @FXML
    private TextField txtNomeMedico;

    @FXML
    private TextField txtCpfMedico;

    @FXML
    private TextField txtCrmMedico;

    @FXML
    private TextField txtValorConsulta;

    @FXML
    private TextField txtRuaEndereco;

    @FXML
    private TextField txtCidadeEndereco;

    @FXML
    private TextField txtBairroEndereco;

    @FXML
    private TextField txtCepEndereco;

    @FXML
    private TextField txtNumeroEndereco;

    @FXML
    private TextField txtComplementoEndereco;

    @FXML
    private Button btnCadastrar;

    private HospitalFacade facade;

    // quando essa tela é aberta pra editar um médico já existente, guardamos
    // o médico aqui. Se for null, a tela continua em modo cadastro normal.
    private Medico medicoEmEdicao;

    public void carregarMedicoParaEdicao(Medico medico) {
        this.medicoEmEdicao = medico;

        txtNomeMedico.setText(medico.getNome());
        txtCpfMedico.setText(medico.getCpf());
        txtCrmMedico.setText(medico.getCrm());
        txtValorConsulta.setText(String.valueOf(medico.getValorConsulta()));

        // CRM é a chave usada pra localizar o médico no banco, não pode mudar
        txtCrmMedico.setEditable(false);

        if (medico.getEndereco() != null) {
            Endereco endereco = medico.getEndereco();
            txtRuaEndereco.setText(endereco.getRua() != null ? endereco.getRua() : "");
            txtCidadeEndereco.setText(endereco.getCidade() != null ? endereco.getCidade() : "");
            txtBairroEndereco.setText(endereco.getBairro() != null ? endereco.getBairro() : "");
            txtCepEndereco.setText(endereco.getCep() != null ? endereco.getCep() : "");
            if (endereco.getNumero() > 0) {
                txtNumeroEndereco.setText(String.valueOf(endereco.getNumero()));
            }
            txtComplementoEndereco.setText(endereco.getComplemento() != null ? endereco.getComplemento() : "");
        }

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

            Endereco endereco = criarEndereco();

            Medico medico = new Medico(
                    txtNomeMedico.getText().trim(),
                    txtCpfMedico.getText().trim(),
                    endereco,
                    txtCrmMedico.getText().trim(),
                    Double.parseDouble(txtValorConsulta.getText().trim())
            );

            Gerente gerente = SessaoGerente.getGerenteLogado();

            facade.addMedico(gerente, medico);

            mostrarAlerta("Sucesso",
                    "Médico cadastrado com sucesso!",
                    Alert.AlertType.INFORMATION);

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
            mostrarAlerta("Erro Geral",
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

            Endereco endereco = criarEndereco();

            medicoEmEdicao.setNome(txtNomeMedico.getText().trim());
            medicoEmEdicao.setCpf(txtCpfMedico.getText().trim());
            medicoEmEdicao.setValorConsulta(Double.parseDouble(txtValorConsulta.getText().trim()));
            medicoEmEdicao.setEndereco(endereco);

            Gerente gerente = SessaoGerente.getGerenteLogado();

            facade.atualizarMedico(gerente, medicoEmEdicao);

            mostrarAlerta("Sucesso",
                    "Médico atualizado com sucesso!",
                    Alert.AlertType.INFORMATION);

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
            mostrarAlerta("Erro Geral",
                    "Erro inesperado: " + e.getMessage(),
                    Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    private boolean validarCampos() {
        return !txtNomeMedico.getText().isBlank() &&
                !txtCpfMedico.getText().isBlank() &&
                !txtCrmMedico.getText().isBlank() &&
                !txtValorConsulta.getText().isBlank() &&
                !txtRuaEndereco.getText().isBlank() &&
                !txtCidadeEndereco.getText().isBlank();
    }

    private Endereco criarEndereco() {
        Endereco endereco = new Endereco();
        endereco.setRua(txtRuaEndereco.getText().trim());
        endereco.setCidade(txtCidadeEndereco.getText().trim());

        if (!txtBairroEndereco.getText().isBlank()) {
            endereco.setBairro(txtBairroEndereco.getText().trim());
        }

        if (!txtCepEndereco.getText().isBlank()) {
            endereco.setCep(txtCepEndereco.getText().trim());
        }

        if (!txtNumeroEndereco.getText().isBlank()) {
            try {
                endereco.setNumero(Integer.parseInt(txtNumeroEndereco.getText().trim()));
            } catch (NumberFormatException e) {
                System.out.println("Número do endereço inválido");
            }
        }

        if (!txtComplementoEndereco.getText().isBlank()) {
            endereco.setComplemento(txtComplementoEndereco.getText().trim());
        }

        return endereco;
    }

    private void limparCampos() {
        txtNomeMedico.clear();
        txtCpfMedico.clear();
        txtCrmMedico.clear();
        txtValorConsulta.clear();
        txtRuaEndereco.clear();
        txtCidadeEndereco.clear();
        txtBairroEndereco.clear();
        txtCepEndereco.clear();
        txtNumeroEndereco.clear();
        txtComplementoEndereco.clear();
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
        System.out.println("Voltando ao menu do gerente");
        if (medicoEmEdicao != null) {
            TrocaTela.trocarTela(event, "/fxml/TelaListarMedicos.fxml", facade);
        } else {
            TrocaTela.trocarTela(event, "/fxml/TelaMenuGerente.fxml", facade);
        }
    }

    private void voltarParaListaMedicos() {
        // reaproveita a janela atual, sem precisar de um ActionEvent
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
        System.out.println(getClass().getSimpleName()
                + " recebeu facade: " + (facade != null));
    }
}
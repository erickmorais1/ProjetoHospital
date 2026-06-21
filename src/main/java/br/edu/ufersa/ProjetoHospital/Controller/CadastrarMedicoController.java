package br.edu.ufersa.ProjetoHospital.Controller;

import br.edu.ufersa.ProjetoHospital.Facade.HospitalFacade;
import br.edu.ufersa.ProjetoHospital.Util.TrocaTela;
import br.edu.ufersa.ProjetoHospital.model.entities.Endereco;
import br.edu.ufersa.ProjetoHospital.model.entities.Gerente;
import br.edu.ufersa.ProjetoHospital.model.entities.Medico;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
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

    private HospitalFacade facade;

    @FXML
    private void cadastrarMedico() {
        try {
            // Validações
            if (!validarCampos()) {
                mostrarAlerta("Erro de Validação",
                        "Por favor, preencha todos os campos obrigatórios.",
                        Alert.AlertType.WARNING);
                return;
            }

            // Criar endereço
            Endereco endereco = criarEndereco();

            // Criar médico
            Medico medico = new Medico(
                    txtNomeMedico.getText().trim(),
                    txtCpfMedico.getText().trim(),
                    endereco,
                    txtCrmMedico.getText().trim(),
                    Double.parseDouble(txtValorConsulta.getText().trim())
            );

            // Criar gerente (necessário para adicionar médico)
            // TODO: Implementar autenticação de gerente real
            Gerente gerente = new Gerente("Gerente Sistema", "00000000000",
                    new Endereco(), "ADMIN");

            // Adicionar médico via facade
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
        TrocaTela.trocarTela(event, "/fxml/TelaMenuGerente.fxml", facade);
    }

    @Override
    public void setFacade(HospitalFacade facade) {
        this.facade = facade;
        System.out.println(getClass().getSimpleName()
                + " recebeu facade: " + (facade != null));
    }
}

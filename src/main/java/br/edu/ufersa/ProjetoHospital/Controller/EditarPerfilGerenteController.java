package br.edu.ufersa.ProjetoHospital.Controller;

import br.edu.ufersa.ProjetoHospital.Facade.HospitalFacade;
import br.edu.ufersa.ProjetoHospital.Util.TrocaTela;
import br.edu.ufersa.ProjetoHospital.model.entities.Endereco;
import br.edu.ufersa.ProjetoHospital.model.entities.Gerente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

// Controller para edição de perfil do gerente.
// Permite editar dados pessoais e endereço
public class EditarPerfilGerenteController implements FacadeController {

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtCpf;

    @FXML
    private TextField txtRua;

    @FXML
    private TextField txtBairro;

    @FXML
    private TextField txtCidade;

    @FXML
    private TextField txtCep;

    @FXML
    private TextField txtNumero;

    @FXML
    private TextField txtComplemento;

    private HospitalFacade facade;
    private Gerente gerenteAtual;

    @Override
    public void setFacade(HospitalFacade facade) {
        this.facade = facade;
        System.out.println("EditarPerfilGerenteController recebeu facade");
        carregarDadosGerente();
    }

    @FXML
    public void initialize() {
        // CPF como read-only
        txtCpf.setEditable(false);
    }

    // Carrega os dados do gerente atual
    private void carregarDadosGerente() {
        System.out.println("Carregando dados do gerente...");

        gerenteAtual = facade.obterGerenteAtual();

        if (gerenteAtual != null) {
            preencherCampos();
        } else {
            mostrarAlerta("Erro", "Não foi possível carregar dados do gerente.",
                    Alert.AlertType.ERROR);
        }
    }

    // Preenche os campos com os dados do gerente
    private void preencherCampos() {
        txtNome.setText(gerenteAtual.getNome() != null ? gerenteAtual.getNome() : "");
        txtCpf.setText(gerenteAtual.getCpf() != null ? gerenteAtual.getCpf() : "");

        if (gerenteAtual.getEndereco() != null) {
            Endereco endereco = gerenteAtual.getEndereco();
            txtRua.setText(endereco.getRua() != null ? endereco.getRua() : "");
            txtBairro.setText(endereco.getBairro() != null ? endereco.getBairro() : "");
            txtCidade.setText(endereco.getCidade() != null ? endereco.getCidade() : "");
            txtCep.setText(endereco.getCep() != null ? endereco.getCep() : "");
            if (endereco.getNumero() > 0) {
                txtNumero.setText(String.valueOf(endereco.getNumero()));
            }
            txtComplemento.setText(endereco.getComplemento() != null ?
                    endereco.getComplemento() : "");
        }

        System.out.println("Campos preenchidos com sucesso");
    }

    // Salva as alterações de perfil
    @FXML
    private void salvarAlteracoes() {
        try {
            if (!validarCamposObrigatorios()) {
                mostrarAlerta("Erro de Validação",
                        "Por favor, preencha os campos obrigatórios (Nome, Rua, Cidade).",
                        Alert.AlertType.WARNING);
                return;
            }

            // Atualizar dados do gerente
            gerenteAtual.setNome(txtNome.getText().trim());

            // Atualizar endereço
            Endereco endereco = new Endereco();
            endereco.setRua(txtRua.getText().trim());
            endereco.setBairro(txtBairro.getText().trim());
            endereco.setCidade(txtCidade.getText().trim());
            endereco.setCep(txtCep.getText().trim());

            if (!txtNumero.getText().isBlank()) {
                try {
                    endereco.setNumero(Integer.parseInt(txtNumero.getText().trim()));
                } catch (NumberFormatException e) {
                    mostrarAlerta("Erro", "Número do endereço inválido", Alert.AlertType.ERROR);
                    return;
                }
            }

            if (!txtComplemento.getText().isBlank()) {
                endereco.setComplemento(txtComplemento.getText().trim());
            }

            gerenteAtual.setEndereco(endereco);

            facade.atualizarGerente(gerenteAtual);

            System.out.println("Alterações salvas com sucesso");

            mostrarAlerta("Sucesso",
                    "Perfil atualizado com sucesso!",
                    Alert.AlertType.INFORMATION);

        } catch (Exception e) {
            mostrarAlerta("Erro",
                    "Erro ao salvar alterações: " + e.getMessage(),
                    Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    @FXML
    public void cancelar(ActionEvent event) {
        System.out.println("Cancelando edição de perfil");
        TrocaTela.trocarTela(event, "/fxml/TelaMenuGerente.fxml", facade);
    }

    // Valida campos obrigatórios
    private boolean validarCamposObrigatorios() {
        return !txtNome.getText().isBlank() &&
                !txtRua.getText().isBlank() &&
                !txtCidade.getText().isBlank();
    }

    // Exibe alerta ao usuário
    private void mostrarAlerta(String titulo, String mensagem, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }
}
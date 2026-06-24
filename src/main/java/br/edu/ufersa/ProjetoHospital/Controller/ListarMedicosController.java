package br.edu.ufersa.ProjetoHospital.Controller;

import br.edu.ufersa.ProjetoHospital.Facade.HospitalFacade;
import br.edu.ufersa.ProjetoHospital.Util.SessaoGerente;
import br.edu.ufersa.ProjetoHospital.Util.TrocaTela;
import br.edu.ufersa.ProjetoHospital.model.entities.Gerente;
import br.edu.ufersa.ProjetoHospital.model.entities.Medico;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ListarMedicosController implements FacadeController {

    private HospitalFacade facade;

    @FXML
    private TableView<Medico> tabelaMedicos;

    @FXML
    private TableColumn<Medico, String> colNome;

    @FXML
    private TableColumn<Medico, String> colCpf;

    @FXML
    private TableColumn<Medico, String> colCrm;

    @FXML
    private TableColumn<Medico, Double> colValorConsulta;

    @FXML
    private Button btnRemover;

    @FXML
    private Button btnAtualizar;

    @FXML
    private Label lblTotalMedicos;

    public void setFacade(HospitalFacade facade) {
        this.facade = facade;
        System.out.println("ListarMedicosController recebeu facade");
        inicializarTabela();
        carregarMedicos();
    }

    @FXML
    public void initialize() {
        // Configurar colunas da tabela
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        colCrm.setCellValueFactory(new PropertyValueFactory<>("crm"));
        colValorConsulta.setCellValueFactory(new PropertyValueFactory<>("valorConsulta"));

        // Desabilitar botões até selecionar uma linha
        btnRemover.setDisable(true);
        btnAtualizar.setDisable(true);

        // Listener para habilitar botões quando selecionar um médico
        tabelaMedicos.getSelectionModel().selectedItemProperty()
                .addListener((obs, oldVal, newVal) -> {
                    boolean temSelecao = newVal != null;
                    btnRemover.setDisable(!temSelecao);
                    btnAtualizar.setDisable(!temSelecao);
                });
    }

    private void inicializarTabela() {
        System.out.println("Inicializando tabela de médicos");
    }

    public void carregarMedicos() {
        System.out.println("Carregando médicos...");
        try {
            List<Medico> medicos = facade.listarMedicos();
            System.out.println("Quantidade de médicos: " + medicos.size());

            for (Medico m : medicos) {
                System.out.println("Médico: " + m.getNome() + " - CRM: " + m.getCrm());
            }

            ObservableList<Medico> dados = FXCollections.observableArrayList(medicos);
            tabelaMedicos.setItems(dados);

            lblTotalMedicos.setText("Total de médicos: " + medicos.size());
            System.out.println("Tabela preenchida com sucesso");

        } catch (SQLException e) {
            System.err.println("Erro ao carregar médicos: " + e.getMessage());
            mostrarAlerta("Erro", "Erro ao carregar médicos: " + e.getMessage(),
                    Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    @FXML
    public void removerMedico(ActionEvent event) {
        Medico selecionado = tabelaMedicos.getSelectionModel().getSelectedItem();

        if (selecionado == null) {
            mostrarAlerta("Aviso", "Por favor, selecione um médico para remover.",
                    Alert.AlertType.WARNING);
            return;
        }

        // Confirmar exclusão
        Optional<ButtonType> resultado = mostrarConfirmacao(
                "Confirmação",
                "Tem certeza que deseja remover o médico: " + selecionado.getNome() + "?",
                "Esta ação não pode ser desfeita."
        );

        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            try {
                System.out.println("Removendo médico: " + selecionado.getCrm());

                Gerente gerente = SessaoGerente.getGerenteLogado();

                facade.removerMedico(gerente, selecionado.getCrm());

                mostrarAlerta("Sucesso", "Médico removido com sucesso!",
                        Alert.AlertType.INFORMATION);
                carregarMedicos(); // Recarregar tabela

            } catch (Exception e) {
                mostrarAlerta("Erro", "Erro ao remover médico: " + e.getMessage(),
                        Alert.AlertType.ERROR);
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void atualizarMedico(ActionEvent event) {
        Medico selecionado = tabelaMedicos.getSelectionModel().getSelectedItem();

        if (selecionado == null) {
            mostrarAlerta("Aviso", "Por favor, selecione um médico para atualizar.",
                    Alert.AlertType.WARNING);
            return;
        }

        System.out.println("Abrindo edição do médico: " + selecionado.getNome());

        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/fxml/TelaCadastrarMedico.fxml"));

            Scene scene = new Scene(loader.load());

            CadastrarMedicoController controller = loader.getController();
            controller.setFacade(facade);
            controller.carregarMedicoParaEdicao(selecionado);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            boolean telaCheia = stage.isFullScreen();

            stage.setScene(scene);
            stage.setFullScreen(telaCheia);

        } catch (Exception e) {
            mostrarAlerta("Erro", "Erro ao abrir tela de edição: " + e.getMessage(),
                    Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    @FXML
    public void voltar(ActionEvent event) {
        System.out.println("Voltando ao menu do gerente");
        TrocaTela.trocarTela(event, "/fxml/TelaMenuGerente.fxml", facade);
    }

    private void mostrarAlerta(String titulo, String mensagem, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }

    private Optional<ButtonType> mostrarConfirmacao(String titulo, String mensagem,
                                                    String conteudo) {
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(mensagem);
        alerta.setContentText(conteudo);
        return alerta.showAndWait();
    }
}
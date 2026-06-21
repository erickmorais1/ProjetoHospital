package br.edu.ufersa.ProjetoHospital.Controller;

import br.edu.ufersa.ProjetoHospital.Facade.HospitalFacade;
import br.edu.ufersa.ProjetoHospital.Util.TrocaTela;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import br.edu.ufersa.ProjetoHospital.model.entities.Consulta;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class ListarConsultasController implements FacadeController{

    private HospitalFacade facade;

    @FXML
    private TableView<Consulta> tabelaConsultas;

    @FXML
    private TableColumn<Consulta, String> colObservacao;

    @FXML
    private TableColumn<Consulta, Integer> colId;

    @FXML
    private TableColumn<Consulta, String> colPaciente;

    @FXML
    private TableColumn<Consulta, String> colMedico;

    @FXML
    private TableColumn<Consulta, LocalDate> colData;

    @FXML
    private TableColumn<Consulta, String> colStatus;

    public void setFacade(HospitalFacade facade) {
        this.facade = facade;
        System.out.println("ListarConsultasController recebeu facade");
        carregarConsultas();
    }
    @FXML
    public void initialize() {

        colId.setCellValueFactory(
                new PropertyValueFactory<>("id"));

        colData.setCellValueFactory(
                new PropertyValueFactory<>("diaHora"));

        colStatus.setCellValueFactory(
                new PropertyValueFactory<>("status"));

        colObservacao.setCellValueFactory(
                new PropertyValueFactory<>("observacao"));

        colPaciente.setCellValueFactory(cellData ->
                new SimpleStringProperty(
                        cellData.getValue().getPaciente().getNome()));

        colMedico.setCellValueFactory(cellData ->
                new SimpleStringProperty(
                        cellData.getValue().getMedico().getNome()));
    }

    public void carregarConsultas() {
        System.out.println("Entrou em carregarConsultas()");
        try {

            List<Consulta> consultas =
                    facade.listarTodasAsConsultas();
            System.out.println("Quantidade de consultas: " + consultas.size());

            for (Consulta c : consultas) {
                System.out.println(c);
            }

            ObservableList<Consulta> dados =
                    FXCollections.observableArrayList(consultas);

            tabelaConsultas.setItems(dados);
            System.out.println("Tabela preenchida");

            System.out.println("Consultas encontradas: "
                    + consultas.size());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void Voltar(ActionEvent event) {
        System.out.println("Facade é null? " + (facade == null));
        TrocaTela.trocarTela(event, "/fxml/TelaMenuMedico.fxml",facade);
    }


}
package br.edu.ufersa.ProjetoHospital.Controller;

import br.edu.ufersa.ProjetoHospital.Facade.HospitalFacade;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import br.edu.ufersa.ProjetoHospital.model.entities.Consulta;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class ListarConsultasController {

    private HospitalFacade facade;

    @FXML
    private TableView<Consulta> tabelaConsultas;

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
        carregarConsultas();
    }


    public void carregarConsultas() {

        try {

            List<Consulta> consultas =
                    facade.listarTodasAsConsultas();
            ObservableList<Consulta> dados =
                    FXCollections.observableArrayList(consultas);

            tabelaConsultas.setItems(dados);
            colId.setCellValueFactory(
                    new PropertyValueFactory<>("id"));

            colData.setCellValueFactory(
                    new PropertyValueFactory<>("diaHora"));

            colStatus.setCellValueFactory(
                    new PropertyValueFactory<>("status"));

            System.out.println(
                    "Consultas encontradas: "
                            + consultas.size()
            );

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
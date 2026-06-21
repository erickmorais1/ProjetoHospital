package br.edu.ufersa.ProjetoHospital.Controller;

import br.edu.ufersa.ProjetoHospital.Facade.HospitalFacade;
import br.edu.ufersa.ProjetoHospital.Util.TrocaTela;
import br.edu.ufersa.ProjetoHospital.model.entities.Consulta;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.event.ActionEvent;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class BuscarConsultaController implements FacadeController {

    private HospitalFacade facade;

    @FXML
    private TextField txtId;

    @FXML
    private TableColumn<Consulta, String> colObservacao;

    @FXML
    private TextField txtCpf;

    @FXML
    private TextField txtCrm;

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

    @Override
    public void setFacade(HospitalFacade facade) {
        this.facade = facade;
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

        colPaciente.setCellValueFactory(cellData -> {
            Consulta c = cellData.getValue();

            if (c != null && c.getPaciente() != null) {
                return new SimpleStringProperty(c.getPaciente().getNome());
            }

            return new SimpleStringProperty("");
        });

        colMedico.setCellValueFactory(cellData -> {
            Consulta c = cellData.getValue();

            if (c != null && c.getMedico() != null) {
                return new SimpleStringProperty(c.getMedico().getNome());
            }

            return new SimpleStringProperty("");
        });
    }

    @FXML
    public void buscarPorId() {

        try {

            int id = Integer.parseInt(txtId.getText());

            Consulta consulta = facade.buscarConsulta(id);
            if (consulta != null) {
                System.out.println(consulta.getPaciente());
                System.out.println(consulta.getMedico());
            }

            if (consulta != null) {
                tabelaConsultas.setItems(
                        FXCollections.observableArrayList(consulta));
            } else {
                tabelaConsultas.getItems().clear();
                System.out.println("Consulta não encontrada.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void buscarPorCpf() {

        try {

            List<Consulta> consultas =
                    facade.listarConsultasPorCpf(txtCpf.getText());

            tabelaConsultas.setItems(
                    FXCollections.observableArrayList(consultas));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void buscarPorMedico() {

        try {

            List<Consulta> consultas =
                    facade.listarConsultasPorMedico(txtCrm.getText());

            tabelaConsultas.setItems(
                    FXCollections.observableArrayList(consultas));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void voltar(ActionEvent event) {

        TrocaTela.trocarTela(
                event,
                "/fxml/TelaMenuMedico.fxml",
                facade);

    }
}
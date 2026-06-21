package br.edu.ufersa.ProjetoHospital.Controller;

import br.edu.ufersa.ProjetoHospital.Facade.HospitalFacade;
import br.edu.ufersa.ProjetoHospital.Service.PacienteService;
import br.edu.ufersa.ProjetoHospital.Util.TrocaTela;
import br.edu.ufersa.ProjetoHospital.model.entities.Endereco;
import br.edu.ufersa.ProjetoHospital.model.entities.Medico;
import br.edu.ufersa.ProjetoHospital.model.entities.Paciente;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import java.sql.SQLException;


public class CadastrarConsultaController implements FacadeController {

    @FXML
    private DatePicker dpDataConsulta;

    private HospitalFacade facade;

    @FXML
    private Button btnCadastrar;

    @FXML
    private Button btnVoltar;

    @FXML
    private Label lblMensagem;

    @FXML
    private TextField txtCpfPaciente;

    @FXML
    private void cadastrarConsulta() {

        try {

            Paciente paciente =
                    facade.buscarPacientePorCpf(
                            txtCpfPaciente.getText()
                    );

            Endereco endereco = new Endereco();
            endereco.setRua("Rua do Médico");

            Medico medico = new Medico(
                    "João Silva",
                    "11111111111",
                    endereco,
                    "CRM12345",
                    200.0
            );

            facade.agendarConsulta(
                    paciente,
                    medico,
                    dpDataConsulta.getValue()
            );

            lblMensagem.setStyle("-fx-text-fill: green;");
            lblMensagem.setText("Consulta cadastrada com sucesso!");

        } catch (Exception e) {

            lblMensagem.setStyle("-fx-text-fill: red;");
            lblMensagem.setText("Erro ao cadastrar consulta.");

            e.printStackTrace();
        }
    }


    public void setFacade(HospitalFacade facade) {
        this.facade = facade;
        System.out.println(getClass().getSimpleName()
                + " recebeu facade: " + (facade != null));
    }


    @FXML
    public void Voltar(ActionEvent event) {
        System.out.println("Facade é null? " + (facade == null));
        TrocaTela.trocarTela(event, "/fxml/TelaMenuMedico.fxml",facade);
    }
}

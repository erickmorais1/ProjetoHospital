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
    private TextField txtCpfPaciente;

    @FXML
    private void cadastrarConsulta() throws PacienteService.ServicoException, SQLException {
        Paciente paciente =
                facade.buscarPacientePorCpf(
                        txtCpfPaciente.getText()
                );

        System.out.println(
                "Paciente encontrado: " +
                        paciente.getNome()
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

        System.out.println("Consulta cadastrada com sucesso!");

        if (facade == null) {
            System.out.println("Facade NULA");
        } else {
            System.out.println("Facade OK");
        }

        System.out.println("CPF: " + txtCpfPaciente.getText());
        System.out.println("Data: " + dpDataConsulta.getValue());
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

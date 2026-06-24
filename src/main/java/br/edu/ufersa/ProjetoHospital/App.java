    package br.edu.ufersa.ProjetoHospital;

    import br.edu.ufersa.ProjetoHospital.Controller.CadastrarConsultaController;
    import br.edu.ufersa.ProjetoHospital.Controller.LoginController;
    import br.edu.ufersa.ProjetoHospital.Controller.MenuMedicoController;
    import br.edu.ufersa.ProjetoHospital.Controller.TelaInicialController;
    import br.edu.ufersa.ProjetoHospital.DAO.ConexaoBD;
    import br.edu.ufersa.ProjetoHospital.DAO.ConsultaDAO;
    import br.edu.ufersa.ProjetoHospital.DAO.MedicoDAO;
    import br.edu.ufersa.ProjetoHospital.DAO.PacienteDAO;
    import br.edu.ufersa.ProjetoHospital.Facade.HospitalFacade;
    import br.edu.ufersa.ProjetoHospital.Service.ConsultaService;
    import br.edu.ufersa.ProjetoHospital.Service.MedicoService;
    import br.edu.ufersa.ProjetoHospital.Service.PacienteService;
    import javafx.application.Application;
    import javafx.fxml.FXMLLoader;
    import javafx.scene.Scene;
    import javafx.stage.Stage;

    import java.sql.Connection;

    public class App extends Application {

        private HospitalFacade facade;

        @Override
        public void start(Stage stage) throws Exception {

            Connection con = ConexaoBD.getConnection();

            MedicoDAO medicoDAO = new MedicoDAO(con);
            MedicoService medicoService = new MedicoService(medicoDAO);

            ConsultaDAO consultaDAO = new ConsultaDAO(con);
            ConsultaService consultaService = new ConsultaService(consultaDAO);

            PacienteDAO pacienteDAO = new PacienteDAO(con);
            PacienteService pacienteService = new PacienteService(pacienteDAO);

            facade = new HospitalFacade(
                    medicoService,
                    consultaService,
                    pacienteService
            );

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/fxml/TelaInicial.fxml")
            );

            Scene scene = new Scene(loader.load());

            TelaInicialController controller = loader.getController();
            controller.setFacade(facade);

            stage.setTitle("Sistema Hospitalar");
            stage.setScene(scene);
            stage.setFullScreenExitHint("");
            stage.setFullScreen(true);
            stage.show();
        }
        }

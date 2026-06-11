package br.edu.ufersa.ProjetoHospital.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {
    @FXML
    private TextField txtUsuario;

    @FXML
    private PasswordField txtSenha;

    @FXML
    private Button btnEntrar;

    @FXML
    private void entrar() {

        String usuario = txtUsuario.getText();
        String senha = txtSenha.getText();

        System.out.println("Usuário: " + usuario);
        System.out.println("Senha: " + senha);
    }

}
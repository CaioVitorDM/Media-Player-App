package br.ufrn.imd.Controller;

import br.ufrn.imd.Visao.Main;
import javafx.event.ActionEvent;
import br.ufrn.imd.DAO.UsuariosDAO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private TextField tnome;
    @FXML
    private PasswordField tsenha;
    private static UsuariosDAO usuariosDAO;


    public void initialize(URL url, ResourceBundle resourceBundle) {
        usuariosDAO = UsuariosDAO.getInstance();
        usuariosDAO.carregarUsuarios();
    }
    public void buttonSubmeter(ActionEvent actionEvent) throws IOException {
        if (usuariosDAO.loginUser(tnome.getText(), tsenha.getText())) {
            // Checagem para saber de qual tipo é o usuário e qual vai ser sua tela
            if (usuariosDAO.isUserVIP(tnome.getText(), tsenha.getText())) {
                // Carregar tela para usuário VIP
                Main.changeScreen("TelaPrincipal");
            } else if (usuariosDAO.isUserAdmin(tnome.getText(), tsenha.getText())) {
                // Carregar tela para usuário admin\
                Main.changeScreen("TelaPrincipal");

            } else {
                // Carregar tela para usuário comum
                Main.changeScreen("TelaPrincipalComum");
            }
        }

        else {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setHeaderText("Login ou Senha inválidos");
            alerta.setTitle("Erro");
            alerta.show();
        }

        tnome.setText("");
        tsenha.setText("");
    }

}

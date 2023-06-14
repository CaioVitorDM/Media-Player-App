package br.ufrn.imd.Controller;

import br.ufrn.imd.DAO.UsuariosDAO;
import br.ufrn.imd.Visao.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class TelaPrincipalController implements Initializable {
    @FXML
    private TextField tnome;
    @FXML
    private PasswordField tsenha;
    private static UsuariosDAO usuariosDAO;


    public void initialize(URL url, ResourceBundle resourceBundle) {
        usuariosDAO = UsuariosDAO.getInstance();
        usuariosDAO.carregarUsuarios();
    }

    public void menuCadastrarComum(ActionEvent actionEvent) {
        Main.changeScreen("TelaCadastroComum");
    }

    public void menuCadastrarVIP(ActionEvent actionEvent){
        Main.changeScreen("TelaCadastroVIP");
    }

    public void menuRemoverUsuario(ActionEvent actionEvent){
        Main.changeScreen("TelaRemoverUsuario");
    }

    public void menuListagemUsuarios(ActionEvent actionEvent) {
        Main.changeScreen("TelaListagemUsuarios");
    }
}

package br.ufrn.imd.Controller;

import br.ufrn.imd.DAO.UsuariosDAO;
import br.ufrn.imd.Modelo.UsuarioComum;
import br.ufrn.imd.Modelo.UsuarioVIP;
import br.ufrn.imd.Visao.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class TelaCadastroVIPController implements Initializable {
    @FXML
    private TextField tnome;
    @FXML
    private PasswordField tsenha;
    private static UsuariosDAO usuariosDAO;
    @FXML
    private Button backButton, submitButton;
    @FXML
    private ImageView backButtonImage, logoImage, backgroundImage, registerTitleImage;
    private Image backButtonImg, logoImg,backgroundImg, registerTitleImg;


    public void initialize(URL url, ResourceBundle resourceBundle) {
        usuariosDAO = UsuariosDAO.getInstance();
        usuariosDAO.carregarUsuarios();

        backButtonImg = new Image(System.getProperty("user.dir") + File.separator + "./src/main/java/br/ufrn/imd/Images/back.png");
        logoImg = new Image(System.getProperty("user.dir") + File.separator + "./src/main/java/br/ufrn/imd/Images/edit.png");
        backgroundImg = new Image(System.getProperty("user.dir") + File.separator + "./src/main/java/br/ufrn/imd/Images/gradient-login.png");
        registerTitleImg = new Image(System.getProperty("user.dir") + File.separator + "./src/main/java/br/ufrn/imd/Images/register-title.png");


        backButtonImage.fitWidthProperty();
        backButtonImage.fitHeightProperty();
        backButtonImage.setImage(backButtonImg);
        logoImage.setImage(logoImg);
        backgroundImage.fitHeightProperty();
        backgroundImage.setImage(backgroundImg);
        registerTitleImage.setImage(registerTitleImg);
    }

    @FXML
    private void handleSubmitButton(ActionEvent actionEvent) {
        UsuarioVIP c = new UsuarioVIP();

        c.setNomeUsuario(tnome.getText());
        c.setSenha(tsenha.getText());

        // persistir dados
        if(usuariosDAO.addUsuario(c)) {
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setHeaderText("Usuário cadastrado com sucesso");
            alerta.setTitle("SUCESS");
            alerta.show();
        }
        else{
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setHeaderText("Não foi possível cadastrar usuário");
            alerta.setTitle("Erro");
            alerta.show();
        }

        tnome.setText("");
        tsenha.setText("");

        tnome.requestFocus();
    }

    @FXML
    private void handleBackButton(ActionEvent actionEvent) {
        Main.changeScreen("TelaPrincipal");
    }
}

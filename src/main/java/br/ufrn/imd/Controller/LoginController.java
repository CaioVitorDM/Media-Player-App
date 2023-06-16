package br.ufrn.imd.Controller;

import br.ufrn.imd.Visao.Main;
import javafx.event.ActionEvent;
import br.ufrn.imd.DAO.UsuariosDAO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private TextField tnome;
    @FXML
    private PasswordField tsenha;
    @FXML
    private ImageView backgroundImage, logoImage, titleImage, loginTitleImage;
    private Image backgroundImg, logoImg, titleImg, loginTitleImg;
    private static UsuariosDAO usuariosDAO;


    public void initialize(URL url, ResourceBundle resourceBundle) {
        usuariosDAO = UsuariosDAO.getInstance();
        usuariosDAO.carregarUsuarios();

        backgroundImg = new Image(System.getProperty("user.dir") + File.separator + "./src/main/java/br/ufrn/imd/Images/gradient-login.png");
        logoImg = new Image(System.getProperty("user.dir") + File.separator + "./src/main/java/br/ufrn/imd/Images/music.png");
        titleImg = new Image(System.getProperty("user.dir") + File.separator + "./src/main/java/br/ufrn/imd/Images/app-title.png");
        loginTitleImg = new Image(System.getProperty("user.dir") + File.separator + "./src/main/java/br/ufrn/imd/Images/sign-in-title.png");

        backgroundImage.fitHeightProperty();
        backgroundImage.setImage(backgroundImg);
        logoImage.setImage(logoImg);
        titleImage.fitHeightProperty();
        titleImage.fitWidthProperty();
        titleImage.setImage(titleImg);
        titleImage.fitHeightProperty();
        titleImage.fitWidthProperty();
        loginTitleImage.setImage(loginTitleImg);
    }
    public void buttonSubmeter(ActionEvent actionEvent) throws IOException {
        if (usuariosDAO.loginUser(tnome.getText(), tsenha.getText())) {
            Main.setUserName(tnome.getText());
            // Checagem para saber de qual tipo é o usuário e qual vai ser sua tela
            if (usuariosDAO.isUserVIP(tnome.getText(), tsenha.getText()) || usuariosDAO.isUserAdmin(tnome.getText(), tsenha.getText())) {
                // Carregar tela para usuário VIP
                Main.changeScreen("TelaPrincipal");
            }
            else {
                // Carregar tela para usuário comum
                Main.setIsUserCommon(true);
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

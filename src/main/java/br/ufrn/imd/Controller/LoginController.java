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

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * LoginController class that handles the actions and elements from the login screen
 * <br>
 * @author  Caio Vitor
 */
public class LoginController implements Initializable {
    @FXML
    private TextField tnome;
    @FXML
    private PasswordField tsenha;
    @FXML
    private ImageView backgroundImage, logoImage, titleImage, loginTitleImage;
    private Image backgroundImg, logoImg, titleImg, loginTitleImg;
    private static UsuariosDAO usuariosDAO;

    /**
     * Method that is called when the screen is loaded and does the setting of the images in
     * the elements of the screen, also calls the carregarUsuarios method from the DAO class.
     * @param url
     * @param resourceBundle
     */
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

    /**
     * Method that handles the submit button action (click), calling for the loginUser
     * method from users DAO, then calls Main class method setUserName, so the main always
     * gets the logged user's name. And also checks if the user is VIP, Admin or Common.
     *
     * If the user is Admin or Common, it calls Main method changeScreen and pass the respective
     * string to the change the screen to the VIP/Admin Main screen.
     *
     * If the user is common, calls the changeScreen and pass the string to change for the common
     * user main screen.
     *
     * If the login is not successful, generates a warning of Error type.
     * @param actionEvent
     * @throws IOException
     */
    public void buttonSubmeter(ActionEvent actionEvent) throws IOException {
        if (usuariosDAO.loginUser(tnome.getText(), tsenha.getText())) {
            Main.setUserName(tnome.getText());
            // Checagem para saber de qual tipo é o usuário e qual vai ser sua tela
            if (usuariosDAO.isUserVIP(tnome.getText(), tsenha.getText()) || usuariosDAO.isUserAdmin(tnome.getText(), tsenha.getText())) {
                //Chama a função da tela da main para informar que o usuário não é comum
                Main.setIsUserCommon(false);
                // Carregar tela para usuário VIP
                Main.changeScreen("TelaPrincipal");
            }
            else {
                //Chama a função da tela da main para informar que o usuário é comum
                Main.setIsUserCommon(true);
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

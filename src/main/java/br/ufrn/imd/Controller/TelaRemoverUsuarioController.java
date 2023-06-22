package br.ufrn.imd.Controller;

import br.ufrn.imd.DAO.UsuariosDAO;
import br.ufrn.imd.Modelo.UsuarioComum;
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

/**
 * TelaRemoverUsuarioController class that handles the actions and elements from the remove screen of users.
 * <br>
 * @author  Caio Vitor
 */
public class TelaRemoverUsuarioController implements Initializable {
    @FXML
    private TextField tnome;
    @FXML
    private PasswordField tsenha;
    private static UsuariosDAO usuariosDAO;
    @FXML
    private Button backButton, submitButton;
    @FXML
    private ImageView backButtonImage, logoImage, backgroundImage, removeTitleImage;
    private Image backButtonImg, logoImg,backgroundImg, removeTitleImg;

    /**
     * Method that is called when the screen is loaded and does the setting of the images in
     * the elements of the screen, also calls the carregarUsuarios method from the usuariosDAO class.
     * @param url
     * @param resourceBundle
     */
    public void initialize(URL url, ResourceBundle resourceBundle) {
        usuariosDAO = UsuariosDAO.getInstance();
        usuariosDAO.carregarUsuarios();

        backButtonImg = new Image(System.getProperty("user.dir") + File.separator + "./src/main/java/br/ufrn/imd/Images/back.png");
        logoImg = new Image(System.getProperty("user.dir") + File.separator + "./src/main/java/br/ufrn/imd/Images/edit.png");
        backgroundImg = new Image(System.getProperty("user.dir") + File.separator + "./src/main/java/br/ufrn/imd/Images/gradient-login.png");
        removeTitleImg = new Image(System.getProperty("user.dir") + File.separator + "./src/main/java/br/ufrn/imd/Images/remove-title.png");


        backButtonImage.fitWidthProperty();
        backButtonImage.fitHeightProperty();
        backButtonImage.setImage(backButtonImg);
        logoImage.setImage(logoImg);
        backgroundImage.fitHeightProperty();
        backgroundImage.setImage(backgroundImg);
        removeTitleImage.setImage(removeTitleImg);
    }

    /**
     * Method that handles the submit button action (click), calling for the removeUsuario
     * method from usuarios DAO, then sets a warning on the screen. If the operation succeeds, it's an
     * information warning, if it fails, it's an error warning.
     *
     * @param actionEvent
     */
    @FXML
    private void handleSubmitButton(ActionEvent actionEvent) {
        UsuarioComum c = new UsuarioComum();

        c.setNomeUsuario(tnome.getText());
        c.setSenha("padrão");

        // persistir dados
        if(usuariosDAO.removeUsuario(c)){
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setHeaderText("Usuário removido com sucesso");
            alerta.setTitle("Sucesss");
            alerta.show();
        }
        else{
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setHeaderText("Não foi possível remover usuário");
            alerta.setTitle("Erro");
            alerta.show();
        }
        tnome.setText("");

        tnome.requestFocus();
    }

    /**
     * Method that handles the back button action (click), calls for the method changeScreen
     * passing the string to the VIP/Admin main screen.
     * @param actionEvent
     */
    @FXML
    private void handleBackButton(ActionEvent actionEvent) {
        Main.changeScreen("TelaPrincipal");
    }
}




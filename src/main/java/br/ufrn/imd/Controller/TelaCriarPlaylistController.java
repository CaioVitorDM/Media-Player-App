package br.ufrn.imd.Controller;

import br.ufrn.imd.DAO.PlaylistDAO;
import br.ufrn.imd.DAO.UsuariosDAO;
import br.ufrn.imd.Modelo.Playlist;
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
 * TelaCriarPlaylistController class that handles the actions and elements from the register screen of Playlists.
 * <br>
 * @author  Caio Vitor
 */
public class TelaCriarPlaylistController implements Initializable {
    @FXML
    private TextField tnome;
    @FXML
    private PasswordField tsenha;
    private static PlaylistDAO playlistDAO;
    @FXML
    private Button backButton, submitButton;
    @FXML
    private ImageView backButtonImage, logoImage, backgroundImage, registerTitleImage;
    private Image backButtonImg, logoImg,backgroundImg, registerTitleImg;

    /**
     * Method that is called when the screen is loaded and does the setting of the images in
     * the elements of the screen, also calls the carregarPlaylists method from the playlistDAO class.
     * @param url
     * @param resourceBundle
     */
    public void initialize(URL url, ResourceBundle resourceBundle) {
        playlistDAO = PlaylistDAO.getInstance();
        playlistDAO.carregarPlaylists();

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

    /**
     * Method that handles the submit button action (click), calling for the addPlaylist
     * method from playlist DAO, then sets a warning on the screen. If the operation succeeds, it's an
     * information warning, if it fails, it's an error warning.
     *
     * @param actionEvent
     */
    @FXML
    private void handleSubmitButton(ActionEvent actionEvent) {
        Playlist c = new Playlist();

        c.setPlaylistName(tnome.getText());
        c.setOwnerName(Main.getUserName());

        // persistir dados
        if(playlistDAO.addPlaylist(c)){
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setHeaderText("Playlist adicionada com sucesso");
            alerta.setTitle("Sucesss");
            alerta.show();
        }
        else{
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setHeaderText("Não foi possível cadastrar playlist");
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




package br.ufrn.imd.Controller;

import br.ufrn.imd.DAO.PlaylistDAO;
import br.ufrn.imd.Modelo.Playlist;
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

public class TelaRemoverPlaylistController implements Initializable {
    @FXML
    private TextField tnome;
    @FXML
    private PasswordField tsenha;
    private static PlaylistDAO playlistDAO;
    @FXML
    private Button backButton, submitButton;
    @FXML
    private ImageView backButtonImage, logoImage, backgroundImage, removeTitleImage;
    private Image backButtonImg, logoImg,backgroundImg, removeTitleImg;


    public void initialize(URL url, ResourceBundle resourceBundle) {
        playlistDAO = PlaylistDAO.getInstance();
        playlistDAO.carregarPlaylists();

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

    @FXML
    private void handleSubmitButton(ActionEvent actionEvent) {
        Playlist c = new Playlist();

        c.setPlaylistName(tnome.getText());
        c.setOwnerName(Main.getUserName());

        // persistir dados
        if(playlistDAO.removePlaylist(c)){
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setHeaderText("Playlist removida com sucesso");
            alerta.setTitle("Sucesss");
            alerta.show();
        }
        else{
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setHeaderText("Não foi possível remover playlist");
            alerta.setTitle("Erro");
            alerta.show();
        }
        tnome.setText("");

        tnome.requestFocus();
    }

    @FXML
    private void handleBackButton(ActionEvent actionEvent) {
        Main.changeScreen("TelaPrincipal");
    }
}




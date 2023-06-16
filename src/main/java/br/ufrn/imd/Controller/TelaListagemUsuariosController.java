package br.ufrn.imd.Controller;

import br.ufrn.imd.DAO.UsuariosDAO;
import br.ufrn.imd.Modelo.UsuarioComum;
import br.ufrn.imd.Visao.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class TelaListagemUsuariosController implements Initializable {
    @FXML
    private ListView<String> tabela;
    @FXML
    private Button backButton, refreshButton;
    @FXML
    private ImageView backButtonImage, logoImage, backgroundImage, refreshButtonImage;

    private Image backButtonImg, logoImg, backgroundImg, refreshButtonImg;
    private static UsuariosDAO usuariosDAO;
    ObservableList<String> items;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        usuariosDAO = UsuariosDAO.getInstance();
        usuariosDAO.carregarUsuarios();

        items = usuariosDAO.getNomesUsuarios();
        tabela.setItems(items);

        backButtonImg = new Image(System.getProperty("user.dir") + File.separator + "./src/main/java/br/ufrn/imd/Images/back.png");
        logoImg = new Image(System.getProperty("user.dir") + File.separator + "./src/main/java/br/ufrn/imd/Images/clipboard.png");
        backgroundImg = new Image(System.getProperty("user.dir") + File.separator + "./src/main/java/br/ufrn/imd/Images/gradient.png");
        refreshButtonImg = new Image(System.getProperty("user.dir") + File.separator + "./src/main/java/br/ufrn/imd/Images/refresh.png");


        backButtonImage.fitWidthProperty();
        backButtonImage.fitHeightProperty();
        backButtonImage.setImage(backButtonImg);
        logoImage.setImage(logoImg);
        backButtonImage.setImage(backButtonImg);
        backgroundImage.setImage(backgroundImg);
        refreshButtonImage.setImage(refreshButtonImg);
    }

    @FXML
    private void atualizarListaUsuarios() {
        items.setAll(usuariosDAO.getNomesUsuarios());
    }

    @FXML
    private void handleBackButton(ActionEvent actionEvent) {
        if(Main.isUserCommon()){
            atualizarListaUsuarios();
            Main.changeScreen("TelaPrincipalComum");
        }
        else {
            atualizarListaUsuarios();
            Main.changeScreen("TelaPrincipal");
        }
    }

    public void handleRefreshButton(ActionEvent actionEvent){
        atualizarListaUsuarios();
    }
}

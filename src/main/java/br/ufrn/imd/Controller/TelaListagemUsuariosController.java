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

/**
 * TelaListagemUsuariosController class that handles the actions and elements from the listing screen of users.
 * <br>
 * @author  Caio Vitor
 */
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

    /**
     * Method that is called when the screen is loaded and does the setting of the images in
     * the elements of the screen, also calls the carregarUsuarios method from the usuariosDAO class.
     * Also calls getNomesUsuarios to get the user's names from the usuariosDAO and pass to the variable items.
     *
     * @param url
     * @param resourceBundle
     */
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

    /**
     * Method that refreshes the list of user's names.
     */
    @FXML
    private void atualizarListaUsuarios() {
        items.setAll(usuariosDAO.getNomesUsuarios());
    }

    /**
     * Method that handles the back button action (click), calls for the method changeScreen
     * and consults the Main to know if the logged user is common or VIP/Admin.
     *
     * If it's common, pass the string to change the screen to the common users main screen.
     * If it's VIP/Admin, pass the string to change the screen to then VIP/Admin users main screen.
     * @param actionEvent
     */
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

    /**
     * Method that handles refresh button and calls for atualizarListaUsuarios.
     * @param actionEvent
     */
    public void handleRefreshButton(ActionEvent actionEvent){
        atualizarListaUsuarios();
    }
}

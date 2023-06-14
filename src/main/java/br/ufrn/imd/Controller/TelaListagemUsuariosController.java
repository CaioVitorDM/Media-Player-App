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
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class TelaListagemUsuariosController implements Initializable {
    @FXML
    private ListView<String> tabela;
    private static UsuariosDAO usuariosDAO;
    ObservableList<String> items;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        usuariosDAO = UsuariosDAO.getInstance();
        usuariosDAO.carregarUsuarios();

        items = usuariosDAO.getNomesUsuarios();
        tabela.setItems(items);

    }

    public void atualizarListaUsuarios() {
        items.setAll(usuariosDAO.getNomesUsuarios());
    }

    public void handleBackButton(ActionEvent actionEvent) {
        atualizarListaUsuarios();
        Main.changeScreen("TelaPrincipal");
    }

    public void handleButtonRefresh(ActionEvent actionEvent){
        atualizarListaUsuarios();
    }
}

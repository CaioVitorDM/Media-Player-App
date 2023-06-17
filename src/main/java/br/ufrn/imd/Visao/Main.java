package br.ufrn.imd.Visao;

import br.ufrn.imd.DAO.PlaylistDAO;
import br.ufrn.imd.DAO.UsuariosDAO;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    //Declaração da janela, telas e singleton
    private static Stage stage;
    private static Scene TelaLoginScene;
    private static Scene TelaPrincipalScene;
    private static Scene TelaPrincipalComumScene;
    private static Scene TelaCadastroComumScene;
    private static Scene TelaCadastroVIPScene;
    private static Scene TelaRemoverUsuarioScene;
    private static Scene TelaListagemUsuariosScene;
    private static Scene TelaCriarPlaylistScene;
    private static Scene TelaRemoverPlaylistScene;
    private static String userName;
    private static boolean isUserCommon;

    private static UsuariosDAO usuariosDAO;
    private static PlaylistDAO playlistDAO;

    @Override
    public void start(Stage primaryStage) throws IOException {
        //Janela primária
        stage = primaryStage;

        //Cash de todas as telas do programa.
        Parent fxmlLogin = FXMLLoader.load((getClass().getResource("TelaLogin.fxml")));
        TelaLoginScene = new Scene(fxmlLogin);

        Parent fxmlTelaPrincipal = FXMLLoader.load((getClass().getResource("TelaPrincipal.fxml")));
        TelaPrincipalScene = new Scene(fxmlTelaPrincipal);

        Parent fxmlTelaPrincipalComum = FXMLLoader.load((getClass().getResource("TelaPrincipalComum.fxml")));
        TelaPrincipalComumScene = new Scene(fxmlTelaPrincipalComum);

        Parent fxmlTelaCadastroComum = FXMLLoader.load((getClass().getResource("TelaCadastroComum.fxml")));
        TelaCadastroComumScene = new Scene(fxmlTelaCadastroComum);

        Parent fxmlTelaCadastroVIP = FXMLLoader.load((getClass().getResource("TelaCadastroVIP.fxml")));
        TelaCadastroVIPScene = new Scene(fxmlTelaCadastroVIP);

        Parent fxmlTelaRemoverUsuario = FXMLLoader.load((getClass().getResource("TelaRemoverUsuario.fxml")));
        TelaRemoverUsuarioScene = new Scene(fxmlTelaRemoverUsuario);

        Parent fxmlTelaListagemUsuarios = FXMLLoader.load((getClass().getResource("TelaListagemUsuarios.fxml")));
        TelaListagemUsuariosScene = new Scene(fxmlTelaListagemUsuarios );

        Parent fxmlTelaCriarPlaylist = FXMLLoader.load((getClass().getResource("TelaCriarPlaylist.fxml")));
        TelaCriarPlaylistScene  = new Scene(fxmlTelaCriarPlaylist );

        Parent fxmlTelaRemoverPlaylist = FXMLLoader.load((getClass().getResource("TelaRemoverPlaylist.fxml")));
        TelaRemoverPlaylistScene  = new Scene(fxmlTelaRemoverPlaylist );

        //Definições padrões da janela
        primaryStage.setTitle("Media Player App");
        primaryStage.setResizable(false);
        primaryStage.setScene(TelaLoginScene);
        primaryStage.show();

        //Definindo uma operação de fechar padrão para a janela que vai funcionar em todas as telas
        stage.setOnCloseRequest(event -> {
            usuariosDAO = UsuariosDAO.getInstance();
            usuariosDAO.salvarUsuarios();
            if(!isUserCommon()){
                playlistDAO = PlaylistDAO.getInstance();
                playlistDAO.salvarPlaylists();
            }
            try {
                System.out.println("Fechando o sistema...");
                Thread.sleep(500);
            } catch (InterruptedException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            System.exit(0);
        });
    }

    public static void setUserName(String name){
        userName = name;
    }

    public static String getUserName(){
        return userName;
    }

    public static void setIsUserCommon(boolean condition){
        isUserCommon = condition;
    }

    public static boolean isUserCommon(){
        return isUserCommon;
    }

    public static void changeScreen(String screen){
        switch (screen){
            case "login":
                stage.setScene(TelaLoginScene);
                break;
            case "TelaPrincipal":
                stage.setScene(TelaPrincipalScene);
                break;
            case "TelaPrincipalComum":
                stage.setScene(TelaPrincipalComumScene);
                break;
            case "TelaCadastroComum":
                stage.setScene(TelaCadastroComumScene);
                break;
            case "TelaCadastroVIP":
                stage.setScene(TelaCadastroVIPScene);
                break;
            case "TelaRemoverUsuario":
                stage.setScene(TelaRemoverUsuarioScene);
                break;
            case "TelaListagemUsuarios":
                stage.setScene(TelaListagemUsuariosScene);
                break;
            case "TelaCriarPlaylist":
                stage.setScene(TelaCriarPlaylistScene);
                break;
            case "TelaRemoverPlaylist":
                stage.setScene(TelaRemoverPlaylistScene);
                break;

        }
    }

    public static void main(String[] args) {
        launch();
    }
}

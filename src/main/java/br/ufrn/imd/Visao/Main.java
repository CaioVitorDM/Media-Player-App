package br.ufrn.imd.Visao;

import br.ufrn.imd.DAO.UsuariosDAO;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    private static Stage stage;
    private static Scene loginScene;
    private static Scene TelaPrincipalScene;
    private static Scene TelaPrincipalComumScene;
    private static UsuariosDAO usuariosDAO;

    @Override
    public void start(Stage primaryStage) throws IOException {
        stage = primaryStage;

        Parent fxmlLogin = FXMLLoader.load((getClass().getResource("TelaLogin.fxml")));
        loginScene = new Scene(fxmlLogin);

        Parent fxmlTelaPrincipal = FXMLLoader.load((getClass().getResource("TelaPrincipal.fxml")));
        TelaPrincipalScene = new Scene(fxmlTelaPrincipal);

        Parent fxmlTelaPrincipalComum = FXMLLoader.load((getClass().getResource("TelaPrincipalComum.fxml")));
        TelaPrincipalComumScene = new Scene(fxmlTelaPrincipalComum);


        primaryStage.setTitle("Hello!");
        primaryStage.setResizable(false);
        primaryStage.setScene(loginScene);
        primaryStage.show();
        stage.setOnCloseRequest(event -> {
            usuariosDAO = UsuariosDAO.getInstance();
            usuariosDAO.salvarUsuarios();
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

    public static void changeScreen(String screen){
        switch (screen){
            case "login":
                stage.setScene(loginScene);
                break;
            case "TelaPrincipal":
                stage.setScene(TelaPrincipalScene);
                break;
            case "TelaPrincipalComum":
                stage.setScene(TelaPrincipalComumScene);
                break;
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
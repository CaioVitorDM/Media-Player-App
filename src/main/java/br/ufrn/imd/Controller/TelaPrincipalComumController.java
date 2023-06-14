package br.ufrn.imd.Controller;

import br.ufrn.imd.DAO.UsuariosDAO;
import br.ufrn.imd.Modelo.Song;
import br.ufrn.imd.Visao.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.DirectoryChooser;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class TelaPrincipalComumController implements Initializable {
    private Song musica;
    private boolean isPlaying;
    private ObservableList<String> musicListItems;
    private int currentMusicIndex = -1;
    private int totalMusicCount = 0;

    //Trazendo os elementos da tela
    @FXML
    private ProgressBar progressBar;
    @FXML
    private ListView<String> musicListView;
    @FXML
    private Button playButton;
    @FXML
    private Button nextButton;
    @FXML
    private Button previousButton;
    @FXML
    private Button stopButton;

    @FXML
    private Button selectDirectoryButton;

    @FXML
    private ImageView playButtonImage;
    @FXML
    private ImageView nextButtonImage;
    @FXML
    private ImageView previousButtonImage;

    //Criação das imagens
    private Image playImage;
    private Image stopImage;
    private Image nextButtonImg;
    private Image previousButtonImg;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        musica = null;
        isPlaying = false;
        musicListItems = FXCollections.observableArrayList();
        musicListView.setItems(musicListItems);
        musicListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (!musicListView.getSelectionModel().isEmpty()) {
                currentMusicIndex = musicListView.getSelectionModel().getSelectedIndex();
            }
        });

        //Definição das imagens que aparecerão no botão do play
        playImage = new Image(System.getProperty("user.dir") + File.separator + "./src/main/java/br/ufrn/imd/Diretorios/play-button.png");
        stopImage = new Image(System.getProperty("user.dir") + File.separator + "./src/main/java/br/ufrn/imd/Diretorios/stop-button.png");
        nextButtonImg = new Image(System.getProperty("user.dir") + File.separator + "./src/main/java/br/ufrn/imd/Diretorios/next.png");
        previousButtonImg = new Image(System.getProperty("user.dir") + File.separator + "./src/main/java/br/ufrn/imd/Diretorios/previous.png");

        playButtonImage.setImage(playImage);
        nextButtonImage.setImage(nextButtonImg);
        previousButtonImage.setImage(previousButtonImg);
    }

    private void updateMusicList(File directory) {
        musicListItems.clear();

        if (directory != null && directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile() && file.getName().toLowerCase().endsWith(".mp3")) {
                        musicListItems.add(file.getName());
                    }
                }
                totalMusicCount = musicListItems.size();
                currentMusicIndex = -1;
                stopProgressBarTimer();
                resetProgressBar();
            }
        }
    }

    @FXML
    private void playButtonClicked(ActionEvent event) {
        if(musicListItems.isEmpty()){}
        else {
            if (isPlaying) {
                musica.pararMusica();
                isPlaying = false;
                playButtonImage.setImage(playImage);
            } else {
                int selectedIndex = musicListView.getSelectionModel().getSelectedIndex();
                if (selectedIndex != -1) {
                    musica.tocarMusica(selectedIndex);
                    isPlaying = true;
                    currentMusicIndex = selectedIndex;
                    musicListView.getSelectionModel().select(selectedIndex);
                    startProgressBarTimer();
                }
                playButtonImage.setImage(stopImage);
            }
        }
    }

    @FXML
    private void nextButtonClicked(ActionEvent event){
        if (currentMusicIndex < totalMusicCount - 1) {
            currentMusicIndex++;
            musica.pararMusica();
            musica.tocarMusica(currentMusicIndex);
            isPlaying = true;
            playButtonImage.setImage(stopImage);
            musicListView.getSelectionModel().select(currentMusicIndex);
            startProgressBarTimer();
        }
    }

    @FXML
    private void previousButtonClicked(ActionEvent event) {
        if (currentMusicIndex > 0) {
            currentMusicIndex--;
            musica.pararMusica();
            musica.tocarMusica(currentMusicIndex);
            playButtonImage.setImage(stopImage);
            isPlaying = true;
            musicListView.getSelectionModel().select(currentMusicIndex);
            startProgressBarTimer();
        }
    }

    private void startProgressBarTimer() {

    }
    private void stopProgressBarTimer() {
        // Implementar a lógica para parar o timer da ProgressBar, se necessário
    }
    private void resetProgressBar() {
        // Implementar a lógica para resetar a ProgressBar
    }
    public void menuListagemUsuarios(ActionEvent actionEvent) {
        Main.changeScreen("TelaListagemUsuarios");
    }
    @FXML
    private void selectDirectoryButtonClicked(ActionEvent event) {
        //Se o Player da Musica for nula, ou seja, nenhuma musica tiver sido tocada ainda
        if(musica == null){
            DirectoryChooser directoryChooser = new DirectoryChooser();
            File selectedDirectory = directoryChooser.showDialog(selectDirectoryButton.getScene().getWindow());
            if (selectedDirectory != null) {
                musica = new Song(selectedDirectory);
                updateMusicList(selectedDirectory);
            }
        }

        //Se estiver tocando alguma musica ao selecionar o diretório
        else if(musica != null && musica.isPlaying()){
            musica.pararMusica();
            playButtonImage.setImage(playImage);
            isPlaying = false;
            DirectoryChooser directoryChooser = new DirectoryChooser();
            File selectedDirectory = directoryChooser.showDialog(selectDirectoryButton.getScene().getWindow());
            if (selectedDirectory != null) {
                musica = new Song(selectedDirectory);
                updateMusicList(selectedDirectory);
            }
        }

        //Se tiver alguma musica pausada ao selecionar o diretório
        else if(musica != null && !musica.isPlaying()){
            DirectoryChooser directoryChooser = new DirectoryChooser();
            File selectedDirectory = directoryChooser.showDialog(selectDirectoryButton.getScene().getWindow());
            if (selectedDirectory != null) {
                musica = new Song(selectedDirectory);
                updateMusicList(selectedDirectory);
            }
        }
    }
}

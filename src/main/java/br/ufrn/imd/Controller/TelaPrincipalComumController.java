package br.ufrn.imd.Controller;

import br.ufrn.imd.DAO.UsuariosDAO;
import br.ufrn.imd.Visao.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.DirectoryChooser;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;


public class TelaPrincipalComumController implements Initializable {
    private static UsuariosDAO usuariosDAO;

    private ObservableList<String> musicListItems;
    private int currentMusicIndex;
    private int totalMusicCount = 0;

    private File directory;
    private File [] currentDirectoryFiles;
    private ArrayList<File> songs;
    private Media media;
    private MediaPlayer mediaPlayer;

    private Timer timer;
    private TimerTask task;
    private boolean isPlaying;
    private boolean running;

    //Trazendo os elementos da tela
    @FXML
    private ProgressBar progressBar;
    @FXML
    private ListView<String> musicListView;
    @FXML
    private Button playButton, nextButton, previousButton, stopButton, selectDirectoryButton;

    @FXML
    private ImageView playButtonImage, nextButtonImage, previousButtonImage;

    //Criação das imagens
    private Image playImage, stopImage, nextButtonImg, previousButtonImg;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        usuariosDAO = UsuariosDAO.getInstance();
        usuariosDAO.carregarUsuarios();

        songs = new ArrayList<File>();
        directory = new File("");
        currentDirectoryFiles = directory.listFiles();

        isPlaying = false;
        musicListItems = FXCollections.observableArrayList();
        musicListView.setItems(musicListItems);
        musicListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (!musicListView.getSelectionModel().isEmpty()) {
                currentMusicIndex = musicListView.getSelectionModel().getSelectedIndex();
            }
        });

        //Definição das imagens que aparecerão nos botões
        playImage = new Image(System.getProperty("user.dir") + File.separator + "./src/main/java/br/ufrn/imd/Images/play-button.png");
        stopImage = new Image(System.getProperty("user.dir") + File.separator + "./src/main/java/br/ufrn/imd/Images/stop-button.png");
        nextButtonImg = new Image(System.getProperty("user.dir") + File.separator + "./src/main/java/br/ufrn/imd/Images/next.png");
        previousButtonImg = new Image(System.getProperty("user.dir") + File.separator + "./src/main/java/br/ufrn/imd/Images/previous.png");

        playButtonImage.setImage(playImage);
        nextButtonImage.setImage(nextButtonImg);
        previousButtonImage.setImage(previousButtonImg);
    }

    //Definição das funções dos itens de menu
    public void menuListagemUsuarios(ActionEvent actionEvent) {
        Main.changeScreen("TelaListagemUsuarios");
    }

    //Definição da função dos botões
    @FXML
    private void playButtonClicked(ActionEvent event) {
        //checa se a lista de musicas está vazia
        if(musicListItems.isEmpty()){

        }
        else {
            if (isPlaying) {
                mediaPlayer.pause();
                isPlaying = false;
                playButtonImage.setImage(playImage);
                resetProgressBar();
            } else {
                int selectedIndex = musicListView.getSelectionModel().getSelectedIndex();
                if (selectedIndex != -1) {
                    currentMusicIndex = selectedIndex;
                    if (songs.get(selectedIndex).isFile() && songs.get(selectedIndex).getName().toLowerCase().endsWith(".mp3")) {
                        media = new Media(songs.get(selectedIndex).toURI().toString());
                    } else {
                        System.out.println(songs.get(selectedIndex).getName().toLowerCase());
                    }
                    mediaPlayer = new MediaPlayer(media);
                    mediaPlayer.play();
                    isPlaying = true;
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
            if(running == true){
                resetProgressBar();
            }
            currentMusicIndex++;
            mediaPlayer.stop();
            media = new Media(songs.get(currentMusicIndex).toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.play();
            isPlaying = true;
            playButtonImage.setImage(stopImage);
            musicListView.getSelectionModel().select(currentMusicIndex);
            startProgressBarTimer();
        }
    }

    @FXML
    private void previousButtonClicked(ActionEvent event) {
        if (currentMusicIndex > 0) {
            if(running == true){
                resetProgressBar();
            }
            currentMusicIndex--;
            mediaPlayer.stop();
            media = new Media(songs.get(currentMusicIndex).toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.play();
            playButtonImage.setImage(stopImage);
            isPlaying = true;
            musicListView.getSelectionModel().select(currentMusicIndex);
            startProgressBarTimer();
        }
    }

    @FXML
    private void selectDirectoryButtonClicked(ActionEvent event) {
        //Se o Player da Musica for nula, ou seja, nenhuma musica tiver sido tocada ainda
        if(mediaPlayer == null){
            DirectoryChooser directoryChooser = new DirectoryChooser();
            directory = directoryChooser.showDialog(selectDirectoryButton.getScene().getWindow());
            if (directory != null) {
                currentDirectoryFiles = directory.listFiles();
                updateMusicList(currentDirectoryFiles);
            }
        }

        //Se estiver tocando alguma musica ao selecionar o diretório
        else if(mediaPlayer != null && isPlaying){
            resetProgressBar();
            mediaPlayer.stop();
            playButtonImage.setImage(playImage);
            isPlaying = false;
            DirectoryChooser directoryChooser = new DirectoryChooser();
            directory = directoryChooser.showDialog(selectDirectoryButton.getScene().getWindow());
            if (directory != null) {
                currentDirectoryFiles = directory.listFiles();
                updateMusicList(currentDirectoryFiles);
            }
        }

        //Se tiver alguma musica pausada ao selecionar o diretório
        else if(mediaPlayer != null && !isPlaying){
            DirectoryChooser directoryChooser = new DirectoryChooser();
            directory = directoryChooser.showDialog(selectDirectoryButton.getScene().getWindow());
            if (directory != null) {
                currentDirectoryFiles = directory.listFiles();
                updateMusicList(currentDirectoryFiles);
            }
        }
    }

    //Definição das funções de funcionalidade da barra de progressão e update da lista de musicas
    private void updateMusicList(File[] files) {
        //limpa a lista
        musicListItems.clear();

        //insere apenas os arquivos .mp3 no array
        for (File file : files) {
            if (file.isFile() && file.getName().toLowerCase().endsWith(".mp3")) {
                songs.add(file);
            }
        }

        //adiciona o nome das músicas ao musiclistview
        if (!songs.isEmpty()) {
            for (File song : songs) {
                musicListItems.add(song.getName());
            }
            totalMusicCount = musicListItems.size();
            currentMusicIndex = -1;
            resetProgressBar();
        }
    }


    private void startProgressBarTimer() {
        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                running = true;
                double current = mediaPlayer.getCurrentTime().toSeconds();
                double end = media.getDuration().toSeconds();
                progressBar.setProgress(current/end);

                if(current/end == 1){
                    resetProgressBar();
                }
            }
        };
        timer.scheduleAtFixedRate(task, 1000, 1000);

    }

    private void resetProgressBar() {
        if(running == true) {
            running = false;
            timer.cancel();
        }
    }
}
package br.ufrn.imd.DAO;

import br.ufrn.imd.Modelo.Admin;
import br.ufrn.imd.Modelo.Playlist;
import br.ufrn.imd.Modelo.Usuario;
import br.ufrn.imd.Visao.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.ArrayList;

public class PlaylistDAO {
    private ArrayList<Playlist> playlists;
    private static PlaylistDAO playlistDAO;

    File arquivo = new File(System.getProperty("user.dir") + File.separator + "./src/main/java/br/ufrn/imd/Diretorios/playlists.txt");

    public PlaylistDAO(){
        playlists = new ArrayList<Playlist>();
    }

    public static PlaylistDAO getInstance(){
        if(playlistDAO == null){
            playlistDAO = new PlaylistDAO();
        }
        return playlistDAO;
    }

    public boolean addPlaylist(Playlist playlist){
        for(int i = 0; i < playlists.size(); i++){
            if(playlist.getPlaylistName().equals(playlists.get(i).getPlaylistName()) && Main.getUserName().equals(playlists.get(i).getOwnerName())){
                System.out.println("Playlist \u001B[33m" + playlist.getPlaylistName() + "\u001B[0m já foi cadastrada");
                return false;
            }
        }
        System.out.println("Playlist \u001B[36m" + playlist.getPlaylistName() + "\u001B[0m cadastrada com sucesso!");
        playlists.add(playlist);
        return true;
    }

    public boolean removePlaylist(Playlist playlist){
        for(int i = 0; i < playlists.size(); i++){
            if(playlist.getPlaylistName().equals(playlists.get(i).getPlaylistName()) && Main.getUserName().equals(playlists.get(i).getOwnerName())) {
                playlists.remove(playlists.get(i));
                System.out.println("Playlist \u001B[31m" + playlist.getPlaylistName() + "\u001B[0m removida com sucesso!");
                return true;
            }
        }
        System.out.println("Playlist \u001B[33m" + playlist.getPlaylistName() + "\u001B[0m não encontrada");
        return false;
    }

    public void salvarPlaylists() {
        try {
            FileOutputStream fileOut = new FileOutputStream(arquivo);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(playlists);
            out.close();
            fileOut.close();
            System.out.println("Playlist salvas com sucesso.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void carregarPlaylists() {
        try {
            if (!arquivo.exists()) {
                arquivo.createNewFile();
                System.out.println("Arquivo playlists.txt criado.");
                return;
            }

            FileInputStream fileIn = new FileInputStream(arquivo);
            if (arquivo.length() == 0) {
                System.out.println("O arquivo playlists.txt está vazio.");
                fileIn.close();
                return;
            }

            ObjectInputStream in = new ObjectInputStream(fileIn);
            playlists = (ArrayList<Playlist>) in.readObject();
            in.close();
            fileIn.close();
            System.out.println("Playlists carregadas com sucesso.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public boolean isEmpty(){
        if(playlists.size() == 0){
            return true;
        }
        return false;
    }


    public ObservableList<String> getPlaylistsNames() {
        ObservableList<String> playlistsNames = FXCollections.observableArrayList();
        for (Playlist playlist : playlists) {
            if(playlist.getOwnerName().equals(Main.getUserName())){
                playlistsNames.add(playlist.getPlaylistName());
            }
        }
        return playlistsNames;
    }
}

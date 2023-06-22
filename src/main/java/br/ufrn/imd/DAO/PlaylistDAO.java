package br.ufrn.imd.DAO;

import br.ufrn.imd.Modelo.Admin;
import br.ufrn.imd.Modelo.Playlist;
import br.ufrn.imd.Modelo.Usuario;
import br.ufrn.imd.Visao.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.ArrayList;

/**
 * PlaylistDAO class that stores an ArrayList of Playlist class
 * also implements singleton architecture to manage the array.
 *<br>
 * @author  Caio Vitor
 */
public class PlaylistDAO {
    private ArrayList<Playlist> playlists;
    private static PlaylistDAO playlistDAO;

    File arquivo = new File(System.getProperty("user.dir") + File.separator + "./src/main/java/br/ufrn/imd/Diretorios/playlists.txt");

    public PlaylistDAO(){
        playlists = new ArrayList<Playlist>();
    }

    /**
     * Method to get singleton instance
     * @return playlistDAO
     */
    public static PlaylistDAO getInstance(){
        if(playlistDAO == null){
            playlistDAO = new PlaylistDAO();
        }
        return playlistDAO;
    }

    /**
     * Method to add playlist in the arraylist
     * the method returns true if the operation suceeds and false if it doesn't.
     * @param playlist
     * @return boolean
     */
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

    /**
     * Method to remove playlist in the arraylist
     * the method returns true if the operation suceeds and false if it doesn't.
     * @param playlist
     * @return boolean
     */
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

    /**
     * Method to save playlist in the ".txt" file
     * the method uses an exception treatment to do so
     * printing a message if succeeded.
     * if it doesn't.
     */
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

    /**
     * Method to load playlists from ".txt" file
     * the method checks if the file exists and if the file is empty before reading it
     * printing a message for every case.
     */
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

    /**
     * Method to return an ObservableList of string that has the
     * names of each playlist in the arraylist of the DAO, the method
     * also does a check if the playlist owner's name is the same of the logged user.
     * If so, it adds the playlist name to the ObservableList.
     * @return playlistNames
     */
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

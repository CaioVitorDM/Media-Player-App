package br.ufrn.imd.Modelo;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Playlist class <br>
 * implements Serializable, to allow playlists to be saved in the ".txt" file.
 * <br>
 * @author  Caio Vitor
 */
public class Playlist implements Serializable {

    /**
     * Variables of the playlist <br>
     * all playlist must have a name, an owner name and an ArrayList of File class.
     */
    private String playlistName;
    private String ownerName;
    private ArrayList<File> songs;

    /**
     * Playlist constructor method <br>
     * Initializes the File ArrayList.
     */
    public Playlist(){
        songs = new ArrayList<File>();
    }

    /**
     * Method to add music in the playlist ArrayList (songs).<br>
     * Receives a File as parameter and then calls the ArrayList add() method.
     * @param file Music File.
     */
    public void addMusic(File file){
        songs.add(file);
    }

    /**
     * Method to remove music from the playlist ArrayList (songs).<br>
     * Receives a File as parameter and then calls the ArrayList remove() method.
     * @param file Music File.
     */
    public void removeMusic(File file){
        songs.remove(file);
    }

    /**
     * Method to set the playlist name.
     * @param name Playlist name.
     */
    public void setPlaylistName(String name){
        this.playlistName = name;
    }

    /**
     * Method to get the playlist name.
     * @return playlistName
     */
    public String getPlaylistName(){
        return this.playlistName;
    }

    /**
     * Method to set playlist's owner name.
     * @param name owner's name
     */
    public void setOwnerName(String name){
        this.ownerName = name;
    }

    /**
     * Method to get playlist's owner name
     * @return ownerName
     */

    public String getOwnerName(){
        return this.ownerName;
    }
}

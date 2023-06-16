package br.ufrn.imd.Modelo;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

public class Playlist implements Serializable {
    private String playlistName;
    private String ownerName;
    private ArrayList<File> songs;

    public Playlist(){
        songs = new ArrayList<File>();
    }

    public void addMusic(File file){
        songs.add(file);
    }
    public void removeMusic(File file){
        songs.remove(file);
    }

    public void setPlaylistName(String name){
        this.playlistName = name;
    }

    public String getPlaylistName(){
        return this.playlistName;
    }

    public void setOwnerName(String name){
        this.ownerName = name;
    }

    public String getOwnerName(){
        return this.ownerName;
    }
}

package br.ufrn.imd.Modelo;

import java.io.File;
import java.util.ArrayList;

public class UsuarioVIP extends Usuario{
   /* private ArrayList<File[]> playlists;*/

    /*public UsuarioVIP(){
        playlists = new ArrayList<File[]>();
    }*/
    public void printUsuario(){
        System.out.println("O nome deste usuário é: " + getNomeusuario() + ", do tipo Usuário VIP");
    }

    /*public void addPlaylist(File directory){
        playlists.add(directory.listFiles());
    }*/
    /*public void removePlaylist(File directory){
        playlists.add(directory.listFiles());
    }*/
}

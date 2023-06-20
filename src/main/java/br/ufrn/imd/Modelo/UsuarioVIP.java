package br.ufrn.imd.Modelo;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author  Caio Vitor
 *
 *<br>
 * VIP User class that inherits from User and.
 * implements Serializable, to allow VIP users to be saved in the ".txt" file.
 *
 */
public class UsuarioVIP extends Usuario implements Serializable {

    /**
     * Overridden method so the class can inherit from User.
     * prints the type and the user's name.
     */
    public void printUsuario(){
        System.out.println("VIP User: " + getNomeusuario());
    }

}

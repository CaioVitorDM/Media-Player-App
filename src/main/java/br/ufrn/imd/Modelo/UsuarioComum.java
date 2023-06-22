package br.ufrn.imd.Modelo;

import java.io.Serializable;

/**
 * Common User class that inherits from User and.
 * implements Serializable, to allow Common users to be saved in the ".txt" file.
 *<br>
 * @author  Caio Vitor
 */
public class UsuarioComum  extends  Usuario implements Serializable {

    /**
     * Overridden method so the class can inherit from User.
     * prints the type and the user's name.
     */
    public void printUsuario(){
        System.out.println("Common User: " + getNomeusuario());
    }
}

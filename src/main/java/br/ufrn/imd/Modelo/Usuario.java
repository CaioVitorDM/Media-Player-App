package br.ufrn.imd.Modelo;

import java.io.Serializable;

/**
 * @author  Caio Vitor
 *
 *<br>
 * Abstract class that will be inherited by all kind of users.
 * implements Serializable, to allow users to be saved in the ".txt" file.
 *
 */
public abstract class Usuario implements Serializable {

    /**
     * Variables that subclasses will inherit
     */

    private String nomeusuario;
    private String senha;

    /**
     * Method to set username.
     * @param nomeUsuario - refers to the user's name
     */
    public void setNomeUsuario(String nomeUsuario){
        this.nomeusuario = nomeUsuario;
    }

    /**
     * Method to set password.
     * @param senha refers to the user's password
     */
    public void setSenha(String senha){
        this.senha = senha;
    }

    /**
     * Method to get username.
     * @return nomeusuario
     */
    public String getNomeusuario(){
        return this.nomeusuario;
    }

    /**
     * Method to get password.
     * @return senha
     */
    public String getSenha(){
        return this.senha;
    }

    /**
     * Abstract method to be able to define the abstract class.
     */
    public abstract void printUsuario();

}

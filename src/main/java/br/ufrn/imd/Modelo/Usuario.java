package br.ufrn.imd.Modelo;

import java.io.Serializable;

public abstract class Usuario implements Serializable {
    private String nomeusuario;
    private String senha;

    public void setNomeUsuario(String nomeUsuario){
        this.nomeusuario = nomeUsuario;
    }
    public void setSenha(String senha){
        this.senha = senha;
    }
    public String getNomeusuario(){
        return this.nomeusuario;
    }
    public String getSenha(){
        return this.senha;
    }
    public abstract void printUsuario();

}

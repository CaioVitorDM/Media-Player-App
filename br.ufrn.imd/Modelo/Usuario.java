package br.ufrn.imd.Modelo;

public abstract class Usuario{
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

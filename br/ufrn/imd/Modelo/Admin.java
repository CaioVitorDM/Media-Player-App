package br.ufrn.imd.Modelo;

public class Admin extends Usuario{
    public Admin(){
        setNomeUsuario("Admin");
        setSenha("Password");
    }

    public void printUsuario() {
        System.out.println("User Admin");
    }
}

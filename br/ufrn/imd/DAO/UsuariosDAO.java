package br.ufrn.imd.DAO;

import br.ufrn.imd.Modelo.Admin;
import br.ufrn.imd.Modelo.Usuario;

import java.util.ArrayList;

public class UsuariosDAO {
    private ArrayList<Usuario> listaUsuarios;
    private static UsuariosDAO usuariosDAO;

    public UsuariosDAO(){
        listaUsuarios = new ArrayList<Usuario>();
        Admin userAdmin = new Admin();
        listaUsuarios.add(userAdmin);
    }

    //Singleton
    public static UsuariosDAO getInstance(){
        if(usuariosDAO == null){
            usuariosDAO = new UsuariosDAO();
        }
        return usuariosDAO;
    }

    public boolean addUsuario(Usuario usuario){
        for(int i = 0; i < listaUsuarios.size(); i++){
            if(usuario.getNomeusuario().equals(listaUsuarios.get(i).getNomeusuario()) || usuario.getNomeusuario().equals("Admin")){
                System.out.println("Usuário \u001B[33m" + usuario.getNomeusuario() + "\u001B[0m já foi cadastrado");
                return false;
            }
        }
        System.out.println("Usuário \u001B[36m" + usuario.getNomeusuario() + "\u001B[0m cadastrado com sucesso!");
        listaUsuarios.add(usuario);
        return true;
    }

    public boolean removeUsuario(Usuario usuario){
        for(int i = 0; i < listaUsuarios.size(); i++){
            if(usuario.getNomeusuario().equals(listaUsuarios.get(i).getNomeusuario()) && !usuario.getNomeusuario().equals("Admin")) {
                listaUsuarios.remove(listaUsuarios.get(i));
                System.out.println("Usuário \u001B[31m" + usuario.getNomeusuario() + "\u001B[0m removido com sucesso!");
                return true;
            }
        }
        System.out.println("Usuário \u001B[33m" + usuario.getNomeusuario() + "\u001B[0m não encontrado");
        return false;
    }

    public void listUsuarios(){
        System.out.println("-----------------------------------------------------------------");
        for(int i = 0; i < listaUsuarios.size(); i++){
            listaUsuarios.get(i).printUsuario();
        }
        System.out.println("-----------------------------------------------------------------");
    }
}

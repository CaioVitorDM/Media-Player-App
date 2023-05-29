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

    public void addUsuario(Usuario usuario){
        listaUsuarios.add(usuario);
        System.out.println("Usuário: " + usuario.getNomeusuario() + " inserido com sucesso!");

    }

    public void removeUsuario(Usuario usuario){
        listaUsuarios.remove(usuario);
        System.out.println("Usuário: " + usuario.getNomeusuario() + " removido com sucesso!");
    }

    public void listUsuarios(){
        System.out.println("-----------------------------------------------------------------");
        for(int i = 0; i < listaUsuarios.size(); i++){
            listaUsuarios.get(i).printUsuario();
        }
        System.out.println("-----------------------------------------------------------------");
    }
}

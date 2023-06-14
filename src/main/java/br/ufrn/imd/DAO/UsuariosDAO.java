package br.ufrn.imd.DAO;

import br.ufrn.imd.Modelo.Admin;
import br.ufrn.imd.Modelo.Usuario;
import br.ufrn.imd.Modelo.UsuarioVIP;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.ArrayList;

public class UsuariosDAO {
    private ArrayList<Usuario> listaUsuarios;
    private static UsuariosDAO usuariosDAO;
    File arquivo = new File(System.getProperty("user.dir") + File.separator + "./src/main/java/br/ufrn/imd/Diretorios/usuarios.txt");

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

    public void salvarUsuarios() {
        try {
            FileOutputStream fileOut = new FileOutputStream(arquivo);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(listaUsuarios);
            out.close();
            fileOut.close();
            System.out.println("Usuários salvos com sucesso.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void carregarUsuarios() {
        try {
            if (!arquivo.exists()) {
                arquivo.createNewFile();
                System.out.println("Arquivo usuarios.txt criado.");
                // Adicionar o usuário Admin padrão
                Admin userAdmin = new Admin();
                listaUsuarios.add(userAdmin);
                salvarUsuarios(); // Salvar a lista atualizada no arquivo
                return;
            }

            FileInputStream fileIn = new FileInputStream(arquivo);
            if (arquivo.length() == 0) {
                System.out.println("O arquivo usuarios.txt está vazio.");
                fileIn.close();
                return;
            }

            ObjectInputStream in = new ObjectInputStream(fileIn);
            listaUsuarios = (ArrayList<Usuario>) in.readObject();
            in.close();
            fileIn.close();
            System.out.println("Usuários carregados com sucesso.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public boolean loginUser(String nome, String senha){
        for(int i = 0; i < listaUsuarios.size(); i++){
            if(listaUsuarios.get(i).getNomeusuario().equals(nome) && listaUsuarios.get(i).getSenha().equals(senha)){
                return true;
            }
        }
        return false;
    }

    public boolean isUserVIP(String nome, String senha){
        for(int i = 0; i < listaUsuarios.size(); i++){
            if(listaUsuarios.get(i).getNomeusuario().equals(nome) && listaUsuarios.get(i).getSenha().equals(senha)){
                if(listaUsuarios.get(i) instanceof UsuarioVIP){
                    return true;
                }
                else{
                    return false;
                }
            }
        }
        return false;
    }

    public boolean isUserAdmin(String nome, String senha){
        for(int i = 0; i < listaUsuarios.size(); i++){
            if(listaUsuarios.get(i).getNomeusuario().equals(nome) && listaUsuarios.get(i).getSenha().equals(senha)){
                if(listaUsuarios.get(i) instanceof Admin){
                    return true;
                }
                else{
                    return false;
                }
            }
        }
        return false;
    }

    //Metodo para retornar uma Lista com os nomes dos usuários para inserir na tabela de listagem
    public ObservableList<String> getNomesUsuarios() {
        ObservableList<String> nomesUsuarios = FXCollections.observableArrayList();
        for (Usuario usuario : listaUsuarios) {
            nomesUsuarios.add(usuario.getNomeusuario());
        }
        return nomesUsuarios;
    }
}

package br.ufrn.imd.DAO;

import br.ufrn.imd.Modelo.Admin;
import br.ufrn.imd.Modelo.Usuario;
import br.ufrn.imd.Modelo.UsuarioVIP;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.ArrayList;

/**
 * UsuariosDAO class that stores an ArrayList of Usuario class
 * also implements singleton architecture to manage the array.
 * <br>
 * @author  Caio Vitor
 */
public class UsuariosDAO {
    private ArrayList<Usuario> listaUsuarios;
    private static UsuariosDAO usuariosDAO;
    File arquivo = new File(System.getProperty("user.dir") + File.separator + "./src/main/java/br/ufrn/imd/Diretorios/usuarios.txt");

    public UsuariosDAO(){
        listaUsuarios = new ArrayList<Usuario>();
        Admin userAdmin = new Admin();
        listaUsuarios.add(userAdmin);
    }

    /**
     * Method to get singleton instance
     * @return usuariosDAO
     */
    public static UsuariosDAO getInstance(){
        if(usuariosDAO == null){
            usuariosDAO = new UsuariosDAO();
        }
        return usuariosDAO;
    }

    /**
     * Method to add users in the arraylist
     * the method returns true if the operation suceeds and false
     * if it doesn't.
     * @param usuario
     * @return boolean
     */
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

    /**
     * Method to remove users in the arraylist
     * the method returns true if the operation succeeds and false
     * if it doesn't.
     * @param usuario
     * @return boolean
     */
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

    /**
     * Method to save users in the ".txt" file
     * the method uses an exception treatment to do so
     * printing a message if succeeded.
     * if it doesn't.
     */
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


    /**
     * Method to load users from ".txt" file
     * the method checks if the file exists and if the file is empty before reading it
     * printing a message for every case.
     */
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

    /**
     * Method that is used to make the login of the user
     * receiving the name and the password of the user and then
     * returns a boolean if the operation succeed or fail.
     * @param nome
     * @param senha
     * @return boolean
     */
    public boolean loginUser(String nome, String senha){
        for(int i = 0; i < listaUsuarios.size(); i++){
            if(listaUsuarios.get(i).getNomeusuario().equals(nome) && listaUsuarios.get(i).getSenha().equals(senha)){
                return true;
            }
        }
        return false;
    }

    /**
     * Method that is used to check if the logged user is VIP
     * receiving the name and the password of the user and then
     * returns a boolean.
     * @param nome
     * @param senha
     * @return boolean
     */
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

    /**
     * Method that is used to check if the logged user is Admin
     * receiving the name and the password of the user and then
     * returns a boolean.
     * @param nome
     * @param senha
     * @return boolean
     */
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

    /**
     * Method to return an ObservableList of string that has the
     * names of each user in the arraylist of the DAO, and also puts
     * the type of the users beside the name.
     * @return nomeUsuarios
     */
    public ObservableList<String> getNomesUsuarios() {
        ObservableList<String> nomesUsuarios = FXCollections.observableArrayList();
        for (Usuario usuario : listaUsuarios) {
            if(isUserVIP(usuario.getNomeusuario(), usuario.getSenha())){
                nomesUsuarios.add(usuario.getNomeusuario() + " - VIP");
            }
            else if(isUserAdmin(usuario.getNomeusuario(), usuario.getSenha())){
                nomesUsuarios.add(usuario.getNomeusuario());
            }
            else{
                nomesUsuarios.add(usuario.getNomeusuario() + " - Common");
            }

        }
        return nomesUsuarios;
    }
}

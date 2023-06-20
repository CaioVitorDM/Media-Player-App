package br.ufrn.imd.Modelo;

/**
 * @author  Caio Vitor
 *
 *<br>
 * Admin User class that inherits from User and.
 * implements Serializable, to allow Admin User to be saved in the ".txt" file.
 *
 */
public class Admin extends Usuario{

    /**
     * Admin class constructor method
     * Calls the setNomeUsuario() and setSenha() methods
     * to define the name and the password of Admin user, making then the pattern ones.
     */
    public Admin(){
        setNomeUsuario("Admin");
        setSenha("Password");
    }

    /**
     * Overridden method so the class can inherit from User.
     * prints the type and the user's name.
     */
    public void printUsuario() {
        System.out.println("User Admin");
    }
}

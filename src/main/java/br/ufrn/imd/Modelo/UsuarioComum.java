package br.ufrn.imd.Modelo;

import java.io.Serializable;

public class UsuarioComum  extends  Usuario implements Serializable {
    public void printUsuario(){
        System.out.println("Common User: " + getNomeusuario());
    }
}

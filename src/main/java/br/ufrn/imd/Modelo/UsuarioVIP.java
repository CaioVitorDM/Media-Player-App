package br.ufrn.imd.Modelo;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

public class UsuarioVIP extends Usuario implements Serializable {
    public void printUsuario(){
        System.out.println("VIP User: " + getNomeusuario());
    }

}

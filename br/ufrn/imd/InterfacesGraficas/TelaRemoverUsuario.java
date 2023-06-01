package br.ufrn.imd.InterfacesGraficas;

import br.ufrn.imd.DAO.UsuariosDAO;
import br.ufrn.imd.Modelo.UsuarioComum;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaRemoverUsuario extends JInternalFrame implements ActionListener{

    private static final long serialVersionUID = 1L;
    private static UsuariosDAO usuariosDAO;

    JLabel lnome   = new JLabel("Nome de Usuário:");

    private Font f	= new Font("Courier", Font.PLAIN, 12);

    //campos
    JTextField tnome   = new JTextField();

    //botões
    JButton btSubmeter = new JButton("Submeter");
    JButton btLimpar = new JButton("Limpar");

    public TelaRemoverUsuario(String str)  {
        super(str,false,true);


        setLayout(new GridLayout(2, 2));


        // setando a fonte
        lnome.setFont(f);

        // adicionando componentes
        add(lnome);
        add(tnome);
        add(btSubmeter);
        add(btLimpar);

        // evento dos bot�es
        btSubmeter.addActionListener(this);
        btLimpar.addActionListener(this);

        // especifica��es do formul�rio
        setSize(300,250);
        setTitle(str);

        //bc = new Repositorio();
        usuariosDAO = UsuariosDAO.getInstance();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btSubmeter){
            // setar atributos Cliente
            UsuarioComum c = new UsuarioComum();

            c.setNomeUsuario(tnome.getText());
            c.setSenha("padrão");

            // persistir dados
            if(usuariosDAO.removeUsuario(c)){
                JOptionPane.showMessageDialog(null, "Usuário " + c.getNomeusuario() + " removido!");
            }
            else{
                JOptionPane.showMessageDialog(null, "Usuário " + c.getNomeusuario() + " não encontrado!");
            }
            tnome.setText("");

            tnome.requestFocus();
        }
        else if(e.getSource() == btLimpar){
            tnome.setText("");
        }
    }
}

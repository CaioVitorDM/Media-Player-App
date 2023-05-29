package br.ufrn.imd.Interfaces;

import br.ufrn.imd.DAO.UsuariosDAO;
import br.ufrn.imd.Modelo.UsuarioComum;
import br.ufrn.imd.Modelo.UsuarioVIP;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaCadastroVIP extends JInternalFrame implements ActionListener{

    private static final long serialVersionUID = 1L;
    private static UsuariosDAO usuariosDAO;

    JLabel lnome   = new JLabel("Nome de Usuário:");
    JLabel lsenha = new JLabel("Senha:");

    private Font f	= new Font("Courier", Font.PLAIN, 12);

    //campos
    JTextField tnome   = new JTextField();
    JTextField tsenha    = new JTextField();

    //botões
    JButton btSubmeter = new JButton("Submeter");
    JButton btLimpar = new JButton("Limpar");

    public TelaCadastroVIP(String str)  {
        super(str,false,true);


        setLayout(new GridLayout(3, 2));


        // setando a fonte
        lnome.setFont(f);
        lsenha.setFont(f);

        // adicionando componentes
        add(lnome);
        add(tnome);
        add(lsenha);
        add(tsenha);
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
            UsuarioVIP c = new UsuarioVIP();

            c.setNomeUsuario(tnome.getText());
            c.setSenha(tsenha.getText());

            // persistir dados
            if(usuariosDAO.addUsuario(c)) {
                JOptionPane.showMessageDialog(null, "Usuário " + c.getNomeusuario() + " cadastrado!");
            }
            else{
                JOptionPane.showMessageDialog(null, "Usuário " + c.getNomeusuario() + " já foi cadastrado!");
            }
            tnome.setText("");
            tsenha.setText("");

            tnome.requestFocus();
        }
        else if(e.getSource() == btLimpar){
            tnome.setText("");
            tsenha.setText("");
        }
    }
}

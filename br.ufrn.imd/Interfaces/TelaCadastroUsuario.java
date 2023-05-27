package br.ufrn.imd.Interfaces;

import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

import br.ufrn.imd.Modelo.UsuarioComum;
import br.ufrn.imd.Modelo.Usuario;
import br.ufrn.imd.DAO.UsuariosDAO;
public class TelaCadastroUsuario extends JInternalFrame implements ActionListener{

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

    public TelaCadastroUsuario(String str)  {
        super(str,false,true);

        Container ct = this.getContentPane();
        ct.setLayout(null);

        // setando a fonte
        lnome.setFont(f);
        lsenha.setFont(f);

        // coordenadas
        lnome.setBounds(10, 10, 100, 30);
        tnome.setBounds(80, 10, 200, 25);

        lsenha.setBounds(10, 50, 100, 30);
        tsenha.setBounds(80, 50, 65, 25);

        btSubmeter.setBounds(50, 150, 100, 30);
        btLimpar.setBounds(230, 150, 100, 30);


        // adicionando componentes
        ct.add(lnome);
        ct.add(tnome);
        ct.add(lsenha);
        ct.add(tsenha);
        ct.add(btSubmeter);
        ct.add(btLimpar);

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
            c.setSenha(tsenha.getText());

            // persistir dados
            usuariosDAO.addUsuario(c);
            JOptionPane.showMessageDialog(null, "Usuário " + c.getNomeusuario() + " incluído!");

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

package br.ufrn.imd.InterfacesGraficas;

import br.ufrn.imd.DAO.UsuariosDAO;
import br.ufrn.imd.Modelo.Admin;
import br.ufrn.imd.Modelo.UsuarioComum;
import br.ufrn.imd.Modelo.UsuarioVIP;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TelaLogin extends JFrame implements ActionListener {
    private static UsuariosDAO usuariosDAO;
    JDesktopPane dtp = new JDesktopPane();
    JLabel lnome   = new JLabel("Nome de Usuário:");
    JLabel lsenha = new JLabel("Senha:");

    private Font f = new Font("Courier", Font.PLAIN, 18); // Alterado o tamanho da fonte

    //campos
    JTextField tnome   = new JTextField();
    JPasswordField tsenha = new JPasswordField();

    JButton btSubmeter = new JButton("Submeter");
    JButton btLimpar = new JButton("Limpar");

    public TelaLogin() {
        usuariosDAO = UsuariosDAO.getInstance();
        usuariosDAO.carregarUsuarios();

        lnome.setFont(f);
        lsenha.setFont(f);

        // Definindo o layout como GridLayout com 3 linhas e 2 colunas
        setLayout(new BorderLayout());

        // Centralizando os componentes
        lnome.setHorizontalAlignment(SwingConstants.CENTER);
        lsenha.setHorizontalAlignment(SwingConstants.CENTER);
        btSubmeter.setHorizontalAlignment(SwingConstants.CENTER);
        btLimpar.setHorizontalAlignment(SwingConstants.CENTER);

        // Definindo o tamanho dos botões
        btSubmeter.setPreferredSize(new Dimension(80, 25)); // Altere as dimensões de acordo com o tamanho desejado
        btLimpar.setPreferredSize(new Dimension(80, 25)); // Altere as dimensões de acordo com o tamanho desejado

        // adicionando componentes
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10)); // GridLayout com 3 linhas e 2 colunas
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Adiciona uma margem ao painel

// Adicione os componentes ao painel
        panel.add(lnome);
        panel.add(tnome);
        panel.add(lsenha);
        panel.add(tsenha);
        panel.add(btSubmeter);
        panel.add(btLimpar);

        add(panel, BorderLayout.CENTER); // Adiciona o painel ao centro do JFrame

        // evento dos botões
        btSubmeter.addActionListener(this);
        btLimpar.addActionListener(this);

        setSize(1000, 700);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
        setTitle("Music Player App");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btSubmeter) {
            // checagem do login
            if (usuariosDAO.loginUser(tnome.getText(), new String(tsenha.getPassword()))) {
                JOptionPane.showMessageDialog(null, "Login Realizado com Sucesso!");

                //Checagem para saber de qual tipo é o usuário e qual vai ser sua tela
                if(usuariosDAO.isUserVIP(tnome.getText(), new String(tsenha.getPassword()))){
                    setVisible(false); // Oculta a tela de login
                    TelaPrincipal telaPrincipal = new TelaPrincipal();
                    //passando o usuário logado para a tela principal
                    telaPrincipal.setUser(tnome.getText());
                }
                else if(usuariosDAO.isUserAdmin(tnome.getText(), new String(tsenha.getPassword()))){
                    setVisible(false); // Oculta a tela de login
                    TelaPrincipal telaPrincipalAdmin = new TelaPrincipal();
                    //passando o usuário logado para a tela principal
                    telaPrincipalAdmin.setUser(tnome.getText());
                }
                else{
                    setVisible(false); // Oculta a tela de login
                    TelaPrincipalComum telaPrincipalComum = new TelaPrincipalComum();
                    //passando o usuário logado para a tela principal
                    TelaPrincipalComum.setUser(tnome.getText());
                }

            } else {
                JOptionPane.showMessageDialog(null, "Usuário ou senha inválidos!");
            }

            tnome.setText("");
            tsenha.setText("");

            tnome.requestFocus();
        } else if (e.getSource() == btLimpar) {
            tnome.setText("");
            tsenha.setText("");
        }
    }

}

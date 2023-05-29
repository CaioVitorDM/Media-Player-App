package br.ufrn.imd.Interfaces;

import br.ufrn.imd.DAO.UsuariosDAO;
import br.ufrn.imd.Modelo.PlayerMusic;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.io.File;

public class TelaPrincipal extends JFrame implements ActionListener{
    private PlayerMusic player;
    private boolean isPlaying;
    private JList<String> musicList;
    private int currentMusicIndex = -1;
    private int totalMusicCount = 0;
    private JProgressBar progressBar;
    private Timer progressBarTimer;
    private static UsuariosDAO usuariosDAO;

    JDesktopPane dtp = new JDesktopPane();

    //Criação dos menus na Menu Bar
    JMenuBar menuBar = new JMenuBar();
    JMenu menuCadastro = new JMenu("Cadastros");
    JMenu menuListagem = new JMenu("Listagem");
    JMenu menuAjuda = new JMenu("Ajuda");


    //Criação dos itens que vão em cada menu
    JMenuItem mCadUsuario = new JMenuItem("Cadastrar usuários");
    JMenuItem mCadUsuarioVIP = new JMenuItem("Cadastrar usuários VIP's");
    JMenuItem mRemUsuario = new JMenuItem("Remover usuário");


    JMenuItem mListUsuarios = new JMenuItem("Listar Usuários");

    JMenuItem mItem7 = new JMenuItem("Sobre");
    JMenuItem mItem8 = new JMenuItem("Sair");


    //criação dos botões
    JButton playButton = new JButton("Play");
    JButton stopButton = new JButton("Stop");
    JButton selectDirectoryButton = new JButton("Select Directory");
    JButton nextButton = new JButton(">>");
    JButton previousButton = new JButton("<<");
    JScrollPane scrollPane;


    public TelaPrincipal() {
        player = null;
        isPlaying = false;
        usuariosDAO = UsuariosDAO.getInstance();

        DefaultListModel<String> listModel = new DefaultListModel<>();
        musicList = new JList<>(listModel);
        musicList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        musicList.setLayoutOrientation(JList.VERTICAL);
        musicList.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    currentMusicIndex = musicList.getSelectedIndex();
                }
            }
        });

        scrollPane = new JScrollPane(musicList);
        scrollPane.setPreferredSize(new Dimension(250, 400));

        //criação dos actions listeners
        playButton.addActionListener(this);
        stopButton.addActionListener(this);
        selectDirectoryButton.addActionListener(this);
        nextButton.addActionListener(this);
        previousButton.addActionListener(this);
        mCadUsuario.addActionListener(this);
        mCadUsuarioVIP.addActionListener(this);
        mRemUsuario.addActionListener(this);
        mListUsuarios.addActionListener(this);
        mItem8.addActionListener(this);

        progressBar = new JProgressBar();
        progressBar.setStringPainted(true);

        setJMenuBar(menuBar);
        menuBar.add(menuCadastro);
        menuBar.add(menuListagem);
        menuBar.add(menuAjuda);

        menuCadastro.add(mCadUsuario);
        menuCadastro.add(mCadUsuarioVIP);
        menuCadastro.add(mRemUsuario);


        menuListagem.add(mListUsuarios);

        menuAjuda.add(mItem7);
        menuAjuda.addSeparator();
        menuAjuda.add(mItem8);

        dtp.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        constraints.fill = GridBagConstraints.BOTH;
        dtp.add(scrollPane, constraints);

        constraints.gridy = 1;
        constraints.weighty = 0.0;
        constraints.anchor = GridBagConstraints.CENTER;
        dtp.add(previousButton, constraints);

        constraints.gridx = 1;
        dtp.add(playButton, constraints);

        constraints.gridx = 2;
        dtp.add(stopButton, constraints);

        constraints.gridx = 3;
        dtp.add(nextButton, constraints);

        constraints.gridx = 4; // Adicionado novo botão ao painel
        dtp.add(selectDirectoryButton, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 5; // Aumentar gridwidth para acomodar novo botão
        dtp.add(progressBar, constraints);

        add(dtp);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
        setTitle("Music Player App");
    }


    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == mCadUsuario) {
            try {
                System.out.println("Esperando...");
                Thread.sleep(500);
            } catch (InterruptedException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }

            System.out.println("Pronto.");

            TelaCadastroUsuario tlUsuario = new TelaCadastroUsuario("Cadastrar Usuário");
            dtp.add(tlUsuario);
            tlUsuario.setVisible(true);
        }

        if(e.getSource() == mCadUsuarioVIP){
            try{
                System.out.println("Esperando...");
                Thread.sleep(500);
            }
            catch (InterruptedException e1){
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }

            System.out.println("Pronto.");

            TelaCadastroVIP tlUsuarioVIP = new TelaCadastroVIP("Cadastrar Usuário VIP");
            dtp.add(tlUsuarioVIP);
            tlUsuarioVIP.setVisible(true);
        }

        if(e.getSource() == mRemUsuario){
            try{
                System.out.println("Esperando...");
                Thread.sleep(500);
            }
            catch (InterruptedException e1){
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }

            System.out.println("Pronto.");

            TelaRemoverUsuario tlRemUser = new TelaRemoverUsuario("Remover usuário");
            dtp.add(tlRemUser);
            tlRemUser.setVisible(true);
        }

        if(e.getSource() == mListUsuarios){
            usuariosDAO = UsuariosDAO.getInstance();
            usuariosDAO.listUsuarios();
        }

        if(e.getSource() == playButton){
            if (!isPlaying && player != null) {
                int selectedIndex = musicList.getSelectedIndex();
                if (selectedIndex != -1) {
                    player.tocarMusica(selectedIndex);
                    isPlaying = true;
                    currentMusicIndex = selectedIndex;
                    musicList.setSelectedIndex(selectedIndex);
                    startProgressBarTimer();
                }
            }
            else if(isPlaying && player != null){
                player.pararMusica();
                int selectedIndex = musicList.getSelectedIndex();
                if (selectedIndex != -1) {
                    player.tocarMusica(selectedIndex);
                    isPlaying = true;
                    currentMusicIndex = selectedIndex;
                    musicList.setSelectedIndex(selectedIndex);
                    startProgressBarTimer();
                }
            }
            else if(!isPlaying && player == null){
                int selectedIndex = musicList.getSelectedIndex();
                if (selectedIndex != -1) {
                    player.tocarMusica(selectedIndex);
                    isPlaying = true;
                    currentMusicIndex = selectedIndex;
                    musicList.setSelectedIndex(selectedIndex);
                    startProgressBarTimer();
                }
            }
        }

        if(e.getSource() == stopButton){
            if (player != null) {
                player.pararMusica();
                isPlaying = false;
                stopProgressBarTimer();
                resetProgressBar();
            }
        }

        if(e.getSource() == selectDirectoryButton){
            if(player == null) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int result = fileChooser.showOpenDialog(TelaPrincipal.this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedDirectory = fileChooser.getSelectedFile();
                    player = new PlayerMusic(selectedDirectory);

                    // Atualizar a lista de músicas ao selecionar um novo diretório
                    updateMusicList(selectedDirectory);
                }
            }
            else if(player != null && player.isPlaying()){
                player.pararMusica();
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int result = fileChooser.showOpenDialog(TelaPrincipal.this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedDirectory = fileChooser.getSelectedFile();
                    player = new PlayerMusic(selectedDirectory);

                    // Atualizar a lista de músicas ao selecionar um novo diretório
                    updateMusicList(selectedDirectory);
                }
            }
            else if(player != null && !player.isPlaying()){
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int result = fileChooser.showOpenDialog(TelaPrincipal.this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedDirectory = fileChooser.getSelectedFile();
                    player = new PlayerMusic(selectedDirectory);

                    // Atualizar a lista de músicas ao selecionar um novo diretório
                    updateMusicList(selectedDirectory);
                }
            }
        }

        if(e.getSource() == nextButton){
            if (currentMusicIndex < totalMusicCount - 1) {
                currentMusicIndex++;
                player.pararMusica();
                player.tocarMusica(currentMusicIndex);
                isPlaying = true;
                musicList.setSelectedIndex(currentMusicIndex);
                startProgressBarTimer();
            }
        }

        if(e.getSource() == previousButton){
            if (currentMusicIndex > 0) {
                currentMusicIndex--;
                player.pararMusica();
                player.tocarMusica(currentMusicIndex);
                isPlaying = true;
                musicList.setSelectedIndex(currentMusicIndex);
                startProgressBarTimer();
            }
        }

        if(e.getSource() == mItem8){
            try {
                System.out.println("Fechando o sistema...");
                Thread.sleep(500);
            } catch (InterruptedException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            System.exit(0);

        }
    }






    private void updateMusicList(File directory) {
            DefaultListModel<String> listModel = (DefaultListModel<String>) musicList.getModel();
            listModel.clear();

            if (directory != null && directory.isDirectory()) {
                File[] files = directory.listFiles();
                if (files != null) {
                    for (File file : files) {
                        if (file.isFile() && file.getName().toLowerCase().endsWith(".mp3")) {
                            listModel.addElement(file.getName());
                        }
                    }
                    totalMusicCount = listModel.getSize();
                    currentMusicIndex = -1;
                    stopProgressBarTimer();
                    resetProgressBar();
                }
            }
        }


    private void startProgressBarTimer() {
        stopProgressBarTimer();
        progressBarTimer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (player != null && player.isPlaying()) {
                    String progress = player.getProgress();
                    progressBar.setString(progress);
                }
            }
        });
        progressBarTimer.start();
    }

    private void stopProgressBarTimer() {
        if (progressBarTimer != null) {
            progressBarTimer.stop();
        }
    }

    private void resetProgressBar() {
        progressBar.setValue(0);
        progressBar.setString("00:00 / 00:00");
    }
}

package br.ufrn.imd.GUI;

import br.ufrn.imd.Modelo.PlayerMusic;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.io.File;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PlayerInterface extends JFrame {
    private PlayerMusic player;
    private boolean isPlaying;
    private JList<String> musicList;
    private int currentMusicIndex = -1;
    private int totalMusicCount = 0;
    private JProgressBar progressBar;
    private Timer progressBarTimer;

    public PlayerInterface() {
        player = null;
        isPlaying = false;

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

        JScrollPane scrollPane = new JScrollPane(musicList);
        scrollPane.setPreferredSize(new Dimension(250, 80));

        JButton playButton = new JButton("Play");
        playButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
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
            }
        });

        JButton stopButton = new JButton("Stop");
        stopButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (player != null) {
                    player.pararMusica();
                    isPlaying = false;
                    stopProgressBarTimer();
                    resetProgressBar();
                }
            }
        });

        JButton selectDirectoryButton = new JButton("Select Directory");
        selectDirectoryButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int result = fileChooser.showOpenDialog(PlayerInterface.this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedDirectory = fileChooser.getSelectedFile();
                    player = new PlayerMusic(selectedDirectory);

                    // Atualizar a lista de músicas ao selecionar um novo diretório
                    updateMusicList(selectedDirectory);
                }
            }
        });

        JButton nextButton = new JButton("Next");
        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (currentMusicIndex < totalMusicCount - 1) {
                    currentMusicIndex++;
                    player.pararMusica();
                    player.tocarMusica(currentMusicIndex);
                    isPlaying = true;
                    musicList.setSelectedIndex(currentMusicIndex);
                    startProgressBarTimer();
                }
            }
        });

        JButton previousButton = new JButton("Previous");
        previousButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (currentMusicIndex > 0) {
                    currentMusicIndex--;
                    player.pararMusica();
                    player.tocarMusica(currentMusicIndex);
                    isPlaying = true;
                    musicList.setSelectedIndex(currentMusicIndex);
                    startProgressBarTimer();
                }
            }
        });

        progressBar = new JProgressBar();
        progressBar.setStringPainted(true);

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;

                // Define as cores do degradê
                Color startColor = Color.BLUE;
                Color endColor = Color.CYAN;

                // Define o ponto inicial e final do degradê
                int startX = 0;
                int startY = 0;
                int endX = getWidth();
                int endY = getHeight();

                // Cria e define o degradê
                GradientPaint gradientPaint = new GradientPaint(startX, startY, startColor, endX, endY, endColor);
                g2d.setPaint(gradientPaint);

                // Preenche o retângulo do degradê
                g2d.fill(new Rectangle2D.Double(0, 0, getWidth(), getHeight()));

                // Chama o paintComponent padrão para desenhar os outros componentes
                super.paintComponent(g);
            }
        };

        panel.setLayout(new FlowLayout());
        panel.add(scrollPane);
        panel.add(previousButton);
        panel.add(playButton);
        panel.add(stopButton);
        panel.add(nextButton);
        panel.add(selectDirectoryButton);
        panel.add(progressBar);

        add(panel);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setVisible(true);
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



    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new PlayerInterface();
            }
        });
    }
}

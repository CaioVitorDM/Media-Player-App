package br.ufrn.imd.Modelo;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.AudioDevice;
import javazoom.jl.player.FactoryRegistry;
import javazoom.jl.player.Player;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;

import java.io.*;

public class Song {
    private File[] musicFiles;
    private Player player;
    private Thread thread;
    private boolean isPlaying;
    private int currentMusicIndex;

    public Song(File directory) {
        this.musicFiles = directory.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(".mp3");
            }
        });
        this.currentMusicIndex = -1;
    }

    public void tocarMusica(int index) {
        thread = new Thread(() -> {
            try {
                if (musicFiles != null && musicFiles.length > index && isPlaying()) {
                    File file = musicFiles[index];
                    AudioDevice audioDevice = FactoryRegistry.systemRegistry().createAudioDevice();
                    player = new Player(new FileInputStream(file), audioDevice);
                    player.play();

                    while (!player.isComplete()) {
                        if (Thread.interrupted()) {
                            player.close();
                            return;
                        }
                    }
                }
            } catch (JavaLayerException | FileNotFoundException e) {
                e.printStackTrace();
            } finally {
                if (player != null) {
                    player.close();
                }
            }
        });
        thread.start();
        isPlaying = true;
        currentMusicIndex = index;
    }

    public void pararMusica() {
        if (player != null) {
            player.close();
        }
        if (thread != null) {
            thread.interrupt();
        }
        isPlaying = false;
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public String getProgress() {
        if (player != null) {
            long elapsedSeconds = player.getPosition() / 1000;
            long elapsedMinutes = elapsedSeconds / 60;
            long elapsedSecondsRemainder = elapsedSeconds % 60;

            if (musicFiles != null && musicFiles.length > currentMusicIndex) {
                File file = musicFiles[currentMusicIndex];
                try {
                    AudioFile audioFile = AudioFileIO.read(file);
                    Tag tag = audioFile.getTag();
                    long durationSeconds = audioFile.getAudioHeader().getTrackLength();

                    long durationMinutes = durationSeconds / 60;
                    long durationSecondsRemainder = durationSeconds % 60;

                    return String.format("%02d:%02d / %02d:%02d", elapsedMinutes, elapsedSecondsRemainder, durationMinutes, durationSecondsRemainder);
                } catch (CannotReadException | IOException | TagException | ReadOnlyFileException | InvalidAudioFrameException e) {
                    e.printStackTrace();
                }
            }
        }
        return "00:00 / 00:00";
    }
}

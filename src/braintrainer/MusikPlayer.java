import javax.sound.sampled.*;
import java.io.File;

public class MusikPlayer {
    private Clip clip;
    private boolean paused = false;

    public MusikPlayer(String dateiPfad) {
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File(dateiPfad));
            clip = AudioSystem.getClip();
            clip.open(audioStream);
        } catch (Exception e) {
            System.err.println("Fehler beim Laden der Musik: " + e.getMessage());
        }
    }

    public void playLoop() {
        if (clip != null) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
            paused = false;
        }
    }

    public void pause() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
            paused = true;
        }
    }

    public void resume() {
        if (clip != null && paused) {
            clip.start();
            paused = false;
        }
    }

    public void stop() {
        if (clip != null) {
            clip.stop();
            clip.close();
        }
    }

    public boolean isPaused() {
        return paused;
    }
}


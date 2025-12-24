package braintrainer;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;

public class MusikPlayer {
    private Clip clip;
    private int pauseFrame = 0;
    private boolean paused = false;
    private boolean looping = false;

    public MusikPlayer(String resourcePath) {
        load(resourcePath);
    }

    public void load(String resourcePath) {
        stop();          // stoppt + reset
        closeClip();     // gibt Audio-Ressourcen frei

        try {
            var raw = MusikPlayer.class.getResourceAsStream(resourcePath);
            if (raw == null) throw new IllegalArgumentException("Resource nicht gefunden: " + resourcePath);

            try (var buf = new BufferedInputStream(raw);
                 var ais = AudioSystem.getAudioInputStream(buf)) {

                clip = AudioSystem.getClip();
                clip.open(ais);
            }

            pauseFrame = 0;
            paused = false;

        } catch (Exception e) {
            throw new RuntimeException("Sound konnte nicht geladen werden: " + resourcePath, e);
        }
    }

    public void play() {
        if (clip == null) return;
        looping = false;
        paused = false;
        clip.stop();
        clip.setFramePosition(0);
        clip.start();
    }

    public void loop() {
        if (clip == null) return;
        looping = true;
        paused = false;
        clip.stop();
        clip.setFramePosition(0);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void pause() {
        if (clip == null || paused || !clip.isRunning()) return;
        pauseFrame = clip.getFramePosition();
        clip.stop();
        paused = true;
    }

    public void resume() {
        if (clip == null || !paused) return;
        clip.setFramePosition(pauseFrame);
        if (looping) clip.loop(Clip.LOOP_CONTINUOUSLY);
        else clip.start();
        paused = false;
    }

    public void stop() {
        if (clip == null) return;
        clip.stop();
        clip.setFramePosition(0);
        pauseFrame = 0;
        paused = false;
    }

    public void volume(float v01) { // 0..1
        if (clip == null) return;
        v01 = Math.max(0f, Math.min(1f, v01));
        if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
            FloatControl gain = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            float dB = (v01 <= 0.0001f) ? gain.getMinimum() : (float)(20.0 * Math.log10(v01));
            dB = Math.max(gain.getMinimum(), Math.min(gain.getMaximum(), dB));
            gain.setValue(dB);
        }
    }

    public boolean isPaused() { return paused; }
    public boolean isPlaying() { return clip != null && clip.isRunning(); }

    public void close() {
        stop();
        closeClip();
    }

    private void closeClip() {
        if (clip != null) {
            clip.close();
            clip = null;
        }
    }
}
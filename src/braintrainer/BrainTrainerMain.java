import javax.swing.*;

public class BrainTrainerMain {
    public static void main(String[] args) {
        // Musikplayer initialisieren und Loop starten
        MusikPlayer backgroundMusic = new MusikPlayer("res/sounds/hintergrundmusik.wav");
        backgroundMusic.playLoop();

        // Spiel-Fenster starten
        SwingUtilities.invokeLater(() -> new SpielFenster());
    }
}



package braintrainer;

import javax.swing.*;

public class SpielFenster extends JFrame {
    private JPanel aktuellesPanel;
    private MusikPlayer musikPlayer;

    public SpielFenster() {
        setTitle("🧠 Brain Trainer ");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        musikPlayer = new MusikPlayer("/hintergrundmusik.wav");
        musikPlayer.loop();
        musikPlayer.volume(0.4f);

        zeigeHauptMenu();
        setVisible(true);
    }

    public MusikPlayer getMusikPlayer() {
        return musikPlayer;
    }

    public void zeigeHauptMenu() {
        wechselPanel(new HauptMenuPanel(this));
        musikPlayer.load("/hintergrundmusik.wav");
        musikPlayer.loop();
        musikPlayer.volume(0.4f);
    }

    public void zeigeMemoryFlip() {
        wechselPanel(new MemoryFlipPanel(this));
        musikPlayer.load("/music.wav");
        musikPlayer.loop();
        musikPlayer.volume(0.4f);
    }

    public void zeigeOddOneOut() {
        wechselPanel(new OddOneOutPanel(this));
    }

    public void zeigeEndPanel(String nachricht, Runnable neustart) {
        wechselPanel(new EndPanel(this, nachricht, neustart));
    }

    private void wechselPanel(JPanel neuesPanel) {
        if (aktuellesPanel != null) remove(aktuellesPanel);
        aktuellesPanel = neuesPanel;
        add(aktuellesPanel);
        revalidate();
        repaint();
    }
}
package braintrainer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;

public class MemoryFlipPanel extends JPanel {
    private final SpielFenster fenster;
    private final int zeilen = 4, spalten = 4;
    private final int paare = zeilen * spalten / 2;

    private Karte[] karten;
    private Karte ersteKarte = null;
    private Karte zweiteKarte = null;
    private int zuege = 0;
    private int gefundenePaare = 0;

    private JLabel infoLabel;

    public MemoryFlipPanel(SpielFenster fenster) {
        this.fenster = fenster;
        setLayout(new BorderLayout(10,10));

        // Info-Label oben
        infoLabel = new JLabel("Züge: 0 | Paare: 0/" + paare);
        infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(infoLabel, BorderLayout.NORTH);

        // Spielfeld
        JPanel gitter = new JPanel(new GridLayout(zeilen, spalten,5,5));
        add(gitter, BorderLayout.CENTER);

        // Karten vorbereiten
        karten = new Karte[zeilen*spalten];
        String[] pool = {"🍎","🍌","🍇","🍓","🍒","🥝","🍑","🍍"};
        ArrayList<String> liste = new ArrayList<>();
        for(int i=0; i<paare; i++) {
            liste.add(pool[i]);
            liste.add(pool[i]);
        }
        Collections.shuffle(liste);

        for(int i=0; i<karten.length; i++){
            Karte k = new Karte(liste.get(i));
            k.addActionListener(e -> karteGeklickt(k));
            karten[i] = k;
            gitter.add(k);
        }

        // ⏸ Pause-Menü (ESC-Taste)
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke("ESCAPE"), "pause");
        this.getActionMap().put("pause", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new PauseMenu(fenster, () -> fenster.zeigeMemoryFlip());
            }
        });
    }

    private void karteGeklickt(Karte k){
        if(k.istAufgedeckt()) return;
        k.aufdecken();

        if(ersteKarte == null) {
            ersteKarte = k;
        } else {
            zweiteKarte = k;
            zuege++;
            if(ersteKarte.gleicherWert(zweiteKarte)){
                gefundenePaare++;
                ersteKarte = null;
                zweiteKarte = null;
                if(gefundenePaare >= paare){
                    fenster.zeigeEndPanel("Memory Flip fertig! Züge: " + zuege,
                            () -> fenster.zeigeMemoryFlip());
                }
            } else {
                Timer t = new Timer(500, e -> {
                    ersteKarte.verstecken();
                    zweiteKarte.verstecken();
                    ersteKarte = null;
                    zweiteKarte = null;
                });
                t.setRepeats(false);
                t.start();
            }
        }
        infoLabel.setText("Züge: " + zuege + " | Paare: " + gefundenePaare + "/" + paare);
    }

    private class Karte extends JButton {
        private final String wert;
        private boolean aufgedeckt = false;

        public Karte(String wert){
            this.wert = wert;
            setFont(new Font("Segoe UI Emoji", Font.PLAIN, 32));
            setText("");
        }

        public void aufdecken(){
            aufgedeckt = true;
            setText(wert);
        }

        public void verstecken(){
            aufgedeckt = false;
            setText("");
        }

        public boolean istAufgedeckt(){ return aufgedeckt; }
        public boolean gleicherWert(Karte k){ return this.wert.equals(k.wert); }
    }
}

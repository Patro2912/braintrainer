import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class OddOneOutPanel extends JPanel {
    private final SpielFenster fenster;
    private final Random zufall = new Random();
    private int level = 1;
    private int punkte = 0;
    private JLabel infoLabel;
    private Feld[] felder;

    public OddOneOutPanel(SpielFenster fenster){
        this.fenster = fenster;
        setLayout(new BorderLayout(10,10));
        infoLabel = new JLabel("Level 1 | Punkte: 0");
        infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(infoLabel, BorderLayout.NORTH);
        starteLevel();

        // ⏸ Pause-Menü (ESC)
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke("ESCAPE"), "pause");
        this.getActionMap().put("pause", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new PauseMenu(fenster, () -> fenster.zeigeOddOneOut());
            }
        });
    }

    private void starteLevel(){
        if(felder!=null && felder[0].getParent()!=null)
            remove(felder[0].getParent());

        int groesse = Math.min(3+level,6);
        JPanel gitter = new JPanel(new GridLayout(groesse,groesse,5,5));
        felder = new Feld[groesse*groesse];
        Color basis = new Color(zufall.nextInt(200),zufall.nextInt(200),zufall.nextInt(200));
        int diffIndex = zufall.nextInt(felder.length);
        for(int i=0;i<felder.length;i++){
            Color c = (i==diffIndex) ? new Color(
                    Math.min(255,basis.getRed()+30),
                    Math.max(0,basis.getGreen()-30),
                    Math.min(255,basis.getBlue()+30)) : basis;
            Feld f = new Feld(c);
            int index=i;
            f.addActionListener(e -> feldGeklickt(index,diffIndex));
            felder[i]=f;
            gitter.add(f);
        }
        add(gitter,BorderLayout.CENTER);
        revalidate(); repaint();
    }

    private void feldGeklickt(int index, int korrekt){
        if(index==korrekt){
            punkte+=10; level++;
            infoLabel.setText("Level "+level+" | Punkte: "+punkte);
            starteLevel();
        } else {
            fenster.zeigeEndPanel("Game Over! Punkte: "+punkte,
                    () -> fenster.zeigeOddOneOut());
        }
    }

    private class Feld extends JButton {
        public Feld(Color c){
            setBackground(c);
            setOpaque(true);
            setBorderPainted(false);
        }
    }
}



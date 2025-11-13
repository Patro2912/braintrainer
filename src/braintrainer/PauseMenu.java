import javax.swing.*;
import java.awt.*;

public class PauseMenu extends JDialog {
    public PauseMenu(SpielFenster fenster, Runnable neustart) {
        super(fenster, "Pause", true);
        setLayout(new GridLayout(4, 1, 10, 10));
        setSize(250, 200);
        setLocationRelativeTo(fenster);

        MusikPlayer musik = fenster.getMusikPlayer();

        JButton fortsetzen = new JButton("Fortsetzen");
        JButton neustartBtn = new JButton("Neustarten");
        JButton musikBtn = new JButton(musik.isPaused() ? "Musik einschalten" : "Musik ausschalten");
        JButton hauptmenue = new JButton("Hauptmenü");

        fortsetzen.addActionListener(e -> {
            musik.resume();
            dispose();
        });

        neustartBtn.addActionListener(e -> {
            musik.resume();
            dispose();
            neustart.run();
        });

        musikBtn.addActionListener(e -> {
            if (musik.isPaused()) {
                musik.resume();
                musikBtn.setText("Musik ausschalten");
            } else {
                musik.pause();
                musikBtn.setText("Musik einschalten");
            }
        });

        hauptmenue.addActionListener(e -> {
            musik.resume();
            dispose();
            fenster.zeigeHauptMenu();
        });

        add(fortsetzen);
        add(neustartBtn);
        add(musikBtn);
        add(hauptmenue);
        setVisible(true);
    }
}


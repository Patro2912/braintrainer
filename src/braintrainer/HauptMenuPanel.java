import javax.swing.*;
import java.awt.*;

public class HauptMenuPanel extends JPanel {
    public HauptMenuPanel(SpielFenster fenster) {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,10,10,10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titel = new JLabel(" Brain Trainer", SwingConstants.CENTER);
        titel.setFont(new Font("SansSerif", Font.BOLD, 28));

        JButton memoryBtn = new JButton(" Memory Flip");
        JButton oddBtn = new JButton(" Odd One Out");
        JButton beendenBtn = new JButton("Beenden");

        memoryBtn.addActionListener(e -> fenster.zeigeMemoryFlip());
        oddBtn.addActionListener(e -> fenster.zeigeOddOneOut());
        beendenBtn.addActionListener(e -> System.exit(0));

        gbc.gridy = 0; add(titel, gbc);
        gbc.gridy = 1; add(memoryBtn, gbc);
        gbc.gridy = 2; add(oddBtn, gbc);
        gbc.gridy = 3; add(beendenBtn, gbc);
    }
}


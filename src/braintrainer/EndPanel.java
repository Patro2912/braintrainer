import javax.swing.*;
import java.awt.*;

public class EndPanel extends JPanel {
    public EndPanel(SpielFenster fenster, String nachricht, Runnable neustart){
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,10,10,10);

        JLabel label = new JLabel("<html><center>"+nachricht+"</center></html>",SwingConstants.CENTER);
        label.setFont(new Font("SansSerif",Font.BOLD,20));

        JButton restart = new JButton("Erneut spielen");
        JButton menu = new JButton("Hauptmenü");

        restart.addActionListener(e->neustart.run());
        menu.addActionListener(e->fenster.zeigeHauptMenu());

        gbc.gridy=0; add(label,gbc);
        gbc.gridy=1; add(restart,gbc);
        gbc.gridy=2; add(menu,gbc);
    }
}



package swingy.mvc.views.gui;

import lombok.Setter;
import swingy.utils.Utils;

import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel {
    @Setter
    private boolean paintLogo = false;

    public MainPanel() {
        setLayout(null);
        setBounds(0, 0, 1920, 1080);
        setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (paintLogo) {
            draw42Logo((Graphics2D) g);
        }
    }

    private void draw42Logo(Graphics2D g2) {
        Image image = getToolkit().getImage(Utils.getPathToResources().concat("42_logo.png"));
        prepareImage(image, this);
        g2.drawImage(image, 900, 100, this);
    }
}
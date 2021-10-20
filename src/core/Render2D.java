package core;

import java.awt.*;
import javax.swing.*;

public class Render2D extends JPanel{

    int x = 0;

    public Render2D(){
        this.setPreferredSize(new Dimension(180,135));
    }

    public void paint(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;

        g2D.setPaint(Color.BLACK);
        g2D.setStroke(new BasicStroke(5));

        for (int i = 0; i < 200; i++) {
            g2D.drawRect(0 + x, i, 10, 10);
        }

        x += 1;
        if (x > 180)
            x = 0;
    }

}
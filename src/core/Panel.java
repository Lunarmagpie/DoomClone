package core;

import java.awt.*;
import javax.swing.*;

public class Panel extends JPanel{

    Panel(){
        this.setPreferredSize(new Dimension(500,500));
    }

    public void paint(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        g2D.setPaint(Color.BLACK);
        g2D.setStroke(new BasicStroke(5));
        g2D.drawLine(0, 0, 500, 500);
    }

}
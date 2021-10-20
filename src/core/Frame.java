package core;

import javax.swing.*;
import java.awt.Dimension;

public class Frame extends JFrame{

    Render2D panel;
 
    Frame(){
        panel = new Render2D();

        panel.setPreferredSize(new Dimension(180, 135));

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(panel);
        this.setPreferredSize(new Dimension(180, 135));
        this.setResizable(false);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }  
}
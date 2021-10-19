package core;

import java.awt.*;
import javax.swing.*;

public class Render2D extends JPanel{

    int x = 0;
    GameLoop game;

    Render2D(GameLoop game){
        this.setPreferredSize(new Dimension(180,135));
        this.game = game;
    }

    public void paint(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;

        this.game.render(g2D);
    }

}
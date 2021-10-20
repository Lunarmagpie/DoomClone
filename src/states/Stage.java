package states;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Dimension;
import core.Render2D;

public class Stage extends StateABC {

    player.Player player;
    int x = 0;

    public Stage() {
        this.player = new player.Player();
    }

    public void tick(double delta) {

    }

    public void renderInit(JFrame frame){
        Render2D panel = new Render2D();
        frame.add(panel);
    }

    public void render() {


        // this.player.render(g2D);

        // g2D.setPaint(Color.BLACK);
        // g2D.setStroke(new BasicStroke(5));

        // for (int i = 0; i < 200; i++) {
        //     g2D.drawRect(0 + x, i, 10, 10);
        // }

        // x += 1;
        // if (x > 180)
        //     x = 0;
    }

}

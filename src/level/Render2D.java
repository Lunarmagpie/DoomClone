package level;

import java.awt.*;
import javax.swing.*;

import player.Player;
import states.Stage;

public class Render2D extends JPanel {

    int x = 0;
    Stage state;
    Player player;
    private Polygon playerShape;

    public Render2D(Stage state) {
        this.setPreferredSize(new Dimension(180, 135));
        this.state = state;
    }

    public void paint(Graphics g) {
        Toolkit.getDefaultToolkit().sync();

        Graphics2D g2D = (Graphics2D) g;

        // DRAW WALLS
        g2D.setPaint(Color.BLUE);
        g2D.setStroke(new BasicStroke(0));

        for (int[] wall : state.walls) {
            Rectangle wallr = new Rectangle(wall[0], wall[1], 10, 10);
            g2D.fill(wallr);
        }

        // DRAW PLAYER
        g2D.setPaint(Color.BLACK);
        g2D.setStroke(new BasicStroke(1));
        this.player = state.player;

        int px = (int) this.player.x;
        int py = (int) this.player.y;
        double pr = this.player.r - Math.PI;
        int[] playerX = { px, px + 10, px + 10, px };
        int[] playerY = { py, py, py + 10, py + 10 };

        playerShape = new Polygon(playerX, playerY, playerX.length);
        g2D.rotate(pr, px + 5, py + 5);

        g2D.fillPolygon(playerShape);

    }

}
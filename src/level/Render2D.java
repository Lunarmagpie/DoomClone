package level;

import java.awt.*;
import javax.swing.*;

import player.Player;
import states.Stage;

public class Render2D extends JPanel {

    float s =  (float) 5;
    Stage state;
    Player player;
    private Polygon playerShape;

    public Render2D(Stage state) {
        this.state = state;
    }

    public void paint(Graphics g) {
        Toolkit.getDefaultToolkit().sync();

        Graphics2D g2D = (Graphics2D) g;

        int mapSize = state.walls.length;

        // DRAW BORDER AND BACKGROUND OF MINIMAP
        g2D.setPaint(Color.WHITE);
        g2D.fillRect(0, 0, (int) (mapSize * s), (int) (mapSize * s));

        g2D.setPaint(Color.BLUE);
        g2D.drawRect(0, 0, (int) (mapSize * s), (int) (mapSize * s));

        // DRAW WALLS
        g2D.setPaint(Color.BLUE);
        g2D.setStroke(new BasicStroke(0));

        int wallx = 0;
        int wally = 0;
        for (int[] wall_row : state.walls) {
            for (int wall : wall_row) {
                if (wall > 0) {
                    Rectangle wallr = new Rectangle((int) (wallx * s), (int) (wally * s), (int) (1 * this.s), (int) (1 * this.s));
                    g2D.fill(wallr);
                }
                wallx++;
            }
            wallx = 0;
            wally++;
        }

        // DRAW PLAYER
        g2D.setPaint(Color.BLACK);
        g2D.setStroke(new BasicStroke(1));
        this.player = state.player;

        int px = (int) (this.player.y * s - (1 * s / 2));
        int py = (int) (this.player.x * s - (1 * s / 2));
        double pr = Math.atan2(this.player.ry, this.player.rx);
        int[] playerX = {px, (int) (px + 1 * s), (int) (px + 1 * s), px };
        int[] playerY = {py, py, (int) (py + 1 * s), (int) (py + 1 * s)  };

        playerShape = new Polygon(playerX, playerY, playerX.length);
        //g2D.rotate(pr, px + 5, py + 5);
        g2D.fillPolygon(playerShape);

    }

}
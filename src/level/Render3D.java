package level;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import states.Stage;
import java.awt.Toolkit;

public class Render3D extends JPanel {

    Stage stage;

    int screenWidth = 500;
    int numberOfRays = 100;
    double fov = 70;

    public Render3D(Stage stage) {
        this.stage = stage;

    }

    public void paint(Graphics g) {
        Toolkit.getDefaultToolkit().sync();

        // DRAW RAYS
        Graphics2D g2D = (Graphics2D) g;

        // fill sky and ground
        g2D.setPaint(Color.WHITE);
        g2D.fillRect(0, 0, this.getWidth(), this.getHeight());

        g2D.setPaint(Color.BLACK);
        g2D.fillRect(0, this.getHeight() / 2, this.getWidth(), this.getHeight() / 2);

        double maxRad = Math.toRadians(this.fov);

        for (double i = -maxRad / 2; i < maxRad / 2; i += maxRad / numberOfRays) {
            Ray r = new Ray(this.stage.player.x, this.stage.player.y, this.stage.player.r - Math.PI + i,
                    this.stage.walls);
            double[] endpoint = r.move(g2D);

            if (endpoint.length > 0) {

                double distance = 1 / Math.sqrt(Math.pow(this.stage.player.x - (double) endpoint[0], 2)
                        + Math.pow(this.stage.player.y - (double) endpoint[1], 2));

                double height = distance * 10000;
                double min_height = 250 - height / 2;

                if (endpoint[2] == 1) {
                    g2D.setPaint(Color.darkGray);
                } else {
                    g2D.setPaint(Color.lightGray);
                }

                g2D.fillRect((int) (i * -1 * screenWidth) + screenWidth / 2, (int) min_height, (int) Math.ceil(screenWidth/numberOfRays) + 2, (int) height);
                // g2D.drawRect();
            }
        }
    }
}

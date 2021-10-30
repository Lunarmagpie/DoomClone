package player;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.BasicStroke;

import states.Stage;

public class Player {

    public double x;
    public double y;
    public double rx; // Rotation
    public double ry;
    public Stage stage;

    public Player(Stage stage) {
        this.x = 22;
        this.y = 12;
        this.rx = -1;
        this.ry = 0;

        this.stage = stage;
    }

    public void move(double distance, double dir){


        this.x += rx * distance;
        this.y += ry * distance;

        // for (int[] wall : stage.walls) {
        //     if (this.x > wall[0] - 9 && this.x < wall[0] + 10 && this.y > wall[1] - 9 && this.y < wall[1] + 10) {
        //         this.x -= Math.sin(dir + rotate) * distance;
        //         this.y -= Math.cos(dir + rotate) * distance;
        //     }
        // }
    }

    public void rotate(double angle){
        double orx = rx;
        rx = rx * Math.cos(angle) - ry * Math.sin(angle);
        ry = orx * Math.sin(angle) + ry * Math.cos(angle);
    }

    public void render(Graphics2D g2D, double delta){
        g2D.setPaint(Color.BLACK);
        g2D.setStroke(new BasicStroke(5));

        g2D.drawRect((int) x,(int) y,10,10);
    }
}

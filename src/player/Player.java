package player;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.BasicStroke;

public class Player {

    double x;
    double y;
    double r; // Rotation

    public Player() {
        this.x = 0;
        this.y = 0;
        this.r = 0;
    }

    public void move_forward(double distance){
        this.move(distance, this.r);
    }

    public void move(double distance, double dir){
        double rotate = Math.PI/2;

        this.x += Math.sin(dir + rotate) * distance;
        this.y += Math.cos(dir + rotate) * distance;
    }

    public void rotate(double angle){
        this.r += angle;
    }

    public void render(Graphics2D g2D, double delta){
        g2D.setPaint(Color.BLACK);
        g2D.setStroke(new BasicStroke(5));

        g2D.drawRect((int) x,(int) y,10,10);
    }
}

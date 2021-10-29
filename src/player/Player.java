package player;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.BasicStroke;

import states.Stage;

public class Player {

    public double x;
    public double y;
    public double r; // Rotation
    public Stage stage;

    public Player(Stage stage) {
        this.x = 50;
        this.y = 100;
        this.r = 0;
        this.stage = stage;
    }

    public void move(double distance, double dir){

        //Handle collisions


        double rotate = Math.PI/2 + this.r;

        this.x += Math.sin(dir + rotate) * distance;
        this.y += Math.cos(dir + rotate) * distance;

        for (int[] wall : stage.walls) {
            if (this.x > wall[0] - 9 && this.x < wall[0] + 10 && this.y > wall[1] - 9 && this.y < wall[1] + 10) {
                this.x -= Math.sin(dir + rotate) * distance;
                this.y -= Math.cos(dir + rotate) * distance;
            }
        }
    }

    public void rotate(double angle){
        this.r += angle;
        if (this.r == Math.PI * 2) this.r = 0;

        if (this.r >= 2*Math.PI){
            this.r -= 2*Math.PI;
        }

        if (this.r < 0){
            this.r += 2*Math.PI;
        }
    }

    public void render(Graphics2D g2D, double delta){
        g2D.setPaint(Color.BLACK);
        g2D.setStroke(new BasicStroke(5));

        g2D.drawRect((int) x,(int) y,10,10);
    }
}

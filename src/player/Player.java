package player;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.BasicStroke;

import states.Stage;

public class Player {

    public double x;
    public double y;
    public double rx = -1;
    public double ry = 0;
    public Stage stage;

    public Player(Stage stage) {
        this.x = 22;
        this.y = 12;

        this.rx = -1;
        this.ry = 0;

        this.stage = stage;
    }

    public void move(double distance, double angle){

        if (distance == 0){
            return;
        }


        double movrx = rx * Math.cos(angle) - ry * Math.sin(angle);
        double movry = rx * Math.sin(angle) + ry * Math.cos(angle);

        this.x += movrx * distance;
        this.y += movry * distance;
        
        //Check in in a block
        if (this.stage.walls[(int) this.x][(int) this.y] > 0){

            // double xDir = Math.signum(distance) * Math.signum(movrx);
            // double yDir = Math.signum(distance) * Math.signum(movry);

            // double inBlockX = xDir * this.x % 1;
            // if (inBlockX < 0){
            //     inBlockX = -1 - inBlockX;
            // }

            // double inBlockY = yDir * this.y % 1;
            // if (inBlockY < 0){
            //     inBlockY = -1 - inBlockY;
            // }

            // this.x -= inBlockX;
            // this.y -= inBlockY;

            
            // Easy collision system
            this.x -= movrx * distance;
            this.y -= movry * distance;

        }

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

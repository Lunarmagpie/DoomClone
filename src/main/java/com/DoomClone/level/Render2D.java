package com.DoomClone.level;

import java.awt.*;
import javax.swing.*;

import com.DoomClone.player.Player;
import com.DoomClone.states.Stage;

public class Render2D extends JPanel {

    int size = 120;
    int mapSize;
    int heightOffset = 445;
    float s =  (float) 5;
    Stage state;
    Player player;

    public Render2D(Stage state) {
        this.state = state;
        this.s = size / 24;
        this.player = state.player;
    }

    public void paint(Graphics g) {
        Toolkit.getDefaultToolkit().sync();

        Graphics2D g2D = (Graphics2D) g;

        // DRAW BACKGROUND OF MINIMAP
        g2D.setPaint(Color.black);
        g2D.fillRect(0, heightOffset, (int) size, (int) size);

        // get player position       
        int px = transformToMinimapX(this.player.y);
        int py = transformToMinimapY(this.player.x);

        // DRAW WALLS
        g2D.setStroke(new BasicStroke(0));

        int wallx = 0;
        int wally = 0;
        for (int[] wall_row : state.walls) {
            for (int wall : wall_row) {
                if (wall > 0) g2D.setPaint(Color.gray);
                else g2D.setPaint(Color.white);

                int wallposx = transformToMinimapX(wallx);
                int wallposy = transformToMinimapY(wally);
                int wallwidth = (int) (this.s);
                int wallheight = (int) (this.s);

                if (wallposx < size && wallposy < heightOffset + size && wallposx > 0 && wallposy > heightOffset)
                    g2D.fillRect(wallposx, wallposy, wallwidth, wallheight);
                wallx++;
            }
            wallx = 0;
            wally++;
        }

        // DRAW PLAYER PROJECTILES
        g2D.setPaint(Color.RED);
        g2D.setStroke(new BasicStroke(1));

        for (int i = 0; i < this.player.projectile.obj.size(); i++) {

            double[] projectile = this.player.projectile.obj.get(i);
            g2D.fillRect(transformToMinimapX(projectile[1]), transformToMinimapY(projectile[0]), 2, 2);
        }

        // DRAW PLAYER
        g2D.setPaint(Color.BLACK);
        g2D.setStroke(new BasicStroke(1));

        // g2D.fillRect(px, py, (int) s, (int) s);
        g2D.fillRect(px, py, (int) s, (int) s);

        // draw minimap border
        g2D.setPaint(Color.black);
        g2D.setStroke(new BasicStroke(5));
        g2D.drawRect(2, heightOffset + 2, size, size);

    }



    private double transformToMinimap(double num) {
        return (num * s - (s / 2));
    }

    private int transformToMinimapX(double num) {
        return (int) (transformToMinimap(num) - transformToMinimap(this.player.y) + size / 2);
    }

    private int transformToMinimapY(double num) {
        return (int) (transformToMinimap(num) - transformToMinimap(this.player.x) + size / 2 + heightOffset);
    }

}
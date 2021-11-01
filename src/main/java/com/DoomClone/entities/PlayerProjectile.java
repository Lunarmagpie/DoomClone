package com.DoomClone.entities;

import com.DoomClone.states.Stage;

import java.util.ArrayList;

public class PlayerProjectile {
    
    public ArrayList<double[]> obj = new ArrayList<double[]>();
    private double speed = 0.1;
    private Stage stage;

    public PlayerProjectile(Stage stage) {
        this.stage = stage;
    }

    public void tick() {

        for (int i = 0; i < this.obj.size(); i++) {

            double[] projectile = this.obj.get(i);

            projectile[4]++;

            if (projectile[4] == 240) {
                this.obj.remove(i);
            }

            // Handle Collisions
            if (this.stage.walls[(int) (projectile[0])][(int) (projectile[1])] > 0) {
                this.obj.remove(i);
            }

            double rx = projectile[2];
            double ry = projectile[3];
 
            projectile[0] += rx * speed;
            projectile[1] += ry * speed;
        }
    }

    public void create(double x, double y, double rx, double ry) {
        double[] res = {x, y, rx, ry, 0};
        this.obj.add(res);
    }
}

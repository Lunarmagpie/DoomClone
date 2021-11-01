package com.DoomClone.entities;

import com.DoomClone.states.Stage;

import java.util.ArrayList;

public class PlayerProjectile {
    
    public ArrayList<double[]> obj = new ArrayList<double[]>();
    private double speed = 0.2;
    private Stage stage;

    public PlayerProjectile(Stage stage) {
        this.stage = stage;
        System.out.println("Initialized");
    }

    public void tick() {
        //System.out.println(this.obj.toString());
        for (int i = 0; i < this.obj.size(); i++) {

            double[] projectile = obj.get(i);

            double rx = projectile[2];
            double ry = projectile[3];

            projectile[0] += rx * speed;
            projectile[1] += ry * speed;
        }
    }

    public void create(double x, double y, double rx, double ry) {
        double[] res = {x, y, rx, ry};
        this.obj.add(res);
    }
}

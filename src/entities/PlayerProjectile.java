package entities;

import java.util.ArrayList;

public class PlayerProjectile {
    
    public ArrayList<Double> location = new ArrayList<Double>();
    public ArrayList<Double> rotation = new ArrayList<Double>();

    public PlayerProjectile() {
        System.out.println("Initialized");
    }

    public void tick() {

    }

    public void createProjectile(double location, double rotation) {
        this.location.add(location);
        this.rotation.add(rotation);
    }
}

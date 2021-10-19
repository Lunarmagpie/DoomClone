package states;

import java.awt.Graphics2D;

public abstract class StateABC {
    
    public abstract void tick(double delta);

    public abstract void render(Graphics2D g2D, double delta);
    
}

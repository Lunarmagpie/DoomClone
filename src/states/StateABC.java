package states;

import java.awt.Graphics2D;

import javax.swing.JFrame;

public abstract class StateABC {
    
    public abstract void tick(double delta);
    
    public abstract void renderInit(JFrame frame);

    public abstract void render();
    
}

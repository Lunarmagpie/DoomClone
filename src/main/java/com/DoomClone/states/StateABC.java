package com.DoomClone.states;

import javax.swing.JFrame;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public abstract class StateABC {
    
    public abstract void tick(double delta, ArrayList<Integer> keysPressed);
    
    public abstract void renderInit(JFrame frame);

    public abstract void keyPressed(KeyEvent e);

    public abstract void keyReleased(KeyEvent e);
    
}

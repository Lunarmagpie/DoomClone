/* This file is in charge of keeping track of the current State and running the
 * state's functions.
 */

package core;

import states.StateABC;

import java.awt.*; 
import javax.swing.*;

import player.Player;

public class GameLoop {

    private float framerate;
    private Frame frame;
    private StateABC currentState;

    private float lastDelta = 0;

    public GameLoop(float framerate){
        this.framerate = framerate;
        createFrame("Game");
    }

    public GameLoop(float framerate, StateABC state){
        this.framerate = framerate;
        this.currentState = state;
        createFrame("Game");
    }

    public GameLoop(float framerate, String windowName){
        this.framerate = framerate;
        createFrame(windowName);
    }

    private void createFrame(String name) {
        frame = new Frame(this);
    }

    public void run(){
        long delta = (long) ((1/framerate)*1000);

        try {
            while (true) {
                tick(delta);
                Thread.sleep(delta);
            }
        }catch(InterruptedException e){
            System.out.println("Game crashed");
        }

    }

    public void tick(float delta){
        // Runs the game
        currentState.tick(delta);


        this.lastDelta = delta;
        // Redraw frame
        frame.repaint();
    }

    public void render(Graphics2D g2D){
        this.currentState.render(g2D, this.lastDelta);
    }
}

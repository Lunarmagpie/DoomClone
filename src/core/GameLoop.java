/* This file is in charge of keeping track of the current State and running the
 * state's functions.
 */

package core;

import player.*;
import java.awt.*; 
import javax.swing.*;

public class GameLoop {

    private float framerate;
    private Frame frame;

    public GameLoop(float framerate){
        this.framerate = framerate;
        createFrame("Game");
    }

    public GameLoop(float framerate, String windowName){
        this.framerate = framerate;
        createFrame(windowName);
    }

    private void createFrame(String name) {
        frame = new Frame();
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

        // Redraw frame
        frame.repaint();
    }
}

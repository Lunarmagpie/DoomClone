/* This file is in charge of keeping track of the current State and running the
 * state's functions.
 */

package core;

import states.StateABC;

import javax.swing.*;
import java.awt.event.KeyListener;

public class GameLoop {

    private float framerate;
    private JFrame frame;
    public StateABC currentState;
    public KeyInput key;

    public GameLoop(float framerate) {
        this.framerate = framerate;
        createFrame("Game");
    }

    public GameLoop(float framerate, StateABC state) {
        this.framerate = framerate;
        this.currentState = state;
        createFrame("Game");
    }

    public GameLoop(float framerate, String windowName) {
        this.framerate = framerate;
        createFrame(windowName);
    }

    private void createFrame(String name) {
        frame = new Frame();
        this.key = new KeyInput(this);

        frame.addKeyListener(this.key);
        frame.setFocusable(true);
    }

    public void run() {
        long delta = (long) ((1 / framerate) * 1000);

        try {
            while (true) {
                tick(delta);
                Thread.sleep(delta);
            }
        } catch (InterruptedException e) {
            System.out.println("Game crashed");
        }

    }

    public void changeState(StateABC state) {
        // frame.removeAll();
        currentState = state;
        currentState.renderInit(frame);
    }

    private void tick(double delta) {
        // Runs the game
        currentState.tick(delta, this.key.keysHeldDown);

        // Redraw frame
        frame.repaint();
    }
}

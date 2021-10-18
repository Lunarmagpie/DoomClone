/* This file is in charge of keeping track of the current State and running the
 * state's functions.
 */

package core;

public class GameLoop {

    protected float framerate;

    public GameLoop(float framerate){
        this.framerate = framerate;
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

    }
}

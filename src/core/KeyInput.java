package core;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.awt.event.KeyEvent;

public class KeyInput implements KeyListener{

    GameLoop gameLoop;
    public ArrayList<Integer> keysHeldDown = new ArrayList<Integer>();

    public KeyInput(GameLoop gameLoop){
        this.gameLoop = gameLoop;
    }

    public void keyTyped(KeyEvent e){
        
    }

    public void keyPressed(KeyEvent e){

        gameLoop.currentState.keyPressed(e);

        if (!keysHeldDown.contains(e.getKeyCode())){
            keysHeldDown.add(e.getKeyCode());
        }

    }

    public void keyReleased(KeyEvent e){
        gameLoop.currentState.keyReleased(e);

        int loc = keysHeldDown.indexOf(e.getKeyCode());

        if (loc != -1) {
            keysHeldDown.remove(loc);
        }
    }

}

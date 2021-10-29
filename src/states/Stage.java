package states;

import javax.swing.JFrame;
import java.awt.Dimension;
import level.Render2D;
import level.Render3D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Stage extends StateABC {

    public player.Player player;
    Render2D panel;
    Render3D panel3D;
    int x = 0;

    public int[][] walls = {
        {20, 20},
        {30, 30},
        {40, 40},
        {200, 200},
    };

    public Stage() {
        this.player = new player.Player(this);
    }

    public void tick(double delta, ArrayList<Integer> keysPressed) {

        //W - 87
        //A - 65
        //S - 83
        //D - 68

        int dir_x = (keysPressed.contains(68)? 1:0) - (keysPressed.contains(65)? 1:0);
        int dir_y = (keysPressed.contains(87)? 1:0) - (keysPressed.contains(83)? 1:0);

        if (keysPressed.contains(39))
            player.rotate(Math.toRadians(-1));

        if (keysPressed.contains(37))
            player.rotate(Math.toRadians(1));

        player.move(.04*delta*dir_x, Math.toRadians(0));
        player.move(.04*delta*dir_y, Math.toRadians(90));

    }

    public void renderInit(JFrame frame){
        // panel = new Render2D(this);
        // panel.setPreferredSize(new Dimension(180, 135));
        // frame.add(panel);
        // frame.pack();
        
        panel3D = new Render3D(this);
        panel3D.setPreferredSize(new Dimension(100, 100));
        frame.add(panel3D);
        frame.pack();
    }

    public void keyPressed(KeyEvent e) {

    }

    public void keyReleased(KeyEvent e) {

    }

}

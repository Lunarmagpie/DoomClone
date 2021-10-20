package states;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class Stage extends StateABC {
    
    player.Player player;
    int frame = 0;
    int x = 0;

    public Stage() {
        this.player = new player.Player();
    }


    public void tick(double delta){
        frame++;
        frame = frame % 60;
        System.out.println(frame);
    }

    public void render(Graphics2D g2D, double delta){
        this.player.render(g2D, delta);

        g2D.setPaint(Color.BLACK);
        g2D.setStroke(new BasicStroke(5));
        
        for (int i = 0; i < 200; i++) {
            g2D.drawRect(0 + x,i,10,10);
        }
        
        x += 1;
        if (x > 180) x = 0;
    }

}

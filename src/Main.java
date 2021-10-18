import core.*;
import java.awt.*; 
import javax.swing.*;

class Main{
    public static void main(String[] args) {
        GameLoop loop = new GameLoop(60);
        loop.run();
    }
}
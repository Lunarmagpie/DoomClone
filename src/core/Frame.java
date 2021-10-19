package core;

import javax.swing.*;

public class Frame extends JFrame{
 
 Render2D panel;
 
 Frame(){
  
  panel = new Render2D();
  
  this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  
  this.add(panel);
  this.pack();
  this.setLocationRelativeTo(null);
  this.setVisible(true);
 }  
}
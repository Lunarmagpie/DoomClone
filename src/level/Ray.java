package level;

import java.awt.Graphics2D;

public class Ray {
    
    double depthOfField = 500;

    double x;
    double y;
    double dir;
    int[][] coll;

    Ray(double x, double y, double dir, int[][] coll){
        this.x = x;
        this.y = y;
        this.dir = dir;

        if (this.dir < 0){
            this.dir += 2*Math.PI;
        }
       if (this.dir > 2*Math.PI){
            this.dir -= 2*Math.PI;
        }


        this.coll = coll;
    }

    public double[] move(Graphics2D g2D){
        for (int iter = 0; iter<depthOfField; iter++){

            int x_coord = (int) this.x;
            int y_coord = (int) this.y;

            for (int i = 0; i<coll.length; i++){
                if (coll[i][0] >= x_coord-10 && coll[i][0] <= x_coord &&
                    coll[i][1] >= y_coord-10 && coll[i][1] <= y_coord){

                        double hit_x = this.x % 10;
                        double hit_y = this.y % 10;

                        int hit_dir = -1;

                        if (hit_x >= hit_y){
                            hit_dir = 1;
                        }

                        return new double[] {x_coord, y_coord, hit_dir};
                }
            }

            this.x += Math.sin(dir);
            this.y += Math.cos(dir);
            
            // g2D.drawLine(x_coord, y_coord, x_coord+1, y_coord+1);
        }

        return new double[] {};
    }
}

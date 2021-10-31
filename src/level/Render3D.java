package level;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import states.Stage;
import java.awt.Toolkit;
import level.Render2D;

// Code is modified from https://lodev.org/cgtutor/raycasting.html

public class Render3D extends JPanel {

    Stage stage;
    Render2D minimap;

    int screenWidth;
    int screenHeight;

    int[][] texture;
    int texHeight = 32;
    int texWidth = 32;

    double planeX = 0, planeY = .75;
    double time = 0;
    double oldTime = 0;

    public Render3D(Stage stage) {
        this.stage = stage;
        this.screenWidth = getWidth();
        this.screenHeight = getHeight();

        // Generate Textures
        // TODO: Generate from images
        // generate some textures
        this.texture = new int[8][texWidth * texHeight + texWidth];

        for (int x = 0; x < texWidth; x++) {
            for (int y = 0; y < texHeight; y++) {
                int xorcolor = (x * 256 / texWidth) ^ (y * 256 / texHeight);
                // int xcolor = x * 256 / texWidth;
                int ycolor = y * 256 / texHeight;
                int xycolor = y * 128 / texHeight + x * 128 / texWidth;
                texture[0][texWidth * y + x] = 65536 * 254 * (x != y && x != texWidth - y ? 0 : 1); // flat red texture
                                                                                                    // with
                // black cross
                texture[1][texWidth * y + x] = xycolor + 256 * xycolor + 65536 * xycolor; // sloped greyscale
                texture[2][texWidth * y + x] = 256 * xycolor + 65536 * xycolor; // sloped yellow gradient
                texture[3][texWidth * y + x] = xorcolor + 256 * xorcolor + 65536 * xorcolor; // xor greyscale
                texture[4][texWidth * y + x] = 256 * xorcolor; // xor green
                texture[5][texWidth * y + x] = 65536 * 192 * ((x % 16 & y % 16) == 0 ? 0 : 1); // red bricks
                texture[6][texWidth * y + x] = 65536 * ycolor; // red gradient
                texture[7][texWidth * y + x] = 128 + 256 * 128 + 65536 * 128; // flat grey texture
            }
        }

        minimap = new Render2D(stage);
    }

    public void rotate(double theta) {
        double oldPlaneX = planeX;
        planeX = planeX * Math.cos(theta) - planeY * Math.sin(theta);
        planeY = oldPlaneX * Math.sin(theta) + planeY * Math.cos(theta);
    }

    public void paint(Graphics g) {
        screenWidth = this.getWidth();
        int h = this.getHeight();
        Toolkit.getDefaultToolkit().sync();

        // DRAW RAYS
        Graphics2D g2D = (Graphics2D) g;

        // fill sky and ground
        g2D.setPaint(Color.WHITE);
        g2D.fillRect(0, 0, this.getWidth(), this.getHeight());

        g2D.setPaint(Color.BLACK);
        g2D.fillRect(0, this.getHeight() / 2, this.getWidth(), this.getHeight() / 2);

        double posX = this.stage.player.x;
        double posY = this.stage.player.y;

        // Player
        double dirX = this.stage.player.rx;
        double dirY = this.stage.player.ry;

        for (int x = 0; x < screenWidth; x++) {

            double cameraX = 2 * x / (double) screenWidth - 1; // x-coordinate in camera space
            double rayDirX = dirX + planeX * cameraX;
            double rayDirY = dirY + planeY * cameraX;

            int mapX = (int) posX;
            int mapY = (int) posY;

            double sideDistX;
            double sideDistY;

            double deltaDistX = (rayDirX == 0) ? 1e30 : Math.abs(1 / rayDirX);
            double deltaDistY = (rayDirY == 0) ? 1e30 : Math.abs(1 / rayDirY);

            double perpWallDist;

            // what direction to step in x or y-direction (either +1 or -1)
            int stepX;
            int stepY;

            int side = 0; // was a NS or a EW wall hit?

            if (rayDirX < 0) {
                stepX = -1;
                sideDistX = (posX - mapX) * deltaDistX;
            } else {
                stepX = 1;
                sideDistX = (mapX + 1.0 - posX) * deltaDistX;
            }
            if (rayDirY < 0) {
                stepY = -1;
                sideDistY = (posY - mapY) * deltaDistY;
            } else {
                stepY = 1;
                sideDistY = (mapY + 1.0 - posY) * deltaDistY;
            }

            while (true) {
                // jump to next map square, either in x-direction, or in y-direction
                if (sideDistX < sideDistY) {
                    sideDistX += deltaDistX;
                    mapX += stepX;
                    side = 0;
                } else {
                    sideDistY += deltaDistY;
                    mapY += stepY;
                    side = 1;
                }

                // Check if ray has hit a wall
                if (this.stage.walls[mapX][mapY] > 0)
                    break;
            }

            if (side == 0)
                perpWallDist = (sideDistX - deltaDistX);
            else
                perpWallDist = (sideDistY - deltaDistY);

            // Calculate height of line to draw on screen
            int lineHeight = (int) (h / perpWallDist);

            // calculate lowest and highest pixel to fill in current stripe
            int drawStart = -lineHeight / 2 + h / 2;
            if (drawStart < 0)
                drawStart = 0;
            int drawEnd = lineHeight / 2 + h / 2;
            if (drawEnd >= h)
                drawEnd = h - 1;

            // texturing calculations
            int texNum = this.stage.walls[mapX][mapY] - 1; // 1 subtracted from it so that texture 0 can be used!

            // calculate value of wallX
            double wallX; // where exactly the wall was hit
            if (side == 0)
                wallX = posY + perpWallDist * rayDirY;
            else
                wallX = posX + perpWallDist * rayDirX;
            wallX -= Math.floor((wallX));

            // x coordinate on the texture
            int texX = (int) (wallX * (double) texWidth);
            if (side == 0 && rayDirX > 0)
                texX = texWidth - texX - 1;
            if (side == 1 && rayDirY < 0)
                texX = texWidth - texX - 1;

            // How much to increase the texture coordinate per screen pixel
            double step = 1.0 * texHeight / lineHeight;
            // Starting texture coordinate
            double texPos = (drawStart - h / 2 + lineHeight / 2) * step;
            for (int y = drawStart; y < drawEnd; y++) {
                // Cast the texture coordinate to integer, and mask with (texHeight - 1) in case
                // of overflow
                int texY = (int) texPos & (texHeight - 1);
                texPos += step;
                int color = texture[texNum][texHeight * texY + texX];
                // make color darker for y-sides: R, G and B byte each divided through two with
                // a "shift" and an "and"
                if (side == 1)
                    color = (color >> 1) & 8355711;

                g2D.setColor(new Color(color));
                g2D.drawLine(x, y, x, y);
            }

        }

        // PAINT MINIMAP
        minimap.paint(g);

    }
}

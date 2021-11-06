package com.DoomClone.level;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

import com.DoomClone.entities.Sprite;
import com.DoomClone.states.Stage;
import java.awt.Toolkit;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Render3D extends JPanel {

    Stage stage;
    Render2D minimap;

    int screenWidth;
    int screenHeight;

    int texWidth = 32;
    int texHeight = 32;
    int[][] buffer;
    int[][] block_textures;
    int[][] sprite_textures;

    JSONObject textureMap;

    int h;
    int w;
    int toolbarHeight = 120;
    int resolution = 2;
    double planeX = 0, planeY = 0.75;
    double time = 0;
    double oldTime = 0;
    int sky = 0x73494c;

    public Render3D(Stage stage) {

        this.stage = stage;

        try {
            this.block_textures = openTexture("blocks");
            this.sprite_textures = openTexture("entities");
        } catch (Exception e) {
            e.printStackTrace();
        }

        minimap = new Render2D(stage);
    }

    public int[][] openTexture(String folder) throws IOException, ParseException {
        // Read the texture map
        JSONParser parser = new JSONParser();
        textureMap = (JSONObject) parser.parse(new FileReader("assets/" + folder + "/texture_map.json"));

        // Find the largest value
        int largestValue = 0;

        for (Object value : textureMap.values()) {
            Long l = (Long) value;
            int val = l.intValue();

            if (val > largestValue) {
                largestValue = val;
            }

        }

        int[][] texture = new int[largestValue][texWidth * texHeight + texWidth];

        // Open blocks folder
        File folder_file = new File("assets/" + folder + "/");
        File[] listOfFiles = folder_file.listFiles();

        for (File file : listOfFiles) {
            if (file.isFile() && file.getName().endsWith(".rtx")) {
                // Read the files
                JSONArray this_tex = (JSONArray) parser.parse(new FileReader(file));

                Object[] ints = this_tex.toArray();
                Long index = (Long) textureMap.get(file.getName().split("\\.")[0]);

                for (int i = 0; i < ints.length; i++) {
                    texture[index.intValue() - 1][i] = ((Long) ints[i]).intValue();
                }
            }
        }

        return texture;
    }

    public void rotate(double theta) {
        double oldPlaneX = planeX;
        planeX = planeX * Math.cos(theta) - planeY * Math.sin(theta);
        planeY = oldPlaneX * Math.sin(theta) + planeY * Math.cos(theta);
    }

    public int blend(int color1, int color2, double val) {
        float blending = (float) val;
        float inverse_blending = 1 - blending;

        int colorR = (color1 & 0xFF0000) >> 16;
        int colorG = (color1 & 0xFF00) >> 8;
        int colorB = (color1 & 0xFF);

        int toR = (color2 & 0xFF0000) >> 16;
        int toG = (color2 & 0xFF00) >> 8;
        int toB = (color2 & 0xFF);

        int red = (int) (colorR * blending + toR * inverse_blending);
        int green = (int) (colorG * blending + toG * inverse_blending);
        int blue = (int) (colorB * blending + toB * inverse_blending);

        return (red << 16) + (green << 8) + blue;
    }

    public void renderInit() {
        this.screenWidth = getWidth();
        this.screenHeight = getHeight();

        this.buffer = new int[screenHeight / resolution][screenWidth / resolution];
    }

    public void paint(Graphics g) {
        w = (int) (this.getWidth() / resolution);
        h = (int) ((this.getHeight() - this.toolbarHeight) / resolution);

        Toolkit.getDefaultToolkit().sync();

        // DRAW RAYS
        Graphics2D g2D = (Graphics2D) g;

        double posX = this.stage.player.x;
        double posY = this.stage.player.y;

        // Player
        double dirX = this.stage.player.rx;
        double dirY = this.stage.player.ry;

        // Clear the buffer
        for (int x = 0; x < this.buffer.length; x++) {
            for (int y = 0; y < this.buffer[x].length; y++) {
                this.buffer[x][y] = this.sky;
            }
        }

        double zbuffer[] = new double[w];

        // FLOOR CASTING
        for (int y = h / 2; y < h; y++) {
            // rayDir for leftmost ray (x = 0) and rightmost ray (x = w)
            double rayDirX0 = dirX - planeX;
            double rayDirY0 = dirY - planeY;
            double rayDirX1 = dirX + planeX;
            double rayDirY1 = dirY + planeY;

            // Current y position compared to the center of the screen (the horizon)
            int p = y - h / 2;

            // Vertical position of the camera.
            double posZ = 0.5 * h;

            // Horizontal distance from the camera to the floor for the current row.
            // 0.5 is the z position exactly in the middle between floor and ceiling.
            double rowDistance = posZ / p;

            // calculate the real world step vector we have to add for each x (parallel to
            // camera plane)
            // adding step by step avoids multiplications with a weight in the inner loop
            double floorStepX = rowDistance * (rayDirX1 - rayDirX0) / w;
            double floorStepY = rowDistance * (rayDirY1 - rayDirY0) / w;

            // real world coordinates of the leftmost column. This will be updated as we
            // step to the right.
            double floorX = posX + rowDistance * rayDirX0;
            double floorY = posY + rowDistance * rayDirY0;

            for (int x = 0; x < w; ++x) {
                // the cell coord is simply got from the integer parts of floorX and floorY
                int cellX = (int) (floorX);
                int cellY = (int) (floorY);

                // get the texture coordinate from the fractional part
                int tx = (int) (texWidth * (floorX - cellX)) & (texWidth - 1);
                int ty = (int) (texHeight * (floorY - cellY)) & (texHeight - 1);

                floorX += floorStepX;
                floorY += floorStepY;

                // choose texture and draw the pixel
                int floorTexture = 1;
                int color;

                // Floor
                color = block_textures[floorTexture][texWidth * ty + tx];
                buffer[y][x] = color;
            }
        }

        // WALL CASTING
        for (int x = 0; x < w; x++) {

            double cameraX = 2 * x / (double) w - 1; // x-coordinate in camera space
            double rayDirX = dirX + planeX * cameraX;
            double rayDirY = dirY + planeY * cameraX;

            int mapX = (int) posX;
            int mapY = (int) posY;

            double sideDistX;
            double sideDistY;

            double deltaDistX = (rayDirX == 0) ? Double.POSITIVE_INFINITY : Math.abs(1 / rayDirX);
            double deltaDistY = (rayDirY == 0) ? Double.POSITIVE_INFINITY : Math.abs(1 / rayDirY);

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
                int color = block_textures[texNum][texHeight * texY + texX];
                // make color darker for y-sides: R, G and B byte each divided through two with
                // a "shift" and an "and"
                if (side == 1)
                    color = (color >> 1) & 0x7F7F7F;

                // blend the color with the sky color based on distance
                if (step > 1)
                    color = blend(color, this.sky, 1 / step);

                buffer[y][x] = color;

                if (y - lineHeight + 1 < 0) {
                    continue;
                }

                buffer[y - lineHeight + 1][x] = color;

                if (y - lineHeight * 2 + 2 < 0) {
                    continue;
                }

                // blend the color with the sky color based on height
                int blended = blend(color, this.sky, Math.pow((double) texY / 32, 2));

                buffer[y - lineHeight * 2 + 2][x] = blended;
            }

            zbuffer[x] = perpWallDist;

            // SPRITE CASTING
            // sort sprites from far to close
            Sprite[] sprites = this.stage.entities.toArray(new Sprite[0]);

            for (int i = 0; i < sprites.length; i++) {
                for (int j = i; j < sprites.length; j++) {
                    double first_dist = ((posX - sprites[i].x) * (posX - sprites[i].x)
                            + (posY - sprites[i].y) * (posY - sprites[i].y));
                    double this_dist = ((posX - sprites[j].x) * (posX - sprites[j].x)
                            + (posY - sprites[j].y) * (posY - sprites[j].y));

                    if (this_dist > first_dist) {
                        Sprite tmp = sprites[i];
                        sprites[i] = sprites[j];
                        sprites[j] = tmp;
                    }
                }
            }

            // after sorting the sprites, do the projection and draw them
            for (Sprite sprite : sprites) {
                // translate sprite position to relative to camera
                double spriteX = sprite.x - posX;
                double spriteY = sprite.y - posY;

                // transform sprite with the inverse camera matrix
                // [ planeX dirX ] -1 [ dirY -dirX ]
                // [ ] = 1/(planeX*dirY-dirX*planeY) * [ ]
                // [ planeY dirY ] [ -planeY planeX ]

                double invDet = 1.0 / (planeX * dirY - dirX * planeY); // required for correct matrix multiplication

                double transformX = invDet * (dirY * spriteX - dirX * spriteY);
                double transformY = invDet * (-planeY * spriteX + planeX * spriteY); // this is actually the depth
                                                                                     // inside the screen, that what Z
                                                                                     // is in 3D

                int spriteScreenX = (int) ((w / 2) * (1 + transformX / transformY));

                // calculate height of the sprite on screen
                int spriteHeight = Math.abs((int) (h / (transformY))); // using 'transformY' instead of the real
                                                                       // distance prevents fisheye
                // calculate lowest and highest pixel to fill in current stripe
                int drawStartY = -spriteHeight / 2 + h / 2;
                if (drawStartY < 0)
                    drawStartY = 0;
                int drawEndY = spriteHeight / 2 + h / 2;
                if (drawEndY >= h)
                    drawEndY = h - 1;

                // calculate width of the sprite
                int spriteWidth = Math.abs((int) (h / (transformY)));
                int drawStartX = -spriteWidth / 2 + spriteScreenX;
                if (drawStartX < 0)
                    drawStartX = 0;
                int drawEndX = spriteWidth / 2 + spriteScreenX;
                if (drawEndX >= w)
                    drawEndX = w - 1;

                // loop through every vertical stripe of the sprite on screen
                for (int stripe = drawStartX; stripe < drawEndX; stripe++) {
                    int spriteTexX = (int) (256 * (stripe - (-spriteWidth / 2 + spriteScreenX)) * texWidth
                            / spriteWidth) / 256;
                    // the conditions in the if are:
                    // 1) it's in front of camera plane so you don't see things behind you
                    // 2) it's on the screen (left)
                    // 3) it's on the screen (right)
                    // 4) ZBuffer, with perpendicular distance
                    if (transformY > 0 && stripe > 0 && stripe < w && transformY < zbuffer[stripe])
                        for (int y = drawStartY; y < drawEndY; y++) // for every pixel of the current stripe
                        {
                            int d = (y) * 256 - h * 128 + spriteHeight * 128; // 256 and 128 factors to avoid floats
                            int texY = ((d * texHeight) / spriteHeight) / 256;
                            int color = this.sprite_textures[sprite.texture][texWidth * texY + spriteTexX]; // get
                                                                                                            // current
                                                                                                            // color
                                                                                                            // from the
                                                                                                            // texture
                            if ((color & 0x00FFFFFF) != 0)
                                buffer[y][stripe] = color; // paint pixel if it isn't black, black is the invisible
                                                           // color
                        }
                }
            }
        }

        for (int y = 0; y < buffer.length; y++) {
            for (int x = 0; x < buffer[y].length; x++) {
                g.setColor(new Color(buffer[y][x]));
                g.fillRect(x * this.resolution, y * this.resolution, this.resolution, this.resolution);
            }
        }

        // PAINT UI COMPONENTS

        g2D.setPaint(Color.gray); // draw toolbar
        g2D.fillRect(0, 450, this.getWidth(), 120);

        minimap.paint(g); // draw minimap

    }
}

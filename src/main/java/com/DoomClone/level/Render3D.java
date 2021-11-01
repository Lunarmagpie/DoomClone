package com.DoomClone.level;

import com.DoomClone.states.Stage;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import java.awt.Toolkit;

import org.json.JSONArray;

public class Render3D extends JPanel {

    Stage stage;
    Render2D minimap;

    int screenWidth;
    int screenHeight;

    int texWidth = 32;
    int texHeight = 32;
    int[][] texture;

    int h;
    int toolbarHeight = 120;
    int resolution = 2;
    double planeX = 0, planeY = 0.75;
    double time = 0;
    double oldTime = 0;

    public Render3D(Stage stage) {
        this.stage = stage;
        this.screenWidth = getWidth();
        this.screenHeight = getHeight();

        // Generate Textures
        // generate some textures
        this.texture = new int[1][texWidth * texHeight + texWidth];

        this.texture[0] = new int[] {3480339, 3939349, 3348753, 3940118, 3019787, 3282960, 4333849, 3348499, 3742225, 2953997, 2824984, 2890264, 2890264, 2890264, 2890264, 2890264, 2890264, 2890264, 2890263, 2890263, 2890263, 2628376, 3218457, 4138530, 9788493, 4398612, 7551536, 5186071, 7617845, 10444363, 8208952, 3022358, 9062713, 12085574, 9586986, 13995354, 10046509, 13798235, 11888450, 9324070, 13928535, 9587250, 3873814, 2759190, 3219227, 3022105, 3022105, 3153434, 3153434, 3153434, 3022107, 3022107, 3022107, 3350041, 2891549, 4529169, 8667184, 5121561, 8273709, 5382675, 8011571, 10773324, 8077104, 3480085, 9062713, 12085321, 9586733, 13995356, 9914673, 13797980, 11822915, 9258536, 13928535, 9587250, 3677208, 2891289, 3613728, 4862764, 4862764, 4731435, 4731435, 4731435, 4862764, 4862764, 4862764, 4731435, 4731434, 10903886, 5185301, 3938834, 8535855, 5513492, 8405043, 11034956, 8338479, 5317661, 9258039, 12282184, 9456425, 14060381, 9980974, 13929819, 11757634, 9258536, 13536346, 9456689, 3480853, 2890520, 5453618, 4600107, 4534570, 4600107, 4600107, 4600107, 4600107, 4796713, 4796713, 4271399, 5320225, 8142383, 4662299, 3808279, 8273452, 5513238, 8273971, 10838602, 8142127, 5054743, 9389110, 12085576, 9521453, 14060889, 9849906, 13995103, 12019521, 9520941, 13732440, 9718067, 3677208, 2890520, 5453618, 4600107, 4534570, 4600107, 4600107, 4600107, 4600107, 4796713, 4796713, 4336935, 5843997, 8537657, 3481113, 3677206, 8273452, 5513238, 8273971, 10838602, 8142127, 5054743, 9982532, 11232327, 9192741, 13799266, 9652785, 13667163, 11430214, 9127462, 13734501, 9522742, 3677461, 2890520, 5453618, 4600107, 4534570, 4600107, 4600107, 4600107, 4600107, 4796713, 4796713, 5714211, 10509387, 4334621, 2955801, 3939865, 8273452, 5513238, 8405300, 10575430, 8207920, 5054743, 8932415, 14790552, 9063981, 14724755, 9261373, 13338978, 14856341, 9653556, 14395786, 9655107, 3349786, 2890520, 5453618, 4600107, 4534570, 4600107, 4600107, 4600107, 4600107, 4796713, 4927785, 10115141, 8011569, 2626833, 3349787, 3874585, 8208174, 5513492, 8208691, 10575175, 8207920, 4989208, 9195329, 14658969, 9654065, 14987928, 8604214, 12944996, 14526355, 9128753, 13936014, 9457733, 3087898, 2168338, 4994094, 4600107, 4600107, 4600107, 4600107, 4600107, 4796713, 4534570, 5190184, 8076846, 4924184, 3218457, 2824984, 3874585, 8404526, 5841940, 8405554, 10575175, 8470573, 5186072, 8998209, 11628377, 9850167, 13142631, 9259831, 13668202, 11627857, 8800302, 13866606, 9983042, 3677208, 2168338, 4994094, 4600107, 4600107, 4600107, 4600107, 4600107, 4796713, 4600107, 5519407, 7225651, 4400669, 2628374, 2562582, 3612184, 7749681, 4989976, 7947061, 10247244, 7946031, 4858650, 3481111, 4270628, 4796712, 5059882, 4401699, 4994089, 5059371, 4796712, 4468001, 4993836, 4863532, 4994094, 4731435, 4731435, 4731435, 4862764, 4994094, 4994094, 4731435, 4731435, 4796713, 5715759, 4993836, 4469034, 4534314, 4140327, 4994089, 4468776, 4993580, 5847091, 4467491, 4600107, 3808280, 3085069, 3544847, 3545103, 4662803, 4925469, 4595993, 3744029, 2759704, 3086871, 2890264, 2628374, 2694166, 3479832, 4004881, 3676692, 4465433, 3610898, 4071188, 3283220, 3874066, 4399381, 3283736, 2759703, 2628121, 2890264, 2890264, 2890264, 3021851, 2758937, 3021851, 2890264, 9456957, 7287066, 13799014, 8010271, 13601373, 9127213, 9389876, 6632486, 2890521, 2759193, 2628122, 3086870, 2628375, 4135696, 13666916, 7287067, 13864553, 7286813, 10902592, 5644307, 10179642, 6697247, 4991009, 2759703, 2759193, 2890263, 2890263, 2890263, 2890264, 2758936, 2890264, 2890264, 9062710, 9718318, 13995611, 9324072, 13798233, 9653557, 9456187, 8275256, 3677206, 3611673, 3677464, 3480340, 3086868, 4595217, 13996130, 9652525, 13797982, 9652273, 10574143, 5513235, 10245437, 7615263, 4661530, 3942689, 2496019, 6767678, 5190446, 1839117, 6438971, 2036239, 2430226, 2496019, 9062965, 9653040, 13996122, 9193003, 13732699, 9653043, 10046779, 9784122, 7223596, 7223597, 7682342, 9981750, 9981492, 9915698, 13601371, 9653036, 13732699, 9586739, 10704957, 5709587, 10441788, 7418658, 4268060, 4534311, 4861222, 6439225, 4007200, 3218969, 6242104, 3153178, 6504763, 5059373, 9062710, 9718318, 13995612, 9258536, 13732699, 9521970, 9915450, 9719100, 7024931, 6630426, 11493954, 12216897, 12151616, 11560508, 13666906, 9652525, 13798235, 9652273, 10639678, 5578773, 10310973, 7484193, 4465179, 4929068, 2561811, 5979188, 4927786, 2956053, 6242104, 2562581, 6242617, 13344402, 9062710, 9718318, 13995612, 9258536, 13732699, 9521970, 9915450, 9719100, 7090722, 14193266, 13468761, 14258014, 13205073, 12021314, 13929820, 9652525, 13798235, 9652273, 10639678, 5578773, 10310973, 7484193, 4465179, 4863275, 3021849, 6242104, 4664872, 2758933, 6767677, 2430996, 3087898, 6505018, 9325625, 9718318, 13995612, 9258536, 13732699, 9521970, 9915450, 9719100, 6696475, 13601120, 13798233, 13798233, 13798233, 11955521, 13666649, 9652525, 13666649, 9520944, 10902592, 5381907, 10310973, 7615522, 4530972, 4600360, 3021849, 5979190, 4861995, 2956055, 6504763, 2497047, 3022105, 6373432, 9259832, 9718319, 13995612, 9258536, 13798235, 9587250, 9849658, 9587773, 8142374, 13337948, 13667166, 13667166, 13732446, 12020546, 13797465, 9652525, 13666649, 9455408, 10902594, 5381907, 10310717, 7549729, 4333849, 4600361, 3021849, 5979191, 4861995, 3021849, 6504763, 2497047, 3022105, 6505018, 8931893, 10112305, 13863770, 10507315, 13600085, 9652785, 9520950, 8930869, 4858650, 4464406, 4201747, 4398611, 4529684, 8668455, 13995872, 10243635, 14061150, 10112054, 10639166, 5906964, 10573631, 7680801, 4465432, 4534570, 2956055, 6438970, 4861995, 2956055, 6504763, 2758933, 2890263, 6636348, 2757644, 5780259, 5911583, 6174502, 5714207, 5714213, 5845287, 5320231, 6239533, 6239533, 6239533, 6174251, 6043181, 4268306, 5451034, 6371624, 5451549, 5254431, 5582884, 5517607, 5451298, 5516577, 5058085, 4994607, 4008483, 5519666, 4796715, 4796715, 4993836, 5059629, 4862251, 4796715, 2759704, 3876899, 4534312, 3745314, 5848119, 4008483, 4730922, 4600108, 4534570, 4534570, 4534570, 4600107, 4469036, 4403241, 4994094, 3876897, 5388595, 4140069, 4599850, 4467752, 5190704, 4403497, 4862765, 4467752, 5059887, 4600107, 4534314, 4600107, 4534314, 4600107, 4402985, 4336935, 3612700, 2889749, 2628376, 2890521, 3283993, 2759193, 3021333, 2824727, 2628121, 2824732, 2628122, 2824727, 2890264, 3481633, 5253663, 4071966, 3677206, 2628886, 2627088, 3416349, 2760476, 2430483, 3218457, 2825235, 2628633, 2890263, 2890264, 2824984, 2890262, 3152917, 3021333, 2890262, 4400928, 4006170, 3152917, 2759704, 3021849, 2890263, 2890261, 2562840, 3086871, 2628376, 2890263, 2824727, 2890264, 3350302, 9590598, 6039069, 5186072, 3349526, 4137247, 4662049, 3414031, 13076846, 8866361, 13011567, 4662304, 2628376, 2628376, 2628376, 2628121, 2628121, 2628121, 2759194, 4661526, 8338475, 11362634, 9522746, 10640716, 8078133, 10511189, 7422266, 3283993, 6371888, 2363405, 2824984, 2890264, 2758933, 12814189, 8208941, 8538422, 2759186, 4266767, 14655342, 8996393, 13798235, 9652010, 13666655, 5382167, 4465435, 7684663, 4924442, 10247244, 8144958, 5384480, 10313291, 8407098, 11955537, 11297088, 13666403, 11429192, 14258284, 10837571, 10640968, 3543563, 11560528, 8208945, 2890520, 3021850, 3218971, 8933181, 6368030, 5448727, 3020821, 13273187, 9981488, 8010279, 13667417, 9324327, 13601883, 5513492, 4791827, 8208690, 5381907, 11559754, 7221538, 5775892, 11560265, 4464406, 8536113, 14062706, 11165508, 13864817, 10638909, 11101005, 10639165, 5711124, 10508864, 14061679, 3544848, 3415578, 3088415, 10115657, 5711124, 4989720, 2627089, 13864289, 8798246, 4726291, 13732699, 9324074, 13733474, 5250836, 5120278, 4528914, 5842197, 8338469, 6236442, 5317657, 8799022, 3479058, 10180680, 5251346, 11165512, 10311493, 10639167, 5514778, 10968644, 10640197, 6696476, 10771780, 11100236, 6830898, 3285281, 10246473, 5711124, 5514781, 3808274, 13535070, 7024928, 3151115, 13732699, 9324074, 13733474, 5250836, 5645594, 7486255, 6039063, 11493701, 8207915, 5185298, 11165508, 3480340, 10706499, 6170649, 11560012, 4658959, 11494733, 8011569, 4464144, 11165769, 7024928, 5251347, 11164739, 10969421, 3283220, 9851972, 5842197, 4989209, 4530193, 13929573, 4135696, 3086862, 13732699, 9324075, 13733475, 5382677, 5514265, 8011572, 5907477, 11231040, 8602416, 5711129, 11099455, 3282965, 11034441, 7815221, 11230788, 8273709, 10245957, 10573890, 4860703, 7880494, 11164991, 8208174, 5187611, 10706248, 10902594, 10706248, 5710870, 5974551, 13010268, 8798252, 3217427, 3283219, 13799004, 9324074, 13930082, 5710106, 5317915, 8274741, 5841942, 11033920, 8340527, 4989205, 11164481, 3282965, 11034441, 7814961, 11230788, 8208175, 4593423, 10574922, 6700338, 4398614, 11429713, 10509645, 2757134, 5843485, 10508357, 10311750, 5841940, 4922385, 13667948, 9062716, 3546646, 3086865, 13535834, 8732708, 13799267, 5578773, 5383448, 8471347, 5841940, 11231042, 8602416, 5251346, 11033921, 3217687, 5648165, 5123879, 5910306, 6438707, 5321512, 5517863, 4599591, 4469034, 6438707, 4795684, 4337963, 4993833, 6175276, 5517863, 5714729, 5517609, 5188642, 6568498, 5584684, 5190699, 5977632, 5385245, 6897450, 5583401, 5386794, 4531743, 5254435, 5910307, 5582629, 4860451, 6042917, 3022107, 4467751, 4402984, 4664100, 3220767, 3482912, 4534314, 4797486, 4336935, 4403241, 4140071, 4534314, 4796713, 4731435, 4600107, 4139043, 4600107, 4927786, 4599338, 4862246, 4271399, 5190956, 4467751, 4271138, 4994607, 4862764, 5453618, 4993835, 4664871, 4467751, 4074790, 4599850};

        minimap = new Render2D(stage);
    }

    public void rotate(double theta) {
        double oldPlaneX = planeX;
        planeX = planeX * Math.cos(theta) - planeY * Math.sin(theta);
        planeY = oldPlaneX * Math.sin(theta) + planeY * Math.cos(theta);
    }

    public void paint(Graphics g) {
        screenWidth = (int) ((this.getWidth()) / resolution);
        h = (int) ((this.getHeight() - this.toolbarHeight) / resolution);
        Toolkit.getDefaultToolkit().sync();

        // DRAW RAYS
        Graphics2D g2D = (Graphics2D) g;

        // fill sky and ground
        g2D.setPaint(Color.WHITE);
        g2D.fillRect(0, 0, this.getWidth(), this.getHeight());

        g2D.setPaint(Color.BLACK);
        g2D.fillRect(0, (this.getHeight() - this.toolbarHeight) / 2, this.getWidth(), this.getHeight() / 2);

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
                g2D.fillRect(x*this.resolution, y*this.resolution, this.resolution, this.resolution);
            }


        }

        //PAINT UI COMPONENTS

        g2D.setPaint(Color.BLUE); //draw toolbar
        g2D.fillRect(0, 450, this.getWidth(), 120);

        minimap.paint(g); //draw minimap

    }
}

package orbitjam;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class SpriteLoader {
    public static BufferedImage player_hurt, enemic1, enemic2, enemic3, enemic4, enemic5, enemic6, enemic7, boss1, caixabomba, bomba,redbox,greenbox,bluebox,fons,
            menu, tinyStars, smallStars, pinklaser, pinklaser2, pinkbullet , yellowlaser2, yellowbullet, redlaser1, redlaser2, redlaser3,redlaser4,greenlaser1,greenlaser2, greenlaser3,
            greenlaser4,bluelaser1,bluelaser2,bluelaser3, bluelaser4,meteoritgran1, meteoritgran2,meteoritgran3,meteoritgran4,miniheart,heart, powerup1, test, right1,right2,left1,left2, shield;
    public static BufferedImage animation1, animation2, animation3, animation4, player_shield_sheet, player_sheet;
    public static BufferedImage moveparticle;
    public static Image[] explosionanimation, triplexplosionanimation, bigexplosionanimation, player, player_shield;
    static {
        try {
            player_sheet  = ImageIO.read(SpriteLoader.class.getResource("/resizesprites/player_sprites/playership_sheet.png"));
            player_hurt  = ImageIO.read(SpriteLoader.class.getResource("/resizesprites/player_sprites/playership_hurt.png"));
            shield  = ImageIO.read(SpriteLoader.class.getResource("/resizesprites/player_sprites/shield.png"));
            enemic1  = ImageIO.read(SpriteLoader.class.getResource("/resizesprites/enemic_sprites/Enemic1.png"));
            enemic2  = ImageIO.read(SpriteLoader.class.getResource("/resizesprites/enemic_sprites/Enemic2.png"));
            enemic3  = ImageIO.read(SpriteLoader.class.getResource("/resizesprites/enemic_sprites/Enemic3.png"));
            enemic4  = ImageIO.read(SpriteLoader.class.getResource("/resizesprites/enemic_sprites/Enemic4.png"));
            enemic5  = ImageIO.read(SpriteLoader.class.getResource("/resizesprites/enemic_sprites/Enemic5.png"));
            enemic6  = ImageIO.read(SpriteLoader.class.getResource("/resizesprites/enemic_sprites/Enemic6.png"));
            enemic7  = ImageIO.read(SpriteLoader.class.getResource("/resizesprites/enemic_sprites/Enemic7.png"));
            boss1  = ImageIO.read(SpriteLoader.class.getResource("/resizesprites/enemic_sprites/boss1.png"));
            caixabomba   = ImageIO.read(SpriteLoader.class.getResource("/resizesprites/caixa_sprites/caixabomba.png"));
            bomba   = ImageIO.read(SpriteLoader.class.getResource("/resizesprites/laser_sprites/bomba.png"));
            redbox   = ImageIO.read(SpriteLoader.class.getResource("/resizesprites/caixa_sprites/heartbox.png"));
            greenbox   = ImageIO.read(SpriteLoader.class.getResource("/resizesprites/caixa_sprites/greenbox.png"));
            bluebox   = ImageIO.read(SpriteLoader.class.getResource("/resizesprites/caixa_sprites/bluebox.png"));
            fons    = ImageIO.read(SpriteLoader.class.getResource("/resizesprites/background.jpg"));
            menu    = ImageIO.read(SpriteLoader.class.getResource("/resizesprites/menu.png"));
            tinyStars = ImageIO.read(SpriteLoader.class.getResource("/resizesprites/estrellespetites.png"));
            smallStars = ImageIO.read(SpriteLoader.class.getResource("/resizesprites/estrellesmitges.png"));
            pinklaser = ImageIO.read(SpriteLoader.class.getResource("/resizesprites/laser_sprites/pinklaser.png"));
            pinklaser2 = ImageIO.read(SpriteLoader.class.getResource("/resizesprites/laser_sprites/pinklaser2.png"));
            pinkbullet = ImageIO.read(SpriteLoader.class.getResource("/resizesprites/laser_sprites/pinkdot.png"));
            yellowbullet = ImageIO.read(SpriteLoader.class.getResource("/resizesprites/laser_sprites/yellowdot.png"));
            yellowlaser2 = ImageIO.read(SpriteLoader.class.getResource("/resizesprites/laser_sprites/yellowlaser2.png"));
            redlaser1 = ImageIO.read(SpriteLoader.class.getResource("/resizesprites/laser_sprites/redlaser1.png"));
            redlaser2 = ImageIO.read(SpriteLoader.class.getResource("/resizesprites/laser_sprites/redlaser2.png"));
            redlaser3 = ImageIO.read(SpriteLoader.class.getResource("/resizesprites/laser_sprites/redlaser3.png"));
            redlaser4 = ImageIO.read(SpriteLoader.class.getResource("/resizesprites/laser_sprites/redlaser4.png"));
            greenlaser1 = ImageIO.read(SpriteLoader.class.getResource("/resizesprites/laser_sprites/greenlaser1.png"));
            greenlaser2 = ImageIO.read(SpriteLoader.class.getResource("/resizesprites/laser_sprites/greenlaser2.png"));
            greenlaser3 = ImageIO.read(SpriteLoader.class.getResource("/resizesprites/laser_sprites/greenlaser3.png"));
            greenlaser4 = ImageIO.read(SpriteLoader.class.getResource("/resizesprites/laser_sprites/greenlaser4.png"));
            bluelaser1 = ImageIO.read(SpriteLoader.class.getResource("/resizesprites/laser_sprites/bluelaser1.png"));
            bluelaser2 = ImageIO.read(SpriteLoader.class.getResource("/resizesprites/laser_sprites/bluelaser2.png"));
            bluelaser3 = ImageIO.read(SpriteLoader.class.getResource("/resizesprites/laser_sprites/bluelaser3.png"));
            bluelaser4 = ImageIO.read(SpriteLoader.class.getResource("/resizesprites/laser_sprites/bluelaser4.png"));
            meteoritgran1 = ImageIO.read(SpriteLoader.class.getResource("/resizesprites/meteorit_sprites/meteorBrown_big1.png"));
            meteoritgran2 = ImageIO.read(SpriteLoader.class.getResource("/resizesprites/meteorit_sprites/meteorBrown_big2.png"));
            meteoritgran3 = ImageIO.read(SpriteLoader.class.getResource("/resizesprites/meteorit_sprites/meteorBrown_big3.png"));
            meteoritgran4 = ImageIO.read(SpriteLoader.class.getResource("/resizesprites/meteorit_sprites/meteorBrown_big4.png"));
            miniheart = ImageIO.read(SpriteLoader.class.getResource("/resizesprites/littleheart.png"));
            heart = ImageIO.read(SpriteLoader.class.getResource("/resizesprites/heart.png"));
            powerup1 = ImageIO.read(SpriteLoader.class.getResource("/resizesprites/caixa_sprites/powerup1.png"));
            right1 = ImageIO.read(SpriteLoader.class.getResource("/resizesprites/player_sprites/playership_right1.png"));
            right2 = ImageIO.read(SpriteLoader.class.getResource("/resizesprites/player_sprites/playership_right2.png"));
            left1 = ImageIO.read(SpriteLoader.class.getResource("/resizesprites/player_sprites/playership_left1.png"));
            left2 = ImageIO.read(SpriteLoader.class.getResource("/resizesprites/player_sprites/playership_left2.png"));
            moveparticle = ImageIO.read(SpriteLoader.class.getResource("/resizesprites/animacions/move_particle.png"));
            animation1 = ImageIO.read(SpriteLoader.class.getResource("/resizesprites/animacions/explosionanimation.png"));
            animation2 = ImageIO.read(SpriteLoader.class.getResource("/resizesprites/animacions/playership_hurt_animation.png"));
            animation3 = ImageIO.read(SpriteLoader.class.getResource("/resizesprites/animacions/tripleexplosionanimation.png"));
            animation4 = ImageIO.read(SpriteLoader.class.getResource("/resizesprites/animacions/bigexplosion.png"));
            player_shield_sheet = ImageIO.read(SpriteLoader.class.getResource("/resizesprites/player_sprites/playership_shielded_sheet.png"));
            explosionanimation = cutAnimations(animation1,4);
            triplexplosionanimation = cutAnimations(animation3,4);
            bigexplosionanimation = cutAnimations(animation4,8);
            player = cutAnimations(player_sheet,5);
            player_shield = cutAnimations(player_shield_sheet,5);
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }

    }
    public static Image[] cutAnimations (BufferedImage animationImage, int frames){
               Image[] animation = new Image[frames];
              // System.out.println("Animation image height: "+ animationImage.getHeight());
              // System.out.println("Animation image width: "+ animationImage.getWidth());
               if(animationImage.getHeight() < animationImage.getWidth()){
                   for (int i = 0; i< frames; i++){
                       // System.out.println("Frame : "+i);
                       animation[i] = animationImage.getSubimage(animationImage.getWidth()/frames * i, 0,animationImage.getWidth()/frames,animationImage.getHeight());
                       // System.out.println("Imatge tallada correctament");
                   }
               } else{
                   for (int i = 0; i< frames; i++){
                       animation[i] = animationImage.getSubimage(0, animationImage.getHeight()/frames * i,animationImage.getWidth(),animationImage.getHeight()/frames);
                   }
               }
               return animation;
    }
}

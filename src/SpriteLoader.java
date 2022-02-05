import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class SpriteLoader {
    public static Image player, player_hurt, player_shield, enemic1, enemic2, enemic3, enemic4, enemic5, enemic6, enemic7, boss1, caixabomba, bomba,redbox,greenbox,bluebox,fons,
            menu, tinyStars, smallStars, pinklaser, pinklaser2, pinkbullet , yellowlaser2, yellowbullet, redlaser1, redlaser2, redlaser3,redlaser4,greenlaser1,greenlaser2, greenlaser3,
            greenlaser4,bluelaser1,bluelaser2,bluelaser3, bluelaser4,meteoritgran1, meteoritgran2,meteoritgran3,meteoritgran4,miniheart,heart, powerup1, test;
    public static BufferedImage animation1;
    public static BufferedImage animation2;
    public static BufferedImage animation3;
    public static BufferedImage animation4;
    public static BufferedImage moveparticle;
    public static Image[] explosionanimation, planet1, planet2;
    static {
        try {
            player  = ImageIO.read(SpriteLoader.class.getResource("/sprites/player_sprites/playership.png"));
            player_hurt  = ImageIO.read(SpriteLoader.class.getResource("/sprites/player_sprites/playership_hurt.png"));
            player_shield  = ImageIO.read(SpriteLoader.class.getResource("/sprites/player_sprites/playership_shielded.png"));
            enemic1  = ImageIO.read(SpriteLoader.class.getResource("/sprites/enemic_sprites/Enemic1.png"));
            enemic2  = ImageIO.read(SpriteLoader.class.getResource("/sprites/enemic_sprites/Enemic2.png"));
            enemic3  = ImageIO.read(SpriteLoader.class.getResource("/sprites/enemic_sprites/Enemic3.png"));
            enemic4  = ImageIO.read(SpriteLoader.class.getResource("/sprites/enemic_sprites/Enemic4.png"));
            enemic5  = ImageIO.read(SpriteLoader.class.getResource("/sprites/enemic_sprites/Enemic5.png"));
            enemic6  = ImageIO.read(SpriteLoader.class.getResource("/sprites/enemic_sprites/Enemic6.png"));
            enemic7  = ImageIO.read(SpriteLoader.class.getResource("/sprites/enemic_sprites/Enemic7.png"));
            boss1  = ImageIO.read(SpriteLoader.class.getResource("/sprites/enemic_sprites/boss1.png"));
            caixabomba   = ImageIO.read(SpriteLoader.class.getResource("/sprites/caixa_sprites/caixabomba.png"));
            bomba   = ImageIO.read(SpriteLoader.class.getResource("/sprites/laser_sprites/bomba.png"));
            redbox   = ImageIO.read(SpriteLoader.class.getResource("/sprites/caixa_sprites/heartbox.png"));
            greenbox   = ImageIO.read(SpriteLoader.class.getResource("/sprites/caixa_sprites/greenbox.png"));
            bluebox   = ImageIO.read(SpriteLoader.class.getResource("/sprites/caixa_sprites/bluebox.png"));
            fons    = ImageIO.read(SpriteLoader.class.getResource("/sprites/background.jpg"));
            menu    = ImageIO.read(SpriteLoader.class.getResource("/sprites/menu.png"));
            tinyStars = ImageIO.read(SpriteLoader.class.getResource("/sprites/estrellespetites.png"));
            smallStars = ImageIO.read(SpriteLoader.class.getResource("/sprites/estrellesmitges.png"));
            pinklaser = ImageIO.read(SpriteLoader.class.getResource("/sprites/laser_sprites/pinklaser.png"));
            pinklaser2 = ImageIO.read(SpriteLoader.class.getResource("/sprites/laser_sprites/pinklaser2.png"));
            pinkbullet = ImageIO.read(SpriteLoader.class.getResource("/sprites/laser_sprites/pinkdot.png"));
            yellowbullet = ImageIO.read(SpriteLoader.class.getResource("/sprites/laser_sprites/yellowdot.png"));
            yellowlaser2 = ImageIO.read(SpriteLoader.class.getResource("/sprites/laser_sprites/yellowlaser2.png"));
            redlaser1 = ImageIO.read(SpriteLoader.class.getResource("/sprites/laser_sprites/redlaser1.png"));
            redlaser2 = ImageIO.read(SpriteLoader.class.getResource("/sprites/laser_sprites/redlaser2.png"));
            redlaser3 = ImageIO.read(SpriteLoader.class.getResource("/sprites/laser_sprites/redlaser3.png"));
            redlaser4 = ImageIO.read(SpriteLoader.class.getResource("/sprites/laser_sprites/redlaser4.png"));
            greenlaser1 = ImageIO.read(SpriteLoader.class.getResource("/sprites/laser_sprites/greenlaser1.png"));
            greenlaser2 = ImageIO.read(SpriteLoader.class.getResource("/sprites/laser_sprites/greenlaser2.png"));
            greenlaser3 = ImageIO.read(SpriteLoader.class.getResource("/sprites/laser_sprites/greenlaser3.png"));
            greenlaser4 = ImageIO.read(SpriteLoader.class.getResource("/sprites/laser_sprites/greenlaser4.png"));
            bluelaser1 = ImageIO.read(SpriteLoader.class.getResource("/sprites/laser_sprites/bluelaser1.png"));
            bluelaser2 = ImageIO.read(SpriteLoader.class.getResource("/sprites/laser_sprites/bluelaser2.png"));
            bluelaser3 = ImageIO.read(SpriteLoader.class.getResource("/sprites/laser_sprites/bluelaser3.png"));
            bluelaser4 = ImageIO.read(SpriteLoader.class.getResource("/sprites/laser_sprites/bluelaser4.png"));
            meteoritgran1 = ImageIO.read(SpriteLoader.class.getResource("/sprites/meteorit_sprites/meteorBrown_big1.png"));
            meteoritgran2 = ImageIO.read(SpriteLoader.class.getResource("/sprites/meteorit_sprites/meteorBrown_big2.png"));
            meteoritgran3 = ImageIO.read(SpriteLoader.class.getResource("/sprites/meteorit_sprites/meteorBrown_big3.png"));
            meteoritgran4 = ImageIO.read(SpriteLoader.class.getResource("/sprites/meteorit_sprites/meteorBrown_big4.png"));
            miniheart = ImageIO.read(SpriteLoader.class.getResource("/sprites/littleheart.png"));
            heart = ImageIO.read(SpriteLoader.class.getResource("/sprites/heart.png"));
            powerup1 = ImageIO.read(SpriteLoader.class.getResource("/sprites/caixa_sprites/powerup1.png"));
            moveparticle = ImageIO.read(SpriteLoader.class.getResource("/sprites/animacions/move_particle.png"));
            animation1 = ImageIO.read(SpriteLoader.class.getResource("/sprites/animacions/explosionanimation.png"));
            animation2 = ImageIO.read(SpriteLoader.class.getResource("/sprites/animacions/playership_hurt_animation.png"));
            animation3 = ImageIO.read(SpriteLoader.class.getResource("/sprites/animacions/planet4animation.png"));
            animation4 = ImageIO.read(SpriteLoader.class.getResource("/sprites/animacions/planet2animation.png"));
            // test = new ImageIcon(new URL("http://i.stack.imgur.com/KSnus.gif")).getImage();
            explosionanimation = cutAnimations(animation1,4);
            planet1 = cutAnimations(animation3,120);
            planet2 = cutAnimations(animation4,30);
            // galaxy1 = cutAnimations(animation5,30);
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

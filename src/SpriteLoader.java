import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class SpriteLoader {
    public static Image player, enemic1, enemic2, enemic3, enemic4, enemic5, enemic6, boss1, caixabomba, bomba,redbox,greenbox,bluebox,fons,
            menu,estrellespetites, estrellesmitges, pinklaser, pinklaser2, yellowlaser2, yellowbullet, redlaser1, redlaser2, redlaser3,greenlaser1,greenlaser2, greenlaser3,
            bluelaser1,bluelaser2,bluelaser3,meteoritgran1, meteoritgran2,meteoritgran3,meteoritgran4,miniheart,heart, powerup1;
    static {
        try {
            player  = ImageIO.read(SpriteLoader.class.getResource("/sprites/player_sprites/player_invis.png"));
            enemic1  = ImageIO.read(SpriteLoader.class.getResource("/sprites/enemic_sprites/Enemic1.png"));
            enemic2  = ImageIO.read(SpriteLoader.class.getResource("/sprites/enemic_sprites/Enemic2.png"));
            enemic3  = ImageIO.read(SpriteLoader.class.getResource("/sprites/enemic_sprites/Enemic3.png"));
            enemic4  = ImageIO.read(SpriteLoader.class.getResource("/sprites/enemic_sprites/Enemic4.png"));
            enemic5  = ImageIO.read(SpriteLoader.class.getResource("/sprites/enemic_sprites/Enemic5.png"));
            enemic6  = ImageIO.read(SpriteLoader.class.getResource("/sprites/enemic_sprites/Enemic6.png"));
            boss1  = ImageIO.read(SpriteLoader.class.getResource("/sprites/enemic_sprites/boss1.png"));
            caixabomba   = ImageIO.read(SpriteLoader.class.getResource("/sprites/caixa_sprites/caixabomba.png"));
            bomba   = ImageIO.read(SpriteLoader.class.getResource("/sprites/laser_sprites/bomba.png"));
            redbox   = ImageIO.read(SpriteLoader.class.getResource("/sprites/caixa_sprites/heartbox.png"));
            greenbox   = ImageIO.read(SpriteLoader.class.getResource("/sprites/caixa_sprites/greenbox.png"));
            bluebox   = ImageIO.read(SpriteLoader.class.getResource("/sprites/caixa_sprites/bluebox.png"));
            fons    = ImageIO.read(SpriteLoader.class.getResource("/sprites/background.jpg"));
            menu    = ImageIO.read(SpriteLoader.class.getResource("/sprites/menu.png"));
            estrellespetites = ImageIO.read(SpriteLoader.class.getResource("/sprites/estrellespetites.png"));
            estrellesmitges = ImageIO.read(SpriteLoader.class.getResource("/sprites/estrellesmitges.png"));
            pinklaser = ImageIO.read(SpriteLoader.class.getResource("/sprites/laser_sprites/pinklaser.png"));
            pinklaser2 = ImageIO.read(SpriteLoader.class.getResource("/sprites/laser_sprites/pinklaser2.png"));
            yellowbullet = ImageIO.read(SpriteLoader.class.getResource("/sprites/laser_sprites/yellowdot.png"));
            yellowlaser2 = ImageIO.read(SpriteLoader.class.getResource("/sprites/laser_sprites/yellowlaser2.png"));
            redlaser1 = ImageIO.read(SpriteLoader.class.getResource("/sprites/laser_sprites/redlaser1.png"));
            redlaser2 = ImageIO.read(SpriteLoader.class.getResource("/sprites/laser_sprites/redlaser2.png"));
            redlaser3 = ImageIO.read(SpriteLoader.class.getResource("/sprites/laser_sprites/redlaser3.png"));
            greenlaser1 = ImageIO.read(SpriteLoader.class.getResource("/sprites/laser_sprites/greenlaser1.png"));
            greenlaser2 = ImageIO.read(SpriteLoader.class.getResource("/sprites/laser_sprites/greenlaser2.png"));
            greenlaser3 = ImageIO.read(SpriteLoader.class.getResource("/sprites/laser_sprites/greenlaser3.png"));
            bluelaser1 = ImageIO.read(SpriteLoader.class.getResource("/sprites/laser_sprites/bluelaser1.png"));
            bluelaser2 = ImageIO.read(SpriteLoader.class.getResource("/sprites/laser_sprites/bluelaser2.png"));
            bluelaser3 = ImageIO.read(SpriteLoader.class.getResource("/sprites/laser_sprites/bluelaser3.png"));
            meteoritgran1 = ImageIO.read(SpriteLoader.class.getResource("/sprites/meteorit_sprites/meteorBrown_big1.png"));
            meteoritgran2 = ImageIO.read(SpriteLoader.class.getResource("/sprites/meteorit_sprites/meteorBrown_big2.png"));
            meteoritgran3 = ImageIO.read(SpriteLoader.class.getResource("/sprites/meteorit_sprites/meteorBrown_big3.png"));
            meteoritgran4 = ImageIO.read(SpriteLoader.class.getResource("/sprites/meteorit_sprites/meteorBrown_big4.png"));
            miniheart = ImageIO.read(SpriteLoader.class.getResource("/sprites/littleheart.png"));
            heart = ImageIO.read(SpriteLoader.class.getResource("/sprites/heart.png"));
            powerup1 = ImageIO.read(SpriteLoader.class.getResource("/sprites/caixa_sprites/powerup1.png"));
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}

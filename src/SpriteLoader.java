import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class SpriteLoader {
    public static Image player, enemic1, enemic2, enemic3, enemic4, enemic5, enemic6, caixabomba, bomba,redbox,greenbox,bluebox,fons,
            menu,estrellespetites, estrellesmitges, pinklaser, yellowbullet, redlaser1, redlaser2,greenlaser1,greenlaser2,bluelaser1,bluelaser2,meteoritgran1,
            meteoritgran2,meteoritgran3,meteoritgran4;
    static {
        try {
            player  = ImageIO.read(new File("sprites/player_sprites/player_invis.png"));
            enemic1 = ImageIO.read(new File("sprites/enemic_sprites/Enemic1.png"));
            enemic2 = ImageIO.read(new File("sprites/enemic_sprites/Enemic2.png"));
            enemic3 = ImageIO.read(new File("sprites/enemic_sprites/Enemic3.png"));
            enemic4 = ImageIO.read(new File("sprites/enemic_sprites/Enemic4.png"));
            enemic5 = ImageIO.read(new File("sprites/enemic_sprites/Enemic5.png"));
            enemic6 = ImageIO.read(new File("sprites/enemic_sprites/Enemic6.png"));
            caixabomba   = ImageIO.read(new File("sprites/caixa_sprites/caixabomba.png"));
            bomba   = ImageIO.read(new File("sprites/laser_sprites/bomba.png"));
            redbox   = ImageIO.read(new File("sprites/caixa_sprites/heartbox.png"));
            greenbox   = ImageIO.read(new File("sprites/caixa_sprites/greenbox.png"));
            bluebox   = ImageIO.read(new File("sprites/caixa_sprites/bluebox.png"));
            fons    = ImageIO.read(new File("sprites/background.jpg"));
            menu    = ImageIO.read(new File("sprites/menu.png"));
            estrellespetites = ImageIO.read(new File("sprites/estrellespetites.png"));
            estrellesmitges = ImageIO.read(new File("sprites/estrellesmitges.png"));
            pinklaser = ImageIO.read(new File("sprites/laser_sprites/pinklaser.png"));
            yellowbullet = ImageIO.read(new File("sprites/laser_sprites/yellowbullet.png"));
            redlaser1 = ImageIO.read(new File("sprites/laser_sprites/redlaser1.png"));
            redlaser2 = ImageIO.read(new File("sprites/laser_sprites/redlaser2.png"));
            greenlaser1 = ImageIO.read(new File("sprites/laser_sprites/greenlaser1.png"));
            greenlaser2 = ImageIO.read(new File("sprites/laser_sprites/greenlaser2.png"));
            bluelaser1 = ImageIO.read(new File("sprites/laser_sprites/bluelaser1.png"));
            bluelaser2 = ImageIO.read(new File("sprites/laser_sprites/bluelaser2.png"));
            meteoritgran1 = ImageIO.read(new File("sprites/meteorit_sprites/meteorBrown_big1.png"));
            meteoritgran2 = ImageIO.read(new File("sprites/meteorit_sprites/meteorBrown_big2.png"));
            meteoritgran3 = ImageIO.read(new File("sprites/meteorit_sprites/meteorBrown_big3.png"));
            meteoritgran4 = ImageIO.read(new File("sprites/meteorit_sprites/meteorBrown_big4.png"));

        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}

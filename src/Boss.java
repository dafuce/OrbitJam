import java.awt.*;

public class Boss extends WindowObject {

    int maxHealth;
    private boolean moveLeft;
    private boolean updown;

    Boss(int bosskills, Game game){
        this.game = game;
        health = 60+30*bosskills;
        maxHealth = health;
        width = 100;
        height = 100;
        velocityY = 0;
        velocityX = 5;
        x = (double) game.getGameHeight() / 2 - (double) width / 2;
        y = -height;
        moveLeft = true;
        timer = System.currentTimeMillis();
        score = 20000+bosskills*20000;
        audio = SoundLoader.explosioboss;
        animation = SpriteLoader.bigexplosionanimation;
    }

    @Override
    public boolean move() {
            if (y < 20 + height) {
                y += 1;
            } else {
                if (moveLeft && x - velocityX >= 0) {
                    x -= velocityX;
                }
                else if (!moveLeft && x + width + velocityX <= game.getGameHeight()) {
                    x += velocityX;
                } else {
                    moveLeft = !moveLeft;
                    if(health <= maxHealth /2) {
                        updown = true;
                    }
                }
                if (updown) {
                    if (x >= (double)(game.getGameHeight() - width)/2) {
                        y = game.getGameWidth() - (x+ width) + 20 + height;
                    } else {
                        y = x + 20 + height;
                    }
                }
            }
            return true;
    }
    public void paint(Graphics g){
        if(game.testools){
            g.drawRect((int)x,(int)y, width, height);
        }
        g.drawImage(SpriteLoader.boss1,(int) x-4,(int) y-4,null);
        g.setColor(Color.RED);
        g.fillRect(game.getGameHeight() /3, game.getGameWidth() /10,(int)(health *((((double) game.getGameHeight() /3)/ maxHealth))),19);
        g.setColor(Color.WHITE);
        g.drawRect(game.getGameHeight() /3, game.getGameWidth() /10, game.getGameHeight() /3,20);

    }
    public void shoot(){
            if (System.currentTimeMillis() - timer > 1000) {
                timer = System.currentTimeMillis();
                Bullet bullet1 = new Bullet(x , y + height -20, game);
                Bullet bullet2 = new Bullet(x + (double) width /2 -12, y + height +20, game);
                Bullet bullet3 = new Bullet(x + width - 24, y + height -20, game);
                bullet1.chooseType(16);
                bullet2.chooseType(16);
                bullet3.chooseType(16);
                bullet1.audio.play();
                game.enemybullets.add(bullet1);
                game.enemybullets.add(bullet2);
                game.enemybullets.add(bullet3);

                Bullet bullet4 = new Bullet(x + (double) (width /2) -10, y+(double) height /2, game);
                bullet4.chooseType(18);
                bullet4.track(6,game.player);
                game.enemybullets.add(bullet4);
            }
    }
}

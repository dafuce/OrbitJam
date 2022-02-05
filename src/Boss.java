import java.awt.*;

public class Boss extends WindowObject {

    int maxHealth;
    private boolean moveLeft;
    private boolean updown;

    Boss(int bosskills){
        health = 60+30*bosskills;
        maxHealth = health;
        width = 100;
        height = 100;
        velocityY = 0;
        velocityX = 5;
        x = (double) Game.WINDOW_WIDTH / 2 - (double) width / 2;
        y = -height;
        moveLeft = true;
        timer = System.currentTimeMillis();
        score = 20000+bosskills*20000;
        audio = SoundLoader.explosioboss;
    }

    @Override
    public boolean move() {
            if (y < 20 + height) {
                y += 1;
            } else {
                if (moveLeft && x - velocityX >= 0) {
                    x -= velocityX;
                }
                else if (!moveLeft && x + width + velocityX <= Game.WINDOW_WIDTH) {
                    x += velocityX;
                } else {
                    moveLeft = !moveLeft;
                    if(health <= maxHealth /2) {
                        updown = true;
                    }
                }
                if (updown) {
                    if (x >= (double)(Game.WINDOW_WIDTH - width)/2) {
                        y = Game.WINDOW_HEIGHT - (x+ width) + 20 + height;
                    } else {
                        y = x + 20 + height;
                    }
                }
            }
            return true;
    }
    public void paint(Graphics g){
        if(Game.testools){
            g.drawRect((int)x,(int)y, width, height);
        }
        g.drawImage(SpriteLoader.boss1,(int) x-4,(int) y-4,null);
        g.setColor(Color.RED);
        g.fillRect(Game.WINDOW_WIDTH /3, Game.WINDOW_HEIGHT /10,(int)(health *((((double) Game.WINDOW_WIDTH /3)/ maxHealth))),19);
        g.setColor(Color.WHITE);
        g.drawRect(Game.WINDOW_WIDTH /3, Game.WINDOW_HEIGHT /10, Game.WINDOW_WIDTH /3,20);

    }
    public void shoot(){
            if (System.currentTimeMillis() - timer > 1000) {
                timer = System.currentTimeMillis();
                Bullet bullet1 = new Bullet(x , y + height -20);
                Bullet bullet2 = new Bullet(x + (double) width /2 -12, y + height +20);
                Bullet bullet3 = new Bullet(x + width - 24, y + height -20);
                bullet1.chooseType(16);
                bullet2.chooseType(16);
                bullet3.chooseType(16);
                bullet1.audio.play();
                Game.enemybullets.add(bullet1);
                Game.enemybullets.add(bullet2);
                Game.enemybullets.add(bullet3);

                Bullet bullet4 = new Bullet(x + (double) width /2 -10, y+(double) height /2);
                bullet4.chooseType(18);
                bullet4.setVelocityX(Game.player.getX()- bullet4.getX()/(double)100);
                bullet4.setVelocityY(Game.player.getY()- bullet4.getY()/(double)100);
                Game.enemybullets.add(bullet4);
            }
    }
}

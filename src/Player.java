import java.awt.*;

public class Player extends WindowObject {
    key RIGHT;
    key LEFT;
    key UP;
    key DOWN;
    double velocity;
    double acceltime;
    int[] ammo;
    long timer2 = 0;

    int shootPower;
    int framecount;

    Player(double x, double y, Game game) {
        RIGHT = new key();
        LEFT = new key();
        DOWN = new key();
        UP = new key();

        shooting = false;
        shootPower = 0;
        width = 20;
        height = 20;
        this.x = x-(double) width /2;
        this.y = y;
        health = 3;
        velocity = 4;
        acceltime = 0.25;
        sprite = SpriteLoader.player;
        ammo = new int[4];
        fireRate = 300;
        framecount = 1;

    }
    int chooseammo() {
        for (int i = ammo.length-1; i >= 0; i--) {
            if (this.ammo[i] > 0) {
                return i;
            }
        }
        return 0;
    }
    public void takeDamage(Game game){
        if(System.currentTimeMillis() - timer2 > 1000 && !Game.testools) {
            timer2 = System.currentTimeMillis();
            SoundLoader.damage.play();
            this.health--;
            if (this.health <= 0) {
                Game.state = Game.State.GAMEOVER;
                game.addRecord();
                SoundLoader.explosio1.play();
            }
        }
    }
    public void shoot(boolean dispara){
        if(dispara && System.currentTimeMillis() - timer > fireRate){
                timer = System.currentTimeMillis();
                Bullet bullet = new Bullet(x+(double) width /2,y);
                int i = chooseammo();
                bullet.chooseType(i+4* shootPower); // todo: la munició millora cada boss kill , punts o temps....
                if(bullet.velocityY > 0){
                    bullet.y =y+ height;
                } else {
                    bullet.y = y- height;
                }
                ammo[i]--;
                bullet.audio.play();
                Game.bullets.add(bullet);
            }
        }
    boolean move(){
        double temp1,temp2,temp3,temp4;
            temp1 = UP.pressed ? -(UP.holding(acceltime)) * velocity : -UP.drifting(acceltime)* velocity;
            temp2 = DOWN.pressed ? DOWN.holding(acceltime) * velocity : DOWN.drifting(acceltime)* velocity;
            velocityY = temp1+temp2;
            temp3 = RIGHT.pressed ? RIGHT.holding(acceltime)* velocity : RIGHT.drifting(acceltime)* velocity;
            temp4 = LEFT.pressed ? -LEFT.holding(acceltime)* velocity : -LEFT.drifting(acceltime)* velocity;
            velocityX = temp3+temp4;

        if (x + velocityX < 8) {
            x = 8;
        }
        else if (x + width + velocityX > Game.WINDOW_WIDTH - 8) {
            x = Game.WINDOW_WIDTH - width -8;
        }
        else if (y + velocityY < 35) {
            y = 35;
        }
        else if (y + height + velocityY > Game.WINDOW_HEIGHT -8) {
            y = Game.WINDOW_HEIGHT - height -8;
        }
        else {
            super.move();
        }
        return true;
    }

    public void paint(Graphics g){
        if(System.currentTimeMillis() - this.timer2 < 1000L){
            if(System.currentTimeMillis() - this.timer2 < 100L *framecount){
                this.sprite = framecount%2 == 1 ? SpriteLoader.player_hurt : SpriteLoader.player;
            }
            else{
                this.framecount++;
            }
        } else{
            framecount = 1;
        }
        g.drawImage(SpriteLoader.moveparticle, (int) this.x+this.width /2 -3, (int) this.y +this.height /2 , null);
        g.drawImage(SpriteLoader.moveparticle, (int) this.x+this.width /2 -3 -(int) velocityX /3  , (int) this.y +this.height /2 -(int) Math.min(velocityY /2,0) , null);
        g.drawImage(SpriteLoader.moveparticle, (int) this.x+this.width /2 -3 -(int) velocityX *2/3  , (int) this.y +this.height /2 -(int) Math.min(velocityY,0) , null);
        g.drawImage(SpriteLoader.moveparticle, (int) this.x+this.width /2 -3 -(int) velocityX, (int) this.y +this.height /2 -(int) Math.min(velocityY *3/2,0) , null);
        super.paint(g);
    }
    public double getVelocity() {
        return velocity;
    }

    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }

    public double getAcceltime() {
        return acceltime;
    }

    public void setAcceltime(double acceltime) {
        this.acceltime = acceltime;
    }

    public int[] getAmmo() {
        return ammo;
    }

    public void setAmmo(int[] ammo) {
        this.ammo = ammo;
    }

    public long getTimer2() {
        return timer2;
    }

    public void setTimer2(long timer2) {
        this.timer2 = timer2;
    }

    public int getShootPower() {
        return shootPower;
    }

    public void setShootPower(int shootPower) {
        this.shootPower = shootPower;
    }

}
class key{
    boolean pressed;
    long date;

    key(){
        pressed = false;
        date =0;
    }
    void press(){
        if(!pressed) {
            pressed = true;
            date = System.currentTimeMillis();
        }
    }
    double holding(double seconds){
        double value;
        value = Math.min((double)(System.currentTimeMillis()-date)/(1000*seconds), 1);

        return value;
    }
    double drifting(double seconds){
        double value;
        value = Math.max(1-(double)(System.currentTimeMillis()-date)/(1000*seconds), 0);

        return value;
    }
    void release() {
        if (pressed) {
            pressed = false;
            date = System.currentTimeMillis();
        }
    }
}

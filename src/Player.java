import java.awt.*;

public class Player extends WindowObject {
    key RIGHT, LEFT, UP, DOWN, SHOOT, ACTION;
    double velocity;
    double acceltime;
    int[] ammo = new int[3];
    int[] inventory = new int[3];
    Image[] movingAnimation = new Image[5];
    long timer2 = 0;
    int shootPower;
    int framecount;
    boolean shielded = false;

    Player(double x, double y, Game game) {
        this.game = game;
        RIGHT = new key();
        LEFT = new key();
        DOWN = new key();
        UP = new key();
        SHOOT = new key();
        ACTION = new key();

        shootPower = 0;
        width = 20;
        height = 20;
        this.x = x-(double) width /2;
        this.y = y;
        health = 3;
        velocity = 3;
        acceltime = 0.3;
        sprite = SpriteLoader.player[2];
        System.arraycopy(SpriteLoader.player, 0, movingAnimation, 0, movingAnimation.length);
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
        if(System.currentTimeMillis() - timer2 > 1000 && !Game.testools && !shielded) {
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
    public void shoot(){
        if(System.currentTimeMillis() - timer > fireRate){
                timer = System.currentTimeMillis();
                Bullet bullet = new Bullet(x+(double) width /2,y, game);
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
        public void action(){
            if (System.currentTimeMillis()- ACTION.whenPressed < 200 && Math.abs(ACTION.whenReleased - ACTION.whenPressed) < 200 && inventory[0] > 0) {
                Bullet bullet = new Bullet(x+(double) width /2,y, game);
                bullet.chooseType(3);
                bullet.setY(y + height);
                inventory[0] -- ;
                bullet.audio.play();
                Game.bullets.add(bullet);
                ACTION.whenReleased = 0;
                System.out.println("Shooting Bomb");
            }
            if(ACTION.pressed && (System.currentTimeMillis() - ACTION.whenPressed > 200) && (System.currentTimeMillis() - ACTION.whenPressed < 2000)) {
                shielded = true;
                System.arraycopy(SpriteLoader.player_shield, 0, movingAnimation, 0, movingAnimation.length);
            } else{
                shielded = false;
                System.arraycopy(SpriteLoader.player, 0, movingAnimation, 0, movingAnimation.length);
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
        else if (x + width + velocityX > game.getGameHeight() - 8) {
            x = game.getGameHeight() - width -8;
        }
        else if (y + velocityY < 35) {
            y = 35;
        }
        else if (y + height + velocityY > game.getGameHeight() -8) {
            y = game.getGameHeight() - height -8;
        }
        else {
            super.move();
        }
        return true;
    }

    public void paint(Graphics g){
        if(velocityX > 0 && velocityX < 5){
            sprite = movingAnimation[3];
        } else if(velocityX >= 5){
            sprite = movingAnimation[4];
        } else if(velocityX < -1 && velocityX > -5){
            sprite = movingAnimation[1];
        } else if(velocityX < -5 ){
            sprite = movingAnimation[0];
        } else{
            sprite = movingAnimation[2];
        }
        if(System.currentTimeMillis() - this.timer2 < 1000L){
            if(System.currentTimeMillis() - this.timer2 < 100L *framecount){
                this.sprite = framecount%2 == 1 ? SpriteLoader.player_hurt : SpriteLoader.player[2];
            }
            else{
                this.framecount++;
            }
        } else{
            framecount = 1;
        }
        /*
        g.drawImage(SpriteLoader.moveparticle, (int) this.x+this.width /2 -3, (int) this.y +this.height /2 , null);
        g.drawImage(SpriteLoader.moveparticle, (int) this.x+this.width /2 -3 -(int) velocityX /3  , (int) this.y +this.height /2 -(int) Math.min(velocityY /2,0) , null);
        g.drawImage(SpriteLoader.moveparticle, (int) this.x+this.width /2 -3 -(int) velocityX *2/3  , (int) this.y +this.height /2 -(int) Math.min(velocityY,0) , null);
        g.drawImage(SpriteLoader.moveparticle, (int) this.x+this.width /2 -3 -(int) velocityX, (int) this.y +this.height /2 -(int) Math.min(velocityY *3/2,0) , null);
         */
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
    long whenPressed, whenReleased;

    key(){
        pressed = false;
        whenPressed = 0;
        whenReleased = 999;
    }
    void press(){
        if(!pressed) {
            pressed = true;
            whenPressed = System.currentTimeMillis();
            System.out.println("pressed");
        }
    }
    double holding(double seconds){
        double value;
        value = Math.min((double)(System.currentTimeMillis()- whenPressed)/(1000*seconds), 1);

        return value;
    }
    double drifting(double seconds){
        double value;
        value = Math.max(1-(double)(System.currentTimeMillis()- whenReleased)/(1000*seconds), 0);

        return value;
    }
    void release() {
            pressed = false;
            whenReleased = System.currentTimeMillis();
            System.out.println("released");
    }
}

package orbitjam;

import java.awt.*;

public class Player extends WindowObject {
    key RIGHT, LEFT, UP, DOWN, SHOOT, ACTION;
    double velocity,acceltime,magnetSpeed;
    int[] ammo = new int[3];
    int[] inventory = new int[]{1,0,0};
    Image[] movingAnimation = new Image[5];
    long lastHurt = 0;
    long lastShielded = 0;
    int shootPower;
    int shieldCooldown, shieldDuration;
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

        magnetSpeed = 0;
        shootPower = 0;
        width = 20;
        height = 20;
        this.x = x-(double) width /2;
        this.y = y;
        health = 3;
        velocity = 3;
        acceltime = 0.3;
        shieldCooldown = 6000;
        shieldDuration = 2000;
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
        if(System.currentTimeMillis() - lastHurt > 1000 && !Game.testools && !shielded) {
            lastHurt = System.currentTimeMillis();
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
                bullet.chooseType(i+4* shootPower);
                bullet.setY(this.y- bullet.getHeight());
                ammo[i]--;
                bullet.audio.play();
                Game.bullets.add(bullet);
            }
        }
        public void action(){
            // Under 200ms press drops bomb ( if inventory not null )
            if (System.currentTimeMillis()- ACTION.whenPressed < 200 && ACTION.whenPressed -ACTION.whenReleased < 200) {
                if(inventory[0] > 0) {
                    Bullet bullet = new Bullet(x + (double) width / 2, y, game);
                    bullet.chooseType(3);
                    bullet.setY(y + height);
                    inventory[0]--;
                    bullet.audio.play();
                    Game.bullets.add(bullet);
                    // This "trick" is so that only enters here once
                    ACTION.whenReleased = 0;
                }
                // Inventory is null, notify the player
                else if(inventory[0] == 0){
                    SoundLoader.back.play();
                    ACTION.whenReleased = 0;
                }
            }
            // You press ACTION key for more than 200ms
            if(ACTION.pressed && (System.currentTimeMillis() - ACTION.whenPressed > 200)) {
                // You are not shielded but it's been over shieldCooldown Time
                if((!shielded && System.currentTimeMillis() - lastShielded > shieldCooldown)){
                    shielded = true;
                }
                // You are shielded and remain like so ,until shieldDuration
                else if((shielded && System.currentTimeMillis() - ACTION.whenPressed < shieldDuration)){
                    shielded = true;
                    System.arraycopy(SpriteLoader.player_shield, 0, movingAnimation, 0, movingAnimation.length);
                    lastShielded = System.currentTimeMillis();
                }
                // None of the above conditions is met
                else{
                    shielded = false;
                    System.arraycopy(SpriteLoader.player, 0, movingAnimation, 0, movingAnimation.length);
                }
            }
            // You are not pressing ACTION key at all
            else {
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
        // TODO: Switch?
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
        if(System.currentTimeMillis() - this.lastHurt < 1000L){
            if(System.currentTimeMillis() - this.lastHurt < 100L *framecount){
                this.sprite = framecount%2 == 1 ? SpriteLoader.player_hurt : SpriteLoader.player[2];
            }
            else{
                this.framecount++;
            }
        } else{
            framecount = 1;
        }
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

    public long getLastHurt() {
        return lastHurt;
    }

    public void setLastHurt(long lastHurt) {
        this.lastHurt = lastHurt;
    }

    public int getShootPower() {
        return shootPower;
    }

    public void setShootPower(int shootPower) {
        this.shootPower = shootPower;
    }

    public int getShieldCooldown() {
        return shieldCooldown;
    }

    public void setShieldCooldown(int shieldCooldown) {
        this.shieldCooldown = shieldCooldown;
    }

    public int getShieldDuration() {
        return shieldDuration;
    }

    public void setShieldDuration(int shieldDuration) {
        this.shieldDuration = shieldDuration;
    }
    public boolean isShielded() {
        return shielded;
    }

    public void setShielded(boolean shielded) {
        this.shielded = shielded;
    }

    public double getMagnetSpeed() {
        return magnetSpeed;
    }

    public void setMagnetSpeed(double magnetSpeed) {
        this.magnetSpeed = magnetSpeed;
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
    }
}

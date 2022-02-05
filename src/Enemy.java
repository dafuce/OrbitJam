import kuusisto.tinysound.Sound;

import java.awt.*;

public class Enemy extends WindowObject {
    int bulletType;

    EnemyType[] type;
    {
        type = new EnemyType[]{
                new EnemyType((double) Utils.rnd2(4)/10,(double) Utils.rnd(2,20)/10,60, 75, 20, 1000, 0,0,SpriteLoader.meteoritgran1,null, SoundLoader.explosio2),
                new EnemyType((double) Utils.rnd2(4)/10,(double) Utils.rnd(2,10)/5,70, 95, 25, 1250,0,0, SpriteLoader.meteoritgran2,null,SoundLoader.explosio2),
                new EnemyType((double) Utils.rnd2(4)/10,(double) Utils.rnd(2,10)/5,60, 70, 20,1000, 0,0,SpriteLoader.meteoritgran3, null,SoundLoader.explosio2),
                new EnemyType((double) Utils.rnd2(4)/10,1.5,80, 80, 25, 1250, 0,0, SpriteLoader.meteoritgran4,null,SoundLoader.explosio2),
                new EnemyType(0, 1.5, 24,24,1, 100,18,1500, SpriteLoader.enemic1, SpriteLoader.explosionanimation,  SoundLoader.explosio1),
                new EnemyType(0, -2.5, 24,24,1, 200,18, 3000,SpriteLoader.enemic2,SpriteLoader.explosionanimation, SoundLoader.explosio4),
                new EnemyType(0, 8, 152,24,1, 300,0, 0,SpriteLoader.enemic3,null, SoundLoader.explosio1),
                new EnemyType(0, 1, 24,24,2, 500, 16,1500,SpriteLoader.enemic4, SpriteLoader.explosionanimation,SoundLoader.explosio3),
                new EnemyType(0, -2, 24,24,2, 500, 19,1000,SpriteLoader.enemic5, SpriteLoader.explosionanimation,SoundLoader.explosio3),
                new EnemyType(0, -4, 24,24,2, 300, 0,0,SpriteLoader.enemic6, SpriteLoader.explosionanimation,SoundLoader.explosio1),
                new EnemyType(0, 2, 24,24,3, 1000, 20,1000,SpriteLoader.enemic7, SpriteLoader.explosionanimation,SoundLoader.explosio1)
        };
    }
    public Enemy(int bosskills, Game game) {
        chooseType(bosskills);
        x = Utils.rnd(1, Game.WINDOW_WIDTH - width);
        if (velocityY > 0) {
            y = -height;
        } else {
            y = Game.WINDOW_HEIGHT + height;
        }
        timer = System.currentTimeMillis();
    }

    public void chooseType(int bosskills) {
        int[] bin;
        if (bosskills == 0) {
            bin = new int[]{0, 1, 2, 3, 4, 4, 4, 5, 5, 5, 6, 6, 7, 8, 8, 9};
        } else {
            bin = new int[]{1, 3, 4, 4, 6, 7, 7, 8, 8, 10};
        }
        int t = bin[(int) (Math.random() * bin.length)];
        this.velocityX = type[t].velocityX;
        this.velocityY = type[t].velocityY;
        this.height = type[t].height;
        this.width = type[t].width;
        this.health = type[t].health;
        this.score = type[t].score;
        this.bulletType = type[t].bulletType;
        this.fireRate = type[t].fireRate;
        this.sprite = type[t].sprite;
        this.animation = type[t].animation;
        this.audio = type[t].audio;
    }
    public void shoot(){
        if(bulletType >0 && bulletType < 100 && System.currentTimeMillis()-timer > fireRate){
            timer = System.currentTimeMillis();
            double temp;
            if(this.velocityY > 0) {
                temp = height;
            } else{
                temp = -height;
            }
            Bullet bullet = new Bullet(x,y+temp);
            bullet.chooseType(bulletType);
            bullet.audio.play();
            bullet.setX((int)this.x + this.width /2 - bullet.width /2);
            if(bullet.num != 1){
                for(int i = 0; i< bullet.num; i++){
                    Bullet otherBullet = new Bullet(x,y+temp);
                    otherBullet.chooseType(bulletType);
                    otherBullet.setX((int)this.x + this.width /2 - bullet.width /2);
                    otherBullet.setVelocityX(-otherBullet.getVelocityY()+(2*otherBullet.getVelocityY()/(bullet.num-1))*i);
                    Game.enemybullets.add(otherBullet);
                }
            } else{
                Game.enemybullets.add(bullet);
            }
        }
    }
}

class EnemyType {
    public double velocityX, velocityY;
    int height, width, health, score,bulletType,fireRate;
    public Image sprite;
    public Image[] animation;
    public Sound audio;
    public EnemyType(double velocityX, double velocityY, int height, int width, int health, int score, int bulletType, int fireRate, Image sprite, Image[] Animation, Sound audio) {
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.height = height;
        this.width = width;
        this.health = health;
        this.score = score;
        this.bulletType = bulletType;
        this.fireRate = fireRate;
        this.sprite = sprite;
        this.animation = Animation;
        this.audio = audio;
    }
}

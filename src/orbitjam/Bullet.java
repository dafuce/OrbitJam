package orbitjam;

import kuusisto.tinysound.Sound;

import java.awt.*;

public class Bullet extends WindowObject {

    BulletType[] type;

    {
        type = new BulletType[]{
                new BulletType(1,1, 1, 24, 24, 0, -20, SpriteLoader.redlaser1, SoundLoader.laser1),
                new BulletType(1,2, 2, 24, 24, 0, -25, SpriteLoader.bluelaser1, SoundLoader.laser2),
                new BulletType(1,3, 3, 24, 24, 0, -30, SpriteLoader.greenlaser1, SoundLoader.laser3),
                new BulletType(1,10, 10, 75, 75, 0, 1.5, SpriteLoader.bomba, SoundLoader.laser1),
                new BulletType(1,1, 1, 32, 24, 0, -20, SpriteLoader.redlaser2, SoundLoader.laser1),
                new BulletType(1,2, 2, 32, 24, 0, -25, SpriteLoader.bluelaser2, SoundLoader.laser2),
                new BulletType(1,3, 3, 32, 24, 0, -30, SpriteLoader.greenlaser2, SoundLoader.laser3),
                new BulletType(1,12, 12, 90, 90, 0, 1.5, SpriteLoader.bomba, SoundLoader.laser1),
                new BulletType(1,2, 1, 48, 24, 0, -20, SpriteLoader.redlaser3, SoundLoader.laser1),
                new BulletType(1,3, 2, 48, 24, 0, -25, SpriteLoader.bluelaser3, SoundLoader.laser2),
                new BulletType(1,4, 3, 48, 24, 0, -30, SpriteLoader.greenlaser3, SoundLoader.laser3),
                new BulletType(1,15, 15, 100, 100, 0, 1.5, SpriteLoader.bomba, SoundLoader.laser1),
                new BulletType(1,2, 2, 64, 32, 0, -22, SpriteLoader.redlaser4, SoundLoader.laser1),
                new BulletType(1,3, 3, 64, 32, 0, -28, SpriteLoader.bluelaser4, SoundLoader.laser2),
                new BulletType(1,4, 4, 64, 32, 0, -32, SpriteLoader.greenlaser4, SoundLoader.laser3),
                new BulletType(1,20, 15, 100, 100, 0, 1.5, SpriteLoader.bomba, SoundLoader.laser1),
                new BulletType(1,1, 0, 16, 24, 0, 4, SpriteLoader.pinklaser, SoundLoader.laser4),
                new BulletType(1,1, 0, 24, 24, 0, 5, SpriteLoader.pinklaser2, SoundLoader.laser4),
                new BulletType(1,1, 0, 16, 16, (double) Utils.rnd2(8)/2, 4, SpriteLoader.pinkbullet, SoundLoader.pickup3),
                new BulletType(1,1, 0, 24, 20, 0, -4, SpriteLoader.pinklaser2, SoundLoader.laser4),
                new BulletType(3,1, 0, 16, 16, 0, 3, SpriteLoader.pinkbullet, SoundLoader.pickup3),
        };
    }

    public Bullet(double x, double y, Game game) {
        this.game = game;
        this.x = x;
        this.y = y;
    }

    public void chooseType(int t) {
        this.num = type[t].num;
        this.health = type[t].health;
        this.score = type[t].score;
        this.width = type[t].width;
        this.height = type[t].height;
        this.velocityX = type[t].velocityX;
        this.velocityY = type[t].velocityY;
        this.sprite = type[t].sprite;
        this.audio = type[t].audio;
        this.x = x-(double) this.width /2;
    }
}

class BulletType {
    public int num,health,score,width,height;
    public double velocityX,velocityY;
    public Image sprite;
    public Sound audio;
    public BulletType(int num, int health, int score, int width, int height, double velocityX, double velocityY, Image sprite, Sound audio) {
        this.num = num;
        this.health = health;
        this.score = score;
        this.width = width;
        this.height = height;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.sprite = sprite;
        this.audio = audio;
    }
}
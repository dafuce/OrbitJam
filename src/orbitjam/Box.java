package orbitjam;

import kuusisto.tinysound.Sound;

import java.awt.*;

public class Box extends WindowObject {
    int content;
    BoxType[] type;
    String message;
    Color color;

    {
        type = new BoxType[]{

                new BoxType(0, 1, 200, "1 UP", Color.RED, SpriteLoader.redbox, SoundLoader.pickup2),
                new BoxType(1, 20, 400, "+400", Color.BLUE, SpriteLoader.bluebox, SoundLoader.pickup1),
                new BoxType(2, 20, 800, "+800", Color.GREEN, SpriteLoader.greenbox, SoundLoader.pickup1),
                new BoxType(3, 1, 600, "+600", Color.LIGHT_GRAY, SpriteLoader.caixabomba, SoundLoader.pickup1),
                new BoxType(4, 1, 1000, "FIRE-RATE UP", Color.RED, SpriteLoader.powerup1, SoundLoader.powerup2),
                new BoxType(5, 1, 1000, "POWER UP", Color.YELLOW, SpriteLoader.powerup1, SoundLoader.powerup2),
                new BoxType(6, 1, 1000, "SPEED UP", Color.magenta, SpriteLoader.powerup1, SoundLoader.powerup1),
                new BoxType(7, 1, 1000, "SHIELD UPGRADE", Color.CYAN, SpriteLoader.powerup1, SoundLoader.powerup2),
                new BoxType(8, 1, 1000, "MAGNET UPGRADE", Color.GRAY, SpriteLoader.powerup1, SoundLoader.powerup2),
                new BoxType(9, 1, 1000, "AMMO", Color.WHITE, SpriteLoader.powerup1, SoundLoader.powerup1),
        };
    }

    public Box(boolean lowhealth, Game game) {
        this.game = game;
        width = 48;
        height = 48;
        x = Utils.rnd(100, game.getGameWidth() - 100);
        y = Utils.rnd(100, game.getGameHeight() - 100);
        velocityY = (double) Utils.rnd(1, 10) / 10;
        velocityX = (double) Utils.rnd2(10) / 10;
        chooseType(lowhealth);
    }

    public void chooseType(boolean lowhealth) {
        int t;
        int[] bin;
        if (lowhealth) {
            bin = new int[]{0, 0, 0, 0, 0, 1, 1, 2, 2, 3, 3, 4, 5, 6, 7, 8,9};
        } else {
            bin = new int[]{1,1,1,2,2, 2, 3, 4, 5, 6, 7, 8,9};
        }
        t = bin[(int) (Math.random() * bin.length)];
        copyType(t);
    }
    public void copyType(int t){
        this.num = type[t].num;
        this.content = type[t].content;
        this.score = type[t].score;
        this.message = type[t].message;
        this.color = type[t].color;
        this.sprite = type[t].sprite;
        this.audio = type[t].audio;
    }

    public void lootBox(int stage) {
        switch (num) {
            case 0:
                if(Game.player.getHealth() < 3) {
                    Game.player.setHealth(Game.player.getHealth() + 1);
                }
                break;
            case 1:
                Game.player.ammo[1]+= content + (content*stage);
                break;
            case 2:
                Game.player.ammo[2]+= content + (content*stage);
                break;
            case 3:
                Game.player.inventory[0]++;
            case 4:
                if (Game.player.getFireRate() > 100) {
                    Game.player.setFireRate(Game.player.getFireRate() - 25);
                    break;
                }
                copyType(5);
            case 5:
                if (Game.player.getShootPower() < 3) {
                    Game.player.setShootPower(Game.player.getShootPower() + 1);
                    break;
                }
                copyType(6);
            case 6:
                if (Game.player.getVelocity() < 6) {
                    Game.player.setVelocity(Game.player.getVelocity() + 0.5);
                    Game.player.setAcceltime(Game.player.getAcceltime() - 0.03);
                    break;
                }
                copyType(7);
            case 7:
                if (Game.player.shieldCooldown >= 3500) {
                    Game.player.setShieldCooldown(Game.player.getShieldCooldown() - 500);
                    Game.player.setShieldDuration(Game.player.getShieldDuration() + 100);
                    break;
                }
                copyType(8);
            case 8:
                if(Game.player.getMagnetSpeed() <= 2.5){
                    Game.player.setMagnetSpeed(Game.player.getMagnetSpeed()+0.5);
                    break;
                }
                copyType(9);
            case 9:
                int[] temp2 = Game.player.getAmmo();
                temp2[1] += 100 +(50 * stage);
                temp2[2] += 50 + (25 * stage);
                Game.player.setAmmo(temp2);
                break;
        }
        Message lootMessage = new Message(message, x, y, 500, Utils.font2, color);
        Game.messages.add(lootMessage);
        audio.play();
    }
}
class BoxType {
    public int num,content,score;
    String message;
    public Image sprite;
    public Sound audio;
    public Color color;
    public BoxType(int num, int content, int score,String message, Color color, Image sprite, Sound audio) {
        this.num = num;
        this.content = content;
        this.score = score;
        this.message = message;
        this.color = color;
        this.sprite = sprite;
        this.audio = audio;
    }
}

import kuusisto.tinysound.Sound;

import java.awt.*;

public class Box extends WindowObject {
    int content;
    BoxType[] type;
    {
        type = new BoxType[]{
                new BoxType(0,1, 200, SpriteLoader.redbox, SoundLoader.pickup2),
                new BoxType(1,20, 400, SpriteLoader.bluebox, SoundLoader.pickup1),
                new BoxType(2,20, 800, SpriteLoader.greenbox, SoundLoader.pickup1),
                new BoxType(3,1, 600, SpriteLoader.caixabomba, SoundLoader.pickup1),
                new BoxType(4,1, 1000,SpriteLoader.powerup1, SoundLoader.powerup1)
        };
    }
    public Box(boolean lowhealth, int bosskills, Game game){
        width = 36;
        height = 36;
        x = Utils.rnd(100, Game.WINDOW_WIDTH - 100);
        y = Utils.rnd(100, Game.WINDOW_WIDTH - 100);
        velocityY = (double) Utils.rnd(1, 10) / 10;
        velocityX = (double) Utils.rnd2(10) / 10;
        chooseType(lowhealth,bosskills);
    }
    public void chooseType(boolean lowhealth, int bosskills){
        int t;
        int [] bin;
        if(lowhealth){
            bin = new int[] {0,0,0,0,0,1,1,2,2,3,3,4,4};
        }else{
            bin = new int []{1,2,3,4};
        }
        t = bin[(int)(Math.random()* bin.length)];
        this.num = type[t].num;
        this.content = type[t].content + type[t].content*bosskills;
        this.score = type[t].score;
        this.sprite = type[t].sprite;
        this.audio = type[t].audio;
    }

    public void lootBox(int bosskills){

        if (num == 0 && Game.player.getHealth() < 3) {
            Game.player.setHealth(Game.player.getHealth() +1);
            audio.play();
        }
        else if (num!= 4){
            int[] temp = Game.player.getAmmo();
            temp[num] += content;
            Game.player.setAmmo(temp);
            audio.play();
        }
        else {
            int[] bin = {1,1,1,2,2,2,3,3,3, 4};
            int pick = bin[(int) (Math.random() * bin.length)];
            switch (pick) {
                case 1:
                    if(Game.player.getFireRate() > 100){
                        Game.player.setFireRate(Game.player.getFireRate()-50);
                        Message cratebox41 = new Message("FIRE-RATE UP", x, y, 500,Utils.font2, Color.white);
                        Game.messages.add(cratebox41);
                        SoundLoader.powerup2.play();
                        break;
                    }
                case 2:
                    if(Game.player.getVelocity() < 7.5){
                        Game.player.setVelocity(Game.player.getVelocity() + 0.5);
                        Game.player.setAcceltime(Game.player.getAcceltime()-0.03);
                        Message cratebox42 = new Message("SPEED UP", x, y, 500,Utils.font2, Color.white);
                        Game.messages.add(cratebox42);
                        SoundLoader.powerup1.play();
                        break;
                    }
                case 3:
                    if(Game.player.getShootPower() < 3){
                        Game.player.setShootPower(Game.player.getShootPower() + 1);
                        Message cratebox43 = new Message("POWER UP", x, y, 500, Utils.font2, Color.WHITE);
                        Game.messages.add(cratebox43);
                        SoundLoader.powerup2.play();
                        break;
                    }
                case 4:
                    int[] temp2 = Game.player.getAmmo();
                    temp2[1]+=100+50*bosskills;
                    temp2[2]+=50+25*bosskills;
                    Game.player.setAmmo(temp2);
                    Message cratebox44 = new Message("AMMO UP", x, y, 500, Utils.font2, Color.WHITE);
                    Game.messages.add(cratebox44);
                    SoundLoader.powerup1.play();
                    break;
            }
        }
    }
}
class BoxType {
    public int num,content,score;
    public Image sprite;
    public Sound audio;
    public BoxType(int num, int content, int score, Image sprite, Sound audio) {
        this.num = num;
        this.content = content;
        this.score = score;
        this.sprite = sprite;
        this.audio = audio;
    }
}

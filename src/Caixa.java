import kuusisto.tinysound.Sound;

import java.awt.*;

public class Caixa extends ObjFinestra {
    int content;
    TipusCaixa[] tipus;
    {
        tipus = new TipusCaixa[]{
                new TipusCaixa(0,1, 200, SpriteLoader.redbox, SoundLoader.pickup2),
                new TipusCaixa(1,20+20*Joc.bosskills, 400, SpriteLoader.bluebox, SoundLoader.pickup1),
                new TipusCaixa(2,20+20*Joc.bosskills, 800, SpriteLoader.greenbox, SoundLoader.pickup1),
                new TipusCaixa(3,1, 600, SpriteLoader.caixabomba, SoundLoader.pickup1),
                new TipusCaixa(4,1, 1000,SpriteLoader.powerup1, SoundLoader.powerup1)
        };
    }
    public Caixa(boolean lowhealth){
        amplada = 36;
        altura = 36;
        triaTipus(lowhealth);
        setX(Joc.rnd(100, Joc.AMPLADA - 100));
        setY(Joc.rnd(100, Joc.AMPLADA - 100));
        setVy((double) Joc.rnd(1, 10) / 10);
        setVx((double) Joc.rnd2(10) / 10);
    }
    public void triaTipus(boolean lowhealth){
        int t;
        if(lowhealth){
            int [] bin = {0,0,0,0,0,1,1,2,2,3,3,4,4};
            t = bin[(int)(Math.random()* bin.length)];
        }else{
            int [] bin = {1,2,3,4};
            t = bin[(int)(Math.random()* bin.length)];
        }
         // todo : weighted random numbers with
         // todo : weighted random numbers with

        this.num = tipus[t].num;
        this.content = tipus[t].content;
        this.puntuacio = tipus[t].puntuacio;
        this.sprite = tipus[t].sprite;
        this.audio = tipus[t].audio;
    }

    public void recullCaixa(){

        if (num == 0 && Joc.player.getVida() < 3) {
            Joc.player.setVida(Joc.player.getVida() +1);
            audio.play();
        }
        else if (num!= 4){
            int[] temp = Joc.player.getMunicio();
            temp[num] += content;
            Joc.player.setMunicio(temp);
            audio.play();
        }
        else {
            int[] bin = {1,1,1,2,2,2,3,3,3, 4};
            int pick = bin[(int) (Math.random() * bin.length)];
            switch (pick) {
                case 1:
                    if(Joc.player.getFireRate() > 100){
                        Joc.player.setFireRate(Joc.player.getFireRate()-50);
                        Message cratebox41 = new Message("FIRE-RATE UP", x, y, 500);
                        Joc.messages.add(cratebox41);
                        SoundLoader.powerup2.play();
                        break;
                    }
                case 2:
                    if(Joc.player.getVel() < 7.5){
                        Joc.player.setVel(Joc.player.getVel() + 0.5);
                        Joc.player.setAcceltime(Joc.player.getAcceltime()-0.03);
                        Message cratebox42 = new Message("SPEED UP", x, y, 500);
                        Joc.messages.add(cratebox42);
                        SoundLoader.powerup1.play();
                        break;
                    }
                case 3:
                    if(Joc.player.getNivellDispar() < 3){
                        Joc.player.setNivellDispar(Joc.player.getNivellDispar() + 1);
                        Message cratebox43 = new Message("POWER UP", x, y, 500);
                        Joc.messages.add(cratebox43);
                        SoundLoader.powerup2.play();
                        break;
                    }
                case 4:
                    int[] temp2 = Joc.player.getMunicio();
                    temp2[1]+=100+50*Joc.bosskills;
                    temp2[2]+=50+25*Joc.bosskills;
                    Joc.player.setMunicio(temp2);
                    Message cratebox44 = new Message("AMMO UP", x, y, 500);
                    Joc.messages.add(cratebox44);
                    SoundLoader.powerup1.play();
                    break;
            }
        }
    }
    public void pinta(Graphics g){
        if(Joc.mspartida < 5000){
            g.setFont(Joc.font2);
            g.drawString("Loot",(int)this.x, (int)this.y-8);
        }
        super.pinta(g);
    }
}
class TipusCaixa {
    public int num;
    public int content;
    public int   puntuacio;
    public Image sprite;
    public Sound audio;
    public TipusCaixa(int num, int content, int puntuacio, Image sprite, Sound audio) {
        this.num = num;
        this.content = content;
        this.puntuacio = puntuacio;
        this.sprite = sprite;
        this.audio = audio;
    }
}

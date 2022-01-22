import kuusisto.tinysound.Sound;

import java.awt.*;

public class Caixa extends ObjFinestra {
    int content;
    TipusCaixa[] tipus;
    {
        tipus = new TipusCaixa[]{
                new TipusCaixa(0,1, 200, SpriteLoader.redbox, SoundLoader.pickup2),
                new TipusCaixa(1,20, 400, SpriteLoader.bluebox, SoundLoader.pickup1),
                new TipusCaixa(2,20, 800, SpriteLoader.greenbox, SoundLoader.pickup1),
                new TipusCaixa(3,1, 600,SpriteLoader.caixabomba, SoundLoader.pickup1)
        };
    }
    public Caixa(){
        amplada = 24;
        altura = 24;
    }
    public static Caixa apareixcaixa(){
            Caixa caixa = new Caixa();
            caixa.triaTipus();
            caixa.setX(Joc.rnd(100, Joc.AMPLADA-100));
            caixa.setY(Joc.rnd(100, Joc.AMPLADA-100));
            caixa.setVy((double)Joc.rnd(1,10)/ 10);
            caixa.setVx((double)Joc.rnd2(10) / 10);
            return caixa;
    }
    public void triaTipus(){
        int [] bin1 = {0,0,0,0,1,1,2,2,2,3,3,3}; // todo : weighted random numbers with
        int [] bin2 = {1,1,2,2,2,3,3,3}; // todo : weighted random numbers with
        int t = bin1[(int)(Math.random()* bin1.length)];
        this.num = tipus[t].num;
        this.content = tipus[t].content;
        this.puntuacio = tipus[t].puntuacio;
        this.sprite = tipus[t].sprite;
        this.audio = tipus[t].audio;
    }
    public void pinta(Graphics g){
        if(Joc.mspartida < 5000){
            g.setFont(Joc.font2);
            g.drawString("Loot",(int)this.x-3, (int)this.y-8);
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

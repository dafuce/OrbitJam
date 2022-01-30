import kuusisto.tinysound.Sound;

import java.awt.*;

public class Enemic extends ObjFinestra {
    int bullettype;

    TipusEnemic[] tipus;
    {
        tipus = new TipusEnemic[]{
                new TipusEnemic((double)Joc.rnd2(4)/10,Joc.rnd(2,20)/10,60, 75, 20, 1000, 0,0,SpriteLoader.meteoritgran1,null, SoundLoader.explosio2),
                new TipusEnemic((double)Joc.rnd2(4)/10,Joc.rnd(2,10)/5,70, 95, 25, 1250,0,0, SpriteLoader.meteoritgran2,null,SoundLoader.explosio2),
                new TipusEnemic((double)Joc.rnd2(4)/10,Joc.rnd(2,10)/5,60, 70, 20,1000, 0,0,SpriteLoader.meteoritgran3, null,SoundLoader.explosio2),
                new TipusEnemic((double)Joc.rnd2(4)/10,1.5,80, 80, 25, 1250, 0,0, SpriteLoader.meteoritgran4,null,SoundLoader.explosio2),
                new TipusEnemic(0, 1.5, 24,24,1, 100,18,1500, SpriteLoader.enemic1, SpriteLoader.explosionanimation,  SoundLoader.explosio1),
                new TipusEnemic(0, -2.5, 24,24,1, 200,18, 3000,SpriteLoader.enemic2,SpriteLoader.explosionanimation, SoundLoader.explosio4),
                new TipusEnemic(0, 8, 152,24,1, 300,0, 0,SpriteLoader.enemic3,null, SoundLoader.explosio1),
                new TipusEnemic(0, 1, 24,24,2, 500, 16,1500,SpriteLoader.enemic4, SpriteLoader.explosionanimation,SoundLoader.explosio3),
                new TipusEnemic(0, -2, 24,24,2, 500, 19,1000,SpriteLoader.enemic5, SpriteLoader.explosionanimation,SoundLoader.explosio3),
                new TipusEnemic(0, -4, 24,24,2, 300, 0,0,SpriteLoader.enemic6, SpriteLoader.explosionanimation,SoundLoader.explosio1),
                new TipusEnemic(0, 2, 24,24,3, 1000, 20,1000,SpriteLoader.enemic7, SpriteLoader.explosionanimation,SoundLoader.explosio1)
        };
    }
    public Enemic() {
        triaTipus(Joc.bosskills);
        x = Joc.rnd(1, Joc.AMPLADA - amplada);
        if (vy > 0) {
            y = -altura;
        } else {
            y = Joc.ALTURA + altura;
        }
        timer = System.currentTimeMillis();
    }
    public void triaTipus(int bosskills) {
        int[] bin;
        switch (bosskills){
            case 0:
                bin = new int[] {0, 1, 2, 3, 4, 4, 4, 5, 5, 5, 6, 6, 7, 8,8, 9};
                break;
            default:
                bin = new int[]{1, 3, 4, 4, 6, 7, 7, 8, 8, 10};
                break;
        }
        int t = bin[(int) (Math.random() * bin.length)];
        this.vx = tipus[t].vx;
        this.vy = tipus[t].vy;
        this.altura = tipus[t].altura;
        this.amplada = tipus[t].amplada;
        this.vida = tipus[t].vida;
        this.puntuacio = tipus[t].puntuacio;
        this.bullettype = tipus[t].bullettype;
        this.fireRate = tipus[t].fireRate;
        this.sprite = tipus[t].sprite;
        this.animation = tipus[t].animation;
        this.audio = tipus[t].audio;
    }
    public void dispara(){
        if(bullettype>0 && bullettype < 100 && System.currentTimeMillis()-timer > fireRate){
            timer = System.currentTimeMillis();
            double temp;
            if(this.vy > 0) {
                temp = altura;
            } else{
                temp = -altura;
            }
            Bala bala = new Bala(x,y+temp);
            bala.triaTipus(bullettype);
            bala.audio.play();
            bala.setX((int)this.x + this.amplada/2 - bala.amplada/2);
            if(bala.num != 1){
                for(int i = 0; i< bala.num; i++){
                    Bala altrebala = new Bala(x,y+temp);
                    altrebala.triaTipus(bullettype);
                    altrebala.setX((int)this.x + this.amplada/2 - bala.amplada/2);
                    altrebala.setVx(-altrebala.getVy()+(2*altrebala.getVy()/(bala.num-1))*i);
                    Joc.enemybullets.add(altrebala);
                }
            } else{
                Joc.enemybullets.add(bala);
            }
        }
    }
}

class TipusEnemic {
    public double vx,vy;
    public int   altura,amplada,vida,puntuacio;
    int bullettype, fireRate;
    public Image sprite;
    public Image[] animation;
    public Sound audio;
    public TipusEnemic(double vx, double vy, int altura, int amplada, int vida, int puntuacio, int bullettype, int fireRate, Image sprite, Image[] Animation, Sound audio) {
        this.vx = vx;
        this.vy = vy;
        this.altura = altura;
        this.amplada = amplada;
        this.vida = vida;
        this.puntuacio = puntuacio;
        this.bullettype = bullettype;
        this.fireRate = fireRate;
        this.sprite = sprite;
        this.animation = Animation;
        this.audio = audio;
    }
}

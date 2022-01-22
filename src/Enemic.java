import kuusisto.tinysound.Sound;

import java.awt.*;

public class Enemic extends ObjFinestra {
    int bullettype;

    TipusEnemic[] tipus;
    {
        tipus = new TipusEnemic[]{
                new TipusEnemic((double)Joc.rnd2(2)/5,Joc.rnd(2,10)/5,70, 90, 20, 1000, 0,SpriteLoader.meteoritgran1, SoundLoader.explosio2),
                new TipusEnemic((double)Joc.rnd2(2)/5,Joc.rnd(2,10)/5,85, 110, 25, 1250,0, SpriteLoader.meteoritgran2,SoundLoader.explosio2),
                new TipusEnemic((double)Joc.rnd2(2)/5,Joc.rnd(2,10)/5,70, 80, 20,1000, 0,SpriteLoader.meteoritgran3, SoundLoader.explosio2),
                new TipusEnemic((double)Joc.rnd2(2)/2,2,90, 90, 25, 1250, 0, SpriteLoader.meteoritgran4,SoundLoader.explosio2),
                new TipusEnemic(0, 2, 24,24,1, 100,8, SpriteLoader.enemic1,  SoundLoader.explosio1),
                new TipusEnemic(0, -4, 24,24,1, 200,0, SpriteLoader.enemic2, SoundLoader.explosio1),
                new TipusEnemic(0, 10, 152,24,1, 300,0, SpriteLoader.enemic3, SoundLoader.explosio1),
                new TipusEnemic(0, 1, 24,24,1, 500, 7,SpriteLoader.enemic4, SoundLoader.explosio1),
                new TipusEnemic(0, -2, 24,24,1, 200, 0,SpriteLoader.enemic5, SoundLoader.explosio1),
                new TipusEnemic(0, -6, 24,24,1, 300, 0,SpriteLoader.enemic6, SoundLoader.explosio1)
        };
    }
    public Enemic() {
        triaTipus();
        x = Joc.rnd(1, Joc.AMPLADA - amplada);
        if (vy > 0) {
            y = -altura;
        } else {
            y = Joc.ALTURA + altura;
        }
        timer = 0;
    }
    public void triaTipus() {
        int[] bin = {0,1, 2, 3,4,4,4,5,5,6,7,7,8,8,9}; // todo : weighted random numbers with
        int t = bin[(int) (Math.random() * bin.length)];
        this.vx = tipus[t].vx;
        this.vy = tipus[t].vy;
        this.altura = tipus[t].altura;
        this.amplada = tipus[t].amplada;
        this.vida = tipus[t].vida;
        this.puntuacio = tipus[t].puntuacio;
        this.bullettype = tipus[t].bullettype;
        this.sprite = tipus[t].sprite;
        this.audio = tipus[t].audio;
    }
    public void dispara(){
        if(bullettype!=0 && System.currentTimeMillis()-timer > 2000){
            timer = System.currentTimeMillis();
            Bala bala = new Bala(x, y+altura);
            bala.triaTipus(bullettype);
            bala.audio.play();
            Joc.enemybullets.add(bala);
        }
    }
}

class TipusEnemic {
    public double vx,vy;
    public int   altura,amplada,vida,puntuacio;
    int bullettype;
    public Image sprite;
    public Sound audio;
    public TipusEnemic(double vx, double vy, int altura, int amplada, int vida, int puntuacio, int bullettype, Image sprite, Sound audio) {
        this.vx = vx;
        this.vy = vy;
        this.altura = altura;
        this.amplada = amplada;
        this.vida = vida;
        this.puntuacio = puntuacio;
        this.bullettype = bullettype;
        this.sprite = sprite;
        this.audio = audio;
    }
}

import kuusisto.tinysound.Sound;

import java.awt.*;

public class Bala extends ObjFinestra {

    TipusBala[] tipus;

    {
        tipus = new TipusBala[]{
                new TipusBala(1,1, 1, 24, 24, 0, -20, SpriteLoader.redlaser1, SoundLoader.laser1),
                new TipusBala(1,2, 2, 24, 24, 0, -25, SpriteLoader.bluelaser1, SoundLoader.laser2),
                new TipusBala(1,3, 3, 24, 24, 0, -30, SpriteLoader.greenlaser1, SoundLoader.laser3),
                new TipusBala(1,10, 10, 75, 75, 0, 1.5, SpriteLoader.bomba, SoundLoader.laser1),
                new TipusBala(1,1, 1, 32, 24, 0, -20, SpriteLoader.redlaser2, SoundLoader.laser1),
                new TipusBala(1,2, 2, 32, 24, 0, -25, SpriteLoader.bluelaser2, SoundLoader.laser2),
                new TipusBala(1,3, 3, 32, 24, 0, -30, SpriteLoader.greenlaser2, SoundLoader.laser3),
                new TipusBala(1,12, 12, 90, 90, 0, 1.5, SpriteLoader.bomba, SoundLoader.laser1),
                new TipusBala(1,2, 1, 48, 24, 0, -20, SpriteLoader.redlaser3, SoundLoader.laser1),
                new TipusBala(1,3, 2, 48, 24, 0, -25, SpriteLoader.bluelaser3, SoundLoader.laser2),
                new TipusBala(1,4, 3, 48, 24, 0, -30, SpriteLoader.greenlaser3, SoundLoader.laser3),
                new TipusBala(1,15, 15, 100, 100, 0, 1.5, SpriteLoader.bomba, SoundLoader.laser1),
                new TipusBala(1,2, 2, 64, 32, 0, -22, SpriteLoader.redlaser4, SoundLoader.laser1),
                new TipusBala(1,3, 3, 64, 32, 0, -28, SpriteLoader.bluelaser4, SoundLoader.laser2),
                new TipusBala(1,4, 4, 64, 32, 0, -32, SpriteLoader.greenlaser4, SoundLoader.laser3),
                new TipusBala(1,20, 15, 100, 100, 0, 1.5, SpriteLoader.bomba, SoundLoader.laser1),
                new TipusBala(1,1, 0, 16, 24, 0, 4, SpriteLoader.pinklaser, SoundLoader.laser4),
                new TipusBala(1,1, 0, 24, 24, 0, 5, SpriteLoader.pinklaser2, SoundLoader.laser4),
                new TipusBala(1,1, 0, 20, 20, Joc.rnd2(8)/2, 4, SpriteLoader.pinkbullet, SoundLoader.pickup3),
                new TipusBala(1,1, 0, 24, 20, 0, -4, SpriteLoader.pinklaser2, SoundLoader.laser4),
                new TipusBala(3,1, 0, 20, 20, 0, 3, SpriteLoader.pinkbullet, SoundLoader.pickup3),
        };
    }

    public Bala(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void triaTipus(int t) {
        this.num = tipus[t].num;
        this.vida = tipus[t].vida;
        this.puntuacio = tipus[t].puntuacio;
        this.amplada = tipus[t].amplada;
        this.altura = tipus[t].altura;
        this.vx = tipus[t].vx;
        this.vy = tipus[t].vy;
        this.sprite = tipus[t].sprite;
        this.audio = tipus[t].audio;
        this.x = x-(double) this.amplada/2;
    }
}

class TipusBala {
    public int num;
    public int vida;
    public int puntuacio;
    public int amplada;
    public int altura;
    public double vx;
    public double  vy;
    public Image sprite;
    public Sound audio;
    public TipusBala(int num, int vida, int puntuacio, int amplada, int altura, double vx, double vy, Image sprite, Sound audio) {
        this.num = num;
        this.vida = vida;
        this.puntuacio = puntuacio;
        this.amplada = amplada;
        this.altura = altura;
        this.vx = vx;
        this.vy = vy;
        this.sprite = sprite;
        this.audio = audio;
    }
}
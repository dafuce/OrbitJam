import kuusisto.tinysound.Sound;

import java.awt.*;

public class Bala extends ObjFinestra {

    TipusBala[] tipus;
    {
        tipus =  new TipusBala[]{
                new TipusBala(1,1, 24,24,0,-20, SpriteLoader.redlaser1, SoundLoader.laser1),
                new TipusBala(2,2, 24,24,0,-25, SpriteLoader.bluelaser1, SoundLoader.laser2),
                new TipusBala(3,3, 24,24,0,-30, SpriteLoader.greenlaser1, SoundLoader.laser3),
                new TipusBala(10,10, 24,24,0,1.5, SpriteLoader.bomba,SoundLoader.laser1),
                new TipusBala(2,1,32,24, 0,-15, SpriteLoader.redlaser2,SoundLoader.laser1),
                new TipusBala(3, 1,32,24,0,-20, SpriteLoader.greenlaser2,SoundLoader.laser2),
                new TipusBala(4,2,32,24, 0,-25, SpriteLoader.bluelaser2,SoundLoader.laser3),
                new TipusBala(1,0,24,24, 0,7, SpriteLoader.pinklaser,SoundLoader.pickup3),
                new TipusBala(1,0,20,20, Joc.rnd2(5)*2,7, SpriteLoader.yellowbullet,SoundLoader.poum)
        };
    }
    public Bala(double x, double y) {
        this.x = x;
        this.y = y;
    }
    public void triaTipus(int t){
        this.vida = tipus[t].vida;
        this.puntuacio = tipus[t].puntuacio;
        this.amplada = tipus[t].amplada;
        this.altura = tipus[t].altura;
        this.vx = tipus[t].vx;
        this.vy = tipus[t].vy;
        this.sprite = tipus[t].sprite;
        this.audio = tipus[t].audio;

    }

}
class TipusBala {
    public int vida;
    public int puntuacio;
    public int amplada;
    public int altura;
    public double vx;
    public double  vy;
    public Image sprite;
    public Sound audio;
    public TipusBala(int vida, int puntuacio, int amplada, int altura, double vx, double vy, Image sprite, Sound audio) {
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
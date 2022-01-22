import kuusisto.tinysound.Sound;

import java.awt.*;

public class ObjFinestra {
    double x;
    double y;
    int amplada;
    int altura;
    double vx = 0;
    double vy = 0;
    int puntuacio;
    int num;
    int vida;
    long timer;
    boolean dispara;
    Image sprite;
    Sound audio;

    // Mètode per moure i saber si està en pantalla (true) o fora (false)
    boolean moure() {
        x += vx;
        y += vy;
        return x <= Joc.AMPLADA && x >= -amplada && y >= -altura && y <= Joc.ALTURA+altura;
    }

    void pinta(Graphics g) {
        if (Joc.testools) {
            g.setFont(Joc.font2);
            g.drawString("vx:" + (int) vx + ", vy:" + (int) vy, (int) (x - 20), (int) (y - 6));
            g.drawString("x:" + (int) x + ", y:" + (int) y, (int) (x - 20), (int) (y + 38));
            g.drawRect((int) x, (int) y, amplada, altura);
            g.setFont(Joc.font1);
        }
        g.drawImage(sprite, (int) (x - 4), (int) (y - 4), null);
    }

    // Retorna true si aquest objecte xoca amb l'altra
    public boolean xoca(ObjFinestra altre) {
        int distx = this.getX() - altre.getX();
        int disty = this.getY() - altre.getY();

        return (distx < altre.getAmplada()) &&
                (distx > -this.getAmplada()) &&
                (disty < altre.getAltura()) &&
                (disty > -this.getAltura());
    }


    public int getPuntuacio() {
        return puntuacio;
    }


    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getX() {
        return (int) x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return (int) y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getAmplada() {
        return amplada;
    }

    public int getAltura() {
        return altura;
    }

    public void setVx(double vx) {
        this.vx = vx;
    }

    public void setVy(double vy) {
        this.vy = vy;
    }

    public int getVida() {
        return vida;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }
}


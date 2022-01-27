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
    int fireRate;
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
        // Draw Centered Sprite
        g.drawImage(sprite, (int) x + getAmplada()/2 -sprite.getWidth(null)/2, (int) y + getAltura()/2 - sprite.getHeight(null)/2, null);
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

    public void track(double speed, ObjFinestra target){
        // System.out.println("Tracking :"+ target.x+","+target.y+" from : "+this.x+","+this.y);
        double xcomp = target.x - this.x;
        double ycomp = target.y -this.y;
        double dist = Math.sqrt(Math.pow(Math.abs(this.x - target.x),2) + Math.pow(Math.abs(this.y - target.y),2));
        //System.out.println("distance: "+dist);
        this.setVx((xcomp * speed)/dist);
        //System.out.println("horiz speed: "+this.vx);
        this.setVy((ycomp * speed)/dist);
        //System.out.println("vert speed: "+this.vy);
    }

    public boolean takeDamage(ObjFinestra agressor){
        this.vida-= agressor.vida;
        SoundLoader.impact1.play();
        // System.out.println("vida:"+this.vida);
        if(this.vida <=0){
            Joc.kills++;
            Joc.puntuacio2 += this.puntuacio* agressor.puntuacio;
            this.audio.play();
            return true;
        }
        return false;
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

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setAmplada(int amplada) {
        this.amplada = amplada;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    public double getVx() {
        return vx;
    }

    public double getVy() {
        return vy;
    }

    public void setPuntuacio(int puntuacio) {
        this.puntuacio = puntuacio;
    }

    public long getTimer() {
        return timer;
    }

    public void setTimer(long timer) {
        this.timer = timer;
    }

    public int getFireRate() {
        return fireRate;
    }

    public void setFireRate(int fireRate) {
        this.fireRate = fireRate;
    }

    public boolean isDispara() {
        return dispara;
    }

    public void setDispara(boolean dispara) {
        this.dispara = dispara;
    }

    public Image getSprite() {
        return sprite;
    }

    public void setSprite(Image sprite) {
        this.sprite = sprite;
    }

    public Sound getAudio() {
        return audio;
    }

    public void setAudio(Sound audio) {
        this.audio = audio;
    }
}


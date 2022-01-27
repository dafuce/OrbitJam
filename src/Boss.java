import kuusisto.tinysound.Sound;

import java.awt.*;

public class Boss extends ObjFinestra{

    private int totalvida;
    private boolean left;
    private boolean updown;

    Boss(){
        vida = 60+30*Joc.bosskills;
        totalvida = vida;
        amplada = 100;
        altura = 100;
        vy = 0;
        vx = 5;
        x = Joc.AMPLADA / 2 - amplada / 2;
        y = -altura;
        left = true;
        timer = System.currentTimeMillis();
        puntuacio = 20000+Joc.bosskills*20000;
        audio = SoundLoader.explosio3;
    }

    public void mou () {
            if (y < 20 + altura) {
                y += 1;
            } else {
                if (left && x - vx >= 0) {
                    x -= vx;
                }
                else if (!left && x + amplada + vx <= Joc.AMPLADA) {
                    x += vx;
                } else {
                    left = !left;
                    if(vida <= totalvida/2) {
                        updown = true;
                    }
                }
                if (updown) {
                    if (x >= (double)(Joc.AMPLADA-amplada)/2) {
                        y = Joc.ALTURA - (x+amplada) + 20 + altura;
                    } else {
                        y = x + 20 + altura;
                    }
                }
            }
    }
    @Override
    public void pinta(Graphics g){
        if(Joc.testools){
            g.drawRect((int)x,(int)y,amplada,altura);
        }
        g.drawImage(SpriteLoader.boss1,(int) x-4,(int) y-4,null);
        g.setColor(Color.RED);
        g.fillRect(Joc.AMPLADA/3,Joc.ALTURA/10,(int)(vida*((((double)Joc.AMPLADA/3)/totalvida))),19);
        g.setColor(Color.WHITE);
        g.drawRect(Joc.AMPLADA/3,Joc.ALTURA/10,Joc.AMPLADA/3,20);

    }
    public void dispara(){
            if (System.currentTimeMillis() - timer > 1000) {
                timer = System.currentTimeMillis();
                Bala bala1 = new Bala(x , y + altura-20);
                Bala bala2 = new Bala((int) (x + (double)amplada/2 -12), y + altura+20);
                Bala bala3 = new Bala(x + amplada - 24, y + altura-20);
                bala1.triaTipus(16);
                bala2.triaTipus(16);
                bala3.triaTipus(16);
                bala1.audio.play();
                Joc.enemybullets.add(bala1);
                Joc.enemybullets.add(bala2);
                Joc.enemybullets.add(bala3);

                Bala bala4 = new Bala((int) (x + (double)amplada/2 -10), y);
                bala4.triaTipus(18);
                bala4.setVx((Joc.player.getX()-bala4.getX())/100);
                bala4.setVy((Joc.player.getY()-bala4.getY())/100);
                Joc.enemybullets.add(bala4);
            }
    }
}

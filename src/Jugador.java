import java.awt.*;

public class Jugador extends ObjFinestra {
    key RIGHT;
    key LEFT;
    key UP;
    key DOWN;
    double vel;
    double acceltime;
    int[] municio;
    long timer2 = 0;

    int nivellDispar;
    int framecount;

    Jugador() {
        RIGHT = new key();
        LEFT = new key();
        DOWN = new key();
        UP = new key();

        dispara = false;
        nivellDispar = 0;
        amplada = 20;
        altura = 20;
        vida = 3;
        vel = 4;
        acceltime = 0.25;
        sprite = SpriteLoader.player;
        municio = new int[4];
        fireRate = 300;
        framecount = 1;

    }
    int chooseammo() {
        for (int i = municio.length-1; i >= 0; i--) {
            if (this.municio[i] > 0) {
                return i;
            }
        }
        return 0;
    }
    public void takeDamage(){
        if(System.currentTimeMillis() - timer2 > 1000 && !Joc.testools) {
            timer2 = System.currentTimeMillis();
            SoundLoader.damage.play();
            this.vida--;
            if (this.vida <= 0) {
                Joc.estat = Joc.Estat.MORT;
                Joc.addRecord();
                SoundLoader.explosio1.play();
            }
        }
    }
    public void dispara(boolean dispara){
        if(dispara && System.currentTimeMillis() - timer > fireRate){
                timer = System.currentTimeMillis();
                Bala bala = new Bala (x+(double) amplada/2,y);
                int i = chooseammo();
                bala.triaTipus(i+4*nivellDispar); // todo: la munició millora cada boss kill , punts o temps....
                if(bala.vy > 0){
                    bala.y =y+altura;
                } else {
                    bala.y = y-altura;
                }
                municio[i]--;
                bala.audio.play();
                Joc.bales.add(bala);
            }
        }
    boolean moure(){
        double temp1,temp2,temp3,temp4;
            temp1 = UP.pressed ? -(UP.holding(acceltime)) * vel : -UP.drifting(acceltime)*vel;
            temp2 = DOWN.pressed ? DOWN.holding(acceltime) * vel : DOWN.drifting(acceltime)*vel;
            vy = temp1+temp2;
            temp3 = RIGHT.pressed ? RIGHT.holding(acceltime)*vel : RIGHT.drifting(acceltime)*vel;
            temp4 = LEFT.pressed ? -LEFT.holding(acceltime)*vel : -LEFT.drifting(acceltime)*vel;
            vx = temp3+temp4;

        if (x + vx < 8) {
            x = 8;
        }
        else if (x + amplada + vx > Joc.AMPLADA- 8) {
            x = Joc.AMPLADA-amplada-8;
        }
        else if (y + vy < 35) {
            y = 35;
        }
        else if (y + altura + vy > Joc.ALTURA-8) {
            y = Joc.ALTURA-altura-8;
        }
        else {
            super.moure();
        }
        return true;
    }

    public void pinta(Graphics g){
        if(System.currentTimeMillis() - this.timer2 < 1000L){
            if(System.currentTimeMillis() - this.timer2 < 100L *framecount){
                this.sprite = framecount%2 == 1 ? SpriteLoader.player_hurt : SpriteLoader.player;
            }
            else{
                this.framecount++;
            }
        } else{
            framecount = 1;
        }
        g.drawImage(SpriteLoader.moveparticle, (int) this.x+this.amplada/2 -3, (int) this.y +this.altura/2 , null);
        g.drawImage(SpriteLoader.moveparticle, (int) this.x+this.amplada/2 -3 -(int) vx/3  , (int) this.y +this.altura/2 -(int) Math.min(vy/2,0) , null);
        g.drawImage(SpriteLoader.moveparticle, (int) this.x+this.amplada/2 -3 -(int) vx*2/3  , (int) this.y +this.altura/2 -(int) Math.min(vy,0) , null);
        g.drawImage(SpriteLoader.moveparticle, (int) this.x+this.amplada/2 -3 -(int) vx  , (int) this.y +this.altura/2 -(int) Math.min(vy*3/2,0) , null);
        super.pinta(g);
    }
    public double getVel() {
        return vel;
    }

    public void setVel(double vel) {
        this.vel = vel;
    }

    public double getAcceltime() {
        return acceltime;
    }

    public void setAcceltime(double acceltime) {
        this.acceltime = acceltime;
    }

    public int[] getMunicio() {
        return municio;
    }

    public void setMunicio(int[] municio) {
        this.municio = municio;
    }

    public long getTimer2() {
        return timer2;
    }

    public void setTimer2(long timer2) {
        this.timer2 = timer2;
    }

    public int getNivellDispar() {
        return nivellDispar;
    }

    public void setNivellDispar(int nivellDispar) {
        this.nivellDispar = nivellDispar;
    }

}
class key{
    boolean pressed;
    long date;

    key(){
        pressed = false;
        date =0;
    }
    void press(){
        if(!pressed) {
            pressed = true;
            date = System.currentTimeMillis();
        }
    }
    double holding(double seconds){
        double value;
        value = Math.min((double)(System.currentTimeMillis()-date)/(1000*seconds), 1);

        return value;
    }
    double drifting(double seconds){
        double value;
        value = Math.max(1-(double)(System.currentTimeMillis()-date)/(1000*seconds), 0);

        return value;
    }
    void release() {
        if (pressed) {
            pressed = false;
            date = System.currentTimeMillis();
        }
    }
}

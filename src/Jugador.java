

public class Jugador extends ObjFinestra {
    double rvel= 0;
    double lvel= 0;
    double uvel = 0;
    double dvel = 0;
    int[] municio;

    Jugador() {
        dispara = false;
        amplada = 24;
        altura = 24;
        vida = 3;
        sprite = SpriteLoader.player;
        municio = new int[4];

    }
    int chooseammo() {
        for (int i = municio.length-1; i >= 0; i--) {
            if (this.municio[i] > 0) {
                return i;
            }
        }
        return 0;
    }
    public void dispara(boolean dispara){
        if(dispara && System.currentTimeMillis() - timer > 250){
                timer = System.currentTimeMillis();
                Bala bala = new Bala (x,y);
                int i = chooseammo();
                bala.triaTipus(i);
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
        vx = rvel-lvel;
        vy = dvel-uvel;
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

    public double getRvel() {
        return rvel;
    }

    public void setRvel(double rvel) {
        this.rvel = rvel;
    }

    public double getLvel() {
        return lvel;
    }

    public void setLvel(double lvel) {
        this.lvel = lvel;
    }

    public double getUvel() {
        return uvel;
    }

    public void setUvel(double uvel) {
        this.uvel = uvel;
    }

    public double getDvel() {
        return dvel;
    }

    public void setDvel(double dvel) {
        this.dvel = dvel;
    }
}

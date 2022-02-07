import kuusisto.tinysound.Sound;
import java.awt.*;

public class WindowObject {
    double x,y,velocityX,velocityY;
    int width,height,score,num,health,fireRate;
    long timer;
    boolean shooting;
    Image sprite;
    Image[] animation;
    Sound audio;
    Game game;

    // Moves the object and returns true when its visible
    boolean move() {
        x += velocityX;
        y += velocityY;
        return x <= game.getGameWidth() && x >= -width && y >= -height && y <= game.getGameHeight() + height;
    }

    void paint(Graphics g) {
        // Display extra info
        if (Game.testools) {
            g.setFont(Utils.font2);
            g.drawString("vx:" + (int) velocityX + ", vy:" + (int) velocityY, (int) (x - 20), (int) (y - 6));
            g.drawString("x:" + (int) x + ", y:" + (int) y, (int) (x - 20), (int) (y + 38));
            g.drawRect((int) x, (int) y, width, height);
            g.setFont(Utils.font1);
        }
        // Draw Centered Sprite
        g.drawImage(sprite, (int) x + getWidth()/2 -sprite.getWidth(null)/2, (int) y + getHeight()/2 - sprite.getHeight(null)/2, null);
    }

    // Returns true if a this object is colliding with the other
    public boolean collide(WindowObject other) {
        int distx = this.getX() - other.getX();
        int disty = this.getY() - other.getY();

        return (distx < other.getWidth()) &&
                (distx > -this.getWidth()) &&
                (disty < other.getHeight()) &&
                (disty > -this.getHeight());
    }
    // This object moves towards a target, with fixed speed
    public void track(double speed, WindowObject target){
        // System.out.println("Tracking :"+ target.x+","+target.y+" from : "+this.x+","+this.y);
        double xcomp = target.x - this.x;
        double ycomp = target.y -this.y;
        double dist = Math.sqrt(Math.pow(Math.abs(this.x - target.x),2) + Math.pow(Math.abs(this.y - target.y),2));
        //System.out.println("distance: "+dist);
        this.setVelocityX((xcomp * speed)/dist);
        //System.out.println("horiz speed: "+this.vx);
        this.setVelocityY((ycomp * speed)/dist);
        //System.out.println("vert speed: "+this.vy);
    }
    // The object takes damage and handles its death
    public boolean takeDamage(WindowObject agressor){
        this.health -= agressor.health;
        SoundLoader.impact1.play();
        if(this.health <=0){
            this.audio.play();
            Message enemykill = new Message("+"+this.score * agressor.score,this.x,this.y,1000, Utils.font2,Color.WHITE);
            Game.messages.add(enemykill);
            if(this.animation !=null){
                GraphicEvent animation = new GraphicEvent(this.animation, this.x+(double) this.width /2, this.y + (double)this.height /2, 500,1);
                Game.graphicEvents.add(animation);
            }
            return true;
        }
        return false;
    }


    public int getScore() {
        return score;
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

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setVelocityX(double velocityX) {
        this.velocityX = velocityX;
    }

    public void setVelocityY(double velocityY) {
        this.velocityY = velocityY;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public double getVelocityX() {
        return velocityX;
    }

    public double getVelocityY() {
        return velocityY;
    }

    public void setScore(int score) {
        this.score = score;
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

    public boolean isShooting() {
        return shooting;
    }

    public void setShooting(boolean shooting) {
        this.shooting = shooting;
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


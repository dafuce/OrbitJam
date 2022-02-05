import java.awt.*;

public class Message {
    String message;
    double x,y;
    long durationMS,timeStamp;
    boolean visible;
    Font font;
    Color color;

    Message(String message, double x, double y, long durationMS, Font font, Color color){
        this.x = x;
        this.y = y;
        this.message = message;
        this.durationMS = durationMS;
        this.timeStamp = System.currentTimeMillis();
        this.visible = true;
        this.font = font;
        this.color = color;
    }


    void show(Graphics g){
        if(System.currentTimeMillis() - timeStamp < durationMS){
            Utils.drawCenteredString(g,message,x,y, font, color);
        } else{
            visible = false;
        }
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}

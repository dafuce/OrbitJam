import java.awt.*;

public class Message {
    String message;
    double x,y;
    long durationMS,timeStamp;
    boolean visible;

    Message(String message, double x, double y, long durationMS){
        this.x = x;
        this.y = y;
        this.message = message;
        this.durationMS = durationMS;
        this.timeStamp = System.currentTimeMillis();
        this.visible = true;
    }
    void drawCenteredString(Graphics g, String text, double x, double y, Font font) {
        FontMetrics metrics = g.getFontMetrics(font);
        x -= (double) metrics.stringWidth(text) / 2;
        y += (double) metrics.getAscent()/2;
        g.setFont(font);
        g.drawString(text, (int) x,(int) y);
    }
    void show(Graphics g){
        if(System.currentTimeMillis() - timeStamp < durationMS){
            drawCenteredString(g,message,x,y,Joc.font2);
        } else{
            visible = false;
        }
    }
}

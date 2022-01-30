import java.awt.*;

public class GraphicEvent {
    Image[] images;
    double x,y;
    int frecuency;
    long durationMS,timeStamp;
    boolean playing;
    int framecount;

    GraphicEvent(Image[] inputImage, double x, double y, long durationMS, int frecuency){
        this.x = x;
        this.y = y;
        this.images = new Image[inputImage.length];
        for(int i = 0; i< inputImage.length; i++){
            this.images[i] = inputImage[i];
        }
        this.durationMS = durationMS;
        this.timeStamp = System.currentTimeMillis();
        this.frecuency = frecuency;
        this.framecount = 0;
        this.playing = true;
    }
    void play(Graphics g){
        if(System.currentTimeMillis() - timeStamp < durationMS){
            if( System.currentTimeMillis() - timeStamp < (durationMS/((long) frecuency*images.length)) * framecount+1){
                g.drawImage(images[framecount% images.length],(int) x-images[framecount%images.length].getWidth(null)/2,(int) y-images[framecount% images.length].getHeight(null)/2,null);
            }
            else {
                framecount++;
            }
        } else{
            playing = false;
        }
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
}
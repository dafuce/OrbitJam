package orbitjam;

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
        System.arraycopy(inputImage, 0, this.images, 0, inputImage.length);
        this.durationMS = durationMS;
        this.timeStamp = System.currentTimeMillis();
        this.frecuency = frecuency;
        this.framecount = 0;
        this.playing = true;
    }
    void play(Graphics g){
        //While it has to be seen
        if(System.currentTimeMillis() - timeStamp < durationMS){
            // (images.length per frecuency) frames in the Duration time
            if ( System.currentTimeMillis() - timeStamp < (durationMS/((long) frecuency*images.length)) * framecount+1){
                g.drawImage(images[framecount% images.length],(int) x-images[framecount%images.length].getWidth(null)/2,(int) y-images[framecount% images.length].getHeight(null)/2,null);
                // System.out.println("Animation frame # "+framecount+" at "+ (System.currentTimeMillis() - timeStamp));
            }
            else {
                framecount++;
                g.drawImage(images[framecount% images.length],(int) x-images[framecount%images.length].getWidth(null)/2,(int) y-images[framecount% images.length].getHeight(null)/2,null);
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

    public boolean isPlaying() {
        return playing;
    }

    public void setPlaying(boolean playing) {
        this.playing = playing;
    }
}
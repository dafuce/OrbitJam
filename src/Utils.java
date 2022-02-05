import java.awt.*;
import java.util.Random;

public class Utils {
    public static Font font1,font2,font3;

    static {
        font1 = new Font("Arial", Font.BOLD, 20);
        font2 = new Font("Arial", Font.PLAIN, 15);
        font3 = new Font("Arial", Font.BOLD, 15);
    }
        public static int rnd(int min, int max) {
            Random r = new Random();
            if (min > 0) {
                return min + Math.abs(r.nextInt() % ((max - min) + 1));
            } else {
                min = -min;
                max = -max;
                return -(min + Math.abs(r.nextInt() % ((max - min) + 1)));
            }
        }

        public static int rnd2(int max) {
            Random r = new Random();
            return r.nextInt(max) * (r.nextBoolean() ? -1 : 1);
        }
    public static void drawCenteredString(Graphics g, String text, double x, double y, Font font, Color color) {
        FontMetrics metrics = g.getFontMetrics(font);
        x -= (double) metrics.stringWidth(text) / 2;
        y += (double) metrics.getAscent() / 2;
        g.setFont(font);
        g.setColor(color);
        g.drawString(text, (int) x, (int) y);
    }
}

import java.io.*;

import kuusisto.tinysound.TinySound;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.*;
import java.util.List;

public class Joc extends JFrame {

    List<Caixa> caixes = Collections.synchronizedList(new ArrayList<>());
    public static List<Bala> bales = Collections.synchronizedList(new ArrayList<>());
    public static List<Bala> enemybullets = Collections.synchronizedList(new ArrayList<>());
    List<Enemic> enemics = Collections.synchronizedList(new ArrayList<>());
    Jugador player;
    public static long mspartida = 0;
    long temp0 = 0;
    int dificultat = 0;
    int puntuacio1 = 0, puntuacio2 = 0;
    int highscore;
    public static boolean testools;
    boolean disparant;
    long hit = 0L;
    Image fons;
    public static Graphics g = SpriteLoader.fons.getGraphics();
    public static int ALTURA = 800;
    public static int AMPLADA = 800;

    public static Font font2 = new Font("Arial", Font.PLAIN, 15);
    public static Font font1 = new Font("Arial", Font.BOLD, 20);

    private enum ESTAT {
        MENU,
        CREDITS,
        JOC,
        PAUSA,
        MORT
    }

    public ESTAT Estat = ESTAT.MENU;

    static Random r = new Random();

    public static int rnd(int min, int max) {
        if (min > 0) {
            return min + Math.abs(r.nextInt() % ((max - min) + 1));
        } else {
            min = -min;
            max = -max;
            return -(min + Math.abs(r.nextInt() % ((max - min) + 1)));
        }
    }

    public static int rnd2(int max) {
        return r.nextInt(max) * (r.nextBoolean() ? -1 : 1);
    }

    public static void main(String[] args) throws Exception {
        TinySound.init();
        new Joc();
    }

    Joc() throws Exception {
        fons = ImageIO.read(new File("sprites/background.jpg"));
        setTitle("Orbit Jam");
        setIconImage(SpriteLoader.player);
        setSize(ALTURA, AMPLADA);
        setVisible(true);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        add(panel);
        panel.setFocusable(true);
        panel.setBackground(Color.black);
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent ek) {
                switch (ek.getKeyCode()) {
                    case KeyEvent.VK_ESCAPE -> {
                        SoundLoader.back.play();
                        switch (Estat) {
                            case JOC -> Estat = ESTAT.PAUSA;
                            case CREDITS -> Estat = ESTAT.MENU;
                            default -> {
                                TinySound.shutdown();
                                System.exit(0);
                            }
                        }
                    }
                    case KeyEvent.VK_ENTER -> {
                        if (Estat == ESTAT.PAUSA) {
                            Estat = ESTAT.JOC;
                            temp0 = System.currentTimeMillis() - mspartida;
                            SoundLoader.toggle.play();
                        } else {
                            inicialitzacio();
                            Estat = ESTAT.JOC;
                            temp0 = System.currentTimeMillis();
                            SoundLoader.confirm.play();
                        }
                    }
                    case KeyEvent.VK_UP -> player.uvel = 8;
                    case KeyEvent.VK_DOWN -> player.dvel = 8;
                    case KeyEvent.VK_LEFT -> player.lvel = 8;
                    case KeyEvent.VK_RIGHT -> player.rvel = 8;
                    case KeyEvent.VK_CONTROL -> testools = !testools;
                    case KeyEvent.VK_P -> TinySound.setGlobalVolume(TinySound.getGlobalVolume() + 0.1);
                    case KeyEvent.VK_O -> TinySound.setGlobalVolume(TinySound.getGlobalVolume() - 0.1);
                    case KeyEvent.VK_SPACE -> {
                        if (Estat == ESTAT.JOC) {
                            // System.out.println("DISPARANT TRUE");
                            disparant = true;
                        }
                    }
                }
            }

            public void keyReleased(KeyEvent d) {
                switch (d.getKeyCode()) {
                    case KeyEvent.VK_UP -> player.uvel = 0;
                    case KeyEvent.VK_DOWN -> player.dvel = 0;
                    case KeyEvent.VK_LEFT -> player.lvel = 0;
                    case KeyEvent.VK_RIGHT -> player.rvel = 0;
                    case KeyEvent.VK_SPACE -> disparant = false;
                }
            }
        });
        run();
    }

    void tick() {
        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    void run() {
        inicialitzacio();
        repaint(g);
        while (true) {
            if (Estat == ESTAT.MENU || Estat == ESTAT.CREDITS) {
                SoundLoader.musicamenu.play(true);
            } else {
                SoundLoader.musicamenu.pause();
                if (Estat == ESTAT.JOC) {
                    moviments();
                    xocs();
                    mspartida = System.currentTimeMillis() - temp0;
                    puntuacio1 = (int) (mspartida / 1000) * 10;
                    // accelera();
                }
                SoundLoader.musicajoc.play(true);
            }
            repaint(g);
            tick();
        }
    }

    void inicialitzacio() {
        dificultat = 0;
        puntuacio1 = 0;
        puntuacio2 = 0;
        bales.clear();
        enemybullets.clear();
        caixes.clear();
        enemics.clear();
        player = new Jugador();
        player.setX((AMPLADA / 2) - (player.getAmplada() / 2));
        player.setY(ALTURA - (ALTURA / 3));
        try{
            Scanner scanner = new Scanner(new File("records.txt"));
            while(scanner.hasNextInt()) {
                highscore= scanner.nextInt();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void moviments() {
        player.dispara(disparant);
        player.moure();
        for (Enemic enemic : enemics){
            enemic.dispara();
        }
        // Aparicions
        if (rnd(1, 200) == 200) {
            caixes.add(Caixa.apareixcaixa());
        }
        if (rnd(1, 40) == 40) {
            enemics.add(new Enemic());
        }
        // Moure i Remove els objectes d'ArrayList que ja no són en pantalla
        for (int i = caixes.size() - 1; i >= 0; i--) {
            if (!caixes.get(i).moure()) {
                caixes.remove(i);
            }
        }
        for (int i = enemics.size() - 1; i >= 0; i--) {
            if (!enemics.get(i).moure()) {
                enemics.remove(i);
            }
        }
        for (int i = bales.size() - 1; i >= 0; i--) {
            if (!bales.get(i).moure()) {
                bales.remove(i);
            }
        }
        for (int i = enemybullets.size() - 1; i >= 0; i--) {
            if (!enemybullets.get(i).moure()) {
                enemybullets.remove(i);
            }
        }
    }

    void xocs() {
        for (int i = caixes.size()-1;i>=0; i--) {
            if (player.xoca(caixes.get(i))) {
                caixes.get(i).audio.play();
                if (caixes.get(i).num == 0 && player.vida < 3) {
                    player.vida += 1;
                } else {
                    player.municio[caixes.get(i).num] += caixes.get(i).content;
                }
                puntuacio2 += caixes.get(i).puntuacio;
                caixes.remove(i);
            }
        }
        for (int i = bales.size() - 1; i >= 0; i--) {
            for (int j = enemics.size() - 1; j >= 0; j--) {
                if (bales.get(i).xoca(enemics.get(j))) {
                    enemics.get(j).setVida(enemics.get(j).getVida() - bales.get(i).vida);
                    SoundLoader.impact1.play();
                    int temp = bales.get(i).getPuntuacio();
                    bales.remove(i);
                    // enemics.get(j).sprite todo hit detection is visible
                    if (enemics.get(j).vida <= 0) {
                        puntuacio2 += enemics.get(j).puntuacio * temp;
                        enemics.get(j).audio.play();
                        enemics.remove(j);
                    }
                    break;
                }
            }
        }
        for (int i = enemybullets.size() - 1; i >= 0; i--) {
            if(enemybullets.get(i).xoca(player)){
                player.vida--;
                SoundLoader.damage.play();
                enemybullets.remove(i);
                if (player.vida <= 0) {
                    Estat = ESTAT.MORT;
                    mort();
                    SoundLoader.explosio1.play();
                    break;
                }
            }
        }
        for (Enemic enemic : enemics) {
            if (player.xoca(enemic) && System.currentTimeMillis() - hit > 500) {
                hit = System.currentTimeMillis();
                SoundLoader.damage.play();
                player.vida--;
                if (player.vida <= 0) {
                    Estat = ESTAT.MORT;
                    mort();
                    SoundLoader.explosio1.play();
                    break;
                }
            }
        }
    }

    void accelera() {
        /*
        if (puntuacio2 + puntuacio1 > 5000 * dificultat && dificultat < 4) {
            for (Enemic enemic : enemics) {
                enemic.setAccel(enemic.getAccel()+1);
            }
            dificultat++;
        }
        */
    }

    void mort() {
        if (highscore < puntuacio1 + puntuacio2) {
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter("records.txt"));
                writer.write(String.valueOf(puntuacio1 + puntuacio2));
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void update(Graphics g) {
        paint(g);
    }

    public void paint(Graphics g) {
        g.drawImage(SpriteLoader.fons, 0, 0, null);
    }

    //todo
    /*
    public void pintamissatge(double temps){
        ExecutorService
    }
     */
    public void printFons(Graphics g){
        g.drawImage(fons, 0, 0, null);
        g.drawImage(SpriteLoader.estrellespetites, 0, -1600 + (int) (mspartida / 50) % 1600, null);
        g.drawImage(SpriteLoader.estrellespetites, 0, (int) (mspartida / 50) % 1600, null);
        g.drawImage(SpriteLoader.estrellesmitges, 0, -1600 + (int) (mspartida / 30) % 1600, null);
        g.drawImage(SpriteLoader.estrellesmitges, 0, (int) (mspartida / 30) % 1600, null);
    }
    public void printHUD(Graphics g){
        g.setFont(font1);
        g.setColor(Color.white);
        g.drawString("Score " + (puntuacio1 + puntuacio2), 30, 60);
        g.drawString("Health " + player.vida, 30, 90);
        g.drawString("Highscore " + highscore, 30, 120);
    }
    public void repaint(Graphics g) {
        printFons(g);
        if (Estat == ESTAT.MENU) {
            g.drawImage(SpriteLoader.menu, 0, 0, null);
        } else if (Estat == ESTAT.CREDITS) {
            g.drawString("Credits", (AMPLADA / 2) - 100, ALTURA / 2);
        } else {
            player.pinta(g);
            for (Caixa caixa : caixes) {
                caixa.pinta(g);
            }
            for (Bala bala : bales) {
                bala.pinta(g);
            }
            for (Bala bala : enemybullets) {
                bala.pinta(g);
            }
            for (Enemic enemic : enemics) {
                enemic.pinta(g);
            }
            printHUD(g);
            g.setFont(font1);
            if (mspartida <= 2000) {
                g.drawString("\u2190 \u2192 \u2191  \u2193", (int) (player.x - 25), (int) (player.y - 40)); // f.getAMPLADA()/2-60 , f.getALTURA()-300)
                g.setFont(font2);
                g.drawString("[SPACE]", (int) (player.x - 15), (int) (player.y + 50));
            }
            int i = player.chooseammo();
                switch (i) {
                    case 1 -> g.setColor(Color.blue);
                    case 2 -> g.setColor(Color.green);
                    case 3 -> g.setColor(Color.gray);
                }
            if(i>0){
                g.drawString("" + player.municio[i], (int) (player.x + 5), (int) (player.y + 50));
            }
            g.setColor(Color.white);
            g.setFont(font1);

            if (Estat == ESTAT.PAUSA) {
                g.drawString("Pause", (AMPLADA / 2) - 100, ALTURA / 2 -50);
                g.drawString("Escape to exit", (AMPLADA / 2) - 100, ALTURA / 2  );
                g.drawString("Enter to continue", (AMPLADA / 2) - 100, ALTURA / 2 + 50);
            }
            if (Estat == ESTAT.MORT) {
                g.drawString("Game Over", (AMPLADA / 2) - 100, ALTURA / 2 -50);
                g.drawString("Your score  " + (puntuacio1 + puntuacio2), (AMPLADA / 2) - 100, ALTURA / 2 );
                if(puntuacio1+puntuacio2 > highscore){
                    g.drawString("New Highscore !", (AMPLADA / 2) - 100, ALTURA / 2 +50);
                }
                g.drawString("Escape to exit", (AMPLADA / 2) - 100, ALTURA / 2 + 100);
                g.drawString("Enter play again", (AMPLADA / 2) - 100, ALTURA / 2 + 150);
            }
            if (testools) {
                g.drawString("Game time: " + (double) mspartida / 1000, 30, 150);
                // g.drawString("dificultat: " + dificultat, 30, 130);
            }
        }
        super.repaint();
    }
}
/*
class Highscore{
    int score;
    String name;
    String date;


}
*/

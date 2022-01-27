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
    public static List<Message> messages = Collections.synchronizedList(new ArrayList<>());
    Boss boss;
    public static ArrayList<Record> records = new ArrayList<Record>();

    public static Jugador player;

    public static long mspartida,temp0,renderTime = 0;
    static int kills;
    static int puntuacio1;
    static int puntuacio2;
    public static int bosskills = 0;
    public static boolean testools;
    boolean disparant;
    Image fons;
    public static Graphics g = SpriteLoader.fons.getGraphics();

    public static int ALTURA = 800;
    public static int AMPLADA = 800;
    public static int FPS = 60;

    public static Font font1 = new Font("Arial", Font.BOLD, 20);
    public static Font font2 = new Font("Arial", Font.PLAIN, 15);
    public static Font font3 = new Font("Arial", Font.BOLD, 15);


    public enum Estat {
        MENU,
        JOC,
        BOSS,
        PAUSA,
        MORT
    }

    public static Estat estat = Estat.MENU;

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
        fons = ImageIO.read(SpriteLoader.class.getResource("/sprites/background.jpg"));
        setTitle("Orbit Jam");
        setIconImage(SpriteLoader.player);
        setSize(ALTURA, AMPLADA);
        setVisible(true);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        add(panel);
        //panel.setFocusable(true);
        panel.setBackground(Color.black);
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent ek) {
                switch (ek.getKeyCode()) {
                    case KeyEvent.VK_ESCAPE:
                        if (estat == Estat.JOC ) {
                            estat = Estat.PAUSA;
                            SoundLoader.back.play();
                        } else if(estat != Estat.BOSS){
                            TinySound.shutdown();
                            System.exit(0);
                        }
                        break;
                    case KeyEvent.VK_ENTER :
                            if (estat == Estat.PAUSA) {
                                estat = Estat.JOC;
                                temp0 = System.currentTimeMillis() - mspartida;
                                SoundLoader.toggle.play();
                            } else if(estat == Estat.MENU ||estat == Estat.MORT){
                                SoundLoader.musicamenu.pause();
                                SoundLoader.musicajoc.play(true);
                                reset();
                                estat = Estat.JOC;
                                temp0 = System.currentTimeMillis();
                                SoundLoader.confirm.play();
                            }
                        break;
                    case KeyEvent.VK_UP :
                        player.UP.press();
                        break;
                    case KeyEvent.VK_DOWN :
                        player.DOWN.press();
                        break;
                    case KeyEvent.VK_LEFT :
                        player.LEFT.press();
                        break;
                    case KeyEvent.VK_RIGHT :
                        player.RIGHT.press();
                        break;
                    case KeyEvent.VK_CONTROL :
                        testools = !testools;
                        break;
                    case KeyEvent.VK_P :
                        TinySound.setGlobalVolume(TinySound.getGlobalVolume() + 0.1);
                    break;
                    case KeyEvent.VK_O :
                        TinySound.setGlobalVolume(TinySound.getGlobalVolume() - 0.1);
                    break;
                    case KeyEvent.VK_SPACE :
                        if (estat == Estat.JOC || estat == Estat.BOSS) {
                            disparant = true;
                        }
                        break;
                }
            }
            public void keyReleased(KeyEvent d) {

                switch (d.getKeyCode()) {
                    case KeyEvent.VK_UP :
                        player.UP.release();
                        break;
                    case KeyEvent.VK_DOWN :
                        player.DOWN.release();
                        break;
                    case KeyEvent.VK_LEFT :
                        player.LEFT.release();
                        break;
                    case KeyEvent.VK_RIGHT :
                        player.RIGHT.release();
                        break;
                    case KeyEvent.VK_SPACE :
                        disparant = false;
                        break;
                }
            }
        });
        SoundLoader.musicamenu.play(true);
        readRecord();
        run();
    }
    void readRecord() {


        records.clear();
        String inputValue;
        try{
            Scanner scanner = new Scanner(new File("records.txt"));
            while(scanner.hasNextLine()) {
                Record record = new Record();
                inputValue = scanner.nextLine();
                String[] value = inputValue.split(", ");
                record.score = Integer.parseInt(value[0]);
                record.kills = Integer.parseInt(value[1]);
                record.playername = value[2];
                record.date = value[3];
                records.add(record);
                //System.out.println("Llegit");
            }
        } catch (IOException ex) {
            try {
                addRecord();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Collections.sort(records);
    }

    void reset(){
        kills = 0;
        puntuacio1 = 0;
        puntuacio2 = 0;
        // This is not always thread-safe as KeyListener may call it when its doing enemic.pinta(g)
        bales.clear();
        enemybullets.clear();
        caixes.clear();
        enemics.clear();
        boss = null;
        player = new Jugador();
        player.setX((AMPLADA / 2) - (player.getAmplada() / 2));
        player.setY(ALTURA - (ALTURA / 3));
    }
    void run() {
        reset();
        while(true){
            renderTime = System.currentTimeMillis();
            if(estat == Estat.JOC || estat == Estat.BOSS){
                if(estat == Estat.JOC){
                    spawns();
                    if(kills%50 == 49){
                        estat = Estat.BOSS;
                        boss = new Boss();
                    }
                }
                moviments();
                xocs();
                mspartida = System.currentTimeMillis() - temp0;
                puntuacio1 = (int) (mspartida / 1000) * 10;
            }
            repaint(g);
            renderTime = System.currentTimeMillis() - renderTime;
            try {
                if((long) (1000/FPS)-renderTime > 0){
                    Thread.sleep(((long) 1000 / FPS) - renderTime);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }



    // Aparicions
    void spawns(){
        if (rnd(1, 200) == 200) {
            if(player.getVida() < 3){
                caixes.add(new Caixa(true));
            } else{
                caixes.add(new Caixa(false));
            }
        }
        int d = 50-kills/10 > 10 ? 50-kills/10 : 10;
        // System.out.println("d:"+d);
        if (rnd(1, d) == d) {
            enemics.add(new Enemic());
        }
    }
    void moviments() {
        player.dispara(disparant);
        player.moure();
        if(estat == Estat.BOSS){
            boss.mou();
            boss.dispara();
        }
        for (Enemic enemic : enemics){
            enemic.dispara();
            if(enemic.bullettype == 100){
                enemic.track(2,player);
            }
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
                caixes.get(i).recullCaixa();
                puntuacio2 += caixes.get(i).puntuacio;
                caixes.remove(i);
            }
        }
        for (int i = bales.size() - 1; i >= 0; i--) {
            for (int j = enemics.size() - 1; j >= 0; j--) {
                if (bales.get(i).xoca(enemics.get(j))) {
                    if(enemics.get(j).takeDamage(bales.get(i))){
                        Message enemykill = new Message("+"+enemics.get(j).puntuacio*bales.get(i).puntuacio,enemics.get(j).x,enemics.get(j).y,500);
                        messages.add(enemykill);
                        enemics.remove(j);
                    };
                    bales.remove(i);
                    break;
                }
            }
        }
        if(estat == Estat.BOSS) {
            for (int i = bales.size() - 1; i >= 0; i--) {
                if (bales.get(i).xoca(boss)) {
                    if (boss.takeDamage(bales.get(i))) {
                        Message bosskill = new Message("+"+boss.puntuacio,boss.x,boss.y,1000);
                        messages.add(bosskill);
                        bosskills++;
                        boss = null;
                        estat = Estat.JOC;
                    }
                    bales.remove(i);
                    break;
                }
            }
        }
        for (int i = enemybullets.size() - 1; i >= 0; i--) {
            if(enemybullets.get(i).xoca(player)){
                player.takeDamage();
            }
        }
        for (Enemic enemic : enemics) {
            if (player.xoca(enemic) ) {
                player.takeDamage();
            }
        }
        if(boss !=null && boss.xoca(player)){
            player.takeDamage();
        }
    }

    public static void addRecord() {
        Record actual = new Record();
        actual.score = puntuacio1+puntuacio2;
        actual.kills = kills;
        actual.playername = "Daniel";
        records.add(actual);
        Collections.sort(records);
        //System.out.println("Escrivint");
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter("records.txt"));
                for(int i = 0; i < records.size();i++){
                    writer.write(records.get(i).score + ", "+records.get(i).kills+", "+records.get(i).playername+", "+records.get(i).date+ "\n");
                }
                writer.close();
            } catch (IOException ex) {
                try {
                    new File("records.txt").createNewFile();
                    addRecord();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
    }
    // Renderer Class ?
    void drawCenteredString(Graphics g, String text, double x, double y, Font font) {
        FontMetrics metrics = g.getFontMetrics(font);
        x -= (double) metrics.stringWidth(text) / 2;
        y += (double) metrics.getAscent()/2;
        g.setFont(font);
        g.drawString(text, (int) x,(int) y);
    }

    public void update(Graphics g) {
        paint(g);
    }

    public void paint(Graphics g) {
        g.drawImage(SpriteLoader.fons, 0, 0, null);
    }

    public void printFons(Graphics g){
        g.drawImage(fons, 0, 0, null);
        g.drawImage(SpriteLoader.estrellespetites, 0, -800 + (int) (mspartida / 50) % 800, null);
        g.drawImage(SpriteLoader.estrellespetites, 0, (int) (mspartida / 50) % 800, null);
        g.drawImage(SpriteLoader.estrellesmitges, 0, -800 + (int) (mspartida / 30) % 800, null);
        g.drawImage(SpriteLoader.estrellesmitges, 0, (int) (mspartida / 30) % 800, null);
    }
    public void printHUD(Graphics g){
        g.setFont(font1);
        g.setColor(Color.white);
        g.drawString("Score " + (puntuacio1 + puntuacio2), 30, 60);

        int count = 0;
        if(player.vida > 0){
            while(count < player.vida) {
                g.drawImage(SpriteLoader.heart,24+count*32,67,null);
                count++;
            }
        }
        g.drawString("Highscore " + records.get(0).score, 30, 120);
    }
    public void repaint(Graphics g) {
        printFons(g);
        if (estat == Estat.MENU) {
            g.drawImage(SpriteLoader.menu, 0, 0, null);
        } else {
            player.pinta(g);
            for (Caixa caixa : caixes) {
                caixa.pinta(g);
            }
            for (Bala bala : bales) {
                bala.pinta(g);
            }
            for (Enemic enemic : enemics) {
                enemic.pinta(g);
            }
            if(boss != null &&( estat == Estat.BOSS || estat == Estat.PAUSA || estat == Estat.MORT)){
                boss.pinta(g);
            }
            for (Bala bala : enemybullets) {
                bala.pinta(g);
            }
            for(Message message :messages){
                message.show(g);
            }
            printHUD(g);
            g.setFont(font1);
            if (mspartida <= 2000) {
                drawCenteredString(g,"[SPACE]", player.x+(player.amplada/2), player.y+35,font2);
                drawCenteredString(g,"\u2190 \u2192 \u2191  \u2193", player.x+(player.amplada/2), player.y-30,font1);
            }
            int i = player.chooseammo();
                switch (i) {
                    case 1 : g.setColor(Color.blue);
                    break;
                    case 2 : g.setColor(Color.green);
                    break;
                    case 3 : g.setColor(Color.gray);
                    break;
                }
            if(i>0){
                drawCenteredString(g,String.valueOf(player.municio[i]), player.x+ (double)(player.amplada/2), player.y+35, font3);
            }
            g.setColor(Color.white);
            if (estat == Estat.PAUSA) {
                drawCenteredString(g,"Pause", (double)AMPLADA/2,(double) ALTURA/2-40,font1);
                drawCenteredString(g,"Enter to continue", (double)AMPLADA/2,(double) ALTURA/2,font1);
                drawCenteredString(g,"Escape to exit", (double)AMPLADA/2,(double) ALTURA/2+40,font1);
            }
            if (estat == Estat.MORT) {
                drawCenteredString(g,"Game Over",(double) AMPLADA/2,(double)ALTURA/2-80,font1);
                drawCenteredString(g,"You got "+(puntuacio1 + puntuacio2)+" points from "+kills+" kills",(double)AMPLADA/2,(double)ALTURA/2-40,font1);
                if(puntuacio1+puntuacio2 > records.get(0).score){
                    drawCenteredString(g,"New Highscore!",(double)AMPLADA/2,(double)ALTURA/2,font1);
                }
                drawCenteredString(g,"Escape to exit",(double)AMPLADA/2,(double)ALTURA/2+40,font1);
                drawCenteredString(g,"Enter play again",(double)AMPLADA/2,(double)ALTURA/2+80,font1);
            }
            if (testools) {
                g.drawString("Game time: " + (double) mspartida / 1000, 30, 150);
                g.drawString("Kills: " + kills, 30, 180);
            }
        }
        super.repaint();
    }

    public static int getPuntuacio1() {
        return puntuacio1;
    }

    public static void setPuntuacio1(int puntuacio1) {
        Joc.puntuacio1 = puntuacio1;
    }

    public static int getPuntuacio2() {
        return puntuacio2;
    }

    public static void setPuntuacio2(int puntuacio2) {
        Joc.puntuacio2 = puntuacio2;
    }
}

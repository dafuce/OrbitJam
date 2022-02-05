import java.io.*;

import kuusisto.tinysound.TinySound;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.*;
import java.util.List;

/*

 Original code by Daniel Fuertes (dafuce) February 2022, Barcelona
 Translated and commented by me

 */
public class Game extends JFrame {

    // Access modifiers aren't nice nor curated atm :/

    List<Box> boxes = Collections.synchronizedList(new ArrayList<>());
    List<Enemy> enemies = Collections.synchronizedList(new ArrayList<>());
    public static List<Bullet> bullets = Collections.synchronizedList(new ArrayList<>());
    public static List<Bullet> enemybullets = Collections.synchronizedList(new ArrayList<>());
    public static List<Message> messages = Collections.synchronizedList(new ArrayList<>());
    public static List<GraphicEvent> graphicEvents = Collections.synchronizedList(new ArrayList<>());
    public static ArrayList<Record> records = new ArrayList<>();

    private Boss boss;
    public static Player player;

    public long gameMS,temp0,renderTime = 0;
    public int kills, score,bosskills;
    public static boolean testools;
    boolean shooting;
    Image fons;
    public static Graphics g = SpriteLoader.fons.getGraphics();

    public static final int WINDOW_HEIGHT = 800;
    public static final int WINDOW_WIDTH = 800;
    public static final int FPS = 60;

    public static Font font1 = new Font("Arial", Font.BOLD, 20);
    public static Font font2 = new Font("Arial", Font.PLAIN, 15);
    public static Font font3 = new Font("Arial", Font.BOLD, 15);

    // State Machine for the Game

    public enum State {
        MENU,
        GAME,
        BOSS,
        PAUSE,
        GAMEOVER
    }
    public static State state = State.MENU;

    // Utils

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

    void drawCenteredString(Graphics g, String text, double x, double y, Font font) {
        FontMetrics metrics = g.getFontMetrics(font);
        x -= (double) metrics.stringWidth(text) / 2;
        y += (double) metrics.getAscent()/2;
        g.setFont(font);
        g.drawString(text, (int) x,(int) y);
    }
    // Main method

    public static void main(String[] args) throws Exception {
        TinySound.init();
        new Game();
    }

    // "Main" Game class constructor
    Game() throws Exception {
        fons = ImageIO.read(Objects.requireNonNull(SpriteLoader.class.getResource("/sprites/background.jpg")));
        setTitle("Orbit Jam");
        setIconImage(SpriteLoader.player);
        setSize(WINDOW_HEIGHT, WINDOW_WIDTH);
        setVisible(true);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        add(panel);
        panel.setBackground(Color.black);

        // Everything Keyboard-Related
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent ek) {
                switch (ek.getKeyCode()) {
                    case KeyEvent.VK_ESCAPE:
                        if (state == State.GAME) {
                            state = State.PAUSE;
                            SoundLoader.back.play();
                        } else if(state != State.BOSS){
                            TinySound.shutdown();
                            System.exit(0);
                        }
                        break;
                    case KeyEvent.VK_ENTER :
                        if (state == State.PAUSE) {
                            state = State.GAME;
                            temp0 = System.currentTimeMillis() - gameMS;
                            SoundLoader.toggle.play();
                        } else if(state == State.MENU || state == State.GAMEOVER){
                            SoundLoader.musicamenu.pause();
                            SoundLoader.musicajoc.play(true);
                            reset();
                            state = State.GAME;
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
                        if (state == State.GAME || state == State.BOSS) {
                            shooting = true;
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
                        shooting = false;
                        break;
                }
            }
        });

        // Start Game

        SoundLoader.musicamenu.play(true);
        readRecord();
        run();

    }
    // Reads current Records from file
    void readRecord() {
        records.clear();
        String inputValue;
        try{
            Scanner scanner = new Scanner(new File("records.txt"));
            while(scanner.hasNextLine()) {
                inputValue = scanner.nextLine();
                String[] value = inputValue.split(", ");
                Record record = new Record(Integer.parseInt(value[0]),Integer.parseInt(value[1]),value[2],value[3]);
                records.add(record);
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
    // Used when you start a new game coming from a Game Over
    void reset(){
        kills = 0;
        bosskills = 0;
        score = 0;
        // This is not always thread-safe as KeyListener may try to clear enemies when rendering its doing enemy.paint(g)
        try{
            bullets.clear();
            enemybullets.clear();
            boxes.clear();
            enemies.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
        boss = null;
        player = new Player((double) WINDOW_WIDTH / 2, WINDOW_HEIGHT - (double) WINDOW_HEIGHT / 3, this);
    }

    // GAME LOOP

    void run() {
        reset();
        boolean running = true;
        while(running){
            renderTime = System.currentTimeMillis();
            if(state == State.GAME || state == State.BOSS){
                if(state == State.GAME){
                    spawns();
                    if(kills%50 == 49){
                        state = State.BOSS;
                        boss = new Boss(bosskills);
                    }
                }
                moveAction();
                collisions();
                gameMS = System.currentTimeMillis() - temp0;
            }
            repaint(g);
            renderTime = System.currentTimeMillis() - renderTime;
            try {
                if((long) (1000/FPS)-renderTime > 0){
                    Thread.sleep(((long) 1000 / FPS) - renderTime);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                running = false;
            }
        }
    }

    // Object spawns handler
    void spawns(){
        if (rnd(1, 200) == 200) {
            if(player.getHealth() < 3){
                boxes.add(new Box(true, bosskills,this));
            } else{
                boxes.add(new Box(false, bosskills,this));
            }
        }
        int d = Math.max(50 - kills / 10, 10);
        if (rnd(1, d) == d) {
            enemies.add(new Enemy(bosskills, this));
        }
    }
    // Game moves and actions performer
    void moveAction() {
        player.shoot(shooting);
        player.move();
        if(state == State.BOSS){
            boss.move();
            boss.shoot();
        }
        for (Enemy enemy : enemies){
            enemy.shoot();
        }
        // Out of bounds window object removers
        for (int i = boxes.size() - 1; i >= 0; i--) {
            if (!boxes.get(i).move()) {
                boxes.remove(i);
            }
        }
        for (int i = enemies.size() - 1; i >= 0; i--) {
            if (!enemies.get(i).move()) {
                enemies.remove(i);
            }
        }
        for (int i = bullets.size() - 1; i >= 0; i--) {
            if (!bullets.get(i).move()) {
                bullets.remove(i);
            }
        }
        for (int i = enemybullets.size() - 1; i >= 0; i--) {
            if (!enemybullets.get(i).move()) {
                enemybullets.remove(i);
            }
        }
        // Not visible or not playing removers
        for(int i = graphicEvents.size()-1; i>= 0;i--){
            if(!graphicEvents.get(i).isPlaying()){
                graphicEvents.remove(i);
            }
        }
        for(int i = messages.size()-1; i>=0; i--){
            if(!messages.get(i).isVisible()){
                messages.remove(i);
            }
        }
    }
    // Collisions between objects handler
    void collisions() {
        for (int i = boxes.size()-1; i>=0; i--) {
            if (player.collide(boxes.get(i))) {
                boxes.get(i).lootBox(bosskills);
                score += boxes.get(i).score;
                boxes.remove(i);
            }
        }
        for (int i = bullets.size() - 1; i >= 0; i--) {
            for (int j = enemies.size() - 1; j >= 0; j--) {
                if (bullets.get(i).collide(enemies.get(j))) {
                    if(enemies.get(j).takeDamage(bullets.get(i))){
                        kills++;
                        score += enemies.get(j).score * bullets.get(i).score;
                        enemies.remove(j);
                    };
                    bullets.remove(i);
                    break;
                }
            }
        }
        if(state == State.BOSS) {
            for (int i = bullets.size() - 1; i >= 0; i--) {
                if (bullets.get(i).collide(boss)) {
                    if (boss.takeDamage(bullets.get(i))) {
                        kills++;
                        score += boss.score * bullets.get(i).score;
                        bosskills++;
                        boss = null;
                        state = State.GAME;
                    }
                    bullets.remove(i);
                    break;
                }
            }
        }
        for (int i = enemybullets.size() - 1; i >= 0; i--) {
            if(enemybullets.get(i).collide(player)){
                player.takeDamage(this);
            }
        }
        for (Enemy enemy : enemies) {
            if (player.collide(enemy) ) {
                player.takeDamage(this);
            }
        }
        if(boss !=null && boss.collide(player)){
            player.takeDamage(this);
        }
    }
    // Records writer to file. Invoked after a Game Over
    public void addRecord() {
        String playerName = "Daniel";
        Record actual = new Record(score, kills, playerName, null);
        records.add(actual);
        Collections.sort(records);
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter("records.txt"));
                for (Record record : records) {
                    writer.write(record.score + ", " + record.kills + ", " + record.playername + ", " + record.date + "\n");
                }
                writer.close();
            } catch (IOException ex) {
                    ex.printStackTrace();
            }
    }

    // Everything painting/rendering related below

    @Override
    public void update(Graphics g) {
        paint(g);
    }
    @Override
    public void paint(Graphics g) {
        g.drawImage(SpriteLoader.fons, 0, 0, null);
    }
    public void printBackground(Graphics g){
        g.drawImage(fons, 0, 0, null);
        g.drawImage(SpriteLoader.tinyStars, 0, -800 + (int) (gameMS / 50) % 800, null);
        g.drawImage(SpriteLoader.tinyStars, 0, (int) (gameMS / 50) % 800, null);
        g.drawImage(SpriteLoader.smallStars, 0, -800 + (int) (gameMS / 30) % 800, null);
        g.drawImage(SpriteLoader.smallStars, 0, (int) (gameMS / 30) % 800, null);
    }
    public void printHUD(Graphics g){
        g.setFont(font1);
        g.setColor(Color.white);
        g.drawString("Score " + (score), 30, 60);
        int count = 0;
        if(player.health > 0){
            while(count < player.health) {
                g.drawImage(SpriteLoader.heart,24+count*32,67,null);
                count++;
            }
        }
        g.drawString("Highscore " + records.get(0).score, 30, 120);
    }
    public void repaint(Graphics g) {
        printBackground(g);
        if (state == State.MENU) {
            g.drawImage(SpriteLoader.menu, 0, 0, null);
        } else {
            // Paint every Window Object
            player.paint(g);
            for(GraphicEvent graphicEvent : graphicEvents){
                graphicEvent.play(g);
            }
            for (Bullet bullet : bullets) {
                bullet.paint(g);
            }
            for (Enemy enemy : enemies) {
                enemy.paint(g);
            }
            for (Box box : boxes) {
                box.paint(g);
            }
            if(boss != null &&( state == State.BOSS || state == State.PAUSE || state == State.GAMEOVER)){
                boss.paint(g);
            }
            for (Bullet bullet : enemybullets) {
                bullet.paint(g);
            }
            for(Message message :messages){
                message.show(g);
            }
            printHUD(g);
            g.setFont(font1);
            if (gameMS <= 2000) {
                drawCenteredString(g,"[SPACE]", player.x+(double) player.width /2, player.y+35,font2);
                drawCenteredString(g,"\u2190 \u2192 \u2191  \u2193", player.x+(double)player.width /2, player.y-30,font1);
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
                drawCenteredString(g,String.valueOf(player.ammo[i]), player.x+ (double)(player.width /2), player.y+35, font3);
            }
            // Sub-menus displays
            g.setColor(Color.white);
            if (state == State.PAUSE) {
                drawCenteredString(g,"Pause", (double) WINDOW_WIDTH /2,(double) WINDOW_HEIGHT /2-40,font1);
                drawCenteredString(g,"Enter to continue", (double) WINDOW_WIDTH /2,(double) WINDOW_HEIGHT /2,font1);
                drawCenteredString(g,"Escape to exit", (double) WINDOW_WIDTH /2,(double) WINDOW_HEIGHT /2+40,font1);
            }
            if (state == State.GAMEOVER) {
                drawCenteredString(g,"Game Over",(double) WINDOW_WIDTH /2,(double) WINDOW_HEIGHT /2-80,font1);
                drawCenteredString(g,"You got "+ score +" points from "+kills+" kills",(double) WINDOW_WIDTH /2,(double) WINDOW_HEIGHT /2-40,font1);
                if(score > records.get(0).score){
                    drawCenteredString(g,"New Highscore!",(double) WINDOW_WIDTH /2,(double) WINDOW_HEIGHT /2,font1);
                }
                drawCenteredString(g,"Escape to exit",(double) WINDOW_WIDTH /2,(double) WINDOW_HEIGHT /2+40,font1);
                drawCenteredString(g,"Enter play again",(double) WINDOW_WIDTH /2,(double) WINDOW_HEIGHT /2+80,font1);
            }
            // Extra Info Display
            if (testools) {
                g.drawString("Game time: " + (double) gameMS / 1000, 30, 150);
                g.drawString("Kills: " + kills, 30, 180);
            }
        }
        super.repaint();
    }
}
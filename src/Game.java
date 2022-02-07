import java.io.*;

import kuusisto.tinysound.TinySound;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.*;
import java.util.Properties;
import java.util.List;
import java.io.FileInputStream;

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
    public int kills, score, stage;
    public static boolean testools;
    boolean shooting;
    Image fons, menu, tinyStars, smallStars;
    String playerName;
    public static Graphics g = SpriteLoader.fons.getGraphics();

    // Some static game parameters

    public int gameWidth, gameHeight;
    public int windowWidth, windowHeight;
    public double SCALE = (double) getGameHeight() /1080;
    public int FPS = 60;


    // State Machine for the Game

    public enum State {
        MENU,
        GAME,
        BOSS,
        PAUSE,
        GAMEOVER
    }
    public static State state = State.MENU;

    // Main method

    public static void main(String[] args) throws Exception {
        TinySound.init();
        new Game();
    }

    // "Main" Game class constructor
    Game() throws Exception {
        setTitle("Orbit Jam");
        setIconImage(SpriteLoader.player[2]);
        setVisible(true);
        setResizable(false);
        setBackground(Color.BLACK);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        // panel.setBorder(BorderFactory.createEmptyBorder(0 , 10 , 0 , 10));
        add(panel);
        panel.setBackground(Color.black);

        // Config file input stream

        FileInputStream fis;
        String path = "config.properties";
        InputStream in = Game.class.getClassLoader().getResourceAsStream(path);
        Properties prop = new Properties();
        prop.load(in);
        playerName = prop.getProperty("playerName");

        setWindowWidth(Integer.parseInt(prop.getProperty("WINDOW_WIDTH")));
        setWindowHeight(Integer.parseInt(prop.getProperty("WINDOW_HEIGHT")));
        setGameHeight(getWindowHeight());
        setGameWidth(getWindowHeight());

        setSize(getWindowWidth(), getWindowHeight());
        setLocationRelativeTo(null);
        fons = SpriteLoader.fons.getScaledInstance(getWindowWidth(),getWindowHeight(),Image.SCALE_SMOOTH);
        menu = SpriteLoader.menu.getScaledInstance(getGameWidth(),getGameHeight(),Image.SCALE_SMOOTH);
        tinyStars = SpriteLoader.tinyStars.getScaledInstance(getGameWidth(),getGameWidth(),Image.SCALE_SMOOTH);
        smallStars = SpriteLoader.smallStars.getScaledInstance(getGameWidth(),getGameWidth(),Image.SCALE_SMOOTH);

        // Everything Keyboard-Related
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent ek) {
                switch (ek.getKeyCode()) {
                    case KeyEvent.VK_ESCAPE:
                        if (state == State.GAME) {
                            state = State.PAUSE;

                        } else if(state == State.PAUSE || state == State.GAMEOVER) {
                            state = State.MENU;
                        }
                        else if(state == State.MENU){
                            TinySound.shutdown();
                            System.exit(0);
                        }
                        SoundLoader.back.play();
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
                    case KeyEvent.VK_C:
                        player.ACTION.press();
                        break;
                    case KeyEvent.VK_X :
                        player.SHOOT.press();
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
                    case KeyEvent.VK_X :
                        player.SHOOT.release();
                        break;
                        case KeyEvent.VK_C:
                            player.ACTION.release();
                            break;

                }
            }
        });

        // Start Game

        SoundLoader.musicajoc.play(true);
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
        stage = 0;
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
        player = new Player((double) getGameWidth() / 2, getGameHeight() - (double) getGameHeight() / 3, this);
    }

    // GAME LOOP

    void run() {
        reset();
        boolean running = true;
        while(running){
            renderTime = System.currentTimeMillis();
            if(state == State.GAME || state == State.BOSS){
                if(state == State.GAME){
                    SoundLoader.musicaboss.stop();
                    spawns();
                    if(kills%50 == 49){
                        SoundLoader.musicajoc.pause();
                        SoundLoader.musicaboss.play(true);
                        state = State.BOSS;
                        boss = new Boss(stage, this);
                    }
                }
                moveAction();
                collisions();
                gameMS = System.currentTimeMillis() - temp0;
            }
            if(state == State.GAMEOVER){
                SoundLoader.musicajoc.stop();
                SoundLoader.musicaboss.stop();
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
        if (Utils.rnd(1, 200) == 200) {
            if(player.getHealth() < 3){
                boxes.add(new Box(true, stage, this));
            } else{
                boxes.add(new Box(false, stage,this));
            }
        }
        int d = Math.max(50 - kills / 10, 10);
        if (Utils.rnd(1, d) == d) {
            enemies.add(new Enemy(stage, this));
        }
    }
    // Game moves and actions performer
    void moveAction() {
        if(player.SHOOT.pressed) {
            player.shoot();
        }
        player.action();
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
                boxes.get(i).lootBox(stage);
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
                        stage++;
                        boss = null;
                        state = State.GAME;
                        SoundLoader.musicajoc.resume();
                        g.setFont(Utils.font1);
                        Message newStage = new Message("Stage "+(stage+1), (double) gameHeight /2,(double) gameWidth /2, 2000, Utils.font1, Color.YELLOW);
                        messages.add(newStage);
                    }
                    bullets.remove(i);
                    break;
                }
            }
        }
        for (int i = enemybullets.size() - 1; i >= 0; i--) {
            if(enemybullets.get(i).collide(player)){
                player.takeDamage(this);
                enemybullets.remove(i);
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
        g.drawImage(SpriteLoader.fons, (getWindowWidth()-getGameWidth())/2, 0, null);
    }
    public void printBackground(Graphics g){
        g.drawImage(fons,0,0,null);
        g.drawImage(tinyStars, 0, -getGameHeight() + (int) (gameMS / 50) % getGameHeight(), null);
        g.drawImage(tinyStars, 0, (int) (gameMS / 50) % getGameHeight(), null);
        g.drawImage(smallStars, 0, -getGameHeight() + (int) (gameMS / 30) % getGameHeight(), null);
        g.drawImage(smallStars, 0, (int) (gameMS / 30) % getGameHeight(), null);
    }
    public void printHUD(Graphics g){
        g.setFont(Utils.font1);
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
            g.drawImage(menu, 0, 0, null);
            g.setFont(Utils.font2);
            for(int i = 0; i< Math.min(records.size(), 5); i++){
                g.drawString(records.get(i).playername, (gameHeight /3)+10, (2* gameHeight /3)+20*i);
                g.drawString(Integer.toString(records.get(i).score), 5* gameHeight /9, (2* gameHeight /3)+20*i);
            }
            g.drawString("Playing as "+playerName,(gameHeight /3)+10,  (2* gameWidth /3)+100);
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
            if (gameMS <= 2000) {
                Utils.drawCenteredString(g,"X to shoot", player.x+(double) player.width /2, player.y+35,Utils.font2, Color.white);
                Utils.drawCenteredString(g,"\u2190 \u2192 \u2191  \u2193", player.x+(double)player.width /2, player.y-30,Utils.font1,Color.white);
            }

            // Sub-menus displays
            g.setColor(Color.white);
            if (state == State.PAUSE) {
                Utils.drawCenteredString(g,"Pause", (double) gameHeight /2,(double) gameHeight /2-40,Utils.font1,Color.white);
                Utils.drawCenteredString(g,"Enter to continue", (double) gameHeight /2,(double) gameHeight /2,Utils.font1,Color.white);
                Utils.drawCenteredString(g,"Escape return to menu", (double) gameHeight /2,(double) gameHeight /2+40,Utils.font1,Color.white);
            }
            if (state == State.GAMEOVER) {
                Utils.drawCenteredString(g,"Game Over",(double) gameHeight /2,(double) gameHeight /2-80,Utils.font1, Color.white);
                Utils.drawCenteredString(g,"You got "+ score +" points from "+kills+" kills",(double) gameHeight /2,(double) gameHeight /2-40,Utils.font1,Color.white);
                if(score > records.get(0).score){
                    Utils.drawCenteredString(g,"New Highscore!",(double) gameHeight /2,(double) gameHeight /2,Utils.font1,Color.white);
                }
                Utils.drawCenteredString(g,"Enter play again",(double) gameHeight /2,(double) gameHeight /2+40,Utils.font1,Color.white);
                Utils.drawCenteredString(g,"Escape return to menu",(double) gameHeight /2,(double) gameHeight /2+80,Utils.font1,Color.white);
            }
            // Extra Info Display
            if (testools) {
                g.drawString("Game time: " + (double) gameMS / 1000, 30, 150);
                g.drawString("Kills: " + kills, 30, 180);
            }
        }
        super.repaint();
    }

    public int getGameHeight() {
        return gameHeight;
    }

    public void setGameHeight(int gameHeight) {
        this.gameHeight = gameHeight;
    }

    public int getGameWidth() {
        return gameWidth;
    }

    public void setGameWidth(int gameWidth) {
        this.gameWidth = gameWidth;
    }

    public int getWindowWidth() {
        return windowWidth;
    }

    public void setWindowWidth(int windowWidth) {
        this.windowWidth = windowWidth;
    }

    public int getWindowHeight() {
        return windowHeight;
    }

    public void setWindowHeight(int windowHeight) {
        this.windowHeight = windowHeight;
    }
}

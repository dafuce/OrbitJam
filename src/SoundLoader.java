import kuusisto.tinysound.Music;
import kuusisto.tinysound.Sound;
import kuusisto.tinysound.TinySound;

import java.io.File;

public class SoundLoader{
    public static Sound damage, shield_hit, laser1, laser2, laser3, confirm, back,explosio1, explosio2, explosio3, impact1, toggle, pickup1, pickup2,pickup3, poum ;
    public static Music musicamenu, musicajoc;
    static {
                damage = TinySound.loadSound(new File("audio/damage.wav"));
                shield_hit = TinySound.loadSound(new File("audio/shield_hit.wav"));
                laser1 = TinySound.loadSound(new File("audio/laserSmall_000.wav"));
                laser2 = TinySound.loadSound(new File("audio/laserSmall_001.wav"));
                laser3 = TinySound.loadSound(new File("audio/laserSmall_004.wav"));
                impact1 = TinySound.loadSound(new File("audio/impact1.wav"));
                explosio1 = TinySound.loadSound(new File("audio/explosionCrunch_000.wav"));
                explosio2 = TinySound.loadSound(new File("audio/explosionCrunch_003.wav"));
                explosio3 = TinySound.loadSound(new File("audio/laser_explosion.wav"));
                toggle = TinySound.loadSound(new File("audio/toggle_001.wav"));
                confirm = TinySound.loadSound(new File("audio/confirmation_001.wav"));
                back = TinySound.loadSound(new File("audio/back_001.wav"));
                pickup1 = TinySound.loadSound(new File("audio/pickup1.wav"));
                pickup2 = TinySound.loadSound(new File("audio/pickup2.wav"));
                pickup3 = TinySound.loadSound(new File("audio/pickup3.wav"));
                poum = TinySound.loadSound(new File("audio/poum2.wav"));
                musicamenu = TinySound.loadMusic(new File("audio/music.wav"));
                musicajoc = TinySound.loadMusic(new File("audio/music2.wav"));
    }

}
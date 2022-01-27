import kuusisto.tinysound.Music;
import kuusisto.tinysound.Sound;
import kuusisto.tinysound.TinySound;

import java.io.File;

public class SoundLoader{
    public static Sound damage, shield_hit, laser1, laser2, laser3, confirm, back,explosio1, explosio2, explosio3, impact1, toggle, pickup1, pickup2,pickup3, poum ;
    public static Music musicamenu, musicajoc;
    static {
                damage = TinySound.loadSound(SpriteLoader.class.getResource("/audio/damage.wav"));
                shield_hit = TinySound.loadSound(SpriteLoader.class.getResource("/audio/shield_hit.wav"));
                laser1 = TinySound.loadSound(SpriteLoader.class.getResource("/audio/laserSmall_000.wav"));
                laser2 = TinySound.loadSound(SpriteLoader.class.getResource("/audio/laserSmall_001.wav"));
                laser3 = TinySound.loadSound(SpriteLoader.class.getResource("/audio/laserSmall_004.wav"));
                impact1 = TinySound.loadSound(SpriteLoader.class.getResource("/audio/impact1.wav"));
                explosio1 = TinySound.loadSound(SpriteLoader.class.getResource("/audio/explosionCrunch_000.wav"));
                explosio2 = TinySound.loadSound(SpriteLoader.class.getResource("/audio/explosionCrunch_003.wav"));
                explosio3 = TinySound.loadSound(SpriteLoader.class.getResource("/audio/laser_explosion.wav"));
                toggle = TinySound.loadSound(SpriteLoader.class.getResource("/audio/toggle_001.wav"));
                confirm = TinySound.loadSound(SpriteLoader.class.getResource("/audio/confirmation_001.wav"));
                back = TinySound.loadSound(SpriteLoader.class.getResource("/audio/back_001.wav"));
                pickup1 = TinySound.loadSound(SpriteLoader.class.getResource("/audio/pickup1.wav"));
                pickup2 = TinySound.loadSound(SpriteLoader.class.getResource("/audio/pickup2.wav"));
                pickup3 = TinySound.loadSound(SpriteLoader.class.getResource("/audio/pickup3.wav"));
                poum = TinySound.loadSound(SpriteLoader.class.getResource("/audio/poum2.wav"));
                musicamenu = TinySound.loadMusic(SpriteLoader.class.getResource("/audio/music.wav"));
                musicajoc = TinySound.loadMusic(SpriteLoader.class.getResource("/audio/music2.wav"));
    }

}
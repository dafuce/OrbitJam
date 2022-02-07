import kuusisto.tinysound.Music;
import kuusisto.tinysound.Sound;
import kuusisto.tinysound.TinySound;

import java.io.File;
import java.io.IOException;

public class SoundLoader{
    public static Sound damage, damage2, shield_hit, laser1, laser2, laser3, laser4,confirm, back,explosio1, explosio2, explosio3, explosio4,
            explosioboss, impact1,impact2, toggle, pickup1, pickup2,pickup3, powerup1,powerup2, poum, poum2 ;
    public static Music musicamenu, musicajoc, musicaboss;
    static {
            damage = TinySound.loadSound(SpriteLoader.class.getResource("/audio/damage.wav"));
            damage2 = TinySound.loadSound(SpriteLoader.class.getResource("/audio/sfx_damage_hit3.wav"));
            shield_hit = TinySound.loadSound(SpriteLoader.class.getResource("/audio/shield_hit.wav"));
            laser1 = TinySound.loadSound(SpriteLoader.class.getResource("/audio/laserSmall_000.wav"));
            laser2 = TinySound.loadSound(SpriteLoader.class.getResource("/audio/laserSmall_001.wav"));
            laser3 = TinySound.loadSound(SpriteLoader.class.getResource("/audio/laserSmall_004.wav"));
            laser4 = TinySound.loadSound(SpriteLoader.class.getResource("/audio/sfx_wpn_laser2.wav"));
            impact1 = TinySound.loadSound(SpriteLoader.class.getResource("/audio/impact1.wav"));
            impact2 = TinySound.loadSound(SpriteLoader.class.getResource("/audio/sfx_exp_shortest_soft9.wav"));
            explosio1 = TinySound.loadSound(SpriteLoader.class.getResource("/audio/explosionCrunch_000.wav"));
            explosio2 = TinySound.loadSound(SpriteLoader.class.getResource("/audio/explosionCrunch_003.wav"));
            explosio3 = TinySound.loadSound(SpriteLoader.class.getResource("/audio/sfx_exp_medium6.wav"));
            explosio4 = TinySound.loadSound(SpriteLoader.class.getResource("/audio/sfx_exp_short_soft5.wav"));
            explosioboss = TinySound.loadSound(SpriteLoader.class.getResource("/audio/sfx_exp_long4.wav"));
            toggle = TinySound.loadSound(SpriteLoader.class.getResource("/audio/toggle_001.wav"));
            confirm = TinySound.loadSound(SpriteLoader.class.getResource("/audio/confirmation_001.wav"));
            back = TinySound.loadSound(SpriteLoader.class.getResource("/audio/back_001.wav"));
            pickup1 = TinySound.loadSound(SpriteLoader.class.getResource("/audio/pickup1.wav"));
            pickup2 = TinySound.loadSound(SpriteLoader.class.getResource("/audio/pickup2.wav"));
            pickup3 = TinySound.loadSound(SpriteLoader.class.getResource("/audio/pickup3.wav"));
            powerup1 = TinySound.loadSound(SpriteLoader.class.getResource("/audio/sfx_sounds_powerup18.wav"));
            powerup2 = TinySound.loadSound(SpriteLoader.class.getResource("/audio/sfx_menu_select1.wav"));
            poum = TinySound.loadSound(SpriteLoader.class.getResource("/audio/poum2.wav"));
            poum2 = TinySound.loadSound(SpriteLoader.class.getResource("/audio/poum2.wav"));
            musicamenu = TinySound.loadMusic(SpriteLoader.class.getResource("/audio/music3.wav"));
            musicaboss = TinySound.loadMusic(SpriteLoader.class.getResource("/audio/bossmusic.wav"));
            musicajoc = TinySound.loadMusic(SpriteLoader.class.getResource("/audio/music3.wav"));
    }
}
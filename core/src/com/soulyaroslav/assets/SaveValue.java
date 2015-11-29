package com.soulyaroslav.assets;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class SaveValue {
    // can sound play
    public static boolean soundPlay;
    // can music play
    public static boolean musicPlay;
    // can vibes work
    public static boolean vibes;
    // high score
    public static int highScore;
    //
    public static int minutes, seconds;
    // audio
    public static Music backgroundMusic;
    public static Sound click;
    public static Sound breaking;
    public static Sound catching;
    public static Sound coin;
    public static Sound explosion;
    public static Sound switching;
    public static Sound youLose;

    public SaveValue(Assets assets) {
        backgroundMusic = assets.getBackgroundMusic();
        click = assets.getClick();
        breaking = assets.getBreaking();
        catching = assets.getCatching();
        coin = assets.getCoin();
        explosion = assets.getExplosion();
        switching = assets.getSwitching();
        youLose = assets.getYouLose();
    }
}

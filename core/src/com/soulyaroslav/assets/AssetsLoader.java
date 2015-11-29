package com.soulyaroslav.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class AssetsLoader {

    public static final AssetManager manager = new AssetManager();

    public static void load(){
        // assets
        manager.load("data/atlas/button_atlas.atlas", TextureAtlas.class);
        manager.load("data/atlas/game_atlas.atlas", TextureAtlas.class);
        manager.load("data/atlas/ui_atlas.atlas", TextureAtlas.class);
        manager.load("data/atlas/info_atlas.atlas", TextureAtlas.class);
        manager.load("data/atlas/bonus_atlas.atlas", TextureAtlas.class);
        manager.load("data/atlas/dialog_atlas.atlas", TextureAtlas.class);
        // animation
        manager.load("data/anim/menu/menu_anim.atlas", TextureAtlas.class);
        manager.load("data/anim/ufo_spectr/ufo_spectr_anim.atlas", TextureAtlas.class);
        manager.load("data/anim/owl/owl_anim.atlas", TextureAtlas.class);
        manager.load("data/anim/thing/thing_anim.atlas", TextureAtlas.class);
        // audio
        manager.load("data/sounds/break.ogg", Sound.class);
        manager.load("data/sounds/catch.ogg", Sound.class);
        manager.load("data/sounds/click.wav", Sound.class);
        manager.load("data/sounds/coin.ogg", Sound.class);
        manager.load("data/sounds/explosion.mp3", Sound.class);
        manager.load("data/sounds/switch.wav", Sound.class);
        manager.load("data/sounds/you_lose.ogg", Sound.class);
        manager.load("data/sounds/menu_sound.wav", Music.class);
    }

    public static void dispose(){
        manager.dispose();
    }
}

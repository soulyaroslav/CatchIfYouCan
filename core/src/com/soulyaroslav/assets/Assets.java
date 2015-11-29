package com.soulyaroslav.assets;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Assets {

    private TextureAtlas buttonAtlas;
    private TextureAtlas gameAtlas;
    private TextureAtlas uiAtlas;
    private TextureAtlas infoAtlas;
    private TextureAtlas bonusAtlas;
    private TextureAtlas dialogAtlas;
    // anim
    private TextureAtlas menuAnimAtlas;
    private TextureAtlas ufoSpectrAnimAtlas;
    private TextureAtlas owlAnim;
    private TextureAtlas thingAnim;
    // audio
    private Sound click;
    private Sound coin;
    private Sound breaking;
    private Sound catching;
    private Sound congratulations;
    private Sound explosion;
    private Sound hurryUp;
    private Sound switching;
    private Sound youLose;
    private Music backgroundMusic;

    public Assets() {
        // game
        initButtonAssets();
        initGameAssets();
        initUiAssets();
        initInfoAssets();
        initBonusAtlas();
        initDialogAtlas();
        // init animation
        initMenuAssets();
        initUfoSpectrAnim();
        initOwlAnim();
        initThingAnim();
        // init audio
        initAudio();
    }

    private void initButtonAssets(){
        buttonAtlas = AssetsLoader.manager.get("data/atlas/button_atlas.atlas", TextureAtlas.class);
        // flip regions
        flipRegions(buttonAtlas);
    }

    private void initGameAssets(){
        gameAtlas = AssetsLoader.manager.get("data/atlas/game_atlas.atlas", TextureAtlas.class);
        flipRegions(gameAtlas);
    }

    private void initMenuAssets(){
        menuAnimAtlas = AssetsLoader.manager.get("data/anim/menu/menu_anim.atlas", TextureAtlas.class);
        flipRegions(menuAnimAtlas);
    }

    private void initUiAssets(){
        uiAtlas = AssetsLoader.manager.get("data/atlas/ui_atlas.atlas", TextureAtlas.class);
        flipRegions(uiAtlas);
    }

    private void initInfoAssets(){
        infoAtlas = AssetsLoader.manager.get("data/atlas/info_atlas.atlas", TextureAtlas.class);
        flipRegions(infoAtlas);
    }

    private void initAudio(){
        click = AssetsLoader.manager.get("data/sounds/click.wav", Sound.class);
        coin = AssetsLoader.manager.get("data/sounds/coin.ogg", Sound.class);
        breaking = AssetsLoader.manager.get("data/sounds/break.ogg", Sound.class);
        catching = AssetsLoader.manager.get("data/sounds/catch.ogg", Sound.class);
        explosion = AssetsLoader.manager.get("data/sounds/explosion.mp3", Sound.class);
        switching = AssetsLoader.manager.get("data/sounds/switch.wav", Sound.class);
        youLose = AssetsLoader.manager.get("data/sounds/you_lose.ogg", Sound.class);
        backgroundMusic = AssetsLoader.manager.get("data/sounds/menu_sound.wav", Music.class);
    }

    private void initUfoSpectrAnim(){
        ufoSpectrAnimAtlas = AssetsLoader.manager
                .get("data/anim/ufo_spectr/ufo_spectr_anim.atlas", TextureAtlas.class);
        flipRegions(ufoSpectrAnimAtlas);
    }

    private void initOwlAnim(){
        owlAnim = AssetsLoader.manager
                .get("data/anim/owl/owl_anim.atlas", TextureAtlas.class);
        flipRegions(owlAnim);
    }

    private void initThingAnim(){
        thingAnim = AssetsLoader.manager
                .get("data/anim/thing/thing_anim.atlas", TextureAtlas.class);
        flipRegions(thingAnim);
    }

    private void initBonusAtlas(){
        bonusAtlas = AssetsLoader.manager.
                get("data/atlas/bonus_atlas.atlas", TextureAtlas.class);
        flipRegions(bonusAtlas);
    }

    private void initDialogAtlas(){
        dialogAtlas = AssetsLoader.manager.
                get("data/atlas/dialog_atlas.atlas", TextureAtlas.class);
        flipRegions(dialogAtlas);
    }

    private void flipRegions(TextureAtlas regions){
        for(TextureAtlas.AtlasRegion atlasRegion : regions.getRegions()){
            atlasRegion.flip(false, true);
        }
    }

    public TextureAtlas getButtonAtlas() {
        return buttonAtlas;
    }

    public TextureAtlas getGameAtlas() {
        return gameAtlas;
    }

    public TextureAtlas getMenuAnimAtlas() {
        return menuAnimAtlas;
    }

    public TextureAtlas getUiAtlas() {
        return uiAtlas;
    }

    public TextureAtlas getInfoAtlas() {
        return infoAtlas;
    }

    public TextureAtlas getDialogAtlas() {
        return dialogAtlas;
    }

    public TextureAtlas getOwlAnim() {
        return owlAnim;
    }

    public Sound getClick() {
        return click;
    }

    public Sound getCoin() {
        return coin;
    }

    public Sound getBreaking() {
        return breaking;
    }

    public Sound getCatching() {
        return catching;
    }

    public Sound getExplosion() {
        return explosion;
    }

    public Sound getSwitching() {
        return switching;
    }

    public Sound getYouLose() {
        return youLose;
    }

    public Music getBackgroundMusic() {
        return backgroundMusic;
    }

    public TextureAtlas getUfoSpectrAnimAtlas() {
        return ufoSpectrAnimAtlas;
    }

    public TextureAtlas getThingAnim() {
        return thingAnim;
    }

    public TextureAtlas getBonusAtlas() {
        return bonusAtlas;
    }


}

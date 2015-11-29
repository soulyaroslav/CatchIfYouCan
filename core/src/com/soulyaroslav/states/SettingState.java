package com.soulyaroslav.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.soulyaroslav.assets.Assets;
import com.soulyaroslav.assets.AssetsConstants;
import com.soulyaroslav.assets.SaveValue;
import com.soulyaroslav.entitiesstate.State;
import com.soulyaroslav.gameui.Button;
import com.soulyaroslav.gameui.Switch;
import com.soulyaroslav.managers.GameStateManager;
import com.soulyaroslav.savehandler.JsonSaver;

public class SettingState extends GameState{

    // assets
    private TextureRegion gameBg, settingsBg;
    private TextureRegion switchOnOff;
    private TextureRegion btnOk;
    public Sprite musicSwitchSprite, soundSwitchSprite, vibesSwitchSprite;
    // touch position
    private Vector3 touchPos;
    // button
    private Button buttonOk;
    private Switch musicSwitch, soundSwitch, vibesSwitch;

    public SettingState(GameStateManager gameStateManager, Assets assets, JsonSaver save) {
        super(gameStateManager, assets, save);
        initAssets();
        init();
    }

    @Override
    public void init() {
        touchPos = new Vector3();
        // Buttons
        buttonOk = new Button(288, 880, 157, 101, btnOk);
        //
        musicSwitch = new Switch(405, 445, 77, 95, musicSwitchSprite);
        soundSwitch = new Switch(405, 576, 77, 95, soundSwitchSprite);
        vibesSwitch = new Switch(405, 710, 77, 95, vibesSwitchSprite);

        if(SaveValue.musicPlay){
            musicSwitch.state = Switch.SWITCH_ON;
        } else {
            musicSwitch.state = Switch.SWITCH_OFF;
        }
        if(SaveValue.soundPlay){
            soundSwitch.state = Switch.SWITCH_ON;
        } else {
            soundSwitch.state = Switch.SWITCH_OFF;
        }
        if(SaveValue.vibes){
            vibesSwitch.state = Switch.SWITCH_ON;
        } else {
            vibesSwitch.state = Switch.SWITCH_OFF;
        }
    }

    @Override
    public void initAssets() {
        gameBg = assets.getGameAtlas().findRegion(AssetsConstants.GAME_BG);
        settingsBg = assets.getUiAtlas().findRegion(AssetsConstants.SETTINGS_BG);
        switchOnOff = assets.getUiAtlas().findRegion(AssetsConstants.SWITCH_ON_OF);
        //
        musicSwitchSprite = new Sprite(assets.getUiAtlas().findRegion(AssetsConstants.NUT_SLIDER));
        soundSwitchSprite = new Sprite(assets.getUiAtlas().findRegion(AssetsConstants.NUT_SLIDER));
        vibesSwitchSprite = new Sprite(assets.getUiAtlas().findRegion(AssetsConstants.NUT_SLIDER));

        btnOk = assets.getButtonAtlas().findRegion(AssetsConstants.OK);
    }

    @Override
    public void update(float delta) {
        inputHandler();
        // play or stop sounds play
        if(soundSwitch.state == Switch.SWITCH_ON){
            SaveValue.soundPlay = true;
        } else {
            SaveValue.soundPlay = false;
        }
        if(musicSwitch.state == Switch.SWITCH_ON){
            SaveValue.musicPlay = true;
        } else {
            SaveValue.musicPlay = false;
        }
        if(vibesSwitch.state == Switch.SWITCH_ON){
            SaveValue.vibes = true;
        } else {
            SaveValue.vibes = false;
        }
    }

    @Override
    public void draw(Batch batch, ShapeRenderer sRenderer) {
        batch.begin();
        // draw bg
        batch.draw(gameBg, 0, 0, gameBg.getRegionWidth(), gameBg.getRegionHeight());
        batch.draw(settingsBg, 20, 302, settingsBg.getRegionWidth(), settingsBg.getRegionHeight());
        // draw audio ui
        batch.draw(switchOnOff, 420, 475, switchOnOff.getRegionWidth(), switchOnOff.getRegionHeight());
        musicSwitch.drawSwitch(batch);
        batch.draw(switchOnOff, 420, 605, switchOnOff.getRegionWidth(), switchOnOff.getRegionHeight());
        soundSwitch.drawSwitch(batch);
        batch.draw(switchOnOff, 420, 735, switchOnOff.getRegionWidth(), switchOnOff.getRegionHeight());
        vibesSwitch.drawSwitch(batch);
        // draw button
        buttonOk.drawButton(batch);

        batch.end();
    }

    @Override
    public void inputHandler() {
        if(Gdx.input.justTouched()){
            gameStateManager.getGame().
                    camera.unproject(touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0));

            if(buttonOk.contains(touchPos.x, touchPos.y)){
                if(SaveValue.soundPlay){
                    SaveValue.click.play();
                }
                save.save();
                gameStateManager.setState(State.MENU);
            }
            if(musicSwitch.getBounds().contains(touchPos.x, touchPos.y)){
                if(SaveValue.soundPlay){
                    SaveValue.switching.play();
                }
                if(musicSwitch.state == Switch.SWITCH_ON){
                    musicSwitch.state = Switch.SWITCH_OFF;
                } else {
                    musicSwitch.state = Switch.SWITCH_ON;
                }
            }
            if(soundSwitch.getBounds().contains(touchPos.x, touchPos.y)){
                if(SaveValue.soundPlay){
                    SaveValue.switching.play();
                }
                if(soundSwitch.state == Switch.SWITCH_ON){
                    soundSwitch.state = Switch.SWITCH_OFF;
                } else {
                    soundSwitch.state = Switch.SWITCH_ON;
                }
            }
            if(vibesSwitch.getBounds().contains(touchPos.x, touchPos.y)){
                if(SaveValue.soundPlay){
                    SaveValue.switching.play();
                }
                if(vibesSwitch.state == Switch.SWITCH_ON){
                    vibesSwitch.state = Switch.SWITCH_OFF;
                } else {
                    vibesSwitch.state = Switch.SWITCH_ON;
                }
            }
        }
    }

    @Override
    public void dispose() {

    }
}

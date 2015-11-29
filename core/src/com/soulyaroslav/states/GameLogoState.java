package com.soulyaroslav.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.soulyaroslav.assets.Assets;
import com.soulyaroslav.assets.AssetsConstants;
import com.soulyaroslav.assets.SaveValue;
import com.soulyaroslav.entitiesstate.State;
import com.soulyaroslav.game.QGame;
import com.soulyaroslav.gameui.Button;
import com.soulyaroslav.managers.GameStateManager;
import com.soulyaroslav.savehandler.JsonSaver;

public class GameLogoState extends GameState {
    //
    private TextureRegion gameLogo;
    private TextureRegion btnAudioOn, btnAudioOff;
    // Ui
    private Button buttonAudio;
    //
    private Vector3 touchPos;

    public GameLogoState(GameStateManager gameStateManager, Assets assets, JsonSaver save) {
        super(gameStateManager, assets, save);
        initAssets();
        init();
    }

    @Override
    public void init() {
        touchPos = new Vector3();
        SaveValue.backgroundMusic.setLooping(true);

        if(SaveValue.musicPlay) {
            buttonAudio = new Button(0, 10, 160, 160, btnAudioOn);
        } else {
            buttonAudio = new Button(0, 10, 160, 160, btnAudioOff);
        }
    }

    @Override
    public void initAssets() {
        gameLogo = assets.getUiAtlas().findRegion(AssetsConstants.GAME_LOGO);
        // ui
        btnAudioOn = assets.getButtonAtlas().findRegion(AssetsConstants.AUDIO_ON);
        btnAudioOff = assets.getButtonAtlas().findRegion(AssetsConstants.AUDIO_OFF);
    }

    @Override
    public void update(float delta) {
        if(SaveValue.musicPlay) {
            SaveValue.backgroundMusic.play();
        } else {
            SaveValue.backgroundMusic.pause();
        }
        inputHandler();
    }

    @Override
    public void draw(Batch batch, ShapeRenderer sRenderer) {
        batch.begin();
        batch.draw(gameLogo, 0, 0);
        drawOnOffAudio(batch);
        batch.end();
    }

    @Override
    public void inputHandler() {
        if(Gdx.input.justTouched()){
            ((QGame) Gdx.app.getApplicationListener()).
                    camera.unproject(touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0));

                if(buttonAudio.contains(touchPos.x, touchPos.y)){
                    if(SaveValue.soundPlay){
                        SaveValue.click.play();
                    } else {
                        SaveValue.click.stop();
                    }
                    if(SaveValue.musicPlay && SaveValue.soundPlay) {
                        SaveValue.musicPlay = false;
                        SaveValue.soundPlay = false;
                        buttonAudio.setTexture(btnAudioOff);
                    } else {
                        SaveValue.musicPlay = true;
                        SaveValue.soundPlay = true;
                        buttonAudio.setTexture(btnAudioOn);
                    }
                    return;
                }

            gameStateManager.setState(State.MENU);
        }
    }

    public void drawOnOffAudio(Batch batch){
        buttonAudio.drawButton(batch);
    }

    @Override
    public void dispose() {

    }
}

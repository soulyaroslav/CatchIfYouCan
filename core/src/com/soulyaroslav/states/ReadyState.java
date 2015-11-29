package com.soulyaroslav.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.soulyaroslav.assets.Assets;
import com.soulyaroslav.assets.AssetsConstants;
import com.soulyaroslav.assets.SaveValue;
import com.soulyaroslav.entitiesstate.State;
import com.soulyaroslav.managers.GameStateManager;
import com.soulyaroslav.savehandler.JsonSaver;
import com.soulyaroslav.tween.SplashTween;

public class ReadyState extends GameState{
    // assets
    private TextureRegion gameBg, ready;
    private TextureRegion readyBg;
    // tween
    private SplashTween splashTween;

    public ReadyState(GameStateManager gameStateManager,
                      Assets assets, JsonSaver save, SplashTween splashTween) {
        super(gameStateManager, assets, save);
        this.splashTween = splashTween;
        init();
        initAssets();
    }

    @Override
    public void init() {
    }

    @Override
    public void initAssets() {
        readyBg = assets.getGameAtlas().findRegion(AssetsConstants.BG_WHITE);
        gameBg = assets.getGameAtlas().findRegion(AssetsConstants.GAME_BG);
        ready = assets.getUiAtlas().findRegion(AssetsConstants.READY);
    }

    public void drawReady(Batch batch){
        batch.draw(ready, 117, 578, ready.getRegionWidth(), ready.getRegionHeight());
    }

    public void drawBackground(Batch batch){
        batch.draw(gameBg, 0, 0, gameBg.getRegionWidth(), gameBg.getRegionHeight());
        //batch.draw(readyBg, 0, 0, readyBg.getRegionWidth(), readyBg.getRegionHeight());
    }

    @Override
    public void update(float delta) {
        splashTween.update(delta);
        inputHandler();
    }

    @Override
    public void draw(Batch batch, ShapeRenderer sRenderer) {
        batch.begin();

        drawBackground(batch);
        drawReady(batch);

        batch.end();
        splashTween.draw(sRenderer);
    }

    @Override
    public void inputHandler() {
        if(Gdx.input.justTouched()){
            splashTween.prepareTransition(0, 0, 0, 1);
            gameStateManager.setState(State.RUNNING);
            if(SaveValue.soundPlay) {
                SaveValue.click.play();
            }
        }
    }

    @Override
    public void dispose() {
        splashTween.dispose();
    }
}

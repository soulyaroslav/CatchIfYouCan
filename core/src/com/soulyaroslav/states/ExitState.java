package com.soulyaroslav.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.soulyaroslav.assets.Assets;
import com.soulyaroslav.assets.AssetsConstants;
import com.soulyaroslav.assets.SaveValue;
import com.soulyaroslav.game.QGame;
import com.soulyaroslav.entitiesstate.State;
import com.soulyaroslav.gameui.Button;
import com.soulyaroslav.managers.GameStateManager;
import com.soulyaroslav.savehandler.JsonSaver;

public class ExitState extends GameState{

    private TextureRegion exitBg, gameBg;
    private TextureRegion btnOk, btnCancel;
    // ui
    private Button buttonOk, buttonCancel;
    //
    private Vector3 touchPos;
    // audio
    private Sound click;

    public ExitState(GameStateManager gameStateManager, Assets assets, JsonSaver save) {
        super(gameStateManager, assets, save);
        initAssets();
        init();
    }

    @Override
    public void init() {
        touchPos = new Vector3();
        buttonOk = new Button(400, 775, 152, 104, btnOk);
        buttonCancel = new Button(200, 775, 152, 104, btnCancel);
    }

    @Override
    public void initAssets() {
        gameBg = assets.getGameAtlas().findRegion(AssetsConstants.GAME_BG);
        exitBg = assets.getUiAtlas().findRegion(AssetsConstants.EXIT_BG);

        btnOk = assets.getButtonAtlas().findRegion(AssetsConstants.OK);
        btnCancel = assets.getButtonAtlas().findRegion(AssetsConstants.CANCEL);
        // audio
        click = assets.getClick();
    }

    @Override
    public void update(float delta) {
        inputHandler();
    }

    @Override
    public void draw(Batch batch, ShapeRenderer sRenderer) {
        batch.begin();

        batch.draw(gameBg, 0, 0, gameBg.getRegionWidth(), gameBg.getRegionHeight());
        batch.draw(exitBg, 20, 455, exitBg.getRegionWidth(), exitBg.getRegionHeight());

        buttonOk.drawButton(batch);
        buttonCancel.drawButton(batch);
        batch.end();
    }

    @Override
    public void inputHandler() {
        if(Gdx.input.justTouched()){
            ((QGame) Gdx.app.getApplicationListener())
                    .camera.unproject(touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0));

            if(buttonOk.contains(touchPos.x, touchPos.y)){
                if(SaveValue.soundPlay){
                    click.play();
                }
                Gdx.app.exit();
                return;
            }

            if(buttonCancel.contains(touchPos.x, touchPos.y)){
                if(SaveValue.soundPlay){
                    click.play();
                }
                gameStateManager.setState(State.MENU);
                return;
            }
        }
    }

    @Override
    public void dispose() {

    }
}

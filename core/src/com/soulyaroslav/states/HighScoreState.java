package com.soulyaroslav.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.soulyaroslav.assets.Assets;
import com.soulyaroslav.assets.AssetsConstants;
import com.soulyaroslav.assets.SaveValue;
import com.soulyaroslav.game.QGame;
import com.soulyaroslav.entitiesstate.State;
import com.soulyaroslav.gameui.Button;
import com.soulyaroslav.gameworld.World;
import com.soulyaroslav.managers.GameStateManager;
import com.soulyaroslav.savehandler.JsonSaver;

public class HighScoreState extends GameState{
    // assets
    private TextureRegion gameBg, highScoreBg;
    private TextureRegion btnReturn;
    // ui
    private Button buttonReturn;
    // detect touch
    private Vector3 touchPos;
    // high score
    public static int highScore, minutes, seconds;
    // font and color for font
    private BitmapFont whiteScoreFont124, blackScoreFont124;
    private BitmapFont whiteTimeFont32, blackTimeFont32;

    public HighScoreState(GameStateManager gameStateManager, Assets assets, JsonSaver save) {
        super(gameStateManager, assets, save);
        initAssets();
        init();
    }

    @Override
    public void init() {
        //
        touchPos = new Vector3();
        buttonReturn = new Button(288, 810, 157, 101, btnReturn);
        //
        if(QGame.firstStart) {
            if (SaveValue.highScore != 0) {
                highScore = SaveValue.highScore;
                minutes = SaveValue.minutes;
                seconds = SaveValue.seconds;
            }
        }
    }

    @Override
    public void initAssets() {
        gameBg = assets.getGameAtlas().findRegion(AssetsConstants.GAME_BG);
        highScoreBg = assets.getUiAtlas().findRegion(AssetsConstants.SCORE_BG);
        // ui
        btnReturn = assets.getButtonAtlas().findRegion(AssetsConstants.RETURN);
        // font score
        whiteScoreFont124 = new BitmapFont(Gdx.files.internal("data/font/white_font_124.fnt"), true);
        blackScoreFont124 = new BitmapFont(Gdx.files.internal("data/font/black_font_124.fnt"), true);
        // font time
        whiteTimeFont32 = new BitmapFont(Gdx.files.internal("data/font/white_font_32.fnt"), true);
        blackTimeFont32 = new BitmapFont(Gdx.files.internal("data/font/black_font_32.fnt"), true);
    }

    @Override
    public void update(float delta) {
        inputHandler();
    }

    @Override
    public void draw(Batch batch, ShapeRenderer sRenderer) {
        batch.begin();
        // draw bg
        batch.draw(gameBg, 0, 0, gameBg.getRegionWidth(), gameBg.getRegionHeight());
        batch.draw(highScoreBg, 17, 303, highScoreBg.getRegionWidth(), highScoreBg.getRegionHeight());
        // draw ui
        buttonReturn.drawButton(batch);
        // draw score font
        if(String.valueOf(highScore).length() == 1){
            blackScoreFont124.draw(batch, String.valueOf(highScore), 344, 522);
            whiteScoreFont124.draw(batch, String.valueOf(highScore), 340, 520);
        } else if(String.valueOf(highScore).length() == 2){
            blackScoreFont124.draw(batch, String.valueOf(highScore), 304, 522);
            whiteScoreFont124.draw(batch, String.valueOf(highScore), 300, 520);
        } else if(String.valueOf(highScore).length() == 3){
            blackScoreFont124.draw(batch, String.valueOf(highScore), 264, 522);
            whiteScoreFont124.draw(batch, String.valueOf(highScore), 260, 520);
        } else if(String.valueOf(highScore).length() == 4){
            blackScoreFont124.draw(batch, String.valueOf(highScore), 224, 522);
            whiteScoreFont124.draw(batch, String.valueOf(highScore), 220, 520);
        }
        // draw time font
        blackTimeFont32.draw(batch, minutes + ":" + seconds, 344, 692);
        whiteTimeFont32.draw(batch, minutes + ":" + seconds, 340, 690);

        batch.end();
    }

    @Override
    public void inputHandler() {
        if(Gdx.input.justTouched()){
            ((QGame) Gdx.app.getApplicationListener())
                        .camera.unproject(touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0));

            if(buttonReturn.contains(touchPos.x, touchPos.y)){
                if(SaveValue.soundPlay) {
                    SaveValue.click.play();
                }
                gameStateManager.setState(State.MENU);
                return;
            }
        }
    }

    @Override
    public void dispose() {
        QGame.firstStart = false;
    }
}

package com.soulyaroslav.states;

import com.badlogic.gdx.Gdx;
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

public class GameOverState extends GameState{
    // bg
    private TextureRegion gameBg, gameOverBg, blackBg;
    // ui
    private TextureRegion btnMenu, btnRetry;
    // buttons
    private Button buttonMenu, buttonRetry;
    //
    private Vector3 touchPos;
    // font
    private BitmapFont whiteScoreFont64, blackScoreFont64;
    private BitmapFont whiteBestScoreFont64, blackBestScoreFont64;
    private BitmapFont whiteTimeFont32, blackTimeFont32;
    // game value
    private int score;
    private int bestScore;
    private int minutes, seconds;

    public GameOverState(GameStateManager gameStateManager, Assets assets, JsonSaver save) {
        super(gameStateManager, assets, save);
        initAssets();
        init();
    }

    @Override
    public void init() {
        touchPos = new Vector3();
        buttonMenu = new Button(400, 900, 157, 101, btnMenu);
        buttonRetry = new Button(185, 900, 157, 101, btnRetry);
        //get score
        score = World.score;
        //get minutes
        minutes = World.minutes;
        // get seconds
        seconds = World.seconds;
        // get high score
        bestScore = HighScoreState.highScore;
        //System.out.println("gameIsOver");
        HighScoreState.minutes = World.minutes;
        HighScoreState.seconds = World.seconds;
    }

    @Override
    public void initAssets() {
        gameBg = assets.getGameAtlas().findRegion(AssetsConstants.GAME_BG);
        gameOverBg = assets.getUiAtlas().findRegion(AssetsConstants.GAME_OVER_BG);
        blackBg = assets.getGameAtlas().findRegion(AssetsConstants.BG_BLACK);
        // ui
        btnMenu = assets.getButtonAtlas().findRegion(AssetsConstants.MENU);
        btnRetry = assets.getButtonAtlas().findRegion(AssetsConstants.RETURN);
        // white font
        whiteScoreFont64 = new BitmapFont(Gdx.files.internal("data/font/white_font_64.fnt"), true);
        whiteBestScoreFont64 = new BitmapFont(Gdx.files.internal("data/font/white_font_64.fnt"), true);
        whiteTimeFont32 = new BitmapFont(Gdx.files.internal("data/font/white_font_32.fnt"), true);
        // black font
        blackScoreFont64 = new BitmapFont(Gdx.files.internal("data/font/black_font_64.fnt"), true);
        blackBestScoreFont64 = new BitmapFont(Gdx.files.internal("data/font/black_font_64.fnt"), true);
        blackTimeFont32 = new BitmapFont(Gdx.files.internal("data/font/black_font_32.fnt"), true);
    }

    @Override
    public void update(float delta) {
        inputHandler();
        if(HighScoreState.highScore < World.score){
            //System.out.println("highScore < World.score");
            HighScoreState.highScore = World.score;
            //System.out.println("highScore = " + HighScoreState.highScore);
        }
    }

    @Override
    public void draw(Batch batch, ShapeRenderer sRenderer) {
        batch.begin();
        // draw bg
        batch.draw(gameBg, 0, 0, gameBg.getRegionWidth(), gameBg.getRegionHeight());
        batch.draw(blackBg, 0, 0, blackBg.getRegionWidth(), blackBg.getRegionHeight());
        batch.draw(gameOverBg, 20, 320, gameOverBg.getRegionWidth(), gameOverBg.getRegionHeight());
        // draw font
        // score
        blackScoreFont64.draw(batch, String.valueOf(score), 444, 522);
        whiteScoreFont64.draw(batch, String.valueOf(score), 440, 520);
        // best score
        blackBestScoreFont64.draw(batch, String.valueOf(bestScore), 434, 652);
        whiteBestScoreFont64.draw(batch, String.valueOf(bestScore), 430, 650);
        // time
        blackTimeFont32.draw(batch, minutes + ":" + seconds, 364, 752);
        whiteTimeFont32.draw(batch, minutes + ":" + seconds, 360, 750);
        // draw button
        buttonRetry.drawButton(batch);
        buttonMenu.drawButton(batch);

        batch.end();
    }

    @Override
    public void inputHandler() {
        if(Gdx.input.justTouched()){
            ((QGame) Gdx.app.getApplicationListener())
                    .camera.unproject(touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0));

            if(buttonMenu.contains(touchPos.x, touchPos.y)){
                gameStateManager.setState(State.MENU);
                World.restart();
                // save score
                save.saveScore();
                return;
            }
            if(buttonRetry.contains(touchPos.x, touchPos.y)){
                gameStateManager.setState(State.READY);
                World.restart();
                // save score
                save.saveScore();
                return;
            }
        }
    }

    @Override
    public void dispose() {

    }
}

package com.soulyaroslav.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.soulyaroslav.assets.Assets;
import com.soulyaroslav.assets.AssetsConstants;
import com.soulyaroslav.assets.SaveValue;
import com.soulyaroslav.entitiesstate.State;
import com.soulyaroslav.managers.GameStateManager;
import com.soulyaroslav.gameui.Button;
import com.soulyaroslav.savehandler.JsonSaver;

public class MenuState extends GameState{

    private GameStateManager gameStateManager;
    // Texture for the background
    private TextureRegion gameBg, btnInfo;
    private Array<TextureAtlas.AtlasRegion> regions;
    private Animation menuAnim;
    // Menu items
    private Button buttonStart, buttonScore, buttonSetting, buttonExit;
    private Button buttonInfo;
    // touch position
    private Vector3 touchPos;
    private float time;

    public MenuState(GameStateManager gameStateManager, Assets assets, JsonSaver save) {
        super(gameStateManager, assets, save);
        this.gameStateManager = gameStateManager;
        initAssets();
        init();
    }

    @Override
    public void init() {
        // Initialization menu items
        buttonStart = new Button(280, 580, 160, 145);
        buttonScore = new Button(275, 730, 165, 145);
        buttonSetting = new Button(190, 915, 210, 145);
        buttonExit = new Button(400, 1020, 167, 91);
        buttonInfo = new Button(560, 10, 160, 160, btnInfo);
        // initialize touch position vector
        touchPos = new Vector3();
    }

    @Override
    public void initAssets() {
        // background texture
        gameBg = assets.getGameAtlas().findRegion(AssetsConstants.GAME_BG);
        // ui
        btnInfo = assets.getButtonAtlas().findRegion(AssetsConstants.INFO);
        // animation
        regions = assets.getMenuAnimAtlas().getRegions();
        menuAnim = new Animation(1 / 10f, regions);
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
        //System.out.println("Menu state is drawing");
        batch.begin();
        // draw background menu
        drawBgHelpScreen(batch);
        // info button
        drawInfo(batch);
        // menu anim
        batch.draw(menuAnim.getKeyFrame(time += Gdx.graphics.getDeltaTime(), false), 50, 100);
        batch.end();
        // draw menu items
        //drawMenuUI(sRenderer);
    }

    @Override
    public void inputHandler() {
        if(Gdx.input.justTouched()){
            gameStateManager.getGame().camera
                    .unproject(touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0));

            if(menuAnim.isAnimationFinished(time)) {
                if (buttonStart.getBounds().contains(touchPos.x, touchPos.y)) {
                    if (SaveValue.soundPlay) {
                        SaveValue.click.play();
                    }
                    gameStateManager.setState(State.READY);
                    return;
                }
                if (buttonScore.getBounds().contains(touchPos.x, touchPos.y)) {
                    if (SaveValue.soundPlay) {
                        SaveValue.click.play();
                    }
                    gameStateManager.setState(State.HIGH_SCORE);
                    return;
                }
                if (buttonSetting.getBounds().contains(touchPos.x, touchPos.y)) {
                    if (SaveValue.soundPlay) {
                        SaveValue.click.play();
                    }
                    gameStateManager.setState(State.SETTING);
                    return;
                }
                if (buttonExit.getBounds().contains(touchPos.x, touchPos.y)) {
                    if (SaveValue.soundPlay) {
                        SaveValue.click.play();
                    }
                    gameStateManager.setState(State.EXIT);
                    return;
                }
                if (buttonInfo.contains(touchPos.x, touchPos.y)) {
                    gameStateManager.setState(State.INFO);
                    return;
                }
            }
        }
    }

    public void drawBgHelpScreen(Batch batch){
        batch.draw(gameBg, 0, 0, gameBg.getRegionWidth(), gameBg.getRegionHeight());
    }

    public void drawMenuUI(ShapeRenderer sRenderer){
        buttonStart.drawRect(sRenderer, Color.BLACK);
        buttonScore.drawRect(sRenderer, Color.BLACK);
        buttonSetting.drawRect(sRenderer, Color.BLACK);
        buttonExit.drawRect(sRenderer, Color.BLACK);
    }

    public void drawInfo(Batch batch){
        buttonInfo.drawButton(batch);
    }

    @Override
    public void dispose() {
    }
}

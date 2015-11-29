package com.soulyaroslav.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.soulyaroslav.assets.Assets;
import com.soulyaroslav.assets.AssetsConstants;
import com.soulyaroslav.game.QGame;
import com.soulyaroslav.entitiesstate.State;
import com.soulyaroslav.gameui.Button;
import com.soulyaroslav.managers.GameStateManager;
import com.soulyaroslav.savehandler.JsonSaver;

public class InfoState extends GameState{
    // background
    private TextureRegion gameBg;
    private Array<TextureRegion> infoRegions;
    // UI
    private TextureRegion btnGoLeft, btnGoRight, btnMenu;
    // buttons
    private Button buttonGoLeft, buttonGoRight, buttonMenu;
    // detect touch position
    private Vector3 touchPos;
    //
    private int currentIndex;

    public InfoState(GameStateManager gameStateManager, Assets assets, JsonSaver save) {
        super(gameStateManager, assets, save);
        initAssets();
        init();
    }

    @Override
    public void init() {
        touchPos = new Vector3();
        // ui
        buttonGoLeft = new Button(20, 1160, 157, 101, btnGoLeft);
        buttonGoRight = new Button(545, 1160, 157, 101, btnGoRight);
        buttonMenu = new Button(300, 1160, 157, 101, btnMenu);
        // current index
        currentIndex = 0;
    }

    @Override
    public void initAssets() {
        infoRegions = new Array<TextureRegion>();
        infoRegions.add(assets.getInfoAtlas().findRegion(AssetsConstants.ONE));
        infoRegions.add(assets.getInfoAtlas().findRegion(AssetsConstants.TWO));
        infoRegions.add(assets.getInfoAtlas().findRegion(AssetsConstants.THREE));
        infoRegions.add(assets.getInfoAtlas().findRegion(AssetsConstants.FOUR));
        infoRegions.add(assets.getInfoAtlas().findRegion(AssetsConstants.FIVE));
        infoRegions.add(assets.getInfoAtlas().findRegion(AssetsConstants.SIX));
        infoRegions.add(assets.getInfoAtlas().findRegion(AssetsConstants.SEVEN));
        //
        gameBg = assets.getInfoAtlas().findRegion(AssetsConstants.INFO_BG);
        //
        btnGoLeft = assets.getButtonAtlas().findRegion(AssetsConstants.GO_LEFT);
        btnGoRight = assets.getButtonAtlas().findRegion(AssetsConstants.GO_RIGHT);
        btnMenu = assets.getButtonAtlas().findRegion(AssetsConstants.MENU);
    }

    @Override
    public void update(float delta) {
        inputHandler();
    }

    @Override
    public void draw(Batch batch, ShapeRenderer sRenderer) {
        batch.begin();

        batch.draw(gameBg, 0, 0);
        for(int i = 0; i < infoRegions.size; i++){
            if(i == currentIndex){
                batch.draw(infoRegions.get(i), 360 - (infoRegions.get(i).getRegionWidth() / 2),
                        640 - (infoRegions.get(i).getRegionHeight() / 2),
                        infoRegions.get(i).getRegionWidth(), infoRegions.get(i).getRegionHeight());
            }
        }

        if(currentIndex == 0){
            buttonGoRight.drawButton(batch);
        } else if(currentIndex < infoRegions.size - 1){
            buttonGoRight.drawButton(batch);
            buttonGoLeft.drawButton(batch);
        } else if(currentIndex == infoRegions.size - 1){
            buttonGoLeft.drawButton(batch);
        }
        buttonMenu.drawButton(batch);
        batch.end();
    }

    @Override
    public void inputHandler() {
        if(Gdx.input.justTouched()){
            ((QGame) Gdx.app.getApplicationListener()).
                    camera.unproject(touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0));

            if(buttonGoLeft.contains(touchPos.x, touchPos.y)){
                if(currentIndex == 0){
                    currentIndex = 0;
                } else {
                    currentIndex--;
                }
                return;
            }
            if(buttonGoRight.contains(touchPos.x, touchPos.y)) {
                if (currentIndex < infoRegions.size - 1) {
                    currentIndex++;
                } else {
                    currentIndex = infoRegions.size - 1;
                }
                return;
            }
        }
        if(buttonMenu.contains(touchPos.x, touchPos.y)){
            System.out.println("Work!!");
            gameStateManager.setState(State.MENU);
            return;
        }
    }

    @Override
    public void dispose() {

    }
}

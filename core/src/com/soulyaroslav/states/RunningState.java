package com.soulyaroslav.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.soulyaroslav.assets.Assets;
import com.soulyaroslav.gameworld.WorldRenderer;
import com.soulyaroslav.gameworld.World;
import com.soulyaroslav.managers.GameStateManager;
import com.soulyaroslav.savehandler.JsonSaver;
import com.soulyaroslav.tween.SplashTween;

/*
*  Its main state were player catch game objects
* */
public class RunningState extends GameState{

    private World worldUpdate;
    private WorldRenderer worldRenderer;
    // touch
    private Vector3 touchPos;
    //
    private SplashTween splashTween;

    public RunningState(GameStateManager gameStateManager,
                        Assets assets, JsonSaver save, SplashTween splashTween) {
        super(gameStateManager, assets, save);
        this.splashTween = splashTween;
        worldUpdate = new World(gameStateManager, assets, splashTween);
        worldRenderer = new WorldRenderer(worldUpdate, assets, splashTween);
        init();
        initAssets();
    }

    @Override
    public void init() {
        touchPos = new Vector3();
    }

    @Override
    public void initAssets() {

    }

    @Override
    public void update(float delta) {
        inputHandler();
        worldUpdate.update(delta);
    }

    @Override
    public void draw(Batch batch, ShapeRenderer sRenderer) {
        worldRenderer.render(batch, sRenderer);
    }

    @Override
    public void inputHandler() {
        //worldUpdate.inputHandler();
    }

    @Override
    public void dispose() {
        splashTween.dispose();
    }

    public World getWorldUpdate(){
        return worldUpdate;
    }
}

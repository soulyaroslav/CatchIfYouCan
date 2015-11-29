package com.soulyaroslav.states;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.soulyaroslav.assets.Assets;
import com.soulyaroslav.assets.SaveHandler;
import com.soulyaroslav.managers.GameStateManager;
import com.soulyaroslav.savehandler.JsonSaver;

public abstract class GameState {

    protected GameStateManager gameStateManager;
    protected Assets assets;
    protected JsonSaver save;

    protected GameState(GameStateManager gameStateManager, Assets assets, JsonSaver save) {
        this.gameStateManager = gameStateManager;
        this.assets = assets;
        this.save = save;
    }

    public abstract void init();

    public abstract void initAssets();

    public abstract void update(float delta);

    public abstract void draw(Batch batch, ShapeRenderer sRenderer);

    public abstract void inputHandler();

    public abstract void dispose();
}

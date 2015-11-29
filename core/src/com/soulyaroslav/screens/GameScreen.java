package com.soulyaroslav.screens;

import com.badlogic.gdx.Screen;
import com.soulyaroslav.game.QGame;
import com.soulyaroslav.gameworld.World;
import com.soulyaroslav.managers.GameStateManager;

public class GameScreen implements Screen {

    private QGame QGame;
    private GameStateManager gameStateManager;
    // check pause
    public static boolean isPause;

    public GameScreen(QGame QGame){
        this.QGame = QGame;
        gameStateManager = new GameStateManager(QGame);
    }

    @Override
    public void show() {
        System.out.println("GameScreen.show()");
    }

    @Override
    public void render(float delta) {
        gameStateManager.update(delta);
        gameStateManager.draw(QGame.batch, QGame.sRenderer);
    }

    @Override
    public void resize(int width, int height) {
        System.out.println("GameScreen.resize()");
    }

    @Override
    public void pause() {
        System.out.println("GameScreen.pause()");
        World.pause = true;
    }

    @Override
    public void resume() {
        System.out.println("GameScreen.resume()");
    }

    @Override
    public void hide() {
        System.out.println("GameScreen.hide()");
    }

    @Override
    public void dispose() {
        System.out.println("GameScreen.dispose()");
        gameStateManager.dispose();
    }
}

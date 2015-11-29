package com.soulyaroslav.managers;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.soulyaroslav.assets.Assets;
import com.soulyaroslav.assets.SaveHandler;
import com.soulyaroslav.assets.SaveValue;
import com.soulyaroslav.game.QGame;
import com.soulyaroslav.entitiesstate.State;
import com.soulyaroslav.savehandler.JsonSaver;
import com.soulyaroslav.states.ExitState;
import com.soulyaroslav.states.GameLogoState;
import com.soulyaroslav.states.GameOverState;
import com.soulyaroslav.states.GameState;
import com.soulyaroslav.states.HighScoreState;
import com.soulyaroslav.states.MenuState;
import com.soulyaroslav.states.InfoState;
import com.soulyaroslav.states.ReadyState;
import com.soulyaroslav.states.RunningState;
import com.soulyaroslav.states.SettingState;
import com.soulyaroslav.tween.SplashTween;

public class GameStateManager {

    private QGame qGame;
    //
    private GameState gameState;
    private State currentState;
    //
    private Assets assets;
    private JsonSaver save;
    private SaveHandler saveHandler;
    private SaveValue saveValue;
    // tween
    private SplashTween splashTween;

    public GameStateManager(QGame qGame) {
        this.qGame = qGame;
        assets = new Assets();
        save = new JsonSaver();
        save.load();
        save.loadScore();
        saveHandler = new SaveHandler(save);
        saveValue = new SaveValue(assets);
        // tween
        splashTween = qGame.getSplashTween();
        setState(State.GAME_LOGO);
    }

    public void setState(State state){
        if(gameState != null) gameState.dispose();
        switch(state){
            case GAME_LOGO:
                splashTween.prepareTransition(0, 0, 0, 1);
                gameState = new GameLogoState(this, assets, save);
                break;
            case MENU:
                splashTween.prepareTransition(0, 0, 0, 1);
                gameState = new MenuState(this, assets, save);
                currentState = State.MENU;
                break;
            case READY:
                splashTween.prepareTransition(0, 0, 0, 1);
                gameState = new ReadyState(this, assets, save, splashTween);
                currentState = State.READY;
                break;
            case RUNNING:
                splashTween.prepareTransition(0, 0, 0, 1);
                gameState = new RunningState(this, assets, save, splashTween);
                currentState = State.RUNNING;
                break;
            case INFO:
                splashTween.prepareTransition(0, 0, 0, 1);
                gameState = new InfoState(this, assets, save);
                currentState = State.INFO;
                break;
            case HIGH_SCORE:
                splashTween.prepareTransition(0, 0, 0, 1);
                gameState = new HighScoreState(this, assets, save);
                currentState = State.HIGH_SCORE;
                break;
            case GAME_OVER:
                splashTween.prepareTransition(0, 0, 0, 1);
                gameState = new GameOverState(this, assets, save);
                currentState = State.GAME_OVER;
                break;
            case SETTING:
                splashTween.prepareTransition(0, 0, 0, 1);
                gameState = new SettingState(this, assets, save);
                currentState = State.SETTING;
                break;
            case EXIT:
                splashTween.prepareTransition(0, 0, 0, 1);
                gameState = new ExitState(this, assets, save);
                currentState = State.EXIT;
                break;
        }
    }

    public void update(float delta){
        gameState.update(delta);
        splashTween.update(delta);
    }

    public void draw(Batch batch, ShapeRenderer sRenderer){
        gameState.draw(batch, sRenderer);
        splashTween.draw(sRenderer);
    }

    public void dispose(){
        gameState.dispose();
        splashTween.dispose();
    }

    public State getState(){
        return currentState;
    }

    public GameState getGameState(){
        return gameState;
    }

    public QGame getGame(){
        return qGame;
    }
}

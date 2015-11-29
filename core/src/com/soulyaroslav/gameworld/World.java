package com.soulyaroslav.gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.soulyaroslav.assets.Assets;
import com.soulyaroslav.assets.AssetsConstants;
import com.soulyaroslav.assets.SaveValue;
import com.soulyaroslav.entities.Bomb;
import com.soulyaroslav.entities.Man;
import com.soulyaroslav.entities.OldMan;
import com.soulyaroslav.entities.Player;
import com.soulyaroslav.entitiesstate.ObjectState;
import com.soulyaroslav.entitiesstate.ScoreLevel;
import com.soulyaroslav.game.QGame;
import com.soulyaroslav.entitiesstate.GameLevel;
import com.soulyaroslav.entitiesstate.State;
import com.soulyaroslav.gameobject.DynamicGameObject;
import com.soulyaroslav.entities.Ufo;
import com.soulyaroslav.gameui.Button;
import com.soulyaroslav.helpers.PlayerDragHandler;
import com.soulyaroslav.managers.GameStateManager;
import com.soulyaroslav.spawners.BadNutSpawner;
import com.soulyaroslav.spawners.BombSpawner;
import com.soulyaroslav.spawners.FineSpawner;
import com.soulyaroslav.spawners.GiftSpawner;
import com.soulyaroslav.spawners.GoldAcornSpawner;
import com.soulyaroslav.spawners.AcornSpawner;
import com.soulyaroslav.states.GameOverState;
import com.soulyaroslav.tween.BonusTween;
import com.soulyaroslav.tween.SplashTween;

import java.util.ArrayList;
import java.util.List;

public class World {
    // world size
    public static final int WORLD_WIDTH = 720;
    public static final int WORLD_HEIGHT = 1280;
    // spalash tween
    // tween
    private SplashTween splashTween;
    private BonusTween bonusTween;
    // game state handler
    private static GameStateManager gameStateManager;
    // input processor
    private PlayerDragHandler playerDragHandler;
    // assets
    private Assets assets;
    // game objects spawn
    private AcornSpawner acornSpawner;
    private BadNutSpawner badNutSpawner;
    private FineSpawner fineSpawner;
    private BombSpawner bombSpawner;
    private GiftSpawner giftSpawner;
    private GoldAcornSpawner goldAcornSpawner;
    // path initialization
    private WorldPaths worldPaths;
    private PathHandler pathHandler;
    // list of all game objects
    private List<DynamicGameObject> allGameObjects;
    // game objects
    private Ufo ufo;
    private Man man;
    private OldMan oldMan;
    private Player player;
    // current level state in the game
    private static GameLevel currentGameLevel;
    // current level for adding point into score
    private static ScoreLevel currentScoreLevel;
    // vector for the detect touch position
    private Vector3 touchPos;
    // value for score
    public static int score;
    // value for counting nuts catch
    public static int nutsCatch;
    // value to detect game over
    public static int fineCount;
    // value to detect bomb catch
    public static int bombCount;
    // game screen UI items
    private Button buttonPause, buttonResume;
    private Button buttonRestart, buttonExit, buttonSettings;
    // buttons assets
    private TextureRegion btnRestart, btnExit;
    private TextureRegion btnPause, btnResume, btnSettings;
    private List<Button> buttons;
    // check when start pause
    public static boolean pause;
    public static boolean gameOver;
    // time
    private float totalTime;
    public static int minutes;
    public static int seconds;
    //
    private int catchInRow;

    public World(GameStateManager gameStateManager,
                 Assets assets, SplashTween splashTween) {
        this.gameStateManager = gameStateManager;
        // tween
        this.splashTween = splashTween;
        bonusTween = new BonusTween();
        // init spawners
        initSpawner();
        // init game objects
        initGameObjects();
        // path
        worldPaths = new WorldPaths();
        pathHandler = new PathHandler(this);
        // init buttons
        initButtons(assets);
        // other value
        initOtherValue();
    }

    private void initSpawner(){
        // spawn
        acornSpawner = new AcornSpawner(this);
        badNutSpawner = new BadNutSpawner(this);
        fineSpawner = new FineSpawner(this);
        bombSpawner = new BombSpawner(this);
        giftSpawner = new GiftSpawner(this);
        goldAcornSpawner = new GoldAcornSpawner(this);
    }

    private void initGameObjects(){
        // game objects list
        allGameObjects = new ArrayList<DynamicGameObject>();
        // game objects
        ufo = new Ufo(0, 0, allGameObjects);
        man = new Man(720, 900, acornSpawner.getAcorns());
        oldMan = new OldMan(-237, 858, acornSpawner.getAcorns());
        player = new Player(410, 995, this);
        // player handler
        playerDragHandler = new PlayerDragHandler(player);
        Gdx.input.setInputProcessor(playerDragHandler);
    }

    private void initButtons(Assets assets){
        // buttons assets
        btnPause = assets.getButtonAtlas().findRegion(AssetsConstants.PAUSE);
        btnResume = assets.getButtonAtlas().findRegion(AssetsConstants.RESUME);
        btnExit = assets.getButtonAtlas().findRegion(AssetsConstants.EXIT);
        btnRestart = assets.getButtonAtlas().findRegion(AssetsConstants.RESTART);
        btnSettings = assets.getButtonAtlas().findRegion(AssetsConstants.SETTINGS);
        // UI
        buttonResume = new Button(300, 335, 160, 126, btnResume);
        buttonRestart = new Button(280, 545, 161, 101, btnRestart);
        buttonSettings = new Button(280, 710, 170, 133, btnSettings);
        buttonExit = new Button(380, 845, 167, 91, btnExit);
        buttonPause = new Button(560, 5, 160, 160, btnPause);
        // btn list
        buttons = new ArrayList<Button>();
        buttons.add(buttonResume);
        buttons.add(buttonRestart);
        buttons.add(buttonSettings);
        buttons.add(buttonExit);
    }

    private void initOtherValue(){
        //
        currentGameLevel = GameLevel.TEST;
        //
        currentScoreLevel = ScoreLevel.X1;
        // touch position for cam
        touchPos = new Vector3();

        // game value
        score = 0;
        nutsCatch = 0;
        fineCount = 1;
        bombCount = 3;
        // set pause false
        pause = false;
        gameOver = false;
        // time
        totalTime = 0;
        minutes = 0;
        seconds = 0;
    }

    public void update(float delta) {
        splashTween.update(delta);
        bonusTween.update(delta);
        if (!pause) {
            updateRunning(delta);
        } else {
            updatePause(delta);
        }
    }

    private void updatePause(float delta) {
        if (Gdx.input.justTouched()) {
            gameStateManager.getGame().camera
                    .unproject(touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0));

            if (buttonResume.contains(touchPos.x, touchPos.y)) {
                splashTween.prepareTransition(0, 0, 0, 1);
                if(SaveValue.soundPlay) {
                    SaveValue.click.play();
                }
                pause = false;
                return;
            }
            if (buttonRestart.contains(touchPos.x, touchPos.y)) {
                splashTween.prepareTransition(0, 0, 0, 1);
                if(SaveValue.soundPlay) {
                    SaveValue.click.play();
                }
                gameStateManager.setState(State.READY);
                restart();
                return;
            }
            if (buttonSettings.contains(touchPos.x, touchPos.y)) {
                splashTween.prepareTransition(0, 0, 0, 1);
                if(SaveValue.soundPlay) {
                    SaveValue.click.play();
                }
                gameStateManager.setState(State.SETTING);
                return;
            }
            if (buttonExit.contains(touchPos.x, touchPos.y)) {
                splashTween.prepareTransition(0, 0, 0, 1);
                if(SaveValue.soundPlay) {
                    SaveValue.click.play();
                }
                gameStateManager.setState(State.EXIT);
                return;
            }
        }
    }

    public void updateRunning(float delta) {
        System.out.println("currentScoreLevel = " + currentScoreLevel);
        if (Gdx.input.justTouched()) {
            ((QGame) Gdx.app.getApplicationListener()).camera
                    .unproject(touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0));

            if (buttonPause.contains(touchPos.x, touchPos.y)) {
                splashTween.prepareTransition(0, 0, 0, 1);
                if(SaveValue.soundPlay) {
                    SaveValue.click.play();
                }
                pause = true;
                return;
            }
        }
        // set score into null
        if(score < 0){
            score = 0;
        }
        // increment time
        totalTime += delta;
        minutes = ((int) totalTime) / 60;
        seconds = ((int) totalTime) - minutes * 60;
        // player update
        player.update(delta);
        // update current level
        updateCurrentLevel(delta);
        // set bonus for catch acorn in row
        setBonus();
        //
        playExplosionSound();
        // detect if game is over
        gameOver();
    }

    private void playExplosionSound(){
        for(DynamicGameObject dynamicGameObject : allGameObjects){
            if(dynamicGameObject instanceof Bomb){
                Bomb bomb = (Bomb) dynamicGameObject;
                if(bomb.getState() == ObjectState.STATE_HIT){
                    if(SaveValue.soundPlay){
                        SaveValue.explosion.play();
                    }
                }
            }
        }
    }

    private void setBonus(){
        catchInRow = player.getCatchInRow();
        //
        if(currentScoreLevel != ScoreLevel.X50) {
            if (catchInRow == 0) {
                currentScoreLevel = ScoreLevel.X1;
            }
            if (catchInRow >= 5 && catchInRow < 10) {
                currentScoreLevel = ScoreLevel.X5;
            }
            if (catchInRow >= 10 && catchInRow < 15) {
                currentScoreLevel = ScoreLevel.X10;
            }
            if (catchInRow >= 15 && catchInRow < 20) {
                currentScoreLevel = ScoreLevel.X15;
            }
            if (catchInRow >= 20) {
                currentScoreLevel = ScoreLevel.X20;
            }
        }
    }

    private void updateCurrentLevel(float delta){
        switch (currentGameLevel) {
            case TEST:
                giftSpawner.setPath(pathHandler.getFreePath());
                giftSpawner.update(delta);
                acornSpawner.setPath(pathHandler.getFreePath());
                acornSpawner.update(delta);
                fineSpawner.setPath(pathHandler.getFreePath());
                fineSpawner.update(delta);
                bombSpawner.setPath(pathHandler.getFreePath());
                bombSpawner.update(delta);
                man.update(delta);
                oldMan.update(delta);
                break;
            case EASY:
                acornSpawner.setPath(pathHandler.getFreePath());
                acornSpawner.update(delta);
                badNutSpawner.setPath(pathHandler.getFreePath());
                badNutSpawner.update(delta);
                bombSpawner.setPath(pathHandler.getFreePath());
                if (nutsCatch == 50) {
                    currentGameLevel = GameLevel.MEDIUM;
                }
                break;
            case MEDIUM:
                acornSpawner.setPath(pathHandler.getFreePath());
                acornSpawner.update(delta);
                badNutSpawner.setPath(pathHandler.getFreePath());
                badNutSpawner.update(delta);
                bombSpawner.setPath(pathHandler.getFreePath());
                bombSpawner.update(delta);
                giftSpawner.setPath(pathHandler.getFreePath());
                giftSpawner.update(delta);
                // enemy
                man.update(delta);
                if (nutsCatch == 100) {
                    currentGameLevel = GameLevel.HARD;
                }
                break;
            case HARD:
                acornSpawner.setPath(pathHandler.getFreePath());
                acornSpawner.update(delta);
                badNutSpawner.setPath(pathHandler.getFreePath());
                badNutSpawner.update(delta);
                bombSpawner.setPath(pathHandler.getFreePath());
                bombSpawner.update(delta);
                giftSpawner.setPath(pathHandler.getFreePath());
                giftSpawner.update(delta);
                fineSpawner.setPath(pathHandler.getFreePath());
                fineSpawner.update(delta);
                // enemy
                man.update(delta);
                oldMan.update(delta);
                if (nutsCatch == 150) {
                    currentGameLevel = GameLevel.VERY_HARD;
                }
                break;
            case VERY_HARD:
                acornSpawner.setPath(pathHandler.getFreePath());
                acornSpawner.update(delta);
                badNutSpawner.setPath(pathHandler.getFreePath());
                badNutSpawner.update(delta);
                bombSpawner.setPath(pathHandler.getFreePath());
                bombSpawner.update(delta);
                giftSpawner.setPath(pathHandler.getFreePath());
                giftSpawner.update(delta);
                fineSpawner.setPath(pathHandler.getFreePath());
                fineSpawner.update(delta);
                goldAcornSpawner.setPath(pathHandler.getFreePath());
                goldAcornSpawner.update(delta);
                // enemy
                man.update(delta);
                oldMan.update(delta);
                ufo.update(delta);
                break;
        }
    }

    private void gameOver() {
        if (bombCount == 0 || fineCount == 0) {
            gameOver = true;
            gameStateManager.setState(State.GAME_OVER);
        }
    }

    public static void restart() {
        // Put all of these variable into start position
        currentGameLevel = GameLevel.TEST;
        score = 0;
        fineCount = 1;
        nutsCatch = 0;
        bombCount = 3;
        AcornSpawner.nutSpawnCount = 0;
        minutes = 0;
        seconds = 0;
        gameOver = false;
    }

    public Ufo getUfo() {
        return ufo;
    }

    public WorldPaths getPathManager() {
        return worldPaths;
    }

    public void setGameObjectIntoList(DynamicGameObject dynamicGameObject) {
        allGameObjects.add(dynamicGameObject);
    }

    public List<DynamicGameObject> getAllGameObjects() {
        return allGameObjects;
    }

    public GameLevel getCurrentLevel() {
        return currentGameLevel;
    }

    public ScoreLevel getCurrentScoreLevel() {
        return currentScoreLevel;
    }

    public AcornSpawner getNutSpawner() {
        return acornSpawner;
    }

    public Button getButtonPause() {
        return buttonPause;
    }

    public List<Button> getButtons() {
        return buttons;
    }

    public Man getMan() {
        return man;
    }

    public OldMan getOldMan() {
        return oldMan;
    }

    public BonusTween getBonusTween() {
        return bonusTween;
    }

    public Player getPlayer() {
        return player;
    }

    // Set

    public void setCurrentScoreLevel(ScoreLevel currentScoreLevel) {
        this.currentScoreLevel = currentScoreLevel;
    }

    public void addDynamicGameObject(DynamicGameObject dynamicGameObject){
        allGameObjects.add(dynamicGameObject);
    }
}
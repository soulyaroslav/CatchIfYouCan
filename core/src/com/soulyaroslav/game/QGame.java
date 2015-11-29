package com.soulyaroslav.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.soulyaroslav.facebook.FacebookResolver;
import com.soulyaroslav.screens.SplashScreen;
import com.soulyaroslav.tween.SplashTween;

public class QGame extends Game {

    public static final int VIRTUAL_WIDTH = 720;
    public static final int VIRTUAL_HEIGHT = 1280;
    // facebook resolver
    private FacebookResolver facebookResolver;
    //
    public SpriteBatch batch;
    public OrthographicCamera camera;
    public ShapeRenderer sRenderer;
    // view port
    private FitViewport fitViewport;
    // splash tween
    private SplashTween splashTween;
    //
    public static boolean firstStart;

    public QGame(FacebookResolver facebookResolver) {
        this.facebookResolver = facebookResolver;
    }

    @Override
    public void create() {
        firstStart = true;
        //
        batch = new SpriteBatch();
        // соотношение сторон
        //float aspectRatio = (float) Gdx.graphics.getHeight() / (float) Gdx.graphics.getWidth();

        camera = new OrthographicCamera();
        camera.setToOrtho(true, 720, 1280);
        fitViewport = new FitViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, camera);

        // привяжем наш batch к камере
        batch.setProjectionMatrix(camera.combined);
        //
        sRenderer = new ShapeRenderer();
        sRenderer.setProjectionMatrix(camera.combined);
        // tween
        splashTween = new SplashTween();

        SplashScreen splashScreen = new SplashScreen(this);
        setScreen(splashScreen);
    }

    public void connectFacebook(){
        facebookResolver.connectFacebook();
    }

    public void discconectFacebook(){
        facebookResolver.disconnectFacebook();
    }

    public boolean isConnectedFacebook(){
        return facebookResolver.isConnectedFacebook();
    }

    @Override
    public void resize(int width, int height) {
        System.out.println("resize()");
        fitViewport.update(width, height);
    }



    @Override
    public void dispose() {
    }

    public SplashTween getSplashTween() {
        return splashTween;
    }
}

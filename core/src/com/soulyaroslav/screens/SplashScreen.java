package com.soulyaroslav.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.GL20;
import com.soulyaroslav.tweenaccessors.SpriteAccessor;
import com.soulyaroslav.game.QGame;


import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;

public class SplashScreen implements Screen {

    private QGame qGame;
    private Sprite studio_logo;
    private TweenManager manager;

    public SplashScreen(QGame qGame) {
        this.qGame = qGame;
    }

    @Override
    public void show() {
        studio_logo = new Sprite(new Texture(Gdx.files.internal("data/studio_logo.png")));
        studio_logo.setColor(1, 1, 1, 0);
        studio_logo.setSize(427, 479);
        studio_logo.setPosition(145, 400);
        studio_logo.flip(false, true);
        // tween
        setupTween();
    }

    private void setupTween() {
        manager = new TweenManager();
        Tween.registerAccessor(Sprite.class, new SpriteAccessor());

        TweenCallback cb = new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                ((QGame) Gdx.app.getApplicationListener()).setScreen(new LoadingScreen(qGame));
            }
        };

        Tween.to(studio_logo, SpriteAccessor.ALPHA, 3f)
                .target(1)
                .start(manager);

        Tween.to(studio_logo, SpriteAccessor.ALPHA, 3f)
                .delay(2f)
                .target(0)
                .setCallback(cb)
                .setCallbackTriggers(TweenCallback.END)
                .start(manager);
     }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        manager.update(delta);
        qGame.batch.begin();
        studio_logo.draw(qGame.batch);
        qGame.batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}

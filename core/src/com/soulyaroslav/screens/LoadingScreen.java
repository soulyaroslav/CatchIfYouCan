package com.soulyaroslav.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import com.soulyaroslav.game.QGame;
import com.soulyaroslav.assets.AssetsLoader;
import com.soulyaroslav.tween.SplashTween;

public class LoadingScreen implements Screen{

    private QGame qGame;

    private TextureAtlas loadingTexture;
    private Animation loading;
    private Texture bg;
    //
    float elapsedTime = 0;
    //
    private SplashTween splashTween;

    public LoadingScreen(QGame qGame) {
        this.qGame = qGame;
        splashTween = qGame.getSplashTween();
    }

    @Override
    public void show() {
        AssetsLoader.load();
        loadingTexture = new TextureAtlas(Gdx.files.internal("data/anim/loading/loading_anim.atlas"));
        loading = new Animation(1 / 2f, getRegions(loadingTexture));
        bg = new Texture(Gdx.files.internal("data/bg1.png"));
        splashTween.prepareTransition(0, 0, 0, 1);
    }

    public static Array<TextureAtlas.AtlasRegion> getRegions(TextureAtlas atlas){
        Array<TextureAtlas.AtlasRegion> regions = new Array<TextureAtlas.AtlasRegion>();
        for(TextureAtlas.AtlasRegion atlasRegion : atlas.getRegions()) {
            atlasRegion.flip(false, true);
            regions.add(atlasRegion);
        }
        return regions;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        splashTween.update(delta);
        qGame.batch.begin();

        qGame.batch.draw(bg, 0, 0);
        if(!AssetsLoader.manager.update()) {
            qGame.batch.draw(loading.getKeyFrame(elapsedTime += delta, true), 160, 552);
        } else {
            System.out.println("Loading done...");
            ((QGame) Gdx.app.getApplicationListener()).setScreen(new GameScreen(qGame));
        }
        AssetsLoader.manager.update();
        qGame.batch.end();
        splashTween.draw(qGame.sRenderer);
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
        AssetsLoader.dispose();
        splashTween.dispose();
    }
}

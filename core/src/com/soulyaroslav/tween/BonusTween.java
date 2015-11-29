package com.soulyaroslav.tween;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.soulyaroslav.tweenaccessors.SpriteAccessor;
import com.soulyaroslav.tweenaccessors.Value;
import com.soulyaroslav.tweenaccessors.ValueAccessors;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;

public class BonusTween {

    private TweenManager manager;

    public BonusTween() {
        init();
    }

    public void init() {
        Tween.registerAccessor(Value.class, new ValueAccessors());
        manager = new TweenManager();
    }

    public void prepareTransition(Sprite sprite) {
        Tween.to(sprite, SpriteAccessor.SCALE, .4f)
                .target(1, 1)
                .start(manager);
    }

    public void update(float delta) {
        manager.update(delta);
    }

    public void dispose() {
        manager.killAll();
    }
}

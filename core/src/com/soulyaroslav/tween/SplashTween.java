package com.soulyaroslav.tween;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.soulyaroslav.tweenaccessors.Value;
import com.soulyaroslav.tweenaccessors.ValueAccessors;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

/*
* this is main tween class make splash square around of screen
*
* @param manager - its object for updating tween
* @param color - its value for change splash color
* @param alpha - this value change transparency of color
*
* */
public class SplashTween {

    private TweenManager manager;
    private Color color;
    private Value alpha = new Value();

    public SplashTween() {
        init();
    }

    public void init() {
        color = new Color();
        Tween.registerAccessor(Value.class, new ValueAccessors());
        manager = new TweenManager();
    }

    public void prepareTransition(int r, int g, int b, float duration) {
        color.set(r, g, b, 1);
        alpha.setValue(1);

        Tween.to(alpha, -1, duration)
                .target(0)
                .ease(TweenEquations.easeOutQuad)
                .start(manager);
    }
    private void drawTransition(ShapeRenderer sRenderer) {
        if (alpha.getValue() > 0) {
            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            sRenderer.begin(ShapeRenderer.ShapeType.Filled);
            sRenderer.setColor(color.r, color.g,
                    color.b, alpha.getValue());
            sRenderer.rect(0, 0, 720, 1280);
            sRenderer.end();
            Gdx.gl.glDisable(GL20.GL_BLEND);
        }
    }

    public void draw(ShapeRenderer sRenderer) {
        drawTransition(sRenderer);
    }

    public void update(float delta) {
        if (alpha.getValue() > 0) {
            manager.update(delta);
        }
    }

    public void dispose() {
        manager.killAll();
    }

}

package com.soulyaroslav.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Pool;
import com.soulyaroslav.game.QGame;
import com.soulyaroslav.entitiesstate.ObjectState;
import com.soulyaroslav.gameobject.DynamicGameObject;

public class Leaf extends DynamicGameObject implements Pool.Poolable{
    public static final float LEAF_WIDTH = 8;
    public static final float LEAF_HEIGHT = 15;
    // current object state
    private ObjectState state;

    public Leaf() {
        super(0, 0, LEAF_WIDTH, LEAF_HEIGHT);
        state = ObjectState.STATE_ALIVE;
    }

    public boolean isDead() {
        return position.y > MathUtils.random(1200, 1280) - LEAF_HEIGHT ;
    }

    public void update(float delta) {

        move(delta);

        if (isDead() || state == ObjectState.STATE_PLAYER_CATCH) {
            dead();
        }
    }

    private void move(float delta){
        velocity.add(acceleration.x * delta, acceleration.y * delta);

        if (velocity.y > 200) {
            velocity.y = 200;
        }

        position.add(velocity.x * delta, velocity.y * delta);
        bounds.set(position.x, position.y, LEAF_WIDTH, LEAF_HEIGHT);
    }

    public void draw(ShapeRenderer sRenderer){
        sRenderer.begin(ShapeRenderer.ShapeType.Line);
        sRenderer.setColor(Color.MAROON);
        sRenderer.rect(bounds.x, bounds.y, bounds.width, bounds.height);
        sRenderer.end();
    }

    public void dead() {
        state = ObjectState.STATE_HIT;
    }

    public void initObject(){
        position.x = MathUtils.random(10, 710);
        position.y = 350;
        state = ObjectState.STATE_ALIVE;
    }

    @Override
    public void reset() {
        position.set(0, 0);
    }

    public ObjectState getState(){
        return state;
    }
}

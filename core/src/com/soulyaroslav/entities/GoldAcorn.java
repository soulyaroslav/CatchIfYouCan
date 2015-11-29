package com.soulyaroslav.entities;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Pool;
import com.soulyaroslav.game.QGame;
import com.soulyaroslav.entitiesstate.ObjectState;
import com.soulyaroslav.gameobject.DynamicGameObject;
import com.soulyaroslav.gameworld.Path;

public class GoldAcorn extends DynamicGameObject implements Pool.Poolable{
    // height
    public static final float GOLD_ACORN_HEIGHT = 66;
    // width
    public static final float GOLD_ACORN_WIDTH = 56;
    // current nut state
    private ObjectState state;

    public GoldAcorn() {
        super(0, 0, GOLD_ACORN_WIDTH, GOLD_ACORN_HEIGHT);
        state = ObjectState.STATE_ALIVE;
    }

    public boolean isDead() {
        return position.y > MathUtils.random(1200, 1280) - GOLD_ACORN_HEIGHT;
    }

    public void update(float delta) {

        move(delta);

        if (isDead() || state == ObjectState.STATE_PLAYER_CATCH) {
            dead();
        }
    }

    private void move(float delta){
        velocity.add(acceleration.x * delta, acceleration.y * delta);

        if (velocity.y > 400) {
            velocity.y = 400;
        }

        position.add(velocity.x * delta, velocity.y * delta);
        bounds.set(position.x, position.y, GOLD_ACORN_WIDTH, GOLD_ACORN_HEIGHT);

    }

    public void dead() {
        state = ObjectState.STATE_HIT;
    }

    public void initObject(Path path) {
        position.x = path.setMidSpawnPosition(GOLD_ACORN_WIDTH / 2);
        position.y = 300;
        state = ObjectState.STATE_ALIVE;
    }

    @Override
    public void reset() {
        position.set(0, 0);
    }

    public float getMiddlePosX(){
        return position.x + (GOLD_ACORN_WIDTH / 2);
    }

    public void setState(ObjectState state){
        this.state = state;
    }

    public ObjectState getState(){
        return state;
    }
}

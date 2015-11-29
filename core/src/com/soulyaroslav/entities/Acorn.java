package com.soulyaroslav.entities;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Pool;
import com.soulyaroslav.entitiesstate.CatchState;
import com.soulyaroslav.game.QGame;
import com.soulyaroslav.entitiesstate.ObjectState;
import com.soulyaroslav.gameobject.DynamicGameObject;
import com.soulyaroslav.gameworld.Path;

public class Acorn extends DynamicGameObject implements Pool.Poolable {

    // висота ореха
    public static final float ACORN_HEIGHT = 66;
    // ширина ореха
    public static final float ACORN_WIDTH = 56;
    // current nut state
    private ObjectState state;
    private int catchState;
    // spawn time
    private float spawnTime;
    //
    public int id;

    public Acorn() {
        super(0, 0, ACORN_WIDTH, ACORN_HEIGHT);
        state = ObjectState.STATE_ALIVE;
        catchState = CatchState.MAN_NOT_CATCH;
    }

    public boolean isDead() {
        return position.y > MathUtils.random(1200, 1280) - ACORN_HEIGHT;
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
        bounds.set(position.x, position.y, ACORN_WIDTH, ACORN_HEIGHT);
    }

    public void dead() {
       state = ObjectState.STATE_HIT;
    }

    public void initObject(Path path) {
        position.x = path.setMidSpawnPosition(ACORN_WIDTH / 2);
        position.y = 310;
        state = ObjectState.STATE_ALIVE;
        catchState = CatchState.MAN_NOT_CATCH;
        id+=1;
    }

    @Override
    public void reset() {
        position.set(0, 0);
    }

    public float getMiddlePosX(){
        return position.x + (ACORN_WIDTH / 2);
    }

    public void setState(ObjectState state){
        this.state = state;
    }

    public ObjectState getState(){
        return state;
    }

    public int getCatchState() {
        return catchState;
    }

    public void setCatchState(int catchState) {
        this.catchState = catchState;
    }
}

package com.soulyaroslav.entities;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Pool;
import com.soulyaroslav.entitiesstate.ObjectState;
import com.soulyaroslav.gameobject.DynamicGameObject;
import com.soulyaroslav.gameworld.Path;
import com.soulyaroslav.gameworld.World;

public class Fine extends DynamicGameObject implements Pool.Poolable {

    public static final float FINE_HEIGHT = 86;
    // ширина ореха
    public static final float FINE_WIDTH = 70;
    // current nut state
    private ObjectState state;

    public Fine() {
        super(0, 0, FINE_WIDTH, FINE_HEIGHT);
    }

    public boolean isDead() {
        return position.y > MathUtils.random(1200, 1280) - FINE_HEIGHT;
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
        bounds.set(position.x, position.y, FINE_WIDTH, FINE_HEIGHT);

    }

    public void dead() {
        state = ObjectState.STATE_HIT;
    }

    public void initObject(Path path) {
        position.x = path.setMidSpawnPosition(FINE_WIDTH / 2);
        position.y = 300;
        state = ObjectState.STATE_ALIVE;
    }

    @Override
    public void reset() {
        position.set(0, 0);
    }

    public float getMiddlePosX(){
        return position.x + (FINE_WIDTH / 2);
    }

    public ObjectState getState() {
        return state;
    }

    public void setState(ObjectState state) {
        this.state = state;
    }
}

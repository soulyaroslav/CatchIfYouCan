package com.soulyaroslav.entities;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Pool;
import com.soulyaroslav.game.QGame;
import com.soulyaroslav.entitiesstate.ObjectState;
import com.soulyaroslav.gameobject.DynamicGameObject;
import com.soulyaroslav.gameworld.Path;

/*
* it`s give player change his score adding more point during 30 sec
* */
public class Gift extends DynamicGameObject implements Pool.Poolable {

    // height
    public static final float GIFT_HEIGHT = 84;
    // width
    public static final float GIFT_WIDTH = 60;
    // current nut state
    private ObjectState state;

    public Gift() {
        super(0, 0, GIFT_WIDTH, GIFT_HEIGHT);
        state = ObjectState.STATE_ALIVE;
    }

    public boolean isDead() {
        return position.y > MathUtils.random(1200, 1280) - GIFT_HEIGHT;
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
        bounds.set(position.x, position.y, GIFT_WIDTH, GIFT_HEIGHT);

    }

    public void dead() {
        state = ObjectState.STATE_HIT;
    }

    public void initObject(Path path) {
        position.x = path.setMidSpawnPosition(GIFT_WIDTH / 2);
        position.y = 300;
        state = ObjectState.STATE_ALIVE;
    }

    @Override
    public void reset() {
        position.set(0, 0);
    }

    public float getMiddlePosX(){
        return position.x + (GIFT_WIDTH / 2);
    }

    public void setState(ObjectState state){
        this.state = state;
    }

    public ObjectState getState(){
        return state;
    }
}

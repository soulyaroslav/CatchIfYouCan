package com.soulyaroslav.gameobject;


import com.badlogic.gdx.math.Vector2;

public class DynamicGameObject extends GameObject{
    // velocity of game object
    protected final Vector2 velocity;
    protected final Vector2 angel;
    protected final Vector2 acceleration;

    public DynamicGameObject(float x, float y, float width, float height) {
        super(x, y, width, height);
        velocity = new Vector2();
        angel = new Vector2();
        acceleration = new Vector2(0, 300);
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public Vector2 getAngel() {
        return angel;
    }

    public Vector2 getAcceleration() {
        return acceleration;
    }
}

package com.soulyaroslav.gameobject;


import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class GameObject {
    // position of game object
    protected Vector2 position;
    // rectangle for detect collision
    protected Rectangle bounds;

    public GameObject(float x, float y, float width, float height) {
        this.position = new Vector2(x, y);
        this.bounds = new Rectangle(x, y, width, height);
    }

    public Vector2 getPosition(){
        return position;
    }

    public Rectangle getBounds(){
        return bounds;
    }
}

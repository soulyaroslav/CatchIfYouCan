package com.soulyaroslav.entities;

import com.badlogic.gdx.math.Rectangle;
import com.soulyaroslav.entitiesstate.CatchState;
import com.soulyaroslav.gameobject.DynamicGameObject;
import com.soulyaroslav.gameobject.GameObject;

import java.util.List;

public class Ufo extends GameObject{

    public static final float UFO_WIDTH = 248;
    public static final float UFO_HEIGHT = 128;
    // list
    private List<DynamicGameObject> gameObjects;
    // rectangles
    private Rectangle spectrBounds;

    private float speed;
    private float angle;
    private float circleX;
    private float circleY;

    public Ufo(float x, float y, List<DynamicGameObject> gameObjects){
        super(x, y, UFO_WIDTH, UFO_HEIGHT);
        this.gameObjects = gameObjects;
        spectrBounds = new Rectangle(0, 0, 144, 727);
        speed = 0.4f;
        angle = 180;
    }

    public void update(float delta){
        move(delta);

        for(DynamicGameObject dynamicGameObject : gameObjects) {
            checkIfGameObjectInside(dynamicGameObject);
        }
        spectrBounds.set(position.x + 52, position.y + 100, 144, 727);
    }

    public void move(float delta){
        angle += (speed * delta) % 360;
        circleX = (float) (Math.cos(angle) * 850);
        circleY = (float) (Math.sin(angle) * 440);
        if (angle >= 360) {
            angle = 180;
        }
        position.x = circleX;
        position.y = circleY;
    }

    public void checkIfGameObjectInside(DynamicGameObject dynamicGameObject){
        if(dynamicGameObject instanceof Acorn) {
            Acorn acorn = (Acorn) dynamicGameObject;
            if (spectrBounds.contains(acorn.getPosition().x, acorn.getPosition().y)
                    && acorn.getCatchState() != CatchState.MAN_CATCH){
                acorn.dead();
            }
        }
    }
}

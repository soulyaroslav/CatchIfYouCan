package com.soulyaroslav.entities;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.soulyaroslav.assets.SaveValue;
import com.soulyaroslav.entitiesstate.CatchState;
import com.soulyaroslav.entitiesstate.ObjectState;
import com.soulyaroslav.gameobject.DynamicGameObject;
import com.soulyaroslav.gameobject.GameObject;

import java.util.ArrayList;
import java.util.List;

public class OldMan extends GameObject{

    public static final float OLD_MAN_WIDTH = 237;
    public static final float OLD_MAN_HEIGHT = 293;
    //
    private Vector2 velocity;
    private Vector2 acceleration;
    //
    private List<Acorn> gameObjects;
    // rect for detect move left
    private Rectangle oldManMoveBounds;
    // rect for detect move right
    private Rectangle netBounds;
    // side for move
    private boolean isMoveRight = false;
    private boolean isMoveLeft = false;
    // max pos man
    private float maxPosX;
    // list for mans acorn couch
    private List<Acorn> oldManCatchAcorn;

    public OldMan(float x, float y, List<Acorn> gameObjects) {
        super(x, y, OLD_MAN_WIDTH, OLD_MAN_HEIGHT);
        this.gameObjects = gameObjects;
        //
        velocity = new Vector2();
        acceleration = new Vector2(150, 0);
        // rect
        oldManMoveBounds = new Rectangle(72, 400, 130, 880);
        netBounds = new Rectangle(0, 1055, 100, 100);
        //
        oldManCatchAcorn = new ArrayList<Acorn>();
    }

    public void update(float delta){
        for(int i = 0; i < gameObjects.size(); i++){
            Acorn acorn = gameObjects.get(i);
            if(oldManMoveBounds.contains(
                    acorn.getPosition().x, acorn.getPosition().y)){
                isMoveRight = true;
                isMoveLeft = false;
                maxPosX = acorn.getPosition().x - 157;
                if(oldManCatchAcorn.size() == 0) {
                    oldManCatchAcorn.add(acorn);
                    acorn.setCatchState(CatchState.MAN_CATCH);
                } else {
                    if(oldManCatchAcorn.get(0).getState() == ObjectState.STATE_HIT){
                        oldManCatchAcorn.add(acorn);
                        acorn.setCatchState(CatchState.MAN_CATCH);
                    }
                }
            }
        }
        for(int i = 0; i < oldManCatchAcorn.size(); i++){
            Acorn acorn = oldManCatchAcorn.get(i);
            if(netBounds.contains(acorn.getPosition().x, acorn.getPosition().y)){
                isMoveLeft = true;
                isMoveRight = false;
                acorn.setState(ObjectState.STATE_HIT);
            }
            if(acorn.getState() == ObjectState.STATE_PLAYER_CATCH){
                isMoveLeft = true;
                isMoveRight = false;
                acorn.setState(ObjectState.STATE_HIT);
            }
            // destroy acorns
            if(acorn.getState() == ObjectState.STATE_HIT){
                oldManCatchAcorn.remove(i);
            }
        }
        if (isMoveRight) {
            moveRight(delta);
        }
        if (isMoveLeft) {
            moveLeft(delta);
        }
        netBounds.set(position.x + OLD_MAN_WIDTH - 100, 1055, 100, 100);
    }

    private void moveLeft(float delta) {
        velocity.add(acceleration.x * delta, acceleration.y * delta);
        if (velocity.x > 300) {
            velocity.x = 300;
        }

        position.add(-velocity.x * delta, velocity.y * delta);

        if (position.x < -237){
            position.x = -237;
        }
    }

    public void moveRight(float delta){
        velocity.add(acceleration.x * delta, acceleration.y * delta);
        if (velocity.x > 300) {
            velocity.x = 300;
        }

        position.add(velocity.x * delta, velocity.y * delta);

        if (position.x > maxPosX){
            position.x = maxPosX;
        }
    }

    public float getMaxPosX() {
        return maxPosX;
    }

    public void drawRect(ShapeRenderer sRenderer){
        sRenderer.rect(oldManMoveBounds.x, oldManMoveBounds.y, oldManMoveBounds.width, oldManMoveBounds.height);
    }

    public void drawNetRect(ShapeRenderer sRenderer){
        sRenderer.rect(netBounds.x, netBounds.y, netBounds.width, netBounds.height);
    }
}

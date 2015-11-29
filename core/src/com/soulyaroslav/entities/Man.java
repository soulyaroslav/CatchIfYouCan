package com.soulyaroslav.entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.soulyaroslav.assets.SaveValue;
import com.soulyaroslav.entitiesstate.CatchState;
import com.soulyaroslav.entitiesstate.ObjectState;
import com.soulyaroslav.gameobject.DynamicGameObject;
import com.soulyaroslav.gameobject.GameObject;

import java.util.ArrayList;
import java.util.List;

public class Man extends GameObject {

    public static final float MAN_WIDTH = 236;
    public static final float MAN_HEIGHT = 255;
    //
    private Vector2 velocity;
    private Vector2 acceleration;
    //
    private List<Acorn> gameObjects;
    // rect for detect move left
    private Rectangle manMoveBounds;
    // rect for detect move right
    private Rectangle netBounds;
    // side for move
    private boolean isMoveRight = false;
    private boolean isMoveLeft = false;
    // max pos man
    private float maxPosX;
    // list for mans acorn couch
    private List<Acorn> manCatchAcorn;
    //
    public int randonDialog;

    public Man(float x, float y, List<Acorn> gameObjects) {
        super(x, y, MAN_WIDTH, MAN_HEIGHT);
        velocity = new Vector2();
        acceleration = new Vector2(150, 0);
        this.gameObjects = gameObjects;
        manMoveBounds = new Rectangle(520, 400, 130, 880);
        netBounds = new Rectangle(0, 1055, 100, 100);
        //
        manCatchAcorn = new ArrayList<Acorn>();
    }

    public void update(float delta){
        for(int i = 0; i < gameObjects.size(); i++){
            Acorn acorn = gameObjects.get(i);
            if(manMoveBounds.contains(
                    acorn.getPosition().x, acorn.getPosition().y)){
                isMoveLeft = true;
                isMoveRight = false;
                maxPosX = acorn.getPosition().x - 20;
                if(manCatchAcorn.size() == 0) {
                    manCatchAcorn.add(acorn);
                    acorn.setCatchState(CatchState.MAN_CATCH);
                } else {
                    if(manCatchAcorn.get(0).getState() == ObjectState.STATE_HIT){
                        manCatchAcorn.add(acorn);
                        acorn.setCatchState(CatchState.MAN_CATCH);
                    }
                }
            }
        }
        for(int i = 0; i < manCatchAcorn.size(); i++){
            Acorn acorn = manCatchAcorn.get(i);
            if(netBounds.contains(acorn.getPosition().x, acorn.getPosition().y)){
                isMoveRight = true;
                isMoveLeft = false;
                acorn.setState(ObjectState.STATE_HIT);
            }
            if(acorn.getState() == ObjectState.STATE_PLAYER_CATCH){
                isMoveRight = true;
                isMoveLeft = false;
                acorn.setState(ObjectState.STATE_HIT);
            }
            if(acorn.getState() == ObjectState.STATE_HIT){
                manCatchAcorn.remove(i);
            }
        }
        if (isMoveRight) {
            moveRight(delta);
        }
        if (isMoveLeft) {
            moveLeft(delta);
        }
        netBounds.set(position.x, 1055, 100, 100);
    }

    public void moveLeft(float delta){
        velocity.add(acceleration.x * delta, acceleration.y * delta);
        if (velocity.x > 300) {
            velocity.x = 300;
        }
        position.add(-velocity.x * delta, velocity.y * delta);
        if (position.x < maxPosX){
            position.x = maxPosX;
        }
    }

    public void moveRight(float delta){
        velocity.add(acceleration.x * delta, acceleration.y * delta);
        if (velocity.x > 300) {
            velocity.x = 300;
        }
        position.add(velocity.x * delta, velocity.y * delta);
        if (position.x > 956) {
            position.x = 720;
        }
    }

    public float getMaxPosX() {
        return maxPosX;
    }

    public void drawRect(ShapeRenderer sRenderer){
        sRenderer.rect(manMoveBounds.x, manMoveBounds.y, manMoveBounds.width, manMoveBounds.height);
        //sRenderer.rect(dialogBounds.x, dialogBounds.y, dialogBounds.width, dialogBounds.height);
    }

    public void drawNetRect(ShapeRenderer sRenderer){
        sRenderer.rect(netBounds.x, netBounds.y, netBounds.width, netBounds.height);
    }
}

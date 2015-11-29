package com.soulyaroslav.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.soulyaroslav.assets.SaveValue;
import com.soulyaroslav.entitiesstate.ObjectState;
import com.soulyaroslav.entitiesstate.ScoreLevel;
import com.soulyaroslav.gameobject.DynamicGameObject;
import com.soulyaroslav.gameobject.GameObject;
import com.soulyaroslav.gameworld.World;

import java.util.List;

public class Player extends GameObject{

    public static final float PLAYER_WIDTH = 100;
    public static final float PLAYER_HEIGHT = 284;
    //
    private World world;
    // list
    private List<Acorn> acorns;
    private List<DynamicGameObject> dynamicGameObjects;
    // rect
    private Rectangle catchBounds;
    private Rectangle playerMove;
    //
    private int catchInRow;
    private int nextId;
    // time
    private float totalTime;
    private float minutes;
    public static float seconds;
    private boolean start;

    public Player(float x, float y, World world) {
        super(x, y, PLAYER_WIDTH, PLAYER_HEIGHT);
        this.world = world;
        acorns = world.getNutSpawner().getAcorns();
        dynamicGameObjects = world.getAllGameObjects();
        //
        bounds.set(x, y, PLAYER_WIDTH, PLAYER_HEIGHT);
        catchBounds = new Rectangle(x, y, 100, 50);
        playerMove = new Rectangle(0, 1080, 720, 200);
        catchInRow = 0;
        nextId = 0;
    }

    public void update(float delta){
        for(int i = 0; i < acorns.size(); i++){
            Acorn acorn = acorns.get(i);
            if(catchBounds.contains(acorn.getMiddlePosX(), acorn.getPosition().y)){
                // set object state
                acorn.setState(ObjectState.STATE_PLAYER_CATCH);
                // increase value
                World.nutsCatch++;
                // increase score
                addScore();
                // update id
                if(!start) {
                    int acornId = world.getNutSpawner().getAcornIds().get(i);
                    // first initialization
                    if (nextId == 0) {
                        nextId = acornId + 1;
                        catchInRow++;
                        return;
                    } else {
                        // compare acorn id with next id
                        if (acornId == nextId) {
                            catchInRow++;
                            nextId = acornId + 1;
                        } else {
                            catchInRow = 0;
                            nextId = 0;
                        }
                    }
                }
                if(SaveValue.soundPlay){
                    SaveValue.coin.play();
                }
            }
        }

        for(DynamicGameObject dynamicGameObject : dynamicGameObjects) {
            if (dynamicGameObject instanceof BadAcorn) {
                BadAcorn badAcorn = (BadAcorn) dynamicGameObject;
                if (catchBounds.contains(badAcorn.getMiddlePosX(), badAcorn.getPosition().y)) {
                    badAcorn.setState(ObjectState.STATE_PLAYER_CATCH);
                    World.score -= 1;
                }
            }
            if (dynamicGameObject instanceof Fine) {
                Fine fine = (Fine) dynamicGameObject;
                if (catchBounds.contains(fine.getMiddlePosX(), fine.getPosition().y)) {
                    fine.setState(ObjectState.STATE_PLAYER_CATCH);
                    World.fineCount -= 1;
                    if(SaveValue.vibes){
                        Gdx.input.vibrate(1000);
                    }
                }
            }
            if (dynamicGameObject instanceof Bomb) {
                Bomb bomb = (Bomb) dynamicGameObject;
                if (catchBounds.contains(bomb.getMiddlePosX(), bomb.getPosition().y)) {
                    bomb.setState(ObjectState.STATE_PLAYER_CATCH);
                    World.bombCount -= 1;
                    if(SaveValue.vibes){
                        Gdx.input.vibrate(1000);
                    }
                }
            }
            if (dynamicGameObject instanceof Gift) {
                Gift gift = (Gift) dynamicGameObject;
                if (catchBounds.contains(gift.getMiddlePosX(), gift.getPosition().y)) {
                    gift.setState(ObjectState.STATE_PLAYER_CATCH);
                    switch (MathUtils.random(1, 3)) {
                        case 1:
                            // time
                            totalTime = 6f;
                            // start decrement time
                            start = true;
                            break;
                        case 2:
                            World.score /= 2;
                            break;
                        case 3:
                            World.score += 500;
                            if(SaveValue.soundPlay){
                                SaveValue.coin.play();
                            }
                            break;
                    }
                }
            }
            if(dynamicGameObject instanceof GoldAcorn){
                GoldAcorn goldAcorn = (GoldAcorn) dynamicGameObject;
                if(catchBounds.contains(goldAcorn.getPosition().x, goldAcorn.getPosition().y)) {
                    goldAcorn.setState(ObjectState.STATE_PLAYER_CATCH);
                    World.score += 1000;
                    if(SaveValue.soundPlay){
                        SaveValue.coin.play();
                    }
                }
            }
        }
        // decrement time for adding point during 10 sec
        decrementTime(delta);
        // position of catch rect
        catchBounds.set(position.x + 10, position.y - 50, 80, 100);
    }

    private void decrementTime(float delta){
        if(start) {
            // set score level
            world.setCurrentScoreLevel(ScoreLevel.X50);
            totalTime -= delta;
            minutes = ((int) totalTime) / 60;
            seconds = ((int) totalTime) - minutes * 60 * 10;
            System.out.println("second = " + seconds);
            if(seconds == 0.0f){
                start = false;
                world.setCurrentScoreLevel(ScoreLevel.X1);
            }
        }
    }

    private void addScore(){
        switch (world.getCurrentScoreLevel()){
            case X1:
                World.score += 1;
                break;
            case X5:
                World.score += 5;
                break;
            case X10:
                World.score += 10;
                break;
            case X15:
                World.score += 15;
                break;
            case X20:
                World.score += 20;
                break;
            case X50:
                World.score += 50;
                break;
        }
    }

    public void drawCatchBounds(ShapeRenderer sRenderer){
        sRenderer.rect(catchBounds.x, catchBounds.y, catchBounds.width, catchBounds.height);
    }

    public void move(float x){
        position.set(x, 995);
    }

    public int getCatchInRow() {
        return catchInRow;
    }

    public Rectangle getPlayerMove() {
        return playerMove;
    }

    public Rectangle getCatchBounds() {
        return catchBounds;
    }

    public int getNextId() {
        return nextId;
    }
}

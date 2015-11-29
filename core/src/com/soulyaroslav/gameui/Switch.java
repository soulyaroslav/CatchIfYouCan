package com.soulyaroslav.gameui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.soulyaroslav.gameobject.GameObject;

public class Switch extends GameObject{

    public static final int SWITCH_ON = 1;
    public static final int SWITCH_OFF = 2;
    public int state;

    private Sprite slider;

    public Switch(float x, float y, float width, float height, Sprite switchSprite) {
        super(x, y, width, height);
        slider = switchSprite;
        slider.setPosition(x, y);
        state = SWITCH_ON;
    }

    public void drawSwitch(Batch batch){
        if(state == SWITCH_ON){
            slider.setPosition(405, position.y);
            bounds.setPosition(405, position.y);
        }
        else if(state == SWITCH_OFF){
            slider.setPosition(480, position.y);
            bounds.setPosition(480, position.y);
        }
        slider.draw(batch);
    }


}

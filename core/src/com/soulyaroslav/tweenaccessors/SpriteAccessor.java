package com.soulyaroslav.tweenaccessors;

import com.badlogic.gdx.graphics.g2d.Sprite;

import aurelienribon.tweenengine.TweenAccessor;

public class SpriteAccessor implements TweenAccessor<Sprite> {

    public static final int ALPHA = 0;
    public static final int POS_Y = 1;
    public static final int SCALE = 2;
    public static final int POS_X = 3;
    public static final int ROTATION = 4;


    @Override
    public int getValues(Sprite target, int tweenType, float[] returnValues) {
        switch (tweenType) {
            case ALPHA:
                returnValues[0] = target.getColor().a;
                return 1;
            case POS_Y:
                returnValues[0] = target.getY();
                return 1;
            case POS_X:
                returnValues[0] = target.getX();
                return 1;
            case SCALE:
                returnValues[0] = target.getScaleX();
                returnValues[1] = target.getScaleY();
                return 2;
            case ROTATION:
                returnValues[0] = target.getRotation();
                return 1;
            default:
                assert false;
                return -1;
        }
    }

    @Override
    public void setValues(Sprite target, int tweenType, float[] newValues) {
        switch (tweenType) {
            case ALPHA:
                target.setColor(target.getColor().r, target.getColor().g, target.getColor().b, newValues[0]);
                break;
            case POS_Y:
                target.setY(newValues[0]);
                break;
            case POS_X:
                target.setX(newValues[0]);
                break;
            case SCALE:
                target.setScale(newValues[0], newValues[1]);
                break;
            case ROTATION:
                target.setRotation(newValues[0]);
                break;
        }
    }

}
package com.soulyaroslav.gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.soulyaroslav.gameobject.GameObject;

public class Path extends GameObject{

    public static final float PATH_WIDTH = 64;
    public static final float PATH_HEIGHT = Gdx.graphics.getHeight() / 2;

    public Path(float positionX, float positionY) {
        super(positionX, positionY, PATH_WIDTH, PATH_HEIGHT);
    }

    public void drawPath(ShapeRenderer sRenderer){
        sRenderer.begin(ShapeRenderer.ShapeType.Line);
        sRenderer.setColor(Color.BLACK);
        sRenderer.rect(getBounds().x, getBounds().y, getBounds().width, getBounds().height);
        sRenderer.end();
    }

    public boolean contains(Rectangle bounds){
        return getBounds().contains(bounds.x, bounds.y);
    }

    public float setMidSpawnPosition(float midNutPosX){
        return (getBounds().x + (PATH_WIDTH / 2)) - midNutPosX;
    }
}

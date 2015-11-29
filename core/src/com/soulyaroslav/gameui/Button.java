package com.soulyaroslav.gameui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.soulyaroslav.gameobject.GameObject;

public class Button extends GameObject{
    // texture
    private Sprite btnDown;
    //
    public boolean isPressed = false;

    public Button(float x, float y, float width, float height, TextureRegion btnDown) {
        super(x, y, width, height);
        this.btnDown = new Sprite(btnDown);

        this.btnDown.setPosition(x, y);
        this.btnDown.setSize(width, height);
        this.btnDown.setScale(1, 1);
    }

    public Button(float x, float y, float width, float height) {
        super(x, y, width, height);
    }

    public void drawButton(Batch batch){
        btnDown.draw(batch);
    }
    public void drawRotateButton(Batch batch){
        btnDown.rotate(90);
        btnDown.draw(batch);
    }
    // test
    public void drawRect(ShapeRenderer sRenderer, Color color){
        sRenderer.begin(ShapeRenderer.ShapeType.Line);
        sRenderer.setColor(color);
        sRenderer.rect(bounds.x, bounds.y, bounds.width, bounds.height);
        sRenderer.end();
    }

    public void drawRect(ShapeRenderer sRenderer, Color color, float x){
        sRenderer.begin(ShapeRenderer.ShapeType.Line);
        sRenderer.setColor(color);
        sRenderer.rect(bounds.x - x, bounds.y, bounds.width, bounds.height);
        sRenderer.end();
    }

    public boolean contains(float positionX, float positionY){
        isPressed = bounds.contains(positionX, positionY);
        return isPressed;
    }

    public void setTexture(TextureRegion textureRegion){
        //textureRegion.flip(false, true);
        btnDown.setRegion(textureRegion);
    }

    public Sprite getSpriteBtnDown(){
        return btnDown;
    }
}

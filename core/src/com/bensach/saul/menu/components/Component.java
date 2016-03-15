package com.bensach.saul.menu.components;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by saul- on 14/03/2016.
 */

public class Component {

    private int width, height, x, y;
    protected Sprite sprite;
    private ComponentType type;

    protected Component(int width, int height, int x, int y, Sprite sprite, ComponentType type) {
        this.width = width;
        this.y = y;
        this.x = x;
        this.height = height;
        this.sprite = sprite;
        this.sprite.setPosition(x,y);
        this.type = type;
        this.sprite.setSize(this.width, this.getHeight());
    }

    public int getWidth() {
        return width;
    }

    public void draw(Batch batch){
        sprite.draw(batch);
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setY(int y) {
        sprite.setY(y);
        this.y = y;
    }

    public ComponentType getType() {
        return type;
    }

    public void setX(int x) {
        sprite.setX(x);
        this.x = x;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public int getHeight() {
        return height;
    }
}

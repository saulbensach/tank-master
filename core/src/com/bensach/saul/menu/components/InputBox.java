package com.bensach.saul.menu.components;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by saul- on 14/03/2016.
 */
public class InputBox extends Component{

    private String text;

    public InputBox(int width, int height, int x, int y, Sprite sprite, ComponentType type) {
        super(width, height, x, y, sprite);
        text = "Username";
    }

    public void draw(Batch batch){
        super.draw(batch);

    }
}

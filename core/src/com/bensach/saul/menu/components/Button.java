package com.bensach.saul.menu.components;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by saul- on 14/03/2016.
 */
public class Button extends Component {

    private BitmapFont font;
    private String text;

    public Button(int width, int height, int x, int y, Sprite sprite, ComponentType type) {
        super(width, height, x, y, sprite, type);
        font = new BitmapFont();
        text = "";
        if(type.equals(ComponentType.login)){
            text = "Login";
        }else{
            text = "Register";
        }
    }

    public void draw(Batch batch){
        super.draw(batch);
        if(text.equals("Login")){
            font.draw(batch, text, super.getX() + super.getWidth() / 2 - 20, super.getY() + super.getHeight() / 1.5f);
        }else{
            font.draw(batch, text, super.getX() + super.getWidth() / 2 - 25, super.getY() + super.getHeight() / 1.5f);
        }

    }
}

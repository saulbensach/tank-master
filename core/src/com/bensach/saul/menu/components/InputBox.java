package com.bensach.saul.menu.components;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by saul- on 14/03/2016.
 */
public class InputBox extends Component{

    private String text;
    private BitmapFont font;
    private boolean focused = false;

    public InputBox(int width, int height, int x, int y, Sprite sprite, ComponentType type) {
        super(width, height, x, y, sprite, type);
        font = new BitmapFont();
        text = "";
        if(type.equals(ComponentType.password)){
            text = "Password";
        }else{
            text = "Username";
        }
    }

    public boolean isFocused() {
        return focused;
    }

    public void setFocused(boolean focused) {
        this.focused = focused;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void draw(Batch batch){
        super.draw(batch);
        font.setColor(Color.DARK_GRAY);
        if(getType().equals(ComponentType.password)){
            String hidePass = "";
            for(int i = 0; i < text.length(); i++){
                hidePass += '*';
            }
            font.draw(batch, hidePass, getX() + 10, getY() + getHeight() / 1.8f);
        }else{
            font.draw(batch, text, getX() + 10, getY() + getHeight() / 1.8f);
        }
    }
}

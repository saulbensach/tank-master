package com.bensach.saul.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bensach.saul.GameStart;
import com.bensach.saul.menu.components.Button;
import com.bensach.saul.menu.components.Component;
import com.bensach.saul.menu.components.ComponentType;
import com.bensach.saul.menu.components.InputBox;
import org.json.*;


import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by saul- on 14/03/2016.
 * Esta ventana es la primera de todas donde el usuario iniciara sesion
 */
public class LoginMenu implements Screen , InputProcessor{

    private GameStart gameStart;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private ArrayList<Component> gui;

    public LoginMenu(GameStart gameStart) {
        this.gameStart = gameStart;
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch = new SpriteBatch();
        gui = new ArrayList<Component>();
        parseJson();
        Gdx.input.setInputProcessor(this);
    }

    private void parseJson(){
        JSONObject jsonObject = new JSONObject(Gdx.files.internal("interfazGrafica/gui.json").readString());
        JSONObject mockup = jsonObject.getJSONObject("mockup");
        JSONObject controls = mockup.getJSONObject("controls");
        JSONArray control = controls.getJSONArray("control");
        for(int i = 0; i < control.length(); i++){
            JSONObject component = (JSONObject) control.get(i);
            JSONObject properties = component.getJSONObject("properties");
            ComponentType type = ComponentType.valueOf(properties.getString("customID"));
            switch (type){
                case inputText:
                    gui.add(
                            new InputBox(
                                    component.getInt("measuredW"),
                                    component.getInt("measuredH"),
                                    component.getInt("x"),
                                    component.getInt("y") - component.getInt("measuredH"),
                                    new Sprite(new Texture(properties.getString("imageSrc"))),
                                    type
                            )
                    );
                    break;
                case login:
                    gui.add(
                            new Button(
                                    component.getInt("measuredW"),
                                    component.getInt("measuredH"),
                                    component.getInt("x"),
                                    component.getInt("y") - component.getInt("measuredH"),
                                    new Sprite(new Texture(properties.getString("imageSrc"))),
                                    type
                            )
                    );
                    break;
                case register:
                    gui.add(
                            new Button(
                                    component.getInt("measuredW"),
                                    component.getInt("measuredH"),
                                    component.getInt("x"),
                                    component.getInt("y") - component.getInt("measuredH"),
                                    new Sprite(new Texture(properties.getString("imageSrc"))),
                                    type
                            )
                    );
                    break;
            }

        }

        //Ahora centramos todos los componentes al centro de la pantalla
        for(int i = 0; i < gui.size(); i++){
            gui.get(i).setX(
                    ((gui.get(i).getX() + Gdx.graphics.getWidth() / 2) - gui.get(i).getWidth() / 2) - Gdx.graphics.getWidth() / 2
            );
            gui.get(i).setY(
                    Math.abs(gui.get(i).getY() - Gdx.graphics.getHeight() / 2) - Gdx.graphics.getHeight() / 2 + gui.get(i).getHeight()
            );
        }


    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.8f,0.8f,0.8f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        for(int i = 0; i < gui.size(); i++){
            gui.get(i).draw(batch);
        }
        batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        switch (amount){
            case 1: camera.zoom += 0.09f;break;
            case -1: camera.zoom -= 0.09f;break;
        }
        return false;
    }
}

package com.bensach.saul.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.bensach.saul.GameStart;
import com.bensach.saul.menu.components.*;
import com.mysql.jdbc.PreparedStatement;
import org.json.*;


import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

/**
 * Created by saul- on 14/03/2016.
 * Esta ventana es la primera de todas donde el usuario iniciara sesion
 */
public class LoginMenu implements Screen , InputProcessor{

    private GameStart gameStart;
    private SpriteBatch batch;
    private InputBox inputBoxFocus;
    private Vector3 mousePos;
    private OrthographicCamera camera;
    private ArrayList<Component> gui;
    private boolean pressed;

    public LoginMenu(GameStart gameStart) {
        this.gameStart = gameStart;
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch = new SpriteBatch();
        gui = new ArrayList<Component>();
        mousePos = new Vector3();
        parseJson();
        Gdx.input.setInputProcessor(this);
    }

    private void parseJson(){
        JSONObject jsonObject = new JSONObject(Gdx.files.internal("interfazGrafica/loginMenu.json").readString());
        JSONObject mockup = jsonObject.getJSONObject("mockup");
        JSONObject controls = mockup.getJSONObject("controls");
        JSONArray control = controls.getJSONArray("control");
        for(int i = 0; i < control.length(); i++){
            JSONObject component = (JSONObject) control.get(i);
            JSONObject properties = component.getJSONObject("properties");
            ComponentType type = ComponentType.valueOf(properties.getString("customID"));
            switch (type){
                case username:
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
                case password:
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
                case panel:
                    gui.add(
                            new Panel(
                                    component.getInt("w"),
                                    component.getInt("h"),
                                    component.getInt("x"),
                                    component.getInt("y"),
                                    new Sprite(new Texture(properties.getString("imageSrc"))),
                                    type
                            )
                    );
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
            if(gui.get(i) instanceof Panel){
                Panel p = (Panel) gui.get(i);
                p.setY((p.getY() - Gdx.graphics.getHeight() / 2) - 85);
                p.setX(p.getX() + 15);
            }
        }

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.8f,0.8f,0.8f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if(Gdx.input.isTouched()){
            mousePos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        }
        camera.unproject(mousePos);
        camera.update();

        //Ponemos 1 para saltarnos el panel
        for(int i = 1; i < gui.size(); i++){
            if(!pressed){
                if(gui.get(i).getSprite().getBoundingRectangle().contains(mousePos.x, mousePos.y)){
                    System.out.println(gui.get(i).getType());
                    if(gui.get(i).getType().equals(ComponentType.login))login();
                    if(gui.get(i).getType().equals(ComponentType.register))register();
                    if(gui.get(i) instanceof InputBox){
                        ((InputBox) gui.get(i)).setFocused(true);
                        inputBoxFocus = (InputBox) gui.get(i);
                        inputBoxFocus.setText("");
                    }
                    pressed = true;
                }
            }
        }

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        for(int i = 0; i < gui.size(); i++){
            gui.get(i).draw(batch);
        }
        batch.end();
    }

    private void login(){
        String user = "", password = "";
        for(int i = 0; i < gui.size(); i++){
            if(gui.get(i) instanceof InputBox){
                if(((InputBox) gui.get(i)).getType().equals(ComponentType.username)){
                    user = ((InputBox) gui.get(i)).getText();
                }else{
                    password = ((InputBox) gui.get(i)).getText();
                }
            }
        }
        try {

            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/tankmaster", "root", "");
            String query = "INSERT INTO users VALUES(?,?,?)";
            PreparedStatement statement = (PreparedStatement) conn.prepareStatement(query);
            statement.setNull(1, Types.INTEGER);
            statement.setString(2, user);
            statement.setString(3, password);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void register(){
        gameStart.setScreen(gameStart.registerMenu);
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
        if(inputBoxFocus != null){
            if(inputBoxFocus.isFocused()){
                if(Gdx.input.isKeyPressed(Input.Keys.BACKSPACE)){
                    String text = "";
                    for(int i = 0; i < inputBoxFocus.getText().length() - 1; i++){
                        text += inputBoxFocus.getText().charAt(i);
                    }
                    inputBoxFocus.setText(text);
                }else if(Gdx.input.isKeyPressed(Input.Keys.ENTER)){
                    login();
                }
                else{
                    inputBoxFocus.setText(inputBoxFocus.getText() + character);
                }
            }
        }
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        pressed = false;
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

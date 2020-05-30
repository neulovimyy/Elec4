package com.tboi.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import com.tboi.game.MainGame;
import com.tboi.game.tools.CharDetails;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

public class LevelScreen implements Screen {

    MainGame game;
    AssetManager manager;
    SpriteBatch batch;

    Skin skin;
    public static TextButton level1, level2, level3, level4, level5, level6, level7, level8, level9;
    Stage stage;
    ButtonGroup bg;

    float y; Integer count;
    int level;
    public static CharDetails details;
    static FileHandle charInfo;

    public LevelScreen(MainGame game) {
        this.game = game;
        this.manager = game.getManager();
        this.batch = MainGame.batch;
        charInfo = Gdx.files.local("bin/char_info.json");
        Json json = new Json();
        details = json.fromJson(CharDetails.class, Base64Coder.decodeString(charInfo.readString()));
        Gdx.app.log(details.getCount(), "Test");
    }

    @Override
    public void show() {
        skin = manager.get("skins/neon/neon-ui.json",Skin.class);
        level = Integer.parseInt(details.getLevel());
        buttons();

        Label.LabelStyle ls = new Label.LabelStyle();
        FreeTypeFontGenerator gen = new FreeTypeFontGenerator(Gdx.files.internal("font/upheavtt.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter param = new FreeTypeFontGenerator.FreeTypeFontParameter();
        param.color = Color.WHITE; param.size = 35;
        ls.font = gen.generateFont(param);

        Label label = new Label("Select Level", ls);
        label.setBounds(MainGame.x * 0.35f, MainGame.y * 0.80f,100, 100);
        stage = new Stage();
        stage.addActor(level1); stage.addActor(level2); stage.addActor(level3);
        stage.addActor(level4); stage.addActor(level5); stage.addActor(level6);
        stage.addActor(level7); stage.addActor(level8); stage.addActor(level9);
        stage.addActor(label);
        Gdx.input.setInputProcessor(stage);
    }

    public static void buttonClickerHandler() {
        level2.setTouchable(details.getLevel2());
        level3.setTouchable(details.getLevel3());
        level4.setTouchable(details.getLevel4());
        level5.setTouchable(details.getLevel5());
        level6.setTouchable(details.getLevel6());
        level7.setTouchable(details.getLevel7());
        level8.setTouchable(details.getLevel8());
        level9.setTouchable(details.getLevel9());
    }

    public static void configureButtonBasedOnLevel(){
        int num = LoadingScreenToGame.num;
        switch(num){
            case 1:
                details.setLevel2(Touchable.enabled);
                details.setLevel((num+1)+"");
                write();
                break;
            case 2:
                details.setLevel3(Touchable.enabled);
                details.setLevel((num+1)+"");
                write();
                break;
        }
    }

    private static void write() {
        Json json = new Json();
        json.setOutputType(JsonWriter.OutputType.json);
        charInfo.writeString(Base64Coder.encodeString(json.toJson(details)),false);
    }

    private void buttons() {
        level1 = new TextButton("1", skin, "default");
        level2 = new TextButton("2", skin, "default");
        level3 = new TextButton("3", skin, "default");
        level4= new TextButton("4", skin, "default");
        level5 = new TextButton("5", skin, "default");
        level6 = new TextButton("6", skin, "default");
        level7 = new TextButton("7", skin, "default");
        level8 = new TextButton("8", skin, "default");
        level9 = new TextButton("9", skin, "default");

        level1.setSize(100,100); level2.setSize(100,100); level3.setSize(100,100);
        level4.setSize(100,100); level5.setSize(100,100); level6.setSize(100,100);
        level7.setSize(100,100); level8.setSize(100,100); level9.setSize(100,100);

        level1.setPosition(MainGame.x * 0.30f, MainGame.y * 0.55f);
        level2.setPosition(MainGame.x * 0.45f, MainGame.y * 0.55f);
        level3.setPosition(MainGame.x * 0.6f, MainGame.y * 0.55f);
        level4.setPosition(MainGame.x * 0.30f, MainGame.y * 0.35f);
        level5.setPosition(MainGame.x * 0.45f, MainGame.y * 0.35f);
        level6.setPosition(MainGame.x * 0.6f, MainGame.y * 0.35f);
        level7.setPosition(MainGame.x * 0.30f, MainGame.y * 0.15f);
        level8.setPosition(MainGame.x * 0.45f, MainGame.y * 0.15f);
        level9.setPosition(MainGame.x * 0.6f, MainGame.y * 0.15f);

        level1.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                stage.addAction(sequence(fadeOut(.5f), run(new Runnable() {

                    @Override
                    public void run() {
                        ((Game)Gdx.app.getApplicationListener()).setScreen(new LoadingScreenToGame(game, level));
                    }
                })));
            }
        });
        level2.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                stage.addAction(sequence(fadeOut(.5f), run(new Runnable() {

                    @Override
                    public void run() {
                        ((Game)Gdx.app.getApplicationListener()).setScreen(new LoadingScreenToGame(game, level));
                    }
                })));
            }
        });
        level3.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                stage.addAction(sequence(fadeOut(.5f), run(new Runnable() {

                    @Override
                    public void run() {
                        ((Game)Gdx.app.getApplicationListener()).setScreen(new LoadingScreenToGame(game, level));
                    }
                })));
                ((Game)Gdx.app.getApplicationListener()).setScreen(new LoadingScreenToGame(game, level));
            }
        });
        level4.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                stage.addAction(sequence(fadeOut(.5f), run(new Runnable() {

                    @Override
                    public void run() {
                        ((Game)Gdx.app.getApplicationListener()).setScreen(new LoadingScreenToGame(game, level));
                    }
                })));
            }
        });
        level5.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                stage.addAction(sequence(fadeOut(.5f), run(new Runnable() {

                    @Override
                    public void run() {
                        ((Game)Gdx.app.getApplicationListener()).setScreen(new LoadingScreenToGame(game, level));
                    }
                })));
            }
        });
        level6.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                stage.addAction(sequence(fadeOut(.5f), run(new Runnable() {

                    @Override
                    public void run() {
                        ((Game)Gdx.app.getApplicationListener()).setScreen(new LoadingScreenToGame(game, level));
                    }
                })));
            }
        });
        level7.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                stage.addAction(sequence(fadeOut(.5f), run(new Runnable() {

                    @Override
                    public void run() {
                        ((Game)Gdx.app.getApplicationListener()).setScreen(new LoadingScreenToGame(game, level));
                    }
                })));
            }
        });
        level8.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                stage.addAction(sequence(fadeOut(.5f), run(new Runnable() {

                    @Override
                    public void run() {
                        ((Game)Gdx.app.getApplicationListener()).setScreen(new LoadingScreenToGame(game, level));
                    }
                })));
            }
        });
        level9.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                stage.addAction(sequence(fadeOut(.5f), run(new Runnable() {

                    @Override
                    public void run() {
                        ((Game)Gdx.app.getApplicationListener()).setScreen(new LoadingScreenToGame(game, level));
                    }
                })));
            }
        });

        buttonClickerHandler();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
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
}

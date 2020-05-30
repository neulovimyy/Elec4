package com.tboi.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.tboi.game.MainGame;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

public class TitleScreen extends Actor implements Screen {

    MainGame game;
    AssetManager manager;
    OrthographicCamera camera;

    Stage stage;
    Skin skin;
    SpriteBatch batch;

    Image bg;

    TextButton play, exit;

    public TitleScreen(MainGame game) {
        this.game = game;
        this.camera = game.getCamera();
        this.manager = game.getManager();
        batch = MainGame.batch;
        //reverse(new Integer[]{1,2,3,4,5,6,7});
    }

//    public Integer[] reverse(Integer[] integers){
//        Integer[] reversed = new Integer[integers.length];
//        for(int i=integers.length - 1;i>-1;i--){
//            int j = 0;
//            reversed[j] = integers[i];
//            System.out.print(reversed[j] + " ");
//            j++;
//        }
//        return reversed;
//    }



    @Override
    public void show() {
        bg = new Image();
        bg.setDrawable(new SpriteDrawable(new Sprite(new Texture("resources/sprites/bd2.jpg"))));
        bg.setBounds(0, 0, MainGame.x, MainGame.y);
        skin = manager.get("skins/neon/neon-ui.json",Skin.class);
        stage = new Stage();

        Label.LabelStyle ls = new Label.LabelStyle();
        FreeTypeFontGenerator gen = new FreeTypeFontGenerator(Gdx.files.internal("font/upheavtt.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter param = new FreeTypeFontGenerator.FreeTypeFontParameter();
        param.color = Color.BLACK; param.size = 40;
        ls.font = gen.generateFont(param);

        play = new TextButton("Play", skin, "default");
        play.setBounds(MainGame.x * 0.40f, MainGame.y * 0.5f, MainGame.x * 0.2f, MainGame.y * 0.15f);
        play.addListener(new ClickListener(){

            @Override
            public void clicked(InputEvent event, float x, float y) {
                stage.addAction(sequence(fadeOut(.5f), run(new Runnable() {

                    @Override
                    public void run() {
                        ((Game) Gdx.app.getApplicationListener()).setScreen(new LevelScreen(game));
                    }
                })));
            }
        });

        exit = new TextButton("Exit", skin, "default");
        exit.setBounds(MainGame.x * .40f, MainGame.y * 0.30f, MainGame.x * 0.2f, MainGame.y * 0.15f);
        exit.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                ((Game)Gdx.app.getApplicationListener()).setScreen(new ConfirmExit(game));
            }
        });

        Label label = new Label("The Adventures of Me", ls);
        label.setBounds(MainGame.x * 0.33f, MainGame.y * 0.7f, MainGame.x *0.7f, MainGame.y * 0.1f);

        stage.addActor(bg);
        stage.addActor(label);
        stage.addActor(play);
        stage.addActor(exit);
        stage.addActor(this);

        stage.addAction(sequence(fadeOut(0f),fadeIn(3f)));
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        game.clearScreen();

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
        stage.dispose();
        manager.dispose();
        batch.dispose();
    }
}

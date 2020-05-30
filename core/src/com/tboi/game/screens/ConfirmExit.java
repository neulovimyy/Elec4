package com.tboi.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.tboi.game.MainGame;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

public class ConfirmExit implements Screen {

    MainGame game;
    AssetManager manager;
    Skin skin;

    Stage stage;

    public ConfirmExit(MainGame game) {
        this.game = game;
        this.manager = game.getManager();
        skin = manager.get("skins/neon/neon-ui.json",Skin.class);
        stage = new Stage();
    }

    @Override
    public void show() {
        final Window window = new Window("", skin);
        TextButton con = new TextButton("No", skin, "default");
        TextButton exit = new TextButton("Yes", skin, "default");
        Label label = new Label("Are you sure you want to quit?", skin, "default");
        con.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game)Gdx.app.getApplicationListener()).setScreen(new TitleScreen(game));
            }
        });
        exit.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
        window.setBackground(new SpriteDrawable(new Sprite(new Texture("resources/sprites/bbg.jpg"))));
        window.setBounds(0,0, game.x, game.y);
        window.add(label).center().padBottom(100).row();
        window.add(exit).width(game.x*0.3f).height(game.y*0.1f).padBottom(100).row();
        window.add(con).width(game.x*0.3f).height(game.y*0.1f);
        window.setMovable(false);
        window.setModal(true);
        window.setVisible(true);
        stage.addActor(window);
        stage.addAction(sequence(moveTo(0, stage.getHeight()), moveTo(0, 0, 0.3f)));
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

    }
}

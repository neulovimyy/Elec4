package com.tboi.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.tboi.game.MainGame;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;

public class LoadingScreen implements Screen {

    ProgressBar pb; Label label;
    Skin skin;
    Stage stage;
    OrthographicCamera camera;

    MainGame game;
    AssetManager manager;
    float sec;

    float statetime;
    public LoadingScreen(MainGame game){
        this.game = game;
        this.manager = game.getManager();
        this.camera = game.getCamera();
        sec = 0;
    }

    @Override
    public void show() {

        stage = new Stage();
        skin = manager.get("skins/neon/neon-ui.json",Skin.class);
        pb = new ProgressBar(0f, 10f, 0.01f, false, skin,"big");
        pb.setBounds(MainGame.x * 0.2f, MainGame.y * 0.2f, MainGame.x * 0.6f, MainGame.y * 0.1f);
        pb.setColor(Color.FIREBRICK);
        pb.setValue(0);
        stage.addActor(pb);

        Label.LabelStyle ls = new Label.LabelStyle();
        FreeTypeFontGenerator gen = new FreeTypeFontGenerator(Gdx.files.internal("font/upheavtt.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter param = new FreeTypeFontGenerator.FreeTypeFontParameter();
        param.color = Color.WHITE; param.size = 35;
        ls.font = gen.generateFont(param);

        label = new Label("Loading Assets", ls);
        label.setColor(Color.WHITE);
        label.setBounds(MainGame.x * 0.45f, MainGame.y * 0.1f, MainGame.x * 0.8f, MainGame.y * 0.1f);
        stage.addActor(label);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        statetime += delta;
        if(statetime>= 1){
            sec++;
        }
        if(pb.getValue() < pb.getMaxValue()){
            pb.setValue(pb.getValue() + 0.5f);
        }
        if(pb.getValue() == pb.getMaxValue() && manager.isFinished()) {
            if(sec == 3) {
                stage.addAction(fadeOut(1.5f));
                ((Game) Gdx.app.getApplicationListener()).setScreen(new TitleScreen(game));
            }
        }
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

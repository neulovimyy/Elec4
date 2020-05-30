package com.tboi.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.tboi.game.MainGame;

public class LoadingScreenToGame implements Screen {

    public static int num;
    MainGame game;
    Stage stage;
    ProgressBar pb;
    Skin skin; Label label;

    AssetManager manager;

    float sec, statetime;
    public LoadingScreenToGame(MainGame game, int num){
        this.game = game;
        LoadingScreenToGame.num = num;
        this.manager = game.getManager();
        sec = 0;
    }

    @Override
    public void show() {
        stage = new Stage();
        skin = manager.get("skins/neon/neon-ui.json", Skin.class);
        pb = new ProgressBar(0f, 10f, 0.01f, false, skin,"big");
        pb.setBounds(MainGame.x * 0.2f, MainGame.y * 0.2f, MainGame.x * 0.6f, MainGame.y * 0.1f);
        pb.setValue(0);
        stage.addActor(pb);

        label = new Label("Loading", skin, "default");
        label.setBounds(MainGame.x * 0.45f, MainGame.y * 0.1f, MainGame.x * 0.8f, MainGame.y * 0.1f);
        stage.addActor(label);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        statetime += delta;
        if(statetime >= 1){
            sec++;
        }

        if(pb.getValue() == pb.getMaxValue() && manager.isFinished()){
            if(sec == 3){
                switch(num) {
                    case 1:
                        ((Game) Gdx.app.getApplicationListener()).setScreen(new GameScreen(game, 1));
                        Gdx.app.log("Level", ""+num);
                        break;
                    case 2:
                        ((Game) Gdx.app.getApplicationListener()).setScreen(new GameScreen(game, 2));
                        Gdx.app.log("Level", ""+num);
                        break;
                    case 3:
                        ((Game) Gdx.app.getApplicationListener()).setScreen(new GameScreen(game, 3));
                        Gdx.app.log("Level", ""+num);
                        break;
                    case 4:
                        ((Game) Gdx.app.getApplicationListener()).setScreen(new GameScreen(game, 4));
                        Gdx.app.log("Level", ""+num);
                        break;
                    case 5:
                        ((Game) Gdx.app.getApplicationListener()).setScreen(new GameScreen(game, 5));
                        Gdx.app.log("Level", ""+num);
                        break;
                    case 6:
                        ((Game) Gdx.app.getApplicationListener()).setScreen(new GameScreen(game, 6));
                        Gdx.app.log("Level", ""+num);
                        break;
                    case 7:
                        ((Game) Gdx.app.getApplicationListener()).setScreen(new GameScreen(game, 7));
                        Gdx.app.log("Level", ""+num);
                        break;
                    case 8:
                        ((Game) Gdx.app.getApplicationListener()).setScreen(new GameScreen(game, 8));
                        Gdx.app.log("Level", ""+num);
                        break;
                    case 9:
                        ((Game) Gdx.app.getApplicationListener()).setScreen(new GameScreen(game, 9));
                        Gdx.app.log("Level", ""+num);
                        break;
                    }
            }
        }

        if(pb.getValue() < pb.getMaxValue()){
            pb.setValue(pb.getValue() + 0.5f);
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

package com.tboi.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import com.tboi.game.MainGame;
import com.tboi.game.screens.LoadingScreenToGame;
import com.tboi.game.tools.CharDetails;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

public class HUD{
    private static boolean win;
    private static CharDetails details;
    SpriteBatch batch;

    public static Integer coins;

    Window pauseWindow;
    TextButton pause;
    AssetManager manager; static Label worldTime;
    Skin skin;
    public Stage stage; public static int level;
    public static boolean levelComplete;

    public static boolean paused, isDead;

    public static FileHandle file;

    public HUD(AssetManager manager, SpriteBatch batch) {
        this.batch = batch;
        this.manager = manager;
        paused = false;
        isDead = false;
        level = LoadingScreenToGame.num;
        file = Gdx.files.local("bin/char_info.json");
        details = new CharDetails();
        Json json = new Json();
        details = json.fromJson(CharDetails.class, Base64Coder.decodeString(file.readString()));
        coins = Integer.parseInt(details.getScore());
        win = false;
        levelComplete = false;

        Label.LabelStyle ls = new Label.LabelStyle();
        FreeTypeFontGenerator gen = new FreeTypeFontGenerator(Gdx.files.internal("font/upheavtt.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter param = new FreeTypeFontGenerator.FreeTypeFontParameter();
        param.color = Color.SALMON; param.size = 35;
        ls.font = gen.generateFont(param);

        stage = new Stage();
        skin = manager.get("skins/neon/neon-ui.json", Skin.class);
        Table timeHolder = new Table(skin);
        timeHolder.setFillParent(true);
        timeHolder.center().top();
        worldTime = new Label(String.format("%03d", coins), ls);
        timeHolder.add(worldTime).pad(10);
        stage.addActor(timeHolder);

        final Window window = new Window("",skin);
        Label text = new Label("Paused", skin, "default");
        TextButton bcon = new TextButton("Return", skin);
        bcon.setSize(MainGame.x * 0.1f, MainGame.y * 0.1f);
        bcon.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                paused = false;
                window.setVisible(false);
            }
        });
        window.setBounds(0, 0, MainGame.x, MainGame.y);
        window.setModal(true);
        window.setMovable(false);
        window.setClip(true);
        window.add(text).center().row();
        window.add(bcon).width(MainGame.x*0.3f).height(MainGame.y*0.1f).center().padTop(MainGame.y*0.5f);
        window.setVisible(false);

        pause = new TextButton("II", skin, "default");
        pause.setBounds(MainGame.x * 0.5f, MainGame.y * 0.7f, MainGame.x * 0.1f, MainGame.x * 0.1f);
        pause.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                paused = true;
                window.setVisible(true);
            }
        });

        stage.addActor(pause);
        stage.addActor(window);
        Gdx.input.setInputProcessor(stage);
    }

    public static void addCoins(){
        coins++;
        if(coins > 999){
            worldTime.setText("999");
        } else {
            if(coins >= 0 && coins < 10){
                worldTime.setText("00"+coins);
            }
            if(coins >= 10 && coins <= 99) {
                worldTime.setText("0"+coins);
            }
            if(coins >= 100 && coins < 1000) {
                worldTime.setText(""+coins);
            }
        }
        details.setScore(""+coins);
    }

    public static void saveEntities() {  //alternative to JSON.serializable save method for modifications
        Json json = new Json();
        levelComplete = true;
        unlockLevel(Integer.parseInt(details.getLevel()));
        json.setOutputType(JsonWriter.OutputType.json);
        file.writeString(Base64Coder.encodeString(json.toJson(details)),false);
        Gdx.app.log("JSON", "Done saving");
    }

    private static void unlockLevel(int num) {
        int level = num;
        switch (level){
            case 1:
                details.setLevel2(Touchable.enabled);
                details.setLevel(2+"");
                break;
            case 2:
                details.setLevel3(Touchable.enabled);
                details.setLevel(3+"");
                break;
            case 3:
                details.setLevel4(Touchable.enabled);
                details.setLevel(4+"");
                break;
            case 4:
                details.setLevel5(Touchable.enabled);
                details.setLevel(5+"");
                break;
            case 5:
                details.setLevel6(Touchable.enabled);
                details.setLevel(6+"");
                break;
            case 6:
                details.setLevel7(Touchable.enabled);
                details.setLevel(7+"");
                break;
            case 7:
                details.setLevel8(Touchable.enabled);
                details.setLevel(8+"");
                break;
            case 8:
                details.setLevel9(Touchable.enabled);
                details.setLevel(9+"");
                break;
            case 9:
            default:
                break;
        }
    }

    public void update(float delta){
        isPaused();
        //Gdx.app.log("Paused", ""+isPaused());
    }

    public static boolean isPaused() {
        return paused;
    }
}

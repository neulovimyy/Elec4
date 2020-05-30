package com.tboi.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import com.tboi.game.entities.sprites.MainChar;
import com.tboi.game.screens.GameScreen;
import com.tboi.game.screens.LevelScreen;
import com.tboi.game.screens.LoadingScreen;
import com.tboi.game.screens.TitleScreen;
import com.tboi.game.tools.CharDetails;
import com.tboi.game.ui.HUD;

public class MainGame extends Game{

	public static SpriteBatch batch;

    public OrthographicCamera camera;
	AssetManager manager;
	ProgressBar pb;
	FileHandle file;
    CharDetails details;
    Integer count;
	public static final float PPM = 100;
	public static final short MC_BIT = 1,
                       OBJECT_BIT = 2,
                       ENEMY_BIT = 4,
                       GROUND_BIT = 8,
					   ITEM_BIT = 16,
					   NOTHING_BIT = 32,
					   ENTITY_BIT = 64,
				       DOOR_BIT = 128;

	public static float x;
	public static float y;

    @Override
	public void create () {
		x = Gdx.graphics.getWidth();
		y = Gdx.graphics.getHeight();
		manager = new AssetManager();
		manager.load("skins/neon/neon-ui.atlas",TextureAtlas.class);
		manager.load("resources/sprites/knight/knight.atlas", TextureAtlas.class);
        manager.load("resources/sprites/entities/coin/coin.atlas", TextureAtlas.class);
        manager.load("resources/sprites/enemies/enemies.atlas", TextureAtlas.class);
		manager.load("skins/neon/neon-ui.json",Skin.class);

		manager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
		manager.load("resources/maps/map/level1.tmx", TiledMap.class);
		manager.load("resources/maps/map/level2.tmx", TiledMap.class);
		manager.load("resources/maps/map/level3.tmx", TiledMap.class);
		manager.load("resources/maps/map/level4.tmx", TiledMap.class);
		manager.load("resources/maps/map/level5.tmx", TiledMap.class);
		manager.load("resources/maps/map/level6.tmx", TiledMap.class);
		manager.load("resources/maps/map/level7.tmx", TiledMap.class);
		manager.load("resources/maps/map/level8.tmx", TiledMap.class);
		manager.load("resources/maps/map/level9.tmx", TiledMap.class);

		manager.finishLoading();
		camera = new OrthographicCamera();
		batch = new SpriteBatch();

		setFileInJSON();

        if(manager.isFinished()) {
            Gdx.app.log("Manager","finished loading");
            setScreen(new TitleScreen(this));
        }
	}

	private void setFileInJSON() {
		/**
		 * this method makes the JSON file for the progress of the player, its levels unlocked, and so on.
		 */
		file = Gdx.files.local("bin/char_info.json");
		if(file.exists()){							    //if exists, use the progress saved to file in the game

		} else {										//if deleted
			file.writeString("1", true); //to write a json file blank
			createJsonFile();
		}
    }

	private void createJsonFile() {
		Json json = new Json();
		details = new CharDetails();
		details.setCount(0+"");
		count = Integer.parseInt(details.getCount());
		if(count == 0){
			/**
			 * to test the user's occurrence in executing the game. for the first time, it is set to zero
		 	 * the next time the user execute the program, this method can't be executed anymore unless the file's deleted
			 */
			details.setLevel2(Touchable.disabled);
			details.setLevel3(Touchable.disabled);
			details.setLevel4(Touchable.disabled);
			details.setLevel5(Touchable.disabled);
			details.setLevel6(Touchable.disabled);
			details.setLevel7(Touchable.disabled);
			details.setLevel8(Touchable.disabled);
			details.setLevel9(Touchable.disabled);
			details.setScore(""+0);
			details.setLevel(""+1);
			count++;
			details.setCount(count+""); //increment to prevent the progress to reset to default
			json.setOutputType(JsonWriter.OutputType.json);
			file.writeString(Base64Coder.encodeString(json.toJson(details)),false);
		} else {
			Gdx.app.log("Count", ""+details.getCount());
		}
    }

	@Override
	public void render() {
		super.render();
	}
	
	@Override
	public void dispose () {
		super.dispose();
		batch.dispose();
		manager.dispose();
	}

	public void controls(MainChar mc){
    	if(HUD.levelComplete != true && HUD.isPaused() != true && HUD.isDead != true){
            if(Gdx.input.isKeyPressed(Input.Keys.W) && mc.body.getLinearVelocity().y == 0){ //&& mc.body.getLinearVelocity().y == 0) {
                MainChar.body.applyLinearImpulse(new Vector2(0, 500 / PPM - MainChar.body.getMass()/PPM), MainChar.body.getWorldCenter(),true);
            }
            if(Gdx.input.isKeyPressed(Input.Keys.A)) {
                MainChar.body.applyLinearImpulse(new Vector2(-10 / PPM - MainChar.body.getMass()/PPM,0 ), MainChar.body.getWorldCenter(),true);
            }
            if(Gdx.input.isKeyPressed(Input.Keys.D)) {
                MainChar.body.applyLinearImpulse(new Vector2(10 / PPM - MainChar.body.getMass()/PPM,0 ), MainChar.body.getWorldCenter(),true);
            }
            if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
                setScreen(new LevelScreen(this));
            }
            if(Gdx.input.isKeyPressed(Input.Keys.NUM_1)){
                Gdx.app.exit();
            }
        } else {
			if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
				setScreen(new LevelScreen(this));
			}
        }
    }

    public static void clearScreen(){
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

	public AssetManager getManager(){
		return manager;
	}

    public OrthographicCamera getCamera() {
        return camera;
    }
}

package com.tboi.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.tboi.game.MainGame;
import com.tboi.game.entities.items.Chest;
import com.tboi.game.entities.items.Coin;
import com.tboi.game.entities.items.Item;
import com.tboi.game.entities.items.ItemDef;
import com.tboi.game.entities.sprites.MainChar;
import com.tboi.game.entities.sprites.enemies.Enemy;
import com.tboi.game.tools.BodyContact;
import com.tboi.game.tools.WorldCreator;
import com.tboi.game.ui.HUD;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

import static com.tboi.game.MainGame.PPM;

public class GameScreen implements Screen {

    MainGame game;
    AssetManager manager;
    SpriteBatch batch;
    FitViewport vp;
    OrthographicCamera camera; OrthogonalTiledMapRenderer renderer;

    WorldCreator creator;

    World world;
    Box2DDebugRenderer b2dr;
    //Stage stage;
    HUD hud; TiledMap tm;

    MainChar mc;

    Array<Item> items;
    int level;

    private LinkedBlockingQueue<ItemDef> itemsToSpawn;
    TextureAtlas knight, coin, enemySprite;
    public GameScreen(){

    }

    public GameScreen(MainGame game){
        initConstructor(game);
    }

    public GameScreen(MainGame game, int level){
        initConstructor(game);
        this.level = level;
        Chest.level = level;
    }

    public void initConstructor(MainGame game){
        this.game = game;
        this.batch = MainGame.batch;
        this.manager = game.getManager();
        camera = new OrthographicCamera();

        enemySprite = manager.get("resources/sprites/enemies/enemies.atlas", TextureAtlas.class);
        knight = manager.get("resources/sprites/knight/knight.atlas", TextureAtlas.class);
        coin = manager.get("resources/sprites/entities/coin/coin.atlas", TextureAtlas.class);
        vp = new FitViewport(230/PPM , 140/PPM, camera);

        b2dr = new Box2DDebugRenderer();
        renderer = new OrthogonalTiledMapRenderer(null, 1/PPM);
        world = new World(new Vector2(0,-981f / PPM), true);

        camera.position.set(vp.getWorldWidth() / 2, vp.getWorldHeight() / 2, 0);
        mc = new MainChar(this);

        hud = new HUD(manager, batch);
        world.setContactListener(new BodyContact(this));
        items = new Array<Item>();
        itemsToSpawn = new LinkedBlockingQueue<ItemDef>();
    }

    public void spawnItem(ItemDef idef){
        itemsToSpawn.add(idef);
    }

    public void handleSpawningItems(){
        if(!itemsToSpawn.isEmpty()){
            ItemDef idef = itemsToSpawn.poll();
            if(idef.type == Coin.class){
                items.add(new Coin(this, idef.position.x, idef.position.y));
            }
        }
    }

    @Override
    public void show() {
        setMap(level);
        renderer.setMap(tm);
        creator = new WorldCreator(this);
    }

    public void update(float delta) {
        hud.update(delta);

        game.controls(mc);

        handleSpawningItems();
        world.step(1/60f, 6, 2);

        mc.update(delta);
        if(HUD.isPaused()){
            mc.body.setActive(false);
        } else {
            mc.body.setActive(true);
        }

        for(Item item : items){
            item.update(delta);
        }

        for(Enemy enemies: creator.getEnemies()){
            enemies.update(delta);
        }

        camera.position.set(MainChar.body.getPosition().x, 48/PPM, 0);
        camera.update();
        renderer.setView(camera);
    }

    @Override
    public void render(float delta) {
        update(delta);

        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();
        b2dr.render(world, camera.combined);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        mc.draw(batch);

        for (Item item : items){
            item.draw(batch);
        }

        for(Enemy enemy: creator.getEnemies()){
            enemy.draw(batch);
        }

        batch.end();

        batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        vp.update(width, height);
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
        renderer.dispose();
        manager.dispose();
        b2dr.dispose();
        world.dispose();
    }

    public World getWorld() {
        return world;
    }

    public TiledMap getMap(){
        return tm;
    }

    public TextureAtlas getKnight(){
        return knight;
    }
    public TextureAtlas getEnemySprite(){
        return enemySprite;
    }
    public TextureAtlas getCoin(){
        return coin;
    }
    public MainChar getMc() {
        return mc;
    }


    private void setMap(int level) {
        switch (level){
            case 1:
                tm = manager.get("resources/maps/map/level1.tmx", TiledMap.class);
                break;
            case 2:
                tm = manager.get("resources/maps/map/level2.tmx", TiledMap.class);
                break;
            case 3:
                tm = manager.get("resources/maps/map/level3.tmx", TiledMap.class);
                break;
            case 4:
                tm = manager.get("resources/maps/map/level4.tmx", TiledMap.class);
                break;
            case 5:
                tm = manager.get("resources/maps/map/level5.tmx", TiledMap.class);
                break;
            case 6:
                tm = manager.get("resources/maps/map/level6.tmx", TiledMap.class);
                break;
            case 7:
                tm = manager.get("resources/maps/map/level7.tmx", TiledMap.class);
                break;
            case 8:
                tm = manager.get("resources/maps/map/level8.tmx", TiledMap.class);
                break;
            case 9:
                tm = manager.get("resources/maps/map/level9.tmx", TiledMap.class);
                break;
        }
    }
}

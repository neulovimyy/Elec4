package com.tboi.game.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.tboi.game.MainGame;
import com.tboi.game.entities.items.Chest;
import com.tboi.game.entities.items.Coin;
import com.tboi.game.entities.items.Door;
import com.tboi.game.entities.sprites.MainChar;
import com.tboi.game.entities.sprites.enemies.Demon;
import com.tboi.game.entities.sprites.enemies.Enemy;
import com.tboi.game.screens.GameScreen;

import static com.tboi.game.MainGame.PPM;

public class WorldCreator {
    GameScreen screen;

    World world;
    TiledMap map;
    Body body;

    TiledMapTileLayer.Cell cell;
    TiledMapTileLayer layer;
    BodyDef bdef = new BodyDef();
    FixtureDef fdef = new FixtureDef();
    PolygonShape shape = new PolygonShape();
    //MainChar mc;
    Array<Demon> demons;
    Array<Enemy> enemies;
    public WorldCreator(GameScreen screen) {
        this.screen = screen;
        this.world = screen.getWorld();
        this.map = screen.getMap();
        layer = (TiledMapTileLayer) map.getLayers().get("coins");
        defineWorldCollision();
    }

    private void defineWorldCollision() {


        for(MapObject object: map.getLayers().get("ground").getObjects().getByType(RectangleMapObject.class)){ //ground

            Rectangle rect = ((RectangleMapObject)object).getRectangle();

            bdef.position.set((rect.getX() + rect.getWidth() / 2) /PPM, (rect.getY() + rect.getHeight() / 2) /PPM);
            bdef.type = BodyDef.BodyType.StaticBody;
            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 /PPM, rect.getHeight() / 2 /PPM);
            fdef.shape = shape;

            fdef.filter.categoryBits = MainGame.GROUND_BIT;
            fdef.filter.maskBits = MainGame.MC_BIT | MainGame.ENEMY_BIT | MainGame.ENTITY_BIT;

            body.createFixture(fdef).setUserData(this);
        }

        for(MapObject object: map.getLayers().get("exitdoor").getObjects().getByType(RectangleMapObject.class)){ //exitdoor
            Rectangle rect = ((RectangleMapObject)object).getRectangle();
            new Door(screen, rect);
        }

        for(MapObject object: map.getLayers().get("objects").getObjects().getByType(RectangleMapObject.class)) { //chest
            new Chest(screen, object);
        }
        demons = new Array<Demon>();
        for(MapObject object: map.getLayers().get(7).getObjects().getByType(RectangleMapObject.class)){ //enemies
            Rectangle rec = ((RectangleMapObject)object).getRectangle();
            demons.add(new Demon(screen, rec.getX()/PPM, rec.getY()/PPM));
        }
    }
    public Array<Enemy> getEnemies(){
        Array<Enemy> enemies = new Array<Enemy>();
        enemies.addAll(demons);
        return enemies;
    }

}

package com.tboi.game.entities.items;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.tboi.game.MainGame;
import com.tboi.game.entities.sprites.MainChar;
import com.tboi.game.screens.GameScreen;

import static com.tboi.game.MainGame.PPM;

public abstract class InteractiveObject {

    protected World world;
    protected GameScreen screen;
    public Body body;
    public Vector2 vec;
    TiledMap map;
    Fixture fixture;
    MapObject object;
    Rectangle bounds;

    public InteractiveObject (GameScreen screen, MapObject object) {
        this.screen = screen;
        this.world = screen.getWorld();
        this.map = screen.getMap();
        this.object = object;
        this.bounds = ((RectangleMapObject)object).getRectangle();

        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set((bounds.getX() + bounds.getWidth() / 2) /PPM, (bounds.getY() + bounds.getHeight() / 2) /PPM);

        body = world.createBody(bdef);

        shape.setAsBox(bounds.getWidth() / 2 /PPM, bounds.getHeight() / 2 / PPM);
        fdef.shape = shape;
        fixture = body.createFixture(fdef);

    }

    public abstract void destroy();

    public void setFilter(short bit){
        Filter filter = new Filter();
        filter.categoryBits = bit;
        fixture.setFilterData(filter);
    }

    public TiledMapTileLayer.Cell getCell(){
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get("items");
        return layer.getCell((int)(body.getPosition().x * MainGame.PPM / 16),
                (int)(body.getPosition().y * MainGame.PPM / 16));
    }
}


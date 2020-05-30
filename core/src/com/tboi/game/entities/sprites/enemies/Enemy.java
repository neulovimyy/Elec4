package com.tboi.game.entities.sprites.enemies;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.tboi.game.screens.GameScreen;

import static com.tboi.game.MainGame.PPM;

public abstract class Enemy extends Sprite {

    protected World world;
    protected GameScreen screen;
    public Body body;
    public Vector2 vec;
    public BodyDef bdef;
    public FixtureDef fdef;
    public PolygonShape shape;
    Rectangle bounds;
    public boolean setToDestroy, destroyed;

    public Enemy (GameScreen screen, float x, float y) {
        this.screen = screen;
        this.world = screen.getWorld();
        setPosition(x,y);
        destroyed = false;
        setToDestroy = false;
        defineEnemy();
    }

    protected abstract void defineEnemy();
    protected abstract void reduceHealth();
    protected abstract void destroy();
    public abstract void update(float delta);
}

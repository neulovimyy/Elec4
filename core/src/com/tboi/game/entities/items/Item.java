package com.tboi.game.entities.items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.tboi.game.MainGame;
import com.tboi.game.entities.sprites.MainChar;
import com.tboi.game.screens.GameScreen;
import com.tboi.game.ui.HUD;

public abstract class Item extends Sprite {

    GameScreen screen;
    World world; boolean toDestroy, destroyed;
    Body body;

    public Item(GameScreen screen, float x, float y){
        this.screen = screen;
        this.world = screen.getWorld();
        toDestroy = false;
        destroyed = false;
        setPosition(x, y);
        setBounds(getX(), getY(), 16 / MainGame.PPM, 16 / MainGame.PPM);
    }

    public void update(float delta){
        if(toDestroy && !destroyed){
            world.destroyBody(body);
            destroyed = true;
        }
    }

    public void draw(Batch batch){
        if(!destroyed)
            super.draw(batch);
    }

    public void destroy(){
        toDestroy = true;
    }

    public abstract void defineItem();

    public void use(MainChar mc){
        destroy();
    }
}

package com.tboi.game.entities.items;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.tboi.game.MainGame;
import com.tboi.game.screens.GameScreen;
import com.tboi.game.tools.CharDetails;
import com.tboi.game.ui.HUD;

import static com.tboi.game.MainGame.PPM;

public class Door {

    GameScreen screen;
    float x, y;
    Body body; Rectangle rect;
    World world;
    public Door(GameScreen screen, Rectangle rect){
        this.screen = screen;
        this.rect = rect;
        this.world = screen.getWorld();
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();

        bdef.position.set((rect.getX() + rect.getWidth() / 2) /PPM, (rect.getY() + rect.getHeight() / 2) /PPM);
        bdef.type = BodyDef.BodyType.StaticBody;
        body = world.createBody(bdef);

        shape.setAsBox(rect.getWidth() / 2 /PPM, rect.getHeight() / 2 /PPM);
        fdef.shape = shape;

        fdef.filter.categoryBits = MainGame.DOOR_BIT;
        fdef.filter.maskBits = MainGame.MC_BIT;

        body.createFixture(fdef).setUserData(this);
    }

    public void win(){
        HUD.saveEntities();
    }
}

package com.tboi.game.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.tboi.game.MainGame;
import com.tboi.game.entities.items.Coin;
import com.tboi.game.entities.items.Door;
import com.tboi.game.entities.items.InteractiveObject;
import com.tboi.game.entities.items.Item;
import com.tboi.game.entities.sprites.MainChar;
import com.tboi.game.screens.GameScreen;

public class BodyContact implements ContactListener {


    /**
     * Contact to all objects in the game
     */

    World world;
    GameScreen screen;

    public BodyContact(GameScreen screen) {
        this.screen = screen;
        this.world = screen.getWorld();
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();

        int collisionDef = a.getFilterData().categoryBits | b.getFilterData().categoryBits;

        switch (collisionDef) {
            case  MainGame.MC_BIT | MainGame.GROUND_BIT :
                if(a.getFilterData().categoryBits == MainGame.MC_BIT) {
                    //Gdx.app.log("Ground", "Collided");
                } else {
                    //Gdx.app.log("Ground", "Collided");
                }
                break;
            case  MainGame.MC_BIT | MainGame.ITEM_BIT :
                if(a.getFilterData().categoryBits == MainGame.ITEM_BIT) {
                    ((InteractiveObject)a.getUserData()).destroy();
                    //Gdx.app.log("Coin", "Collided");
                } else {
                    ((InteractiveObject)b.getUserData()).destroy();
                    //Gdx.app.log("Coin", "Collided");
                }
                break;
            case  MainGame.MC_BIT | MainGame.ENTITY_BIT :
                if(a.getFilterData().categoryBits == MainGame.ENTITY_BIT) {
                    ((Item)a.getUserData()).destroy();
                    ((Item)a.getUserData()).use((MainChar)b.getUserData());
                    //Gdx.app.log("Coin", "Collided");
                } else {
                    ((Item)b.getUserData()).destroy();
                    ((Item)b.getUserData()).use((MainChar)a.getUserData());
                    //Gdx.app.log("Coin", "Collided");
                }
                break;
            case  MainGame.MC_BIT | MainGame.DOOR_BIT :
                if(a.getFilterData().categoryBits == MainGame.DOOR_BIT) {
                    ((Door)a.getUserData()).win();
                } else {
                    ((Door)b.getUserData()).win();
                }
                break;
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
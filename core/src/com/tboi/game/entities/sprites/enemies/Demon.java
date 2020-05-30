package com.tboi.game.entities.sprites.enemies;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.tboi.game.MainGame;
import com.tboi.game.screens.GameScreen;

import static com.tboi.game.MainGame.PPM;


public class Demon extends Enemy{

    Array<TextureRegion> frames;
    Animation<TextureRegion> running;
    float stateTime;

    public Demon(GameScreen screen, float x, float y) {
        super(screen, x, y);
        frames();
        stateTime = 0;
        setBounds(getX(), getY(), 32 / MainGame.PPM, 36 / MainGame.PPM);
    }

    private void frames() {
        frames = new Array<TextureRegion>();
        for(int i = 0;i<4;i++){
            frames.add(new TextureRegion(screen.getEnemySprite().findRegion("big_demon"), i*32, 0, 32, 36));
        }
        for(int i = 0;i<4;i++){
            frames.add(new TextureRegion(screen.getEnemySprite().findRegion("big_demon"), i*32, 36, 32, 36));
        }
        running = new Animation<TextureRegion>(0.1f, frames);
        frames.clear();
    }

    @Override
    protected void defineEnemy() {
        bdef = new BodyDef();
        fdef = new FixtureDef();
        shape = new PolygonShape();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bdef);

        shape.setAsBox(10/PPM,15/PPM);
        fdef.shape = shape;
        fdef.filter.categoryBits = MainGame.ENEMY_BIT;
        fdef.filter.maskBits = MainGame.MC_BIT | MainGame.GROUND_BIT | MainGame.OBJECT_BIT;
        body.createFixture(fdef).setUserData(this);
    }

    @Override
    protected void reduceHealth() {

    }

    @Override
    protected void destroy() {
        setToDestroy = true;
    }

    @Override
    public void update(float delta){
        stateTime += delta;
        if(setToDestroy && !destroyed){
            world.destroyBody(body);
            destroyed = true;
            stateTime = 0;
        } else if(!destroyed) {
            setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2 - 2/PPM );
            setRegion(running.getKeyFrame(stateTime, true));
        }
    }
}

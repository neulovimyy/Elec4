package com.tboi.game.entities.sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.tboi.game.MainGame;
import com.tboi.game.screens.GameScreen;
import com.tboi.game.ui.HUD;

import static com.tboi.game.MainGame.PPM;

public class MainChar extends Sprite {

    public enum Direction{JUMPING, RUNNING, STANDING}

    public Direction currentState, previousState;

    GameScreen screen;

    World world;
    public static Body body;
    FixtureDef fdef;
    BodyDef bdef;
    PolygonShape shape;
    float timer;
    TextureRegion stand, up;
    private boolean isRight;

    Animation<TextureRegion> jump,standing,run;

    public MainChar(GameScreen screen){
        super(screen.getKnight().findRegion("knight"));
        this.screen = screen;
        this.world = screen.getWorld();
        isRight = true;
        frames();

        defineMC();

        stand = new TextureRegion(getTexture(), 0, 84, 16, 28);
        setBounds(0, 0, 16 / PPM, 48 / PPM);
        setRegion(stand);
        currentState = Direction.STANDING;
        previousState = Direction.STANDING;
        timer = 0;
    }

    private void frames() {
        Array<TextureRegion> frames = new Array<TextureRegion>();
        for(int i=0;i<2; i++){
            frames.add(new TextureRegion(getTexture(), 0, i * 28, 16, 28));
        }
        jump = new Animation<TextureRegion>(0.2f, frames);
        frames.clear();
        standing = new Animation<TextureRegion>(0.2f, new TextureRegion(getTexture(), 0, 84, 16, 28));
        for(int i=0;i<3;i++){
            frames.add(new TextureRegion(getTexture(), 0, 112 + (i * 28), 16, 28));
        }
        run = new Animation<TextureRegion>(0.2f, frames);
        frames.clear();
    }

    public void update(float delta){
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2 + 10 / PPM);
        setRegion(getFacade(delta));
    }

    public Direction getDirection() {
        if (body.getLinearVelocity().y > 0 ) {
            return Direction.JUMPING;  //up
        } else if (body.getLinearVelocity().x != 0){
            return Direction.RUNNING;
        } else {
            return Direction.STANDING;//steady
        }
    }

    public TextureRegion getFacade(float delta) {
        currentState = getDirection();
        TextureRegion region;
        switch (currentState){
            case JUMPING:
                region = jump.getKeyFrame(timer, false);
                break;
            case RUNNING:
                region = run.getKeyFrame(timer, true);
                break;
            default:
                region = stand;
                break;
        }

        if((body.getLinearVelocity().x < 0 || !isRight) && !region.isFlipX()){
            region.flip(true, false);
            isRight = false;
        } else if((body.getLinearVelocity().x > 0 || isRight) && region.isFlipX()){
            region.flip(true, false);
            isRight = true;
        }

        timer = currentState == previousState ? timer + delta : 0;
        previousState = currentState;

        return region;
    }

    public void defineMC(){
        bdef = new BodyDef();
        fdef = new FixtureDef();
        shape = new PolygonShape();
        bdef.position.set(50/PPM, 100/PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bdef);

        shape.setAsBox(3/PPM, 10/PPM);
        fdef.shape = shape;
        fdef.filter.categoryBits = MainGame.MC_BIT;
        fdef.filter.maskBits = MainGame.GROUND_BIT | MainGame.ENEMY_BIT | MainGame.ITEM_BIT| MainGame.DOOR_BIT;

        body.createFixture(fdef).setUserData(this);
    }

    public void use(){
        HUD.addCoins();
    }
}

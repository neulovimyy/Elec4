package com.tboi.game.entities.items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.tboi.game.MainGame;
import com.tboi.game.entities.sprites.MainChar;
import com.tboi.game.screens.GameScreen;
import com.tboi.game.ui.HUD;

import static com.tboi.game.MainGame.PPM;

public class Coin extends Item {

    public Array<TextureRegion> spin;
    public Animation<TextureRegion> spinning;

    public Coin(GameScreen screen, float x, float y) {
        super(screen, x, y);
        setRegion(screen.getCoin().findRegion("gold_coin"), 0, 0, 16, 16);
        frames();
        defineItem();
    }

    private void frames() {
        spin = new Array<TextureRegion>();

        spin.add(new TextureRegion(getTexture(), 0, 0, 8, 8));
        spin.add(new TextureRegion(getTexture(), 8, 0, 8, 8));
        spin.add(new TextureRegion(getTexture(), 16, 0, 8, 8));
        spin.add(new TextureRegion(getTexture(), 24, 0, 8, 8));

        spinning = new Animation<TextureRegion>(0.1f, spin);
    }

    @Override
    public void defineItem() {

        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        //bdef.bullet = true;
        bdef.linearDamping = 10;
        bdef.gravityScale = 0;

        body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(8 / MainGame.PPM, 8/ PPM);
        fdef.filter.categoryBits = MainGame.ENTITY_BIT;
        fdef.filter.maskBits = MainGame.MC_BIT |
                MainGame.GROUND_BIT |
                MainGame.OBJECT_BIT | MainGame.ENTITY_BIT;
        fdef.shape = shape;
        fdef.density = 2;
        fdef.restitution = 0.99f;
        body.createFixture(fdef).setUserData(this);
    }

    @Override
    public void use(MainChar mc){
        super.use(mc);
        mc.use();
    }

    @Override
    public void update(float delta){
        super.update(delta);
        destroyCoin();
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
        setRegion(spinning.getKeyFrame(0.1f, true));
    }

    public void destroyCoin(){
        float mcWidth = screen.getMc().getX() + screen.getMc().getWidth();
        float mcX = screen.getMc().getX();
        float mcY = screen.getMc().getY();
        float mcHeight = screen.getMc().getY() + screen.getMc().getHeight();
        float itemWidth = this.getX() + getWidth();
        float itemHeight = this.getY() + getHeight();

        if((this.getX() < mcWidth && this.getY() < mcHeight && itemWidth > mcX && itemHeight > mcY)&&(this instanceof Coin)){
                Gdx.app.log("Gathered coin","Deleted");
                use(screen.getMc());
        }

    }

}

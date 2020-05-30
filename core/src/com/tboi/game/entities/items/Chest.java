package com.tboi.game.entities.items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;
import com.tboi.game.MainGame;
import com.tboi.game.entities.sprites.MainChar;
import com.tboi.game.screens.GameScreen;
import com.tboi.game.ui.HUD;

import static com.tboi.game.MainGame.PPM;

public class Chest extends InteractiveObject{

    public TiledMapTileSet tileSet;
    public int OPEN_CHEST = 364;
    public static int level;
    int max, min;
    float minx, maxx, miny, maxy;
    //public Json json;

    public Chest(GameScreen screen, MapObject object) {
        super(screen, object);
        level = 0;
        tileSet = map.getTileSets().getTileSet("tileset");
        fixture.setUserData(this);
        setFilter(MainGame.ITEM_BIT);
    }

    @Override
    public void destroy() {

        switch(level){
            case 1:
                min = 0; max = 10;
                minx = -5 / PPM; maxx = 5 / PPM;
                miny = 0; maxy = 5 / PPM;
                break;
            case 2:
                min = 5; max = 20;
                minx = -5 / PPM; maxx = 5 / PPM;
                miny = 0; maxy = 5 / PPM;
                break;
            case 3:
                min = 10; max = 35;
                minx = -5 / PPM; maxx = 5 / PPM;
                miny = 0; maxy = 5 / PPM;
                break;
            case 4:
                min = 15; max = 50;
                minx = -5 / PPM; maxx = 5 / PPM;
                miny = 0; maxy = 5 / PPM;
                break;
            case 5:
                min = 3; max = 20;
                minx = -5 / PPM; maxx = 5 / PPM;
                miny = 0; maxy = 5 / PPM;
                break;
            case 6:
                min = 5; max = 30;
                minx = -5 / PPM; maxx = 5 / PPM;
                miny = 0; maxy = 5 / PPM;
                break;
            case 7:
                min = 1; max = 10;
                minx = -5 / PPM; maxx = 5 / PPM;
                miny = 0; maxy = 5 / PPM;
                break;
            case 8:
                min = 5; max = 15;
                minx = -5 / PPM; maxx = 5 / PPM;
                miny = 0; maxy = 5 / PPM;
                break;
            case 9:
                min = 10; max = 20;
                minx = -5 / PPM; maxx = 5 / PPM;
                miny = 0; maxy = 5 / PPM;
                break;
            default:
                min = 0; max = 20;
                minx = -5 / PPM; maxx = 5 / PPM;
                miny = 0; maxy = 5 / PPM;
                break;
        }

        int coinCount = MathUtils.random(min, max);

        if(object.getProperties().containsKey("coin")){
            for(int i=0;i<coinCount;i++){
                screen.spawnItem(new ItemDef(new Vector2(body.getPosition().x, body.getPosition().y + 15/ PPM), Coin.class));
            }
            setFilter(MainGame.NOTHING_BIT);
            getCell().setTile(tileSet.getTile(OPEN_CHEST));
        } else {
            setFilter(MainGame.NOTHING_BIT);
            getCell().setTile(tileSet.getTile(OPEN_CHEST));
        }

    }
}

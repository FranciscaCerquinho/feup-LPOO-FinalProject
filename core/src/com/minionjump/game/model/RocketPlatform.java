package com.minionjump.game.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Sissi on 22/05/2017.
 */

public class RocketPlatform extends Platform {

    public RocketPlatform(float x, float y) {
        positionPlat = new Vector2(x, y);
        boundsPlat = new Rectangle(positionPlat.x, positionPlat.y, 150, 90);
    }



}

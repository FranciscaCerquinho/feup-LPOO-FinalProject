package com.minionjump.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.minionjump.game.MyMinionJump;
import com.minionjump.game.model.Minion;
import com.minionjump.game.model.NormalPlatform;
import com.minionjump.game.model.Platform;
import com.minionjump.game.model.RocketPlatform;
import com.minionjump.game.model.SplitPlatform;
import com.minionjump.game.model.SpringPlatform;

import java.util.Random;

/**
 * Created by Mariana on 10/05/2017.
 */

public class PlayState extends State {
    private static final int PLATFORM_SPACING = 250;
    private static final int PLATFORM_COUNT = 100;

    private Minion minion;
    private Texture bg;

    private Array<Platform> platforms;
    private int ymax=0;

    public PlayState(GameStateManager gam) {
        super(gam);
        minion = new Minion(150, 300,gam);
        cam.setToOrtho(false, MyMinionJump.WIDTH, MyMinionJump.HEIGHT);
        bg = new Texture("background.png");
        Random rand = new Random();
        platforms = new Array<Platform>();
        int deltaX = MyMinionJump.WIDTH/2 - 150;
        for(int i = 0; i < PLATFORM_COUNT/2; i++){
            for(int j = 0; j < 2; j++){
                int platformType= rand.nextInt(7);

                switch (platformType){
                    case 0:
                    platforms.add(new SpringPlatform(MyMinionJump.WIDTH/2*j+rand.nextFloat()*deltaX,PLATFORM_SPACING*i+rand.nextFloat()*10));
                        break;
                    case 1:
                        platforms.add(new SplitPlatform(MyMinionJump.WIDTH/2*j+rand.nextFloat()*deltaX,PLATFORM_SPACING*i+rand.nextFloat()*10));
                        break;
                    case 2:
                        platforms.add(new RocketPlatform(MyMinionJump.WIDTH/2*j+rand.nextFloat()*deltaX,PLATFORM_SPACING*i+rand.nextFloat()*10));
                        break;
                    case 3:
                        platforms.add(new NormalPlatform(MyMinionJump.WIDTH/2*j+rand.nextFloat()*deltaX,PLATFORM_SPACING*i+rand.nextFloat()*10));
                        break;
                    case 4:
                        platforms.add(new NormalPlatform(MyMinionJump.WIDTH/2*j+rand.nextFloat()*deltaX,PLATFORM_SPACING*i+rand.nextFloat()*10));
                        break;
                    case 5:
                        platforms.add(new NormalPlatform(MyMinionJump.WIDTH/2*j+rand.nextFloat()*deltaX,PLATFORM_SPACING*i+rand.nextFloat()*10));
                        break;
                    case 6:
                        platforms.add(new SplitPlatform(MyMinionJump.WIDTH/2*j+rand.nextFloat()*deltaX,PLATFORM_SPACING*i+rand.nextFloat()*10));
                        break;
                }
            }
        }
    }

    @Override
    protected void handleInput() {
        /*
        if(Gdx.input.justTouched())
            minion.jump();
            */
        Vector3 new_position = minion.getPosition();

        if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT)){
            new_position.x-=15;
            minion.setPosition(new_position);
        }
        else if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)){
            new_position.x+=15;
            minion.setPosition(new_position);
        }
     if(Gdx.input.getAccelerometerX() != 0){
            new_position.x-=Gdx.input.getAccelerometerX()/1.2f;
        }



        //new_position.x+= Gdx.input.getAccelerometerX()/10;
        //System.out.println( Gdx.input.getAccelerometerX());


      if(minion.getPosition().x > MyMinionJump.WIDTH){
          new_position.x = 0 ;
          minion.setPosition(new_position);
      }
        if(minion.getPosition().x < 0){
            new_position.x = MyMinionJump.WIDTH-10;
            minion.setPosition(new_position);
        }

    }

    @Override
    public void update(float dt) {
        handleInput();
        minion.update(dt);
        cam.position.y = minion.getPosition().y + 80;

        for(int i = 0; i < platforms.size; i++){
            Platform platform = platforms.get(i);

            if(cam.position.y - (cam.viewportHeight / 2) > platform.getPositionPlatform().y + platform.getTextPlatform().getRegionHeight())
                platform.reposition(platform.getPositionPlatform().y + ((Platform.PLATFORM_HEIGHT + PLATFORM_SPACING) * PLATFORM_COUNT));

            if((platforms.get(i) instanceof NormalPlatform)) {
                if (platform.collides(minion.getBounds())) {
                    if (minion.getVelocity().y < 0)
                        minion.jump(750);
                }
            }

            else if((platforms.get(i) instanceof SpringPlatform)) {
                if(((SpringPlatform) platforms.get(i)).collide){
                    ((SpringPlatform) platforms.get(i)).update(dt);
                    if(((SpringPlatform) platforms.get(i)).getAnimation().isAtEnd())
                        minion.jump(1000);
                }

                if (platform.collides(minion.getBounds())) {
                    if (minion.getVelocity().y < 0 || ((SpringPlatform)platforms.get(i)).collide) {
                        minion.jump(750);
                        ((SpringPlatform)platforms.get(i)).update(dt);

                    }
                }
            }

            else if((platforms.get(i) instanceof RocketPlatform)) {
                if (platform.collides(minion.getBounds())) {
                    if (minion.getVelocity().y < 0)
                        minion.jump(1500);
                }
            }

            else if((platforms.get(i) instanceof SplitPlatform)) {
                if(((SplitPlatform) platforms.get(i)).collide)
                    ((SplitPlatform) platforms.get(i)).update(dt);

                if (platform.collides(minion.getBounds())) {
                    if (minion.getVelocity().y < 0) {
                        ((SplitPlatform) platforms.get(i)).update(dt);
                    }
                    //platforms.removeIndex(i);
                }
            }
        }
        cam.update();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(bg, 0, cam.position.y - (cam.viewportHeight / 2));
        sb.draw(minion.getTexture(), minion.getPosition().x, minion.getPosition().y);
        for(Platform platform : platforms) {
            sb.draw(platform.getTextPlatform(), platform.getPositionPlatform().x, platform.getPositionPlatform().y);
        }
        sb.end();
    }

    @Override
    public void dispose() {
        bg.dispose();
        minion.dispose();
        for(Platform platform : platforms)
            platform.dispose();
        System.out.println("Play State Disposed");
    }
}
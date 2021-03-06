package com.minionjump.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.minionjump.game.MyMinionJump;

/**
 * Class that represents the state: Game Over
 */
public class GameOverMenu extends State{

    /**
     * Texture of background for menu "Game Over"
     */
    private Texture menugameover;
    /**
     * Texture of button "Play Again"
     */
    private Texture playAgainBtn;
    /**
     *  Texture of button "Menu"
     */
    private Texture menuBtn;
    /**
     * Button for "Play Again" and "Menu"
     */
    private Button playAgainBut,menuBut;
    /**
     * Stage of this class
     */
    private Stage stage;
    /**
     * Label that indicates user score
     */
    private Label userScore;

    /**
     * Constructs Game Over Menu and defines the position of each button
     * @param gam Game State Manager
     */
    public GameOverMenu(GameStateManager gam) {
        super(gam);

        cam.setToOrtho(false, MyMinionJump.WIDTH, MyMinionJump.HEIGHT);

        menugameover = new Texture("menugameover.png");
        playAgainBtn = new Texture("btnplayagain.png");
        menuBtn = new Texture("btnmenu.png");
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/al-seana.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 55;
        parameter.borderWidth=1;
        parameter.borderColor=Color.BLACK;
        BitmapFont font12 = generator.generateFont(parameter); // font size 12 pixels
        generator.dispose(); // don't forget to dispose to avoid memory leaks!
        userScore= new Label("",new Label.LabelStyle(font12, Color.BLACK));
        userScore.setText("Your Score: " + String.format("%06d",Hud.score));
        if(Hud.score > MyMinionJump.prefs.getScore())
            MyMinionJump.prefs.setScore(Hud.score);
        userScore.setPosition(Gdx.graphics.getWidth()/6, 9*Gdx.graphics.getHeight()/16);
        stage.addActor(userScore);

        Drawable buttonDrawableSha = new TextureRegionDrawable(new TextureRegion(playAgainBtn));
        playAgainBut = new ImageButton(buttonDrawableSha);
        playAgainBut.setSize(Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/5);
        playAgainBut.setPosition(Gdx.graphics.getWidth()/2- 2*playAgainBut.getWidth()/4,Gdx.graphics.getHeight()/2- 4*playAgainBut.getHeight()/3);

        stage.addActor(playAgainBut);

        buttonDrawableSha = new TextureRegionDrawable(new TextureRegion(menuBtn));
        menuBut = new ImageButton(buttonDrawableSha);
        menuBut.setSize(Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/5);
        menuBut.setPosition(Gdx.graphics.getWidth()/2- 2*menuBut.getWidth()/4,Gdx.graphics.getHeight()/2- 7*menuBut.getHeight()/3);

        stage.addActor(menuBut);

    }

    /**
     * Method for handle input
     */
    @Override
    protected void handleInput() {

    }

    /**
     * Method that updates Game Over Menu
     * @param dt
     */
    @Override
    public void update(float dt) {

    }

    /**
     * Method that renders Game Over Menu
     * @param sb
     */
    @Override
    public void render(SpriteBatch sb) {
        Gdx.gl.glClearColor(1, 1,1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(menugameover,0,0);
        sb.end();
        stage.act();
        stage.draw();

        if(playAgainBut.isPressed())
            gam.set(new GameView(gam));


        if(menuBut.isPressed())
            gam.set(new MainMenu(gam));

    }

    /**
     * Disposes all buttons
     */
    @Override
    public void dispose() {
       menugameover.dispose();
        playAgainBtn.dispose();
        menuBtn.dispose();
        System.out.println("Menu State Disposed");
    }
}

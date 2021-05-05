package dk.sdu.mmmi.cbse.core.pausemenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.utils.GdxRuntimeException;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

public class PauseMenu {
    private Stage stage;
    private BitmapFont font;
    private Skin skin;
    private TextureAtlas buttonAtlas;
    private TextButtonStyle textButtonStyle;
    private TextButton exitButton;
    
    private ShapeRenderer shapeRenderer;
    public PauseMenu(GameData gameData, World world, OrthographicCamera cam){
        stage = new Stage();
        font = new BitmapFont();
        exitButton = new TextButton("Exit button",skin,"small");
        exitButton.setSize(200, 100);
        exitButton.setPosition(500,1000);
        try {
            skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        } catch (GdxRuntimeException e) {
            skin = new Skin(Gdx.files.internal("../../skin/uiskin.json"));
        }
        
        stage.addActor(exitButton);
        shapeRenderer = new ShapeRenderer();
        /*
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        //textButtonStyle.font = yourCustomFont;
        textButtonStyle.fontColor = Color.WHITE;
        stage.addActor(new TextButton("Custom Btn ", textButtonStyle));
        */
    }
    
    public void draw(){
        drawTest();
        stage.draw();
    }
    private void drawTest(){
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(1, 1, 1, 1);
        shapeRenderer.line(0,500,1920,500);
        shapeRenderer.end();
    }
}

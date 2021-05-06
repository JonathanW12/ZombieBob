package dk.sdu.mmmi.cbse.core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.GameKeys;
import dk.sdu.mmmi.cbse.core.main.ZombieBobGame;

public class MainMenuScreen implements Screen {
    
    private final ZombieBobGame game;
    private GameData gameData;
    private Stage stage;
    private Skin skin;
    
    public MainMenuScreen(ZombieBobGame game) {
        this.game = game;
        gameData = game.getGameData();
        stage = new Stage(new StretchViewport(
            gameData.getDisplayWidth(),
            gameData.getDisplayHeight()
        ));
        
        skin = new Skin(getSkinJSON());
        setupUI();
    }
    
    private FileHandle getSkinJSON() {
        FileHandle filehandle;
        String osName = System.getProperty("os.name");
        if (osName.startsWith("Windows")) {
            filehandle = new FileHandle("../../skin/uiskin.json");
        } else {
            filehandle = new FileHandle("./skin/uiskin.json");
        }
        
        return filehandle;
    }
    
    private void setupUI() {
        Label title;
        title = new Label("ZombieBob", skin, "title");
        title.setFontScale(1);
        float titleWidth = title.getWidth();
        title.setX(stage.getWidth() / 2 - titleWidth / 2);
        title.setY(stage.getHeight() - 150);
        stage.addActor(title);
        
        TextButton test = new TextButton("Test", skin);
        stage.addActor(test);
    }
    
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.149f, 0.133f, 0.133f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
        
        if (gameData.getKeys().isPressed(GameKeys.ESCAPE)) {
            Gdx.app.exit();
        }
    }
    
     @Override
    public void show() {

    }
    
    @Override
    public void hide() { }
    
    @Override
    public void pause() { }

    @Override
    public void resume() { }
    
    @Override
    public void resize(int width, int height) {
    }
    
    @Override
    public void dispose() {
    }
}

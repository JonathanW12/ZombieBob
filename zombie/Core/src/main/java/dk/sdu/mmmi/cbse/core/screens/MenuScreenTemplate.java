package dk.sdu.mmmi.cbse.core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.GameKeys;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.core.coreprocessors.AudioProcessor;
import dk.sdu.mmmi.cbse.core.main.ZombieBobGame;
import dk.sdu.mmmi.cbse.core.managers.GameInputProcessor;
import dk.sdu.mmmi.cbse.core.managers.MouseInputProcessor;

public class MenuScreenTemplate implements Screen {
    
    private final ZombieBobGame game;
    private GameData gameData;
    private World world;
    private Stage stage;
    private Skin skin;
    private BitmapFont titleFont;
    
    // Stage actors
    private Button musicButton, soundButton;
    
    public MenuScreenTemplate(ZombieBobGame game) {
        this.game = game;
        gameData = game.getGameData();
        world = game.getWorld();
        stage = new Stage(new StretchViewport(
            gameData.getDisplayWidth(),
            gameData.getDisplayHeight()
        ));
        
        skin = new Skin(getSkinFile());
        titleFont = getTitleFontFile();
        
        setupUI();
    }
    
    private FileHandle getSkinFile() {
        FileHandle filehandle;
        String osName = System.getProperty("os.name");
        if (osName.startsWith("Windows")) {
            filehandle = new FileHandle("../../skin/uiskin.json");
        } else {
            filehandle = new FileHandle("./skin/uiskin.json");
        }
        
        return filehandle;
    }
    
    private BitmapFont getTitleFontFile() {
        BitmapFont font;
        String osName = System.getProperty("os.name");
        if (osName.startsWith("Windows")) {
            font = new BitmapFont(new FileHandle("../../font/titleFont.fnt"));
        } else {
            font = new BitmapFont(new FileHandle("./skin/titleFont.fnt"));
        }
        
        return font;
    }
    
    private void setupUI() {
        addMusicButton();
        addSoundButton();
    }
    
    private void addMusicButton() {
        musicButton = new Button(skin, "music");
        musicButton.setChecked(true);
        musicButton.setPosition(
            stage.getWidth() - 60,
            stage.getHeight() - 60
        );
        stage.addActor(musicButton);
    }
    
    private void addSoundButton() {
        soundButton = new Button(skin, "sound");
        soundButton.setChecked(true);
        soundButton.setPosition(
            stage.getWidth() - 115,
            stage.getHeight() - 60
        );
        stage.addActor(soundButton);
    }
    
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();

        update();
        gameData.getKeys().update();
    }
    
    public void update() {
        game.getAudioProcessor().processAudio();
        
        if (gameData.getKeys().isPressed(GameKeys.ESCAPE)) {
            Gdx.app.exit();
        }
    }
    
    public ZombieBobGame getGame() {
        return game;
    }
    
    public GameData getGameData() {
        return gameData;
    }
    
    public World getWorld() {
        return world;
    }
    
    public Stage getStage() {
        return stage;
    }
    
    public Skin getSkin() {
        return skin;
    }
    
    public BitmapFont getTitleFont() {
        return titleFont;
    }
    
    @Override
    public void show() { 
        setupInputProcessors();
        setupCursor();
        game.getAudioProcessor().setMusicState(AudioProcessor.MusicState.MENUMUSIC);
    }
    
    @Override
    public void hide() { }
    
    public void setupCursor() {
        Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Arrow);
    }
    
    public void setupInputProcessors() {
        InputProcessor keyInputProcessor = new GameInputProcessor(gameData);
        InputProcessor mouseInputProcessor = new MouseInputProcessor(gameData, (OrthographicCamera) stage.getCamera());
        InputMultiplexer inputMultiplexer = new InputMultiplexer();

        inputMultiplexer.addProcessor(keyInputProcessor);
        inputMultiplexer.addProcessor(mouseInputProcessor);

        Gdx.input.setInputProcessor(inputMultiplexer); 
    }
    
    @Override
    public void pause() { }

    @Override
    public void resume() { }
    
    @Override
    public void resize(int width, int height) { }
    
    @Override
    public void dispose() { }
    
}

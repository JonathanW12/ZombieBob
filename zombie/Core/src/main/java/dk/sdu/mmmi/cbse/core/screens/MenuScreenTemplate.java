package dk.sdu.mmmi.cbse.core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.core.coreprocessors.AudioProcessor;
import dk.sdu.mmmi.cbse.core.main.ZombieBobGame;

public class MenuScreenTemplate implements Screen {
    
    private final ZombieBobGame game;
    private GameData gameData;
    private Stage stage;
    private Skin skin;
    private BitmapFont titleFont;
    
    // Stage actors
    private Button musicButton, soundButton;
    
    public MenuScreenTemplate(ZombieBobGame game) {
        this.game = game;
        gameData = game.getGameData();
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
    }
    
    private void update() {
        game.getAudioProcessor().processAudio();
    }
    
    public ZombieBobGame getGame() {
        return game;
    }
    
    public GameData getGameData() {
        return gameData;
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
        game.getAudioProcessor().setMusicState(AudioProcessor.MusicState.MENUMUSIC);
    }
    
    @Override
    public void hide() { }
    
    @Override
    public void pause() { }

    @Override
    public void resume() { }
    
    @Override
    public void resize(int width, int height) { }
    
    @Override
    public void dispose() { }
    
}

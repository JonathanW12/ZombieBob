package dk.sdu.mmmi.cbse.core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.GameKeys;
import dk.sdu.mmmi.cbse.common.data.MouseMovement;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.core.coreprocessors.AudioProcessor;
import dk.sdu.mmmi.cbse.core.main.ZombieBobGame;
import dk.sdu.mmmi.cbse.core.managers.GameInputProcessor;
import dk.sdu.mmmi.cbse.core.managers.MouseInputProcessor;

public class MenuScreenTemplate implements Screen {
    
    private final ZombieBobGame game;
    private final GameData gameData;
    private final World world;
    private final Stage stage;
    private final Skin skin;
    private final BitmapFont titleFont;
    
    // Stage actors
    private Image musicButton, soundButton;
    private final Group hoverButtonGroup;
    
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
        
        hoverButtonGroup = new Group();
        
        setupUI();
    }
    
    public Group getHoverButtonGroup() {
        return hoverButtonGroup;
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
        
        stage.addActor(hoverButtonGroup);
    }
    
    private void addMusicButton() {
        musicButton = new Image(getButtonTexture(getMusicButtonIcon()));
        musicButton.setPosition(
            stage.getWidth() - 160,
            stage.getHeight() - 80
        );
        hoverButtonGroup.addActor(musicButton);
    }
    
    private void addSoundButton() {
        soundButton = new Image(getButtonTexture(getSoundButtonIcon()));
        soundButton.setPosition(
            stage.getWidth() - 85,
            stage.getHeight() - 80
        );
        hoverButtonGroup.addActor(soundButton);
    }
    
    public Texture getButtonTexture(String fileName) {
        Texture buttonTexture;
        
        try {
            buttonTexture = new Texture(Gdx.files.local("raw-assets/" + fileName));
        } catch (GdxRuntimeException e) {
            buttonTexture = new Texture(Gdx.files.local("../../raw-assets/" + fileName));
        }
        
        return buttonTexture;
    }
    
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();

        update();
        gameData.getKeys().update();
        gameData.getMouse().update();
    }
    
    public void update() {
        if (gameData.getKeys().isPressed(GameKeys.ESCAPE)) {
            Gdx.app.exit();
        }
        
        game.getAudioProcessor().processAudio();
        handleAudioButtons();
    }
    
    private void handleAudioButtons() {
        // Handle music button
        if (isMouseOnActor(musicButton) && gameData.getMouse().isPressed(MouseMovement.LEFTCLICK)) {
            game.getAudioProcessor().setMusicOn(!game.getAudioProcessor().getMusicOn());
            musicButton.setDrawable(new SpriteDrawable(new Sprite(getButtonTexture(getMusicButtonIcon()))));
        }
       
        // Handle sound button
        if (isMouseOnActor(soundButton) && gameData.getMouse().isPressed(MouseMovement.LEFTCLICK)) {
            game.getAudioProcessor().setSoundOn(!game.getAudioProcessor().getSoundOn());
            soundButton.setDrawable(new SpriteDrawable(new Sprite(getButtonTexture(getSoundButtonIcon()))));
        }
    }
    
    private String getMusicButtonIcon() {
        boolean soundOn = game.getAudioProcessor().getMusicOn();
        
        if (soundOn) {
            return "music-button.png";
        } else {
            return "music-button-disabled.png";
        }
    }
    
    private String getSoundButtonIcon() {
        boolean soundOn = game.getAudioProcessor().getSoundOn();
        
        if (soundOn) {
            return "sound-button.png";
        } else {
            return "sound-button-disabled.png";
        }
    }
    
    private boolean isMouseOnActor(Actor actor) {
        float mouseX = Gdx.input.getX();
        float mouseY = stage.getHeight() - Gdx.input.getY();
        
        return (
            mouseX >= actor.getX() &&
            mouseX <= actor.getX() + actor.getWidth() &&
            mouseY >= actor.getY() &&
            mouseY <= actor.getY() + actor.getHeight()
        ) ;

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
        game.getAudioProcessor().setMusicState(AudioProcessor.MusicState.MENUMUSIC);
        setupInputProcessors();
        setupCursor();
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
        inputMultiplexer.addProcessor(stage);

        Gdx.input.setInputProcessor(inputMultiplexer); 
    }
    
    @Override
    public void pause() { }

    @Override
    public void resume() { }
    
    @Override
    public void resize(int width, int height) { }
    
    @Override
    public void dispose() {    
        stage.dispose();
    }
    
}

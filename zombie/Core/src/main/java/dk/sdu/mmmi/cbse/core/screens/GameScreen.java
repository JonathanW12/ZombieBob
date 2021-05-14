package dk.sdu.mmmi.cbse.core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.GameKeys;
import dk.sdu.mmmi.cbse.common.data.entitytypeparts.PlayerPart;
import dk.sdu.mmmi.cbse.core.coreprocessors.AudioProcessor;
import dk.sdu.mmmi.cbse.core.coreprocessors.RenderProcessor;
import dk.sdu.mmmi.cbse.core.main.GameLookup;
import dk.sdu.mmmi.cbse.core.main.ZombieBobGame;
import dk.sdu.mmmi.cbse.core.managers.GameInputProcessor;
import dk.sdu.mmmi.cbse.core.managers.MouseInputProcessor;

public class GameScreen implements Screen {

    private final ZombieBobGame game;
    private final GameData gameData;
    private final World world;
    private OrthographicCamera cam;
    private ExtendViewport viewport;
    private final RenderProcessor renderProcessor;
    private final GameLookup gameLookup;
    private Vector3 mousePosition;
    
    public GameScreen(ZombieBobGame game) {
        this.game = game;
        gameData = game.getGameData();
        world = game.getWorld();
        
        setupCam();
        renderProcessor = new RenderProcessor(gameData, world, cam);
        mousePosition = new Vector3(); 
        gameLookup = GameLookup.getInstance(gameData, world);
        setupCursorImage();
        setupInputProcessors();
    }
    
    @Override
    public void render(float delta) {
        gameData.setCamPosX(cam.position.x);
        gameData.setCamPosY(cam.position.y);
        
        gameData.setDelta(delta);
        
        update();
        renderProcessor.draw();
        renderProcessor.processRendering(gameData);

        gameData.getKeys().update();
        gameData.getMouse().update();
    }
    
    @Override
    public void show() {
        setupInputProcessors();
        setupCursorImage();
        game.getAudioProcessor().setMusicState(AudioProcessor.MusicState.GAMEMUSIC);
    }
    
    @Override
    public void hide() { }
    
    @Override
    public void pause() { }

    @Override
    public void resume() { }
    
    @Override
    public void resize(int width, int height) {
        renderProcessor.resize(width, height);
        viewport.update(width, height, true);
    }
    
    @Override
    public void dispose() {
        renderProcessor.dispose();
    }
    
    private void setupCam() {
        cam = new OrthographicCamera(
            gameData.getDisplayWidth(),
            gameData.getDisplayHeight()
        );
        cam.translate(
            gameData.getDisplayWidth() / 2,
            gameData.getDisplayHeight() / 2
        );
        cam.update();

        viewport = new ExtendViewport(
            gameData.getDisplayWidth() / 2,
            gameData.getDisplayHeight() / 2, 
            cam
        );
    }

    private void update() {
        handleGameOver();
        
        // Process entities
        gameLookup.getEntityProcessingServices().forEach(entityProcessorService -> {
            entityProcessorService.process(gameData, world);
        });

        // Post Update
        gameLookup.getPostEntityProcessingServices().forEach(postEntityProcessorService -> {
            postEntityProcessorService.process(gameData, world);
        });
        
        game.getAudioProcessor().processAudio();
        
        // Update mousePosition every gameloop even if no event is fired
        mousePosition = cam.unproject(new Vector3(mousePosition.set(Gdx.input.getX(),Gdx.input.getY(),0)));
        gameData.getMouse().setMousePosition(mousePosition.x,mousePosition.y);
        
        // Pause if escape is clicked
        if (gameData.getKeys().isPressed(GameKeys.ESCAPE)) {
            game.setScreen(new PauseMenuScreen(game));
        }
    }
      
    private void setupCursorImage() {
        Pixmap pm;

        try {
            pm = new Pixmap(Gdx.files.local("raw-assets/crosshair.png"));
        } catch (GdxRuntimeException e) {
            pm = new Pixmap(Gdx.files.local("../../raw-assets/crosshair.png"));
        }

        int xOffset = (pm.getWidth() / 2);
        int yOffset = (pm.getHeight() / 2);

        Cursor cursor = Gdx.graphics.newCursor(pm, xOffset, yOffset);
        Gdx.graphics.setCursor(cursor);
        pm.dispose();
    }
    
    private void setupInputProcessors() {
        InputProcessor keyInputProcessor = new GameInputProcessor(gameData);
        InputProcessor mouseInputProcessor = new MouseInputProcessor(gameData, cam);
        InputMultiplexer inputMultiplexer = new InputMultiplexer();

        inputMultiplexer.addProcessor(keyInputProcessor);
        inputMultiplexer.addProcessor(mouseInputProcessor);

        Gdx.input.setInputProcessor(inputMultiplexer); 
    }
    
    private void handleGameOver() {
        if (world.getMapByPart(PlayerPart.class.getSimpleName()).keySet().toArray().length == 0) {
            game.setScreen(new GameOverScreen(game));
        }
    }
    
}

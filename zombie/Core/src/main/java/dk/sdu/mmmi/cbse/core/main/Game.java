package dk.sdu.mmmi.cbse.core.main;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.graphics.Pixmap;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.core.managers.GameInputProcessor;
import dk.sdu.mmmi.cbse.core.managers.MouseInputProcessor;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
import com.badlogic.gdx.utils.GdxRuntimeException;
import dk.sdu.mmmi.cbse.common.data.GameKeys;
import dk.sdu.mmmi.cbse.core.coreprocessors.AudioProcessor;
import dk.sdu.mmmi.cbse.core.coreprocessors.RenderProcessor;

public class Game implements ApplicationListener {

    private static OrthographicCamera cam;
    private ExtendViewport viewport;
    private GameData gameData;
    private World world;
    private AudioProcessor audioProcessor;
    private RenderProcessor renderProcessor;
    private GameLookup gameLookup;
    
    @Override
    public void create() {
        gameData = new GameData();
        world = new World();
           
        gameData.setDisplayWidth(Gdx.graphics.getWidth());
        gameData.setDisplayHeight(Gdx.graphics.getHeight());
        
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
        
        audioProcessor = new AudioProcessor(world);
        renderProcessor = new RenderProcessor(gameData, world, cam);
        gameLookup = new GameLookup(gameData, world);
        
        setupCursorImage();
        setupInputProcessors();
    }

    @Override
    public void render() {
        renderProcessor.processRendering();
        update();
        renderProcessor.draw();
        gameData.getKeys().update();
    }

    private void update() {
        // Update
        for (IEntityProcessingService entityProcessorService : gameLookup.getEntityProcessingServices()) {
            entityProcessorService.process(gameData, world);
        }

        // Post Update
        for (IPostEntityProcessingService postEntityProcessorService : gameLookup.getPostEntityProcessingServices()) {
            postEntityProcessorService.process(gameData, world);
        }
        
        // Process audio
        audioProcessor.processAudio();
        
        // Quit if escape is clicked
        if (gameData.getKeys().isPressed(GameKeys.ESCAPE)) {
            Gdx.app.exit();
        }
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }
    
    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        renderProcessor.resize(width, height);
    }

    @Override
    public void dispose() {
        renderProcessor.dispose();
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

        Gdx.input.setCursorImage(pm, xOffset, yOffset);
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
}

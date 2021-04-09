package dk.sdu.mmmi.cbse.core.main;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import dk.sdu.mmmi.cbse.commonanimation.Animation;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.EntityPart;
import dk.sdu.mmmi.cbse.common.data.entitytypeparts.VisualPart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
import dk.sdu.mmmi.cbse.core.managers.GameInputProcessor;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import com.badlogic.gdx.utils.Array;
import dk.sdu.mmmi.cbse.common.data.entityparts.AnimationPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import java.util.concurrent.CopyOnWriteArrayList;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;

public class Game implements ApplicationListener {

    private static OrthographicCamera cam;
    private ExtendViewport viewport;
    private final Lookup lookup = Lookup.getDefault();
    private final GameData gameData = new GameData();
    private World world = new World();
    private List<IGamePluginService> gamePlugins = new CopyOnWriteArrayList<>();
    private Lookup.Result<IGamePluginService> result;
    private SpriteBatch batch;
    private TextureAtlas textureAtlas;
    private TextureAtlas animationTextureAtlas;
    private final HashMap<String, Sprite> sprites = new HashMap<String, Sprite>();
    private final HashMap<String, TextureRegion> animationRegions = new HashMap<String, TextureRegion>();

    @Override
    public void create() {
        gameData.setDisplayWidth(Gdx.graphics.getWidth());
        gameData.setDisplayHeight(Gdx.graphics.getHeight());
        
        batch = new SpriteBatch();
        textureAtlas = new TextureAtlas("../../assets/sprites.txt");
        animationTextureAtlas = new TextureAtlas("../../assets/animations.txt");
        
        addSprites();
        addAnimations();

        cam = new OrthographicCamera(gameData.getDisplayWidth(), gameData.getDisplayHeight());
        cam.translate(gameData.getDisplayWidth() / 2, gameData.getDisplayHeight() / 2);
        cam.update();
        viewport = new ExtendViewport(1100, 800, cam);

        Gdx.input.setInputProcessor(new GameInputProcessor(gameData));

        result = lookup.lookupResult(IGamePluginService.class);
        result.addLookupListener(lookupListener);
        result.allItems();

        for (IGamePluginService plugin : result.allInstances()) {
            plugin.start(gameData, world);
            gamePlugins.add(plugin);
        }
    }

    @Override
    public void render() {
        // clear screen to black
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        gameData.setDelta(Gdx.graphics.getDeltaTime());
        

        update();
        draw();
        gameData.getKeys().update();
    }

    private void update() {
        // Update
        for (IEntityProcessingService entityProcessorService : getEntityProcessingServices()) {
            entityProcessorService.process(gameData, world);
        }

        // Post Update
        for (IPostEntityProcessingService postEntityProcessorService : getPostEntityProcessingServices()) {
            postEntityProcessorService.process(gameData, world);
        }
    }

    private void draw() {
        batch.begin();  
        
        for (Map.Entry<UUID, EntityPart> entry: world.getMapByPart("VisualPart").entrySet()){ 
            PositionPart positionPart = (PositionPart) world.getMapByPart("PositionPart").get(entry.getKey());
            VisualPart visualPart = (VisualPart) world.getMapByPart("VisualPart").get(entry.getKey());
            AnimationPart animationPart = (AnimationPart) world.getMapByPart("AnimationPart").get(entry.getKey());

            if (animationPart != null && animationPart.isAnimated()) {                
                drawAnimation(
                    animationPart,
                    positionPart.getX(),
                    positionPart.getY(),
                    positionPart.getRadians(),
                    visualPart.getWidth(),
                    animationPart.getAnimationByName(animationPart.getCurrentAnimationName()).getFrameCount()
                ); 
            } else {
                drawSprite(
                    visualPart.getSpriteName(),
                    positionPart.getX(),
                    positionPart.getY(),
                    positionPart.getRadians(),
                    visualPart.getWidth()
                );
            }
            
        }
        
        batch.end();
    }
    
    private void drawSprite(String spriteName, float x, float y, float radians, float width) {
        Sprite sprite = sprites.get(spriteName);
        float originalWidth = sprite.getWidth();
        float originalHeight = sprite.getHeight();
        float newHeight = (originalHeight / originalWidth) * width;
        
        
        sprite.setBounds(x, y, width, newHeight);
        sprite.setOriginCenter();
        sprite.setRotation((float) Math.toDegrees(radians - 3.1415f / 2));
        sprite.translate(-(width / 2), -(newHeight / 2));
        
        sprite.draw(batch);
    }
    
    private void drawAnimation(AnimationPart animationPart, float x, float y, float radians, float width, int frameCount) {
        TextureRegion region = animationRegions.get(animationPart.getAnimationByName(animationPart.getCurrentAnimationName()).getTextureFileName());
        TextureRegion[] subRegions = new TextureRegion[frameCount];
        Animation animation = animationPart.getCurrentAnimation();
        
        for (int i = 0; i < frameCount; i++) {
            subRegions[i] = new TextureRegion(
                region,
                (i * (region.getRegionWidth() / frameCount)),
                0,
                region.getRegionWidth() / frameCount,
                region.getRegionHeight()
            );
        }
        
        Sprite sprite = new Sprite(subRegions[animation.getCurrentFrame()]);
        float originalWidth = sprite.getWidth();
        float originalHeight = sprite.getHeight();
        float newHeight = (originalHeight / originalWidth) * width;
       
        sprite.setBounds(x, y, width, newHeight);
        sprite.setRotation((float) Math.toDegrees(radians - 3.1415f / 2));
        sprite.setOriginCenter();
        sprite.translate(-(width / 2), -(newHeight / 2));

        sprite.draw(batch);
    }
    
    private void addSprites() {
        Array<AtlasRegion> regions = textureAtlas.getRegions();

        for (AtlasRegion region : regions) {
            Sprite sprite = textureAtlas.createSprite(region.name);

            sprites.put(region.name, sprite);
        }
    }
    
    private void addAnimations() {
        Array<AtlasRegion> regions = animationTextureAtlas.getRegions();

        for (AtlasRegion region : regions) {
            animationRegions.put(region.name, region);
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        batch.setProjectionMatrix(cam.combined);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        batch.dispose();
        textureAtlas.dispose();
        animationTextureAtlas.dispose(); 
    }

    private Collection<? extends IEntityProcessingService> getEntityProcessingServices() {
        return lookup.lookupAll(IEntityProcessingService.class);
    }

    private Collection<? extends IPostEntityProcessingService> getPostEntityProcessingServices() {
        return lookup.lookupAll(IPostEntityProcessingService.class);
    }

    private final LookupListener lookupListener = new LookupListener() {
        @Override
        public void resultChanged(LookupEvent le) {

            Collection<? extends IGamePluginService> updated = result.allInstances();

            for (IGamePluginService us : updated) {
                // Newly installed modules
                if (!gamePlugins.contains(us)) {
                    us.start(gameData, world);
                    gamePlugins.add(us);
                }
            }

            // Stop and remove module
            for (IGamePluginService gs : gamePlugins) {
                if (!updated.contains(gs)) {
                    gs.stop(gameData, world);
                    gamePlugins.remove(gs);
                }
            }
        }

    };
}

package dk.sdu.mmmi.cbse.core.main;

//import com.codeandweb.physicseditor.PhysicsShapeCache;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import dk.sdu.mmmi.cbse.common.data.entitytypeparts.PlayerPart;
import dk.sdu.mmmi.cbse.commonanimation.Animation;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Gdx2DPixmap;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.core.managers.GameInputProcessor;
import dk.sdu.mmmi.cbse.core.managers.MouseInputProcessor;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.EntityPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.VisualPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.ColliderPart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import dk.sdu.mmmi.cbse.common.data.GameKeys;
import dk.sdu.mmmi.cbse.common.data.entityparts.AnimationPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.AudioPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.TextPart;
import dk.sdu.mmmi.cbse.commontiles.Tiles;
import dk.sdu.mmmi.cbse.commontiles.Tile;

import java.util.ArrayList;
import java.util.Queue;
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
    private final int zDepth = 5;
    private ArrayList<ArrayList<UUID>> sortedVisualList = new ArrayList<>(zDepth);
    private BitmapFont font;

    @Override
    public void create() {
        //physicsBodies = new PhysicsShapeCache("physics.xml");
        gameData.setDisplayWidth(Gdx.graphics.getWidth());
        gameData.setDisplayHeight(Gdx.graphics.getHeight());


        batch = new SpriteBatch();
        FreeTypeFontGenerator fontGenerator;


        try {
            fontGenerator = new FreeTypeFontGenerator(Gdx.files.local("font/Roboto-Light.ttf"));

            textureAtlas = new TextureAtlas(Gdx.files.local("assets/sprites.txt"));
            animationTextureAtlas = new TextureAtlas(Gdx.files.local("assets/animations.txt"));

            //temp way of adding hotbar
            Texture img1 = new Texture(Gdx.files.local("assets/Hotbar_official.png"));
            sprites.put("hotbar_sprite", new Sprite(img1));

            Texture img2 = new Texture(Gdx.files.local("assets/Weapon_test1.png"));
            sprites.put("sword_sprite", new Sprite(img2));

            Texture img3 = new Texture(Gdx.files.local("assets/Background_Test1.png"));
            sprites.put("background_sprite", new Sprite(img3));

            Texture img4 = new Texture(Gdx.files.local("assets/Wall_Test2.png"));
            sprites.put("wall_sprite", new Sprite(img4));
        } catch (GdxRuntimeException e) {
            fontGenerator = new FreeTypeFontGenerator(Gdx.files.local("../../font/Roboto-Light.ttf"));

            textureAtlas = new TextureAtlas("../../assets/sprites.txt");
            animationTextureAtlas = new TextureAtlas("../../assets/animations.txt");

            //temp way of adding hotbar
            Texture img1 = new Texture("../../assets/Hotbar_official.png");
            sprites.put("hotbar_sprite", new Sprite(img1));

            Texture img2 = new Texture("../../assets/Weapon_test1.png");
            sprites.put("sword_sprite", new Sprite(img2));

            Texture img3 = new Texture("../../assets/Background_Test1.png");
            sprites.put("background_sprite", new Sprite(img3));

            Texture img4 = new Texture("../../assets/Wall_Test2.png");
            sprites.put("wall_sprite", new Sprite(img4));
        }


        font = fontGenerator.generateFont(30);
        font.setScale(.5f);
        //font = new BitmapFont();
        fontGenerator.dispose();

        addSprites();
        addAnimations();

        cam = new OrthographicCamera(gameData.getDisplayWidth(), gameData.getDisplayHeight());
        cam.translate(gameData.getDisplayWidth() / 2, gameData.getDisplayHeight() / 2);
        cam.update();
        viewport = new ExtendViewport(gameData.getDisplayWidth() / 2, gameData.getDisplayHeight() / 2, cam);

        InputProcessor keyInputProcessor = new GameInputProcessor(gameData);
        InputProcessor mouseInputProcessor = new MouseInputProcessor(gameData, cam);

        InputMultiplexer inputMultiplexer = new InputMultiplexer();

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


        inputMultiplexer.addProcessor(keyInputProcessor);
        inputMultiplexer.addProcessor(mouseInputProcessor);

        Gdx.input.setInputProcessor(inputMultiplexer);

        result = lookup.lookupResult(IGamePluginService.class);
        result.addLookupListener(lookupListener);
        result.allItems();

        for (IGamePluginService plugin : result.allInstances()) {
            plugin.start(gameData, world);
            gamePlugins.add(plugin);
        }
        for (int i = 0; i < zDepth; i++) {
            sortedVisualList.add(new ArrayList());
        }
    }

    @Override
    public void render() {

        if (world.getMapByPart(PlayerPart.class.getSimpleName()).keySet().toArray().length > 0) {
            UUID uuid = (UUID) world.getMapByPart(PlayerPart.class.getSimpleName()).keySet().toArray()[0];
            PositionPart playerPositionPart = (PositionPart) world.getMapByPart(PositionPart.class.getSimpleName()).get(uuid);
            cam.unproject(new Vector3(playerPositionPart.getX(), playerPositionPart.getY(), 0));
            cam.position.set(playerPositionPart.getX(), playerPositionPart.getY(), 0);
            cam.update();
            batch.setProjectionMatrix(cam.combined);
        }

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
        
        // Process audio
        processAudio();
        
        // Quit if escape is clicked
        if (gameData.getKeys().isPressed(GameKeys.ESCAPE)) {
            Gdx.app.exit();
        }
    }
    
    private void processAudio() {
        if (world.getMapByPart(AudioPart.class.getSimpleName()) != null) {
            for (Map.Entry<UUID, EntityPart> entry : world.getMapByPart(AudioPart.class.getSimpleName()).entrySet()) { 
                AudioPart audioPart = (AudioPart) entry.getValue();

                if (audioPart.getIsPlaying()) {
                    Sound sound = getSound(audioPart.getFileName());
                    audioPart.setIsPlaying(false);
                    long soundId = sound.play(0.1f);
                    sound.setLooping(soundId, false);
                }
            }
        }
    }
    
    private Sound getSound(String fileName) {
        Sound sound;

        try {
            sound = Gdx.audio.newSound(Gdx.files.local("audio/" + fileName));
        } catch (GdxRuntimeException e) {
            sound = Gdx.audio.newSound(Gdx.files.local("../../audio/" + fileName));
        }
        
        return sound;
    }

    private ArrayList<ArrayList<UUID>> sortVisualParts() {
        for (Map.Entry<UUID, EntityPart> entry : world.getMapByPart("VisualPart").entrySet()) {
            VisualPart visualPart = (VisualPart) world.getMapByPart("VisualPart").get(entry.getKey());
            int zPosition = visualPart.getZPostion();
            sortedVisualList.get(zPosition).add(entry.getKey());
        }
        return sortedVisualList;
    }

    private void clearSortedVisualList() {
        for (int i = 0; i < zDepth; i++) {
            sortedVisualList.get(i).clear();
        }
    }

    private void draw() {
        batch.begin();

        sortVisualParts();
        for (int i = 0; i < zDepth; i++) {

            int edgeCount = sortedVisualList.get(i).size();
            for (int j = 0; j < edgeCount; j++) {
                UUID entityUUID = sortedVisualList.get(i).get(j);
                PositionPart positionPart = (PositionPart) world.getMapByPart("PositionPart").get(entityUUID);
                VisualPart visualPart = (VisualPart) world.getMapByPart("VisualPart").get(entityUUID);
                AnimationPart animationPart = (AnimationPart) world.getMapByPart("AnimationPart").get(entityUUID);

                if (animationPart != null && animationPart.isAnimated()) {
                    drawAnimation(
                            animationPart,
                            positionPart.getX(),
                            positionPart.getY(),
                            positionPart.getRadians(),
                            visualPart.getWidth(),
                            animationPart.getAnimationByName(animationPart.getCurrentAnimationName()).getFrameCount()
                    );
                } else if (visualPart.getIsVisible()) {
                    drawSprite(
                            visualPart.getSpriteName(),
                            positionPart.getX(),
                            positionPart.getY(),
                            positionPart.getRadians(),
                            visualPart.getWidth(),
                            visualPart.getResizable(),
                            visualPart.getHeight()
                    );
                }

            }
        }
        drawFonts();
        batch.end();
        clearSortedVisualList();

        if (gameData.getKeys().isDown(GameKeys.SPACE)) {
            drawHitboxes();
            drawTiles();
        }


    }

    private void drawHitboxes() {
        ShapeRenderer sr = new ShapeRenderer();
        if (world.getMapByPart(ColliderPart.class.getSimpleName()) != null) {
            for (Map.Entry<UUID, EntityPart> entry : world.getMapByPart(ColliderPart.class.getSimpleName()).entrySet()) {

                if (world.getMapByPart(PositionPart.class.getSimpleName()) != null) {
                    PositionPart position = (PositionPart) world.getMapByPart(PositionPart.class.getSimpleName()).get(entry.getKey());
                    if (position != null) {

                        sr.setColor(1, 1, 1, 1);
                        sr.begin(ShapeRenderer.ShapeType.Line);


                        if (((ColliderPart) entry.getValue()).getRadius() != 0) {

                            ArrayList<ColliderPart.Vector2> corners = ((ColliderPart) entry.getValue()).getCornerVecs(position);
                            for (int i = 0, j = corners.size() - 1;
                                 i < corners.size();
                                 j = i++) {

                                sr.line(corners.get(i).x, corners.get(i).y, corners.get(j).x, corners.get(j).y);
                            }
                        }

                        sr.end();
                    }
                }
            }
        }
    }

    private void drawFonts() {
        if (world.getMapByPart("TextPart") != null) {
            for (Map.Entry<UUID, EntityPart> entry : world.getMapByPart("TextPart").entrySet()) {
                PositionPart positionPart = (PositionPart) world.getMapByPart("PositionPart").get(entry.getKey());
                TextPart textPart = (TextPart) world.getMapByPart("TextPart").get(entry.getKey());
                font.draw(batch, textPart.getMessage(), positionPart.getX(), positionPart.getY());
            }
        }
    }

    private void drawTiles() {
        ShapeRenderer shapeRenderer = new ShapeRenderer();
        Tile[][] tiles = Tiles.getInstance(gameData).getTiles();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

        shapeRenderer.setColor(1, 1, 1, 1);

        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                shapeRenderer.line(
                        tiles[i][j].getX(),
                        tiles[i][j].getY(),
                        tiles[i][j].getX() + tiles[i][j].getWidth(),
                        tiles[i][j].getY()
                );

                shapeRenderer.line(
                        tiles[i][j].getX(),
                        tiles[i][j].getY(),
                        tiles[i][j].getX(),
                        tiles[i][j].getY() + tiles[i][j].getHeight()
                );
            }
        }

        shapeRenderer.end();
    }

    private void drawSprite(String spriteName, float x, float y, float radians, float width, boolean resizable, float height) {
        Sprite sprite = sprites.get(spriteName);
        float originalWidth = sprite.getWidth();
        float originalHeight = sprite.getHeight();
        float newHeight;
        if (resizable == true) {
            newHeight = (originalHeight / originalWidth) * width;
        } else {
            newHeight = height;
        }

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

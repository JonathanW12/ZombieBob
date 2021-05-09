package dk.sdu.mmmi.cbse.core.coreprocessors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.GameKeys;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.AnimationPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.ColliderPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.EntityPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.TextPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.VisualPart;
import dk.sdu.mmmi.cbse.common.data.entitytypeparts.PlayerPart;
import dk.sdu.mmmi.cbse.commonanimation.Animation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RenderProcessor {

    private final GameData gameData;
    private final World world;
    private final OrthographicCamera cam;

    private final SpriteBatch batch;
    private final int zDepth;
    private ArrayList<ArrayList<UUID>> sortedVisualList;
    private final BitmapFont font;
    private FreeTypeFontGenerator fontGenerator;
    private final HashMap<String, Sprite> sprites;
    private final HashMap<String, TextureRegion> animationRegions;
    private TextureAtlas textureAtlas;
    private TextureAtlas animationTextureAtlas;
    private ShapeRenderer shapeRenderer;

    public RenderProcessor(GameData gameData, World world, OrthographicCamera cam) {
        this.gameData = gameData;
        this.world = world;
        this.cam = cam;
        batch = new SpriteBatch();
        zDepth = 5;
        sortedVisualList = new ArrayList<>(zDepth);
        sprites = new HashMap<>();
        animationRegions = new HashMap<>();
        shapeRenderer = new ShapeRenderer();

        // Initialize font generator
        try {
            fontGenerator = new FreeTypeFontGenerator(Gdx.files.local("font/Roboto-Light.ttf"));
        } catch (GdxRuntimeException e) {
            fontGenerator = new FreeTypeFontGenerator(Gdx.files.local("../../font/Roboto-Light.ttf"));
        }

        // Initialize assets
        try {
            textureAtlas = new TextureAtlas(Gdx.files.local("assets/sprites.txt"));
            animationTextureAtlas = new TextureAtlas(Gdx.files.local("assets/animations.txt"));

            Texture img1 = new Texture(Gdx.files.local("assets/Hotbar_official.png"));
            sprites.put("hotbar_sprite", new Sprite(img1));

            Texture img3 = new Texture(Gdx.files.local("assets/Background_Test1.png"));
            sprites.put("background_sprite", new Sprite(img3));
        } catch (GdxRuntimeException e) {
            textureAtlas = new TextureAtlas("../../assets/sprites.txt");
            animationTextureAtlas = new TextureAtlas("../../assets/animations.txt");

            Texture img1 = new Texture("../../assets/Hotbar_official.png");
            sprites.put("hotbar_sprite", new Sprite(img1));

            Texture img3 = new Texture("../../assets/Background_Test1.png");
            sprites.put("background_sprite", new Sprite(img3));
        }

        font = getFont();
        font.getData().setScale(0.5f);

        for (int i = 0; i < zDepth; i++) {
            sortedVisualList.add(new ArrayList());
        }

        addSprites();
        addAnimations();
    }
    
    private BitmapFont getFont() {
        BitmapFont font;
        String osName = System.getProperty("os.name");
        
        if (osName.startsWith("Windows")) {
            font = new BitmapFont(new FileHandle("../../font/hotbarFont.fnt"));
        } else {
            font = new BitmapFont(new FileHandle("./skin/hotbarFont.fnt"));
        }
        
        return font;
    }

    public void processRendering(GameData gameData) {
        float lerp = 3f;
        Vector3 camPosition = cam.position;
        
        if (world.getMapByPart(PlayerPart.class.getSimpleName()).keySet().toArray().length > 0) {
            UUID uuid = (UUID) world.getMapByPart(PlayerPart.class.getSimpleName()).keySet().toArray()[0];
            PositionPart playerPositionPart = (PositionPart) world.getMapByPart(PositionPart.class.getSimpleName()).get(uuid);
            cam.unproject(new Vector3(playerPositionPart.getX(), playerPositionPart.getY(), 0));
            
            camPosition.x += (playerPositionPart.getX() - camPosition.x) * lerp * gameData.getDelta();
            camPosition.y += (playerPositionPart.getY() - camPosition.y) * lerp * gameData.getDelta();
            cam.update();

            batch.setProjectionMatrix(cam.combined);
        }
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

    public void draw() {
        // clear screen to black
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
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
                            animationPart.getAnimationByName(
                                    animationPart.getCurrentAnimationName()
                            ).getFrameCount()
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
        }
    }

    private void drawHitboxes() {
        if (world.getMapByPart(ColliderPart.class.getSimpleName()) != null) {
            for (Map.Entry<UUID, EntityPart> entry : world.getMapByPart(ColliderPart.class.getSimpleName()).entrySet()) {

                if (world.getMapByPart(PositionPart.class.getSimpleName()) != null) {
                    PositionPart position = (PositionPart) world.getMapByPart(PositionPart.class.getSimpleName()).get(entry.getKey());
                    if (position != null) {

                        shapeRenderer.setColor(1, 1, 1, 1);
                        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

                        if (((ColliderPart) entry.getValue()).getRadius() != 0) {

                            ArrayList<ColliderPart.Vector2> corners = ((ColliderPart) entry.getValue()).getCornerVecs(position);
                            for (int i = 0, j = corners.size() - 1;
                                    i < corners.size();
                                    j = i++) {

                                shapeRenderer.line(corners.get(i).x / 4, corners.get(i).y / 4, corners.get(j).x / 4, corners.get(j).y / 4);
                            }
                        }

                        shapeRenderer.end();
                    }
                }
            }
        }
    }

    private void drawFonts() {
        font.setUseIntegerPositions(false);
        
        if (world.getMapByPart("TextPart") != null) {
            for (Map.Entry<UUID, EntityPart> entry : world.getMapByPart("TextPart").entrySet()) {
                PositionPart positionPart = (PositionPart) world.getMapByPart("PositionPart").get(entry.getKey());
                TextPart textPart = (TextPart) world.getMapByPart("TextPart").get(entry.getKey());
                font.draw(batch, textPart.getMessage(), positionPart.getX(), positionPart.getY());
            }
        }
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
        Array<TextureAtlas.AtlasRegion> regions = textureAtlas.getRegions();

        for (TextureAtlas.AtlasRegion region : regions) {
            fixBleeding(region);
            Sprite sprite = textureAtlas.createSprite(region.name);
            sprites.put(region.name, sprite);
        }
    }
    
    // From https://stackoverflow.com/questions/27391911/white-vertical-lines-and-jittery-horizontal-lines-in-tile-map-movement
    private static void fixBleeding(TextureRegion region) {
        float fix = 1.25f;

        float x = region.getRegionX();
        float y = region.getRegionY();
        float width = region.getRegionWidth();
        float height = region.getRegionHeight();
        float invTexWidth = 1f / region.getTexture().getWidth();
        float invTexHeight = 1f / region.getTexture().getHeight();
        region.setRegion((x + fix) * invTexWidth, (y + fix) * invTexHeight, (x + width - fix) * invTexWidth, (y + height - fix) * invTexHeight);
    }

    private void addAnimations() {
        Array<TextureAtlas.AtlasRegion> regions = animationTextureAtlas.getRegions();

        for (TextureAtlas.AtlasRegion region : regions) {
            animationRegions.put(region.name, region);
        }
    }

    public void resize(int width, int height) {
        batch.setProjectionMatrix(cam.combined);
    }

    public void dispose() {
        batch.dispose();
        textureAtlas.dispose();
        animationTextureAtlas.dispose();
        shapeRenderer.dispose();
    }

}

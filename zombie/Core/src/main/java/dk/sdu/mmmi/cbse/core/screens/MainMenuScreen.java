package dk.sdu.mmmi.cbse.core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.GdxRuntimeException;
import dk.sdu.mmmi.cbse.common.data.GameKeys;
import dk.sdu.mmmi.cbse.core.main.GameLookup;
import dk.sdu.mmmi.cbse.core.main.ZombieBobGame;
import java.util.HashMap;
import java.util.Map;

public class MainMenuScreen extends MenuScreenTemplate implements Screen {
    
    private BitmapFont title;
    private final SpriteBatch secondaryBatch; // Sprite batch for non-actors
    private final GameLookup gameLookup;
    private int currentlyDisplayedMap;
    private final Map<String, String> allMaps;
    private String currentMapName;
    private Texture currentMapThumbnail;
    
    // Scene actors
    private Label mapLabel;
    private Image mapImage, nextMapButton, prevMapButton;
    
    public MainMenuScreen(ZombieBobGame game) {
        super(game);
        secondaryBatch = new SpriteBatch();
        gameLookup = GameLookup.getInstance(getGameData(), getWorld());
        currentlyDisplayedMap = 0;
        allMaps = new HashMap<>();
        
        setupMaps();
        setupUI();
    }
    
    private void setupUI() {
        title = getTitleFont();
        setupMapUI();
        addNextMapButton();
        addPrevMapButton();
    }
    
    private void setupMapUI() {
        float thumbnailWidth = 800;
        float thumbnailHeight = 450; 
        float labelWidth = 275;
        float labelHeight = 75;
        
        // Create map thumbnail image
        mapImage = new Image();
        mapImage.setBounds(
            getStage().getWidth() / 2 - thumbnailWidth / 2,
            250,
            thumbnailWidth,
            thumbnailHeight
        );
        
        // Create map name label
        mapLabel = new Label("", getSkin(), "title");
        mapLabel.setBounds(
            getStage().getWidth() / 2 - labelWidth / 2,
            100,
            labelWidth,
            labelHeight
        );
        mapLabel.setAlignment(Align.center);
        
        // Add actors to stage and set their values
        getStage().addActor(mapImage);
        getHoverButtonGroup().addActor(mapLabel);
        setMap();
    }
    
    private void setupMaps() {
        Map<String, String> mapFiles = gameLookup.getMapLookup().findAllMaps();
        mapFiles.entrySet().forEach(mapFile -> {
            allMaps.put(mapFile.getKey(), mapFile.getValue());
        });
    }
    
    private void setMap() {
        int mapIndex = currentlyDisplayedMap;
        int currentIndex = 0;
        
        for (Map.Entry<String, String> map: allMaps.entrySet()) {
            if (currentIndex == mapIndex) {
                currentMapName = map.getKey();
                currentMapThumbnail = getMapImageTexture(map.getValue());
            }
            
            currentIndex++;
        }
        
        mapImage.setDrawable(new SpriteDrawable(new Sprite(currentMapThumbnail)));
        mapLabel.setText("Map: " + getCapitalizedMapName());
    }
    
    private String getCapitalizedMapName() {
        return (currentMapName.substring(0,1).toUpperCase()) + currentMapName.substring(1);
    }
    
    private void addNextMapButton() {      
        nextMapButton = new Image(getButtonTexture("right-nav-button.png"));
        nextMapButton.setPosition(
            getStage().getWidth() / 2 + 150,
            100
        );
        getHoverButtonGroup().addActor(nextMapButton);
    }
    
    private void addPrevMapButton() {
        float buttonWidth = 55;
        
        prevMapButton = new Image(getButtonTexture("left-nav-button.png"));
        prevMapButton.setPosition(
            getStage().getWidth() / 2 - 150 - buttonWidth,
            100
        );
        getHoverButtonGroup().addActor(prevMapButton);
    }
    
    public void nextMap() {
        currentlyDisplayedMap = (currentlyDisplayedMap + 1) % allMaps.size();
        setMap();
    }
    
    public void prevMap() {
        currentlyDisplayedMap = (currentlyDisplayedMap - 1) % allMaps.size();
        setMap();
    }
    
    private Texture getMapImageTexture(String fileName) {
        Texture imageTexture;
        
        try {
            imageTexture = new Texture(Gdx.files.local("./maps/thumbnails/" + fileName));
        } catch (GdxRuntimeException e) {
            imageTexture = new Texture(Gdx.files.local("../../maps/thumbnails/" + fileName));
        }
        
        return imageTexture;
    }
    
    @Override
    public void render(float delta) {
        super.render(delta);
        
        secondaryBatch.begin();
        
        float estimatedTitleWidth = 625;
        title.setColor(0.541f, 0.011f, 0.011f, 1);
        title.draw(
            secondaryBatch,
            "ZombieBob",
            getStage().getWidth() / 2 - estimatedTitleWidth / 2,
            getStage().getHeight() - 150
        );
        
        secondaryBatch.end();
        
        update();
    }
    
    @Override
    public void update() { 
        super.update();
        
        if (getGameData().getKeys().isPressed(GameKeys.RIGHT)) {
            nextMap();
        }
    }
    
    @Override
    public void show() { 
        super.show();
    }
    
}

package dk.sdu.mmmi.cbse.core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dk.sdu.mmmi.cbse.common.data.GameKeys;
import dk.sdu.mmmi.cbse.core.main.ZombieBobGame;

public class MainMenuScreen extends MenuScreenTemplate implements Screen {
    
    private BitmapFont title;
    private SpriteBatch secondaryBatch; // Sprite batch for non-actors
    
    public MainMenuScreen(ZombieBobGame game) {
        super(game);
        secondaryBatch = new SpriteBatch();
        
        setupUI();
    }
    
    private void setupUI() {
        title = getTitleFont();
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
    
    private void update() {
        if (getGameData().getKeys().isPressed(GameKeys.ESCAPE)) {
            Gdx.app.exit();
        }
    }
    
}

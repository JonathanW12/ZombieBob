package dk.sdu.mmmi.cbse.core.main;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.core.managers.GameInputProcessor;
import dk.sdu.mmmi.cbse.core.screens.MainMenuScreen;

public class ZombieBobGame extends Game implements ApplicationListener {
    
    private GameData gameData;
    private World world;
    
    @Override
    public void create() {
        gameData = new GameData();
        world = new World();
           
        gameData.setDisplayWidth(Gdx.graphics.getWidth());
        gameData.setDisplayHeight(Gdx.graphics.getHeight());
        
        setupInputProcessors();

        setScreen(new MainMenuScreen(this));
    }
    
    public GameData getGameData() {
        return gameData;
    }
    
    public World getWorld() {
        return world;
    }
    
    private void setupInputProcessors() {
        InputProcessor keyInputProcessor = new GameInputProcessor(gameData);
        Gdx.input.setInputProcessor(keyInputProcessor); 
    }
    
}

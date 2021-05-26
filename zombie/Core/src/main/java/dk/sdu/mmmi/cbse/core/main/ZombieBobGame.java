package dk.sdu.mmmi.cbse.core.main;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.core.coreprocessors.AudioProcessor;
import dk.sdu.mmmi.cbse.core.screens.MainMenuScreen;

public class ZombieBobGame extends Game implements ApplicationListener {

    private GameData gameData;
    private World world;
    private AudioProcessor audioProcessor;

    @Override
    public void create() {
        gameData = new GameData();
        world = new World();

        gameData.setDisplayWidth(Gdx.graphics.getWidth());
        gameData.setDisplayHeight(Gdx.graphics.getHeight());

        audioProcessor = new AudioProcessor(world);

        setScreen(new MainMenuScreen(this));
    }

    public GameData getGameData() {
        return gameData;
    }

    public World getWorld() {
        return world;
    }

    public AudioProcessor getAudioProcessor() {
        return audioProcessor;
    }

    @Override
    public void dispose() {
        audioProcessor.dispose();
    }

}

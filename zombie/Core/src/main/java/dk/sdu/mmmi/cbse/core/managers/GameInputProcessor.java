package dk.sdu.mmmi.cbse.core.managers;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.GameKeys;

public class GameInputProcessor extends InputAdapter {

    private final GameData gameData;

    public GameInputProcessor(GameData gameData) {
        this.gameData = gameData;
    }

    
    @Override
    public boolean keyDown(int k) {
        if (k == Keys.UP || k == Keys.W) {
            gameData.getKeys().setKey(GameKeys.UP, true);
        }
        if (k == Keys.LEFT || k == Keys.A) {
            gameData.getKeys().setKey(GameKeys.LEFT, true);
        }
        if (k == Keys.DOWN || k == Keys.S) {
            gameData.getKeys().setKey(GameKeys.DOWN, true);
        }
        if (k == Keys.RIGHT || k == Keys.D) {
            gameData.getKeys().setKey(GameKeys.RIGHT, true);
        }
        if (k == Keys.ENTER) {
            gameData.getKeys().setKey(GameKeys.ENTER, true);
        }
        if (k == Keys.ESCAPE) {
            gameData.getKeys().setKey(GameKeys.ESCAPE, true);
        }
        if (k == Keys.SPACE) {
            gameData.getKeys().setKey(GameKeys.SPACE, true);
        }
        if (k == Keys.E) {
            gameData.getKeys().setKey(GameKeys.E, true);
        }
        if (k == Keys.SHIFT_LEFT || k == Keys.SHIFT_RIGHT) {
            gameData.getKeys().setKey(GameKeys.SHIFT, true);
        }
        return true;
    }

    @Override
    public boolean keyUp(int k) {
        if (k == Keys.UP || k == Keys.W) {
            gameData.getKeys().setKey(GameKeys.UP, false);
        }
        if (k == Keys.LEFT || k == Keys.A) {
            gameData.getKeys().setKey(GameKeys.LEFT, false);
        }
        if (k == Keys.DOWN || k == Keys.S) {
            gameData.getKeys().setKey(GameKeys.DOWN, false);
        }
        if (k == Keys.RIGHT || k == Keys.D) {
            gameData.getKeys().setKey(GameKeys.RIGHT, false);
        }
        if (k == Keys.ENTER) {
            gameData.getKeys().setKey(GameKeys.ENTER, false);
        }
        if (k == Keys.ESCAPE) {
            gameData.getKeys().setKey(GameKeys.ESCAPE, false);
        }
        if (k == Keys.SPACE) {
            gameData.getKeys().setKey(GameKeys.SPACE, false);
        }
        if (k == Keys.SHIFT_LEFT || k == Keys.SHIFT_RIGHT) {
            gameData.getKeys().setKey(GameKeys.SHIFT, false);
        }
        return true;
    }

}

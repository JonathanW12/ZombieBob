package dk.sdu.mmmi.cbse.core.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.MouseMovement;

public class MouseInputProcessor implements InputProcessor {

    private final GameData gameData;
    private final OrthographicCamera cam;

    public MouseInputProcessor(GameData gameData, OrthographicCamera cam) {
        this.gameData = gameData;
        this.cam = cam;
    }


    @Override
    public boolean keyDown(int i) {
        return false;
    }

    @Override
    public boolean keyUp(int i) {
        return false;
    }

    @Override
    public boolean keyTyped(char c) {
        return false;
    }

    @Override
    public boolean touchDown(int i, int i1, int i2, int i3) {

        if (i3 == Input.Buttons.LEFT){
            gameData.getMouse().setKey(MouseMovement.LEFTCLICK, true);
        }
        if (i3 == Input.Buttons.RIGHT){
            gameData.getMouse().setKey(MouseMovement.RIGHTCLICK, true);
        }
        if (i3 == Input.Buttons.MIDDLE){
            gameData.getMouse().setKey(MouseMovement.MIDDLECLICK, true);
        }
        return false;
    }

    @Override
    public boolean touchUp(int i, int i1, int i2, int i3) {

        if (i3 == Input.Buttons.LEFT){
            gameData.getMouse().setKey(MouseMovement.LEFTCLICK, false);
        }
        if (i3 == Input.Buttons.RIGHT){
            gameData.getMouse().setKey(MouseMovement.RIGHTCLICK, false);
        }
        if (i3 == Input.Buttons.MIDDLE){
            gameData.getMouse().setKey(MouseMovement.MIDDLECLICK, false);
        }

        return false;
    }

    @Override
    public boolean touchDragged(int i, int i1, int i2) {
        Vector3 mousePosition = cam.unproject(new Vector3(i,i1,0));
        gameData.getMouse().setMousePosition(mousePosition.x,mousePosition.y);

        return false;
    }

    @Override
    public boolean mouseMoved(int i, int i1) {

        Vector3 mousePosition = cam.unproject(new Vector3(Gdx.input.getX(),Gdx.input.getY(),0));
        gameData.getMouse().setMousePosition(mousePosition.x,mousePosition.y);

        return false;
    }

    @Override
    public boolean scrolled(int i) {

        gameData.getMouse().setScroll(i);

        return false;
    }

}

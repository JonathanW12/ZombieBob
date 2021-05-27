package dk.sdu.mmmi.cbse.commonlocations;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.Position;

import java.util.Random;

public class SpawnerLocation {

    private int topBarOffset = 250;
    private int wallOffset = 65;

    private Position position = new Position();

    public Position topLeft(GameData gameData) {
        position.setX(50);
        position.setY(gameData.getDisplayHeight() - topBarOffset);
        return position;
    }

    public Position topMid(GameData gameData) {
        position.setX(gameData.getDisplayWidth() / 2);
        position.setY(gameData.getDisplayHeight() - topBarOffset);
        return position;
    }

    public Position topRight(GameData gameData) {
        position.setX(gameData.getDisplayWidth() - 50);
        position.setY(gameData.getDisplayHeight() - topBarOffset);
        return position;
    }

    public Position midLeft(GameData gameData) {
        position.setX(wallOffset);
        position.setY(gameData.getDisplayHeight() / 2);
        return position;
    }

    public Position center(GameData gameData) {
        position.setX(gameData.getDisplayWidth() / 2);
        position.setY(gameData.getDisplayHeight() / 2);
        return position;
    }

    public Position midRight(GameData gameData) {
        position.setX(gameData.getDisplayWidth() - wallOffset);
        position.setY(gameData.getDisplayHeight() / 2);
        return position;
    }

    public Position bottomLeft() {
        position.setX(wallOffset);
        position.setY(wallOffset);
        return position;
    }

    public Position bottomMid(GameData gameData) {
        position.setX(gameData.getDisplayWidth() / 2);
        position.setY(wallOffset);
        return position;
    }

    public Position bottomRight(GameData gameData) {
        position.setX(gameData.getDisplayWidth() - wallOffset);
        position.setY(wallOffset);
        return position;
    }

    public Position random(GameData gameData) {
        Random r = new Random();
        int low = wallOffset;
        int highX = gameData.getDisplayWidth() - wallOffset;
        int highY = gameData.getDisplayHeight() - wallOffset;
        int resultX = r.nextInt(highX - low) + low;
        int resultY = r.nextInt(highY - low) + low;

        position.setX(resultX);
        position.setY(resultY);

        return position;
    }

}

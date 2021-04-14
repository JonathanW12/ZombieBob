package dk.sdu.mmmi.cbse.aimovementsystem;

import com.badlogic.gdx.Gdx;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;

public class Pathfinding {
    public void findTarget(MovingPart movingPart, PositionPart entityPosistionPart, GameData gameData){
        movingPart.setUp(true);
        entityPosistionPart.setRadians((float) Math.atan2(gameData.getMouse().getY()-entityPosistionPart.getY(), gameData.getMouse().getX()-entityPosistionPart.getX()));
    }

    public void findMouse(PositionPart entityPosistionPart, GameData gameData){
        entityPosistionPart.setRadians((float) Math.atan2(gameData.getMouse().getY()-entityPosistionPart.getY(), gameData.getMouse().getX()-entityPosistionPart.getX()));
    }
}

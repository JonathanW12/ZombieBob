package dk.sdu.mmmi.cbse.aimovementsystem;

import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;

public class Pathfinding {
    public void findTarget(MovingPart movingPart,PositionPart entityPosistionPart, PositionPart targetPosistionPart){
        movingPart.setUp(true);
        entityPosistionPart.setRadians((float) Math.atan2(targetPosistionPart.getY()-entityPosistionPart.getY(), targetPosistionPart.getX()-entityPosistionPart.getX()));
    }
}

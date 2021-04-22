package dk.sdu.mmmi.cbse.aimovementsystem;

import com.badlogic.gdx.Gdx;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.EntityPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.data.entitytypeparts.PlayerPart;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class Pathfinding {
    public void findPlayer(MovingPart movingPart, PositionPart entityPosistionPart, World world){
        Set<Map.Entry<UUID, EntityPart>> playerUUID = world.getMapByPart(PlayerPart.class.getSimpleName()).entrySet();
        
        if (world.getMapByPart(PlayerPart.class.getSimpleName()).keySet().toArray().length > 0) {
            UUID uuid = (UUID) world.getMapByPart(PlayerPart.class.getSimpleName()).keySet().toArray()[0];
            PositionPart playerPositionPart = (PositionPart) world.getMapByPart(PositionPart.class.getSimpleName()).get(uuid);
            
            movingPart.setUp(true);
            entityPosistionPart.setRadians((float) Math.atan2(playerPositionPart.getY()-entityPosistionPart.getY(), playerPositionPart.getX()-entityPosistionPart.getX()));
        } 
    }

    public void findMouse(PositionPart entityPosistionPart, GameData gameData){
        entityPosistionPart.setRadians((float) Math.atan2(gameData.getMouse().getY()-entityPosistionPart.getY(), gameData.getMouse().getX()-entityPosistionPart.getX()));
    }
}

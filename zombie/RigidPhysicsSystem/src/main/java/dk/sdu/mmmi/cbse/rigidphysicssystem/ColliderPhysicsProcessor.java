/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.rigidphysicssystem;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.ColliderPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.EntityPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.data.entitytypeparts.PlayerPart;
import dk.sdu.mmmi.cbse.common.data.entitytypeparts.StructurePart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
import java.util.Map;
import java.util.UUID;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author phili
 */
@ServiceProvider(service = IPostEntityProcessingService.class)
public class ColliderPhysicsProcessor implements IPostEntityProcessingService{

    @Override
    public void process(GameData gameData, World world) {
        
        if (world.getMapByPart(MovingPart.class.getSimpleName()) != null){
            for (Map.Entry<UUID,EntityPart> entry : world.getMapByPart(MovingPart.class.getSimpleName()).entrySet()){
                ColliderPart collider = (ColliderPart)world.getMapByPart(ColliderPart.class.getSimpleName()).get(entry.getKey());
                if(collider!= null){
                    if(!collider.getCollidingEntities().isEmpty()){
                        PositionPart position1 = (PositionPart)world.getMapByPart(PositionPart.class.getSimpleName()).get(entry.getKey());
                        if(position1 != null){
                            
                            
                            
                            PositionPart position2 = (PositionPart)world.getMapByPart(PositionPart.class.getSimpleName()).get(collider.getCollidingEntities().get(0));
                            if(position2 != null){
                            for(UUID collidingEntity: collider.getCollidingEntities()){
                                StructurePart structure = (StructurePart)world.getMapByPart(StructurePart.class.getSimpleName()).get(collidingEntity);
                                if(structure == null){
                                    continue;
                                } else {
                                    position2 = (PositionPart)world.getMapByPart(PositionPart.class.getSimpleName()).get(collidingEntity);
                                    break;
                                }
                            }
                            
                            
                            
                            //we need to make sure, that strutures dominate the offset
                            float xOffset = position2.getX() - position1.getX();
                            float yOffset = position2.getY() - position1.getY();
                            
                            float dx = ((MovingPart)entry.getValue()).getDx();
                            float dy = ((MovingPart)entry.getValue()).getDy();
                            
                            
                            position1.setX(position1.getX() - xOffset/50-dx*1.1f);
                            position1.setY(position1.getY() - yOffset/50-dy*1.1f);
                            }
                        }
                    }
                }
            }
        }
    }
}

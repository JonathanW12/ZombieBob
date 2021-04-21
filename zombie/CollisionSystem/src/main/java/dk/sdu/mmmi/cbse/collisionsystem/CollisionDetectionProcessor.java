/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.collisionsystem;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.ColliderPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.EntityPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import java.util.Map;
import java.util.UUID;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

/**
 *
 * @author phili
 */

@ServiceProviders(value = {
    @ServiceProvider(service = IEntityProcessingService.class)})
public class CollisionDetectionProcessor implements IEntityProcessingService {

    @Override
    public void process(GameData gameData, World world) {
        for (Map.Entry<UUID, EntityPart> entry: world.getMapByPart(ColliderPart.class.getSimpleName()).entrySet()) {
            
            ColliderPart collider1 = ((ColliderPart)entry.getValue());
            collider1.getCollidingEntities().clear();
            
            PositionPart collider1Pos = ((PositionPart)world.getMapByPart("PositionPart").get(entry.getKey()));
            
            if(collider1Pos != null){
                if(collider1.getRadius() != 0){
                    for (Map.Entry<UUID, EntityPart> entry2: world.getMapByPart("ColliderPart").entrySet()) {
                        //collider1 should not check collision on itself
                        if(entry.getKey() != entry2.getKey()){
                            ColliderPart collider2 = ((ColliderPart)entry.getValue());
                            PositionPart collider2Pos = ((PositionPart)world.getMapByPart("PositionPart").get(entry2.getKey()));
                            float distance = (float)Math.sqrt(Math.pow(collider1Pos.getX() - collider2Pos.getX(),2) + Math.pow(collider1Pos.getY() - collider2Pos.getY(),2));
                            if(distance < collider1.getRadius() + collider2.getRadius()){
                                collider1.getCollidingEntities().add(entry2.getKey());
                            }
                        }

                    } else if (collider1.getWidth() != 0 && collider1.getHeight() != 0) {

                    }
                
                }else if(collider1.getWidth() != 0 && collider1.getHeight() != 0){
                    //check for box collision
                }
                if (!collider1.getCollidingEntities().isEmpty()) {
                    //System.out.println(collider1.getCollidingEntities().toString());
                }
            }
        }
    }
}


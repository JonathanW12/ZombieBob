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
import dk.sdu.mmmi.cbse.common.data.entitytypeparts.StructurePart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import java.util.Map;
import java.util.UUID;

import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

/**
 *
 * @author phili
 */

@ServiceProviders(value = {
    @ServiceProvider(service = IEntityProcessingService.class)})
public class CollisionDetectionProcessor implements IEntityProcessingService {
    BoxCollisionChecker boxCollisionChecker = new BoxCollisionChecker();

    @Override
    public void process(GameData gameData, World world) {
        if(world.getMapByPart(ColliderPart.class.getSimpleName())!= null){
            for (Map.Entry<UUID, EntityPart> entry: world.getMapByPart(ColliderPart.class.getSimpleName()).entrySet()) {
                ((ColliderPart)entry.getValue()).getCollidingEntities().clear();
                
            }
        }
        
        
        if(world.getMapByPart(ColliderPart.class.getSimpleName())!= null){
            for (Map.Entry<UUID, EntityPart> entry: world.getMapByPart(ColliderPart.class.getSimpleName()).entrySet()) {
                
            
                ColliderPart collider1 = ((ColliderPart)entry.getValue());            
                PositionPart collider1Pos = ((PositionPart)world.getMapByPart("PositionPart").get(entry.getKey()));
            
                if(collider1Pos != null){
                    for (Map.Entry<UUID, EntityPart> entry2: world.getMapByPart("ColliderPart").entrySet()) {
                        
                        //checking if both entities are structures. If so, dont process collision on them. 
                        StructurePart structure1 = ((StructurePart)world.getMapByPart("StructurePart").get(entry.getKey()));
                        StructurePart structure2 = ((StructurePart)world.getMapByPart("StructurePart").get(entry2.getKey()));
                        if(structure1 == null || structure2 == null ){
                            
                            ColliderPart collider2 = ((ColliderPart)entry2.getValue());
                            PositionPart collider2Pos = ((PositionPart)world.getMapByPart("PositionPart").get(entry2.getKey()));
                            
                        
                    //collider1 should not check collision on itself, or on colliders that already processed
                        if(entry.getKey() != entry2.getKey() /*&& !collider1.getCollidingEntities().contains(entry2.getKey()) && collider2.getCollidingEntities().contains(entry.getKey())*/){
                                
                                
                            if(collider1.getRadius()!= 0 && collider2.getRadius()!=0){
                                float distance = (float)Math.sqrt(Math.pow(collider1Pos.getX() - collider2Pos.getX(),2) + Math.pow(collider1Pos.getY() - collider2Pos.getY(),2));
                                if(distance < collider1.getRadius() + collider2.getRadius()){
                                    collider1.getCollidingEntities().add(entry2.getKey());
                                    collider2.getCollidingEntities().add(entry.getKey());
                                }
                            }
                            else if(collider1.getHeight()!=0 && collider2.getHeight()!=0){
                                if(boxCollisionChecker.areColliding(collider1, collider1Pos, collider2, collider2Pos)){
                                    collider1.getCollidingEntities().add(entry2.getKey());
                                    collider2.getCollidingEntities().add(entry.getKey());
                                }
                            }
                            else if(collider1.getHeight()!=0 && collider2.getRadius()!=0){
                                if(boxCollisionChecker.areColliding2(collider1, collider1Pos, collider2, collider2Pos)){
                                    //System.out.println("is colliding with poly");
                                    collider1.getCollidingEntities().add(entry2.getKey());
                                    collider2.getCollidingEntities().add(entry.getKey());
                                }
//                                if(boxCollisionChecker.areColliding2(collider1, collider1Pos, collider2, collider2Pos)){
//                                    System.out.println("yeah");
//                                }
                            }
                        }
                    }
                }
            }
        }

    
    }
    
}}


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.projectilesystem;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityammoparts.BulletAmmoPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.ColliderPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.EntityPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.ProjectilePart;
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
public class ProjectileProcessingSystem implements IEntityProcessingService {

    @Override
    public void process(GameData gameData, World world) {
         if(world.getMapByPart(ProjectilePart.class.getSimpleName())!= null){
             for(Map.Entry<UUID,EntityPart> entry : world.getMapByPart(ProjectilePart.class.getSimpleName()).entrySet()){
                 ColliderPart collider = (ColliderPart)world.getMapByPart("ColliderPart").get(entry.getKey());
                 if(collider != null){
                     if(!collider.getCollidingEntities().isEmpty()){
                         LifePart lifepart = (LifePart)world.getMapByPart("LifePart").get(entry.getKey());
                         if(lifepart != null){
                             lifepart.setDead(true);
                             System.out.println("projectile is has collided");
                         }
                     }
                 }
                 
                 
                 //PositionPart projectilePosition = ((PositionPart)world.getMapByPart(PositionPart.class.getSimpleName()).get(entry.getKey()));
                 //System.out.println("X: "+projectilePosition.getX()+" , Y: "+projectilePosition.getY()+" , radians: "+ projectilePosition.getRadians());
                 ProjectilePart projectilePart = (ProjectilePart)entry.getValue();
                 MovingPart movingPart = ((MovingPart)world.getMapByPart(MovingPart.class.getSimpleName()).get(entry.getKey()));
                 float projectileTravel = (float)Math.sqrt(((double)movingPart.getDx()*movingPart.getDx()+ movingPart.getDy()*movingPart.getDy()));
                 projectilePart.setCurrentTravelDistance(projectilePart.getCurrentTravelDistance() + projectileTravel);
                 
                 if(projectilePart.getMaxTravelDistance() < projectilePart.getCurrentTravelDistance()){
                     world.removeEntityParts(entry.getKey());
                     //System.out.println("projectile destroyed");
                 }
                
             }
         }
    }
}

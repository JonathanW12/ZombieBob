package dk.sdu.mmmi.cbse.healthsystem;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.ColliderPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.DamagePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.EntityPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.TimerPart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

import java.util.Map;
import java.util.UUID;

@ServiceProviders(value = {
        @ServiceProvider(service = IEntityProcessingService.class)})

public class HealthProcessingSystem implements IEntityProcessingService {

    @Override
    public void process(GameData gameData, World world) {

        // Check if hashmap exists
        if (world.getMapByPart(LifePart.class.getSimpleName()) != null){

            // Loops through all LifeParts in hashmap
            for (Map.Entry<UUID, EntityPart> entry : world.getMapByPart(LifePart.class.getSimpleName()).entrySet()){

                // process specific lifeParts
                LifePart lifePart = (LifePart) world.getMapByPart(LifePart.class.getSimpleName()).get(entry.getKey());

                if (world.getMapByPart(ColliderPart.class.getSimpleName()) != null){
                    ColliderPart collider = (ColliderPart)world.getMapByPart("ColliderPart").get(entry.getKey());
                    if(collider != null){
                        for(UUID collidingEntity: collider.getCollidingEntities()) {
                            if (world.getMapByPart(DamagePart.class.getSimpleName()) != null) {
                                DamagePart damagePart = (DamagePart) world.getMapByPart("DamagePart").get(collidingEntity);
                                if (damagePart != null) {
                                    lifePart.setLife(lifePart.getLife() - damagePart.getDamage());
                                }
                            } //#
                        }
                    }
                }
                

                        // If health is 0 dead true
                        if (lifePart.getLife() <= 0){
                            lifePart.setDead(true);
                        }

                        // If dead remove
                        if (lifePart.isDead()){
                        world.removeEntityParts(entry.getKey());
                        } else {

                            // If isHit remove some health
                            if (lifePart.getIsHit()){
                                lifePart.setLife(lifePart.getLife()-1);
                                lifePart.setIsHit(false);
                            }
                        }   
                    }
                }
            }
        }
    

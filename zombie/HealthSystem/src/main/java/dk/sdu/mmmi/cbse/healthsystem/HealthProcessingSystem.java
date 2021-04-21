package dk.sdu.mmmi.cbse.healthsystem;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.*;
import dk.sdu.mmmi.cbse.common.data.entitytypeparts.EnemyPart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
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
        if (world.getMapByPart(LifePart.class.getSimpleName()) != null) {

            // Loops through all LifeParts in hashmap
            for (Map.Entry<UUID, EntityPart> entry : world.getMapByPart(LifePart.class.getSimpleName()).entrySet()) {

                // Check if LifePart is connected with DamagePart
                if (world.getMapByPart(DamagePart.class.getSimpleName()) != null ){
                    if (world.getMapByPart(DamagePart.class.getSimpleName()).get(entry.getKey()) != null){
                        continue;
                    }
                }

                // setting specific lifeParts
                LifePart lifePart = (LifePart) world.getMapByPart(LifePart.class.getSimpleName()).get(entry.getKey());

                // Check if hashmap with colliderParts exists
                if (world.getMapByPart(ColliderPart.class.getSimpleName()) != null) {
                    ColliderPart collider = (ColliderPart) world.getMapByPart(ColliderPart.class.getSimpleName()).get(entry.getKey());

                    // check if colliderPart exists with specific UUID
                    if (collider != null) {

                        // WE know collider is not a bullet
                        for (UUID collidingEntity : collider.getCollidingEntities()) {

                            // Check if hashmap with damagaPart exists
                            if (world.getMapByPart(DamagePart.class.getSimpleName()) != null) {
                                DamagePart damagePart = (DamagePart) world.getMapByPart(DamagePart.class.getSimpleName()).get(collidingEntity);
                                // Check if damagePart exists on collidingEntity
                                if (damagePart != null) {
                                    // ITS A PROJECTILE
                                    lifePart.setLife(lifePart.getLife() - damagePart.getDamage());
                                    // Removing bullet after impact
                                    world.removeEntityParts(collidingEntity);
                                }
                            }
                            // If health is 0 dead true
                            if (lifePart.getLife() <= 0) {
                                lifePart.setDead(true);
                            }

                            // If dead remove
                            if (lifePart.isDead()) {
                                world.removeEntityParts(entry.getKey());
                            }
                        }
                    }
                }
            }
        }
    }
}
    

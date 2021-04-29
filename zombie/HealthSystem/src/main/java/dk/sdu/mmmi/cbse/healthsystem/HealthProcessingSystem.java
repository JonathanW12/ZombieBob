package dk.sdu.mmmi.cbse.healthsystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
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

    private LifePart lifePartOfDmger;
    private LifePart lifePartCollidingEntity;
    private DamagePart damagePart;
    //scuff method for counting dead zombies
    private int zombiesKilled = 0;

    @Override
    public void process(GameData gameData, World world) {

        // Check if hashmap exists
        if (world.getMapByPart(DamagePart.class.getSimpleName()) != null) {

            // Loops through all DamageParts in hashmap
            for (Map.Entry<UUID, EntityPart> entry : world.getMapByPart(DamagePart.class.getSimpleName()).entrySet()) {

                // setting specific lifeParts
                if (world.getMapByPart(LifePart.class.getSimpleName()) != null) {
                    lifePartOfDmger = (LifePart) world.getMapByPart(LifePart.class.getSimpleName()).get(entry.getKey());
                }

                if (world.getMapByPart(DamagePart.class.getSimpleName()) != null) {
                    damagePart = (DamagePart) world.getMapByPart(DamagePart.class.getSimpleName()).get(entry.getKey());
                }

                // Check if hashmap with colliderParts exists
                if (world.getMapByPart(ColliderPart.class.getSimpleName()) != null) {
                    ColliderPart collider = (ColliderPart) world.getMapByPart(ColliderPart.class.getSimpleName()).get(entry.getKey());

                    // check if colliderPart exists with specific UUID
                    if (collider != null) {

                        for (UUID collidingEntity : collider.getCollidingEntities()) {

                            lifePartCollidingEntity = (LifePart) world.getMapByPart(LifePart.class.getSimpleName()).get(collidingEntity);

                            if (damagePart != null) {
                                if (lifePartCollidingEntity != null) {
                                    lifePartCollidingEntity.setLife(lifePartCollidingEntity.getLife() - damagePart.getDamage());
                                }
                                // Removing bullet after impact
                                world.removeEntityParts(entry.getKey());
                            }

                            if (lifePartCollidingEntity != null && lifePartCollidingEntity.getLife() <= 0) {
                                lifePartCollidingEntity.setDead(true);
                            }

                            // If dead remove
                            if (lifePartCollidingEntity != null && lifePartCollidingEntity.isDead()) {
                                world.removeEntityParts(collidingEntity);
                                //TO BE CHANGED.
                                gameData.getLevelInformation().setEnemiesKilled(++zombiesKilled);

                            }
                        }
                    }
                }
            }
        }
    }

}
    

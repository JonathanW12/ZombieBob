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
        if (world.getMapByPart(LifePart.class.getSimpleName()) != null) {

            // Loops through all Lifeparts in hashmap
            for (Map.Entry<UUID, EntityPart> entry : world.getMapByPart(LifePart.class.getSimpleName()).entrySet()) {
                LifePart lifepart = (LifePart) world.getMapByPart(LifePart.class.getSimpleName()).get(entry.getKey());
                if (lifepart != null && lifepart.getLife() <= 0) {
                    lifepart.setDead(true);
                }

                // If dead remove
                if (lifepart != null && lifepart.isDead()) {
                    world.removeEntityParts(entry.getKey());
                }

            }
        }

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

                                // If bullet can explode, set explosive part to true
                                if (world.getMapByPart(ExplosivePart.class.getSimpleName()) != null) {

                                    ExplosivePart explosivePart = (ExplosivePart) world.getMapByPart(ExplosivePart.class.getSimpleName()).get(entry.getKey());
                                    if (explosivePart != null) {
                                        explosivePart.setIsReadyToExplode(true);
                                    }
                                }
                                LifePart lifePart = (LifePart) world.getMapByPart(LifePart.class.getSimpleName()).get(entry.getKey());
                                // Removing bullet after impact
                                if (lifePart != null) {
                                    lifePart.setDead(true);
                                }
                            }

                            if (lifePartCollidingEntity != null && lifePartCollidingEntity.getLife() <= 0) {
                                lifePartCollidingEntity.setDead(true);
                            }

                            // If dead remove
                            if (lifePartCollidingEntity != null && lifePartCollidingEntity.isDead()) {

                                //TO BE CHANGED.
                                if (world.getMapByPart(EnemyPart.class.getSimpleName()).get(collidingEntity) != null) {
                                    gameData.getLevelInformation().setEnemiesKilled(++zombiesKilled);
                                }
                                world.removeEntityParts(collidingEntity);
                            }
                        }
                    }
                }
            }
        }
    }

}
    

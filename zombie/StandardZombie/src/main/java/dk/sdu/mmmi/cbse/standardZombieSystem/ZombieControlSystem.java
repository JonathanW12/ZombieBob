package dk.sdu.mmmi.cbse.standardZombieSystem;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.entityparts.*;
import dk.sdu.mmmi.cbse.common.data.entitytypeparts.EnemyPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.VisualPart;
import dk.sdu.mmmi.cbse.common.data.entitytypeparts.PlayerPart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;
import java.util.Map;
import java.util.UUID;

@ServiceProviders(value = {
    @ServiceProvider(service = IEntityProcessingService.class)})
public class ZombieControlSystem implements IEntityProcessingService {
    
    private final long spawnDelay = 5000;
    private long currentTime = System.currentTimeMillis();
    private long lastSpawnTime = currentTime;

    @Override
    public void process(GameData gameData, World world) {
        if (world.getMapByPart(EnemyPart.class.getSimpleName()) != null) {
            for (Map.Entry<UUID,EntityPart> entry : world.getMapByPart(EnemyPart.class.getSimpleName()).entrySet()){
            
                PositionPart positionPart = (PositionPart) world.getMapByPart("PositionPart").get(entry.getKey());
                MovingPart movingPart = (MovingPart) world.getMapByPart("MovingPart").get(entry.getKey());
                VisualPart visualPart = (VisualPart) world.getMapByPart("VisualPart").get(entry.getKey());
                AnimationPart animationPart = (AnimationPart) world.getMapByPart(AnimationPart.class.getSimpleName()).get(entry.getKey());
                CombatPart combatPart = (CombatPart) world.getMapByPart(CombatPart.class.getSimpleName()).get(entry.getKey());
                
                attackPlayer(gameData, world, entry.getKey());

                // Animation processing
                if (!animationPart.isCurrentAnimationInterruptible() && !animationPart.hasCurrentAnimationLooped()) {
                    animationPart.setIsAnimated(true);
                } else {
                    animationPart.setIsAnimated(
                        (movingPart.isDown() || movingPart.isLeft() || movingPart.isRight() || movingPart.isUp() || combatPart.isAttacking())
                    );
                }

                visualPart.setSpriteName("spiderIdle");
                if (movingPart.isDown() || movingPart.isLeft() || movingPart.isRight() || movingPart.isUp()) {
                    animationPart.setCurrentAnimation("walk");
                }
            }
        }
        
        spawnZombies(gameData, world);
    }
    
    private void attackPlayer(GameData gameData, World world, UUID zombieID) {
        PositionPart zombiePos = (PositionPart) world.getMapByPart(PositionPart.class.getSimpleName()).get(zombieID);
        CombatPart combatPart = (CombatPart) world.getMapByPart(CombatPart.class.getSimpleName()).get(zombieID);
        
        if (world.getMapByPart(PlayerPart.class.getSimpleName()) != null) {
            for (Map.Entry<UUID,EntityPart> player : world.getMapByPart(EnemyPart.class.getSimpleName()).entrySet()) {
                PositionPart playerPos = (PositionPart) world.getMapByPart(PositionPart.class.getSimpleName()).get(player.getKey());
                float distance = (float) Math.sqrt(
                    Math.pow(zombiePos.getX() - playerPos.getX(), 2) +
                    Math.pow(zombiePos.getY() - playerPos.getY(), 2)
                );
                
                if (distance <= 5) {
                    combatPart.setAttacking(true);
                } 
            }
        }
    }
    
    private void spawnZombies(GameData gameData, World world) {
        currentTime = System.currentTimeMillis();
        if (currentTime > lastSpawnTime + spawnDelay) {
            float spawnX = gameData.getDisplayWidth() - 25;
            float spawnY = gameData.getDisplayHeight() / 2;
            
            ZombiePlugin.createZombie(gameData, world, spawnX, spawnY);
            lastSpawnTime = currentTime;
        }
    }
            
}

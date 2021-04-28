package dk.sdu.mmmi.cbse.playersystem;

import dk.sdu.mmmi.cbse.common.data.entityparts.VisualPart;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.*;
import dk.sdu.mmmi.cbse.common.data.entitytypeparts.*;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import org.openide.util.lookup.ServiceProvider;
import java.util.Map;
import java.util.UUID;

@ServiceProvider(service = IGamePluginService.class)
public class PlayerPlugin implements IGamePluginService {

    @Override
    public void start(GameData gameData, World world) {
        createPlayer(gameData, world);
    }

    private void createPlayer(GameData gameData, World world) {

        float speed = 3;
        float rotationSpeed = 5;
        float x = gameData.getDisplayWidth() / 2;
        float y = gameData.getDisplayHeight() / 2;
        float radians = 3.1415f / 2;

        Entity player = new Entity();
        
        world.addtoEntityPartMap(new PositionPart(x, y, radians), player);
        world.addtoEntityPartMap(new VisualPart("playerIdle", 80, 80,3), player);
        world.addtoEntityPartMap(new MovingPart(speed,rotationSpeed),player);
        world.addtoEntityPartMap(new PlayerPart(),player);
        world.addtoEntityPartMap(new LifePart(4000), player);
        world.addtoEntityPartMap(new AiMovementPart(5),player);
        world.addtoEntityPartMap(new PlayerPart(),player);
        world.addtoEntityPartMap(new WeaponInventoryPart(4), player);
        world.addtoEntityPartMap(new CombatPart(), player);
        world.addtoEntityPartMap(new CollectorPart(), player);
        world.addtoEntityPartMap(new ColliderPart(30), player);
        world.addtoEntityPartMap(createInitialAnimation(), player);
    }

    @Override
    public void stop(GameData gameData, World world) {
        for (Map.Entry<UUID,EntityPart> entry : world.getMapByPart(PlayerPart.class.getSimpleName()).entrySet()){
            world.removeEntityParts(entry.getKey());
        }
    }
    
    // Unarmed initial animation
    private AnimationPart createInitialAnimation() {
        AnimationPart animationPart = new AnimationPart();
        animationPart.addAnimation(
            "walk", // Set animation Id
            "PlayerWalk", // Animation asset name
            4, // Frame count
            0.15f, // Frame duration
            true // Animation can be interrupted
        );
        animationPart.setCurrentAnimation("walk");
        
        // Increment loop counter to avoid walk animation on start
        animationPart.getCurrentAnimation().setLoopCounter(2);
        
        return animationPart;
    }
    
    // Armed initial animation
    private AnimationPart createInitialAnimation(WeaponAnimationPart weaponAnimationPart) {
        AnimationPart animationPart = new AnimationPart();
        animationPart.addAnimation(
            "walk", // Set animation Id
            "PlayerWalk", // Animation asset name
            4, // Frame count
            0.15f, // Frame duration
            true // Animation can be interrupted
        );
        animationPart.addAnimation(
            "shoot", 
            weaponAnimationPart.getAttackAnimationName(),
            weaponAnimationPart.getAttackAnimationFrameCount(), 
            weaponAnimationPart.getAttackAnimationFrameDuration(),
            false // Animation can't be interrupted
        );
        animationPart.addAnimation(
            "walkWithWeapon",
            weaponAnimationPart.getWalkAnimationName(),
            weaponAnimationPart.getWalkAnimationFrameCount(),
            weaponAnimationPart.getWalkAnimationFrameDuration(),
            true // Animation can be interrupted
        );
        animationPart.setCurrentAnimation("walk");
        
        // Increment loop counter to avoid walk animation on start
        animationPart.getCurrentAnimation().setLoopCounter(2); 
        
        return animationPart;
    }
    
}

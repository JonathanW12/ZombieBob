package dk.sdu.mmmi.cbse.playersystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityammoparts.IWeaponAmmo;
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
        
        world.addtoEntityPartMap(new PositionPart(x, y, radians), player); // Create gun depends on position
        CombatPart combatPart = new CombatPart(WeaponGenerator.createGun(player, world).getUUID());
        
        
        IWeaponAmmo ammo = WeaponGenerator.getAmmoPart(combatPart.getCurrentWeapon());
        
        world.addtoEntityPartMap(createInitialAnimation(ammo), player);
        world.addtoEntityPartMap(combatPart, player);
        world.addtoEntityPartMap(new VisualPart("playerIdle", 80, 80), player);
        world.addtoEntityPartMap(new MovingPart(speed,rotationSpeed),player);
        world.addtoEntityPartMap(new PlayerPart(),player);
        world.addtoEntityPartMap(new LifePart(100), player);
        world.addtoEntityPartMap(new AiMovementPart(5),player);
        world.addtoEntityPartMap(new PlayerPart(),player);
        world.addtoEntityPartMap(new WeaponInventoryPart(2), player); 
    }

    @Override
    public void stop(GameData gameData, World world) {
        for (Map.Entry<UUID,EntityPart> entry : world.getMapByPart(PlayerPart.class.getSimpleName()).entrySet()){
            // for every player in world get UUID and remove everything for that UUID
            world.removeEntityParts(entry.getKey());
        }
    }
    
    private AnimationPart createInitialAnimation(IWeaponAmmo currentWeaponAmmo) {
        AnimationPart animationPart = new AnimationPart(false);
        animationPart.addAnimation("walk", "PlayerWalk", 4, 0.2f);
        animationPart.addAnimation("shoot", currentWeaponAmmo.getAttackAnimationName(), 2, 0.03f);
        animationPart.addAnimation("walkWithWeapon", currentWeaponAmmo.getWalkAnimationName(), 2, 0.2f);
        animationPart.setCurrentAnimation("walk");
        animationPart.getCurrentAnimation().setLoopCounter(2); // Increment loop counter to avoid walk animation on start
        
        return animationPart;
    }
}

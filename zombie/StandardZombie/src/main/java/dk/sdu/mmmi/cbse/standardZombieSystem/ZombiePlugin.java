package dk.sdu.mmmi.cbse.standardZombieSystem;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.*;
import dk.sdu.mmmi.cbse.common.data.entitytypeparts.*;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import java.util.UUID;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;
import java.util.Map;

@ServiceProviders(value = {
    @ServiceProvider(service = IGamePluginService.class),})
public class ZombiePlugin implements IGamePluginService{

    @Override
    public void start(GameData gameData, World world) {
        // Do nothing
    }
    @Override
    public void stop(GameData gameData, World world) {
        for (Map.Entry<UUID,EntityPart> entry : world.getMapByPart(EnemyPart.class.getSimpleName()).entrySet()){
            world.removeEntityParts(entry.getKey());
        }
    }
    
    protected static void createZombie(GameData gameData, World world, float x, float y){
        float speed = 2;
        float radians = 3.1415f / 2;
        float rotationSpeed = 3;
        
        Entity zombie = new Entity();
        
        // Standard zombie attack
        Entity zombieAttack = new Entity();
        
        WeaponPart weaponPart = new WeaponPart(
            30,
            40,
            1f,
            1
        );
        WeaponAnimationPart weaponAnimationPart = new WeaponAnimationPart(
            "enemyIdle",
            "enemyHit",
            "enemyWalk",
            2,
            1,
            0.3f,
            50f
        );
        
        world.addtoEntityPartMap(weaponPart, zombieAttack);
        world.addtoEntityPartMap(weaponAnimationPart, zombieAttack);
        
        CombatPart combatPart = new CombatPart();
        combatPart.setCurrentWeapon(zombieAttack.getUUID());
        
        // Create zombie 
        world.addtoEntityPartMap(new PositionPart(x, y, radians), zombie);
        world.addtoEntityPartMap(new VisualPart("enemyIdle", 80, 80), zombie);
        world.addtoEntityPartMap(new MovingPart(speed, rotationSpeed), zombie);
        world.addtoEntityPartMap(new EnemyPart(), zombie);
        world.addtoEntityPartMap(new LifePart(100), zombie);
        world.addtoEntityPartMap(new AiMovementPart(4), zombie);
        world.addtoEntityPartMap(new ColliderPart(40),zombie);
        world.addtoEntityPartMap(combatPart, zombie);
        world.addtoEntityPartMap(createInitialAnimation(weaponAnimationPart), zombie);
    }
    
    // Armed initial animation
    private static AnimationPart createInitialAnimation(WeaponAnimationPart weaponAnimationPart) {
        AnimationPart animationPart = new AnimationPart();
        animationPart.addAnimation(
            "hit", 
            weaponAnimationPart.getAttackAnimationName(),
            weaponAnimationPart.getAttackAnimationFrameCount(), 
            weaponAnimationPart.getAttackAnimationFrameDuration(),
            false // Animation can't be interrupted
        );
        animationPart.addAnimation(
            "walk",
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

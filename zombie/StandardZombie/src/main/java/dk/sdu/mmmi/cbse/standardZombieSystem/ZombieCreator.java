package dk.sdu.mmmi.cbse.standardZombieSystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.*;
import dk.sdu.mmmi.cbse.common.data.entitytypeparts.EnemyPart;
import dk.sdu.mmmi.cbse.common.data.Position;

public class ZombieCreator {
    public void createZombie(int health, Position position, World world){
        float speed = 2;
        float rotationSpeed = 3;
        float spawnX = position.getX();
        float spawnY = position.getY();
        float radians = 3.14159f / 2;

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
        
        // Configure zombie audio
        AudioPart audioPart = new AudioPart("zombie-growl.ogg");
        audioPart.playRandomly(5000L, 15000L);

        // Create zombie
        world.addtoEntityPartMap(new PositionPart(spawnX, spawnY, radians), zombie);
        world.addtoEntityPartMap(new VisualPart("enemyIdle", 80, 80), zombie);
        world.addtoEntityPartMap(new MovingPart(speed, rotationSpeed), zombie);
        world.addtoEntityPartMap(new EnemyPart(), zombie);
        world.addtoEntityPartMap(new LifePart(health), zombie);
        world.addtoEntityPartMap(new AiMovementPart(4), zombie);
        world.addtoEntityPartMap(new ColliderPart(40,40),zombie);
        world.addtoEntityPartMap(audioPart, zombie);
        world.addtoEntityPartMap(combatPart, zombie);
        world.addtoEntityPartMap(createInitialAnimationEnemy(weaponAnimationPart), zombie);
    }

    // Armed initial animation
    private static AnimationPart createInitialAnimationEnemy(WeaponAnimationPart weaponAnimationPart) {
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

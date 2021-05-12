package dk.sdu.mmmi.cbse.spiderboss;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.Position;
import dk.sdu.mmmi.cbse.common.data.entityparts.*;
import dk.sdu.mmmi.cbse.common.data.entitytypeparts.EnemyPart;
import dk.sdu.mmmi.cbse.common.data.entitytypeparts.SpiderPart;
import java.util.Random;

public class SpiderCreator {

    SpiderWebCreator spiderWebCreator = new SpiderWebCreator();
    private Random randomgenerator;

    public void createSpiderBoss(int health, Position position, World world) {
        float speed = 2;
        float radians = 3.14159f / 2;
        float rotationSpeed = 3;

        Entity spider = new Entity();

        // Creating webshooter for the spider
        Entity webShooter = spiderWebCreator.createWebShooter(world);

        CombatPart combatPart = new CombatPart();

        WeaponAnimationPart weaponAnimationPart = (WeaponAnimationPart) world.getMapByPart(WeaponAnimationPart.class.getSimpleName()).get(webShooter.getUUID());
        PositionPart positionPartWeb = new PositionPart(position.getX(),position.getY(),radians);
        world.addtoEntityPartMap(positionPartWeb,webShooter);

        // Setting webshooter as weapon
        combatPart.setCurrentWeapon(webShooter.getUUID());

        // Create Spdierboss
        ColliderPart colliderPart = new ColliderPart();
        for(int i = 0; i < 8; i++) {
            colliderPart.addShapePoint(25, (float) (i * Math.PI / 4));
        }

        world.addtoEntityPartMap(new PositionPart(
                position.getX() + getRandomOffset(),
                position.getY() + getRandomOffset(),
                radians
        ), spider);
        world.addtoEntityPartMap(new VisualPart("spiderIdle", 100, 100), spider);
        world.addtoEntityPartMap(new MovingPart(speed, rotationSpeed), spider);
        world.addtoEntityPartMap(new EnemyPart(), spider);
        world.addtoEntityPartMap(new LifePart(health), spider);
        world.addtoEntityPartMap(new AiMovementPart(4), spider);
        world.addtoEntityPartMap(colliderPart,spider);
        world.addtoEntityPartMap(combatPart, spider);
        world.addtoEntityPartMap(new SpiderPart(),spider);
        world.addtoEntityPartMap(createInitialAnimationSpider(weaponAnimationPart), spider);
    }
    
    private float getRandomOffset() {
        if (randomgenerator == null) {
            randomgenerator = new Random();
        }
        
        return (float) randomgenerator.nextInt(30) - 15;
    }

    // Armed initial animation
    private static AnimationPart createInitialAnimationSpider(WeaponAnimationPart weaponAnimationPart) {
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

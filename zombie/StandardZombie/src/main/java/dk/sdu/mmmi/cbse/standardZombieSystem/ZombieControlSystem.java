package dk.sdu.mmmi.cbse.standardZombieSystem;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.entityparts.*;
import dk.sdu.mmmi.cbse.common.data.entitytypeparts.EnemyPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.VisualPart;
import dk.sdu.mmmi.cbse.common.data.entitytypeparts.PlayerPart;
import dk.sdu.mmmi.cbse.common.data.entitytypeparts.ZombiePart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@ServiceProviders(value = {
    @ServiceProvider(service = IEntityProcessingService.class)})
public class ZombieControlSystem implements IEntityProcessingService {

    private WeaponAnimationPart weaponAnimationPart;

    @Override
    public void process(GameData gameData, World world) {

        if (world.getMapByPart(ZombiePart.class.getSimpleName()) != null) {
            for (Map.Entry<UUID, EntityPart> entry : world.getMapByPart(ZombiePart.class.getSimpleName()).entrySet()) {

                MovingPart movingPart = (MovingPart) world.getMapByPart("MovingPart").get(entry.getKey());
                VisualPart visualPart = (VisualPart) world.getMapByPart("VisualPart").get(entry.getKey());
                AnimationPart animationPart = (AnimationPart) world.getMapByPart(AnimationPart.class.getSimpleName()).get(entry.getKey());
                CombatPart combatPart = (CombatPart) world.getMapByPart(CombatPart.class.getSimpleName()).get(entry.getKey());

                attackPlayer(world, entry.getKey());

                // Animation processing
                if (!animationPart.isCurrentAnimationInterruptible() && !animationPart.hasCurrentAnimationLooped()) {
                    animationPart.setIsAnimated(true);
                } else {
                    animationPart.setIsAnimated(
                            (movingPart.isDown() || movingPart.isLeft() || movingPart.isRight() || movingPart.isUp() || combatPart.isAttacking())
                    );
                }

                if (combatPart != null && combatPart.getCurrentWeapon() != null) {
                    weaponAnimationPart = (WeaponAnimationPart) world.getMapByPart(
                            WeaponAnimationPart.class.getSimpleName()).get(combatPart.getCurrentWeapon()
                    );

                    setAnimation(animationPart, weaponAnimationPart);

                    visualPart.setSpriteName(weaponAnimationPart.getIdleSpriteName());
                    if (((WeaponPart) world.getMapByPart(WeaponPart.class.getSimpleName()).get(combatPart.getCurrentWeapon())).isIsAttacking()) {
                        animationPart.setCurrentAnimation("hit");
                    } else if (movingPart.isDown() || movingPart.isLeft() || movingPart.isRight() || movingPart.isUp()) {
                        if (animationPart.hasCurrentAnimationLooped()) {
                            animationPart.setCurrentAnimation("walk");
                        }
                    }
                } else {
                    visualPart.setSpriteName(weaponAnimationPart.getIdleSpriteName());
                    if (movingPart.isDown() || movingPart.isLeft() || movingPart.isRight() || movingPart.isUp()) {
                        animationPart.setCurrentAnimation("walk");
                    }
                }
            }
        }
    }

    private void attackPlayer(World world, UUID zombieID) {
        CombatPart combatPart = (CombatPart) world.getMapByPart(CombatPart.class.getSimpleName()).get(zombieID);

        combatPart.setAttacking(true);
    }

    private void setAnimation(AnimationPart animationPart, WeaponAnimationPart weaponAnimation) {
        if (animationPart.getAnimationByName("hit") == null
                || !animationPart.getAnimationByName("hit").getTextureFileName().equals(weaponAnimation.getAttackAnimationName())) {
            animationPart.addAnimation(
                    "hit",
                    weaponAnimation.getAttackAnimationName(),
                    weaponAnimation.getAttackAnimationFrameCount(),
                    weaponAnimation.getAttackAnimationFrameDuration(),
                    false // Animation can't be interrupted
            );
        }

        if (animationPart.getAnimationByName("walk") == null
                || !animationPart.getAnimationByName("walk").getTextureFileName().equals(weaponAnimation.getWalkAnimationName())) {
            animationPart.addAnimation(
                    "walk",
                    weaponAnimation.getWalkAnimationName(),
                    weaponAnimation.getWalkAnimationFrameCount(),
                    weaponAnimation.getWalkAnimationFrameDuration(),
                    true // Animation can be interrupted
            );
        }
    }

}

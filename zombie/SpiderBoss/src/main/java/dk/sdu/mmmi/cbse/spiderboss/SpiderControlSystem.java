package dk.sdu.mmmi.cbse.spiderboss;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.Position;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.*;
import dk.sdu.mmmi.cbse.common.data.entitytypeparts.PlayerPart;
import dk.sdu.mmmi.cbse.common.data.entitytypeparts.SpiderPart;
import dk.sdu.mmmi.cbse.common.data.entitytypeparts.ZombiePart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import org.openide.util.lookup.ServiceProvider;

import java.util.Map;
import java.util.UUID;


@ServiceProvider(service = IEntityProcessingService.class)
public class SpiderControlSystem implements IEntityProcessingService {
    private WeaponAnimationPart weaponAnimationPart;

    @Override
    public void process(GameData gameData, World world) {


    if (world.getMapByPart(SpiderPart.class.getSimpleName()) != null){
        for (Map.Entry<UUID, EntityPart> entry : world.getMapByPart(SpiderPart.class.getSimpleName()).entrySet()){

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
                if (combatPart.isAttacking()) {
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
        PositionPart zombiePos = (PositionPart) world.getMapByPart(PositionPart.class.getSimpleName()).get(zombieID);
        CombatPart combatPart = (CombatPart) world.getMapByPart(CombatPart.class.getSimpleName()).get(zombieID);
        WeaponPart weaponPart = (WeaponPart) world.getMapByPart(WeaponPart.class.getSimpleName()).get(combatPart.getCurrentWeapon());

        combatPart.setAttacking(false);

        if (world.getMapByPart(PlayerPart.class.getSimpleName()) != null) {
            for (Map.Entry<UUID,EntityPart> player : world.getMapByPart(PlayerPart.class.getSimpleName()).entrySet()) {
                PositionPart playerPos = (PositionPart) world.getMapByPart(PositionPart.class.getSimpleName()).get(player.getKey());
                LifePart lifePart = (LifePart) world.getMapByPart(LifePart.class.getSimpleName()).get(player.getKey());

                float distance = (float) Math.sqrt(
                        Math.pow(zombiePos.getX() - playerPos.getX(), 2) +
                                Math.pow(zombiePos.getY() - playerPos.getY(), 2)
                );

                if (distance <= weaponPart.getRange() && weaponPart.getTimeSinceLastTrigger() > weaponPart.getFireRate()) {
                    combatPart.setAttacking(true);
                    PositionPart positionPart = (PositionPart) world.getMapByPart(PositionPart.class.getSimpleName()).get(combatPart.getCurrentWeapon());
                    positionPart.setX(zombiePos.getX());
                    positionPart.setY(zombiePos.getY());

                    if (world.getMapByPart(PlayerPart.class.getSimpleName()).keySet().toArray().length > 0) {
                        UUID uuid = (UUID) world.getMapByPart(PlayerPart.class.getSimpleName()).keySet().toArray()[0];
                        PositionPart playerPositionPart = (PositionPart) world.getMapByPart(PositionPart.class.getSimpleName()).get(uuid);

                        positionPart.setRadians((float) Math.atan2(playerPositionPart.getY()-positionPart.getY(), playerPositionPart.getX()-positionPart.getX()));
                    }
                    AudioPart audioPart = (AudioPart) world.getMapByPart(AudioPart.class.getSimpleName()).get(combatPart.getCurrentWeapon());
                    audioPart.setIsPlaying(true);

                }
            }
        }
    }

    private void setAnimation(AnimationPart animationPart, WeaponAnimationPart weaponAnimation) {
        if (animationPart.getAnimationByName("hit") == null ||
                !animationPart.getAnimationByName("hit").getTextureFileName().equals(weaponAnimation.getAttackAnimationName())
        ) {
            animationPart.addAnimation(
                    "hit",
                    weaponAnimation.getAttackAnimationName(),
                    weaponAnimation.getAttackAnimationFrameCount(),
                    weaponAnimation.getAttackAnimationFrameDuration(),
                    false // Animation can't be interrupted
            );
        }

        if (animationPart.getAnimationByName("walk") == null ||
                !animationPart.getAnimationByName("walk").getTextureFileName().equals(weaponAnimation.getWalkAnimationName())
        ) {
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

package dk.sdu.mmmi.cbse.playersystem;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.GameKeys;
import dk.sdu.mmmi.cbse.common.data.MouseMovement;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.AnimationPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.CombatPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.*;
import dk.sdu.mmmi.cbse.common.data.entitytypeparts.PlayerPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.WeaponInventoryPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.VisualPart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import org.openide.util.lookup.ServiceProvider;
import java.util.Map;
import java.util.UUID;

@ServiceProvider(service = IEntityProcessingService.class)
public class PlayerControlSystem implements IEntityProcessingService {

    private WeaponPart weaponPart;

    @Override
    public void process(GameData gameData, World world) {

        // all playerParts
        if (world.getMapByPart(PlayerPart.class.getSimpleName()) != null) {
            for (Map.Entry<UUID, EntityPart> entry : world.getMapByPart(PlayerPart.class.getSimpleName()).entrySet()) {

                // entity parts on player
                PositionPart positionPart = (PositionPart) world.getMapByPart("PositionPart").get(entry.getKey());
                MovingPart movingPart = (MovingPart) world.getMapByPart("MovingPart").get(entry.getKey());
                VisualPart visualPart = (VisualPart) world.getMapByPart("VisualPart").get(entry.getKey());
                AnimationPart animationPart = (AnimationPart) world.getMapByPart("AnimationPart").get(entry.getKey());
                CombatPart combatPart = (CombatPart) world.getMapByPart(CombatPart.class.getSimpleName()).get(entry.getKey());
                WeaponInventoryPart weaponInventoryPart = (WeaponInventoryPart) world.getMapByPart("WeaponInventoryPart").get(entry.getKey());
                CollectorPart collectorPart = (CollectorPart) world.getMapByPart(CollectorPart.class.getSimpleName()).get(entry.getKey());
                AudioPart walkingSound = (AudioPart) world.getMapByPart(AudioPart.class.getSimpleName()).get(entry.getKey());

                setWalkingSound(walkingSound, movingPart);

                combatPart.setAttacking(gameData.getMouse().isDown(MouseMovement.LEFTCLICK));
                if (world.getMapByPart(WeaponPart.class.getSimpleName()) != null && combatPart.getCurrentWeapon() != null) {
                    weaponPart = (WeaponPart) world.getMapByPart(WeaponPart.class.getSimpleName()).get(combatPart.getCurrentWeapon());
                }

                // Update player direction
                updateDirection(positionPart, gameData);

                if (gameData.getMouse().getScroll() == -1) {
                    gameData.getMouse().setScroll(0);
                }
                if (gameData.getMouse().getScroll() == 1) {
                    gameData.getMouse().setScroll(0);
                }

                // movement
                movingPart.setLeft(gameData.getKeys().isDown(GameKeys.LEFT));
                movingPart.setRight(gameData.getKeys().isDown(GameKeys.RIGHT));
                movingPart.setUp(gameData.getKeys().isDown(GameKeys.UP));
                movingPart.setDown(gameData.getKeys().isDown(GameKeys.DOWN));

                if (!combatPart.isAttacking()) {
                    if (gameData.getKeys().isPressed(GameKeys.NUM_1)) {
                        if (weaponInventoryPart.getInventory().size() > 0) {
                            combatPart.setCurrentWeapon(weaponInventoryPart.getInventory().get(0));
                        }
                    }
                    if (gameData.getKeys().isPressed(GameKeys.NUM_2)) {
                        if (weaponInventoryPart.getInventory().size() > 1) {
                            combatPart.setCurrentWeapon(weaponInventoryPart.getInventory().get(1));
                        }
                    }
                    if (gameData.getKeys().isPressed(GameKeys.NUM_3)) {
                        if (weaponInventoryPart.getInventory().size() > 2) {
                            combatPart.setCurrentWeapon(weaponInventoryPart.getInventory().get(2));
                        }
                    }
                    if (gameData.getKeys().isPressed(GameKeys.NUM_4)) {
                        if (weaponInventoryPart.getInventory().size() > 3) {
                            combatPart.setCurrentWeapon(weaponInventoryPart.getInventory().get(3));
                        }
                    }
                }
                //combatPart.setAttacking(gameData.getKeys().isPressed(GameKeys.SPACE));

                collectorPart.setCollecting(gameData.getKeys().isPressed(GameKeys.E));

                // Animation processing
                if (!animationPart.isCurrentAnimationInterruptible() && !animationPart.hasCurrentAnimationLooped()) {
                    animationPart.setIsAnimated(true);
                } else {
                    animationPart.setIsAnimated(
                            (movingPart.isDown() || movingPart.isLeft() || movingPart.isRight() || movingPart.isUp() || (weaponPart != null && weaponPart.isIsAttacking()))
                    );
                }

                if (combatPart != null && combatPart.getCurrentWeapon() != null) {
                    WeaponAnimationPart weaponAnimationPart = (WeaponAnimationPart) world.getMapByPart(
                            WeaponAnimationPart.class.getSimpleName()).get(combatPart.getCurrentWeapon()
                    );

                    setAnimation(animationPart, weaponAnimationPart);

                    visualPart.setSpriteName(weaponAnimationPart.getIdleSpriteName());
                    if (weaponPart != null && weaponPart.isIsAttacking()) {
                        AudioPart audioPart = (AudioPart) world.getMapByPart(AudioPart.class.getSimpleName()).get(combatPart.getCurrentWeapon());
                        audioPart.setIsPlaying(true);
                        animationPart.setCurrentAnimation("shoot");
                    } else if (movingPart.isDown() || movingPart.isLeft() || movingPart.isRight() || movingPart.isUp()) {
                        if (animationPart.hasCurrentAnimationLooped()) {
                            animationPart.setCurrentAnimation("walkWithWeapon");
                        }
                    }
                } else {
                    visualPart.setSpriteName("playerIdle");
                    if (movingPart.isDown() || movingPart.isLeft() || movingPart.isRight() || movingPart.isUp()) {
                        animationPart.setCurrentAnimation("walk");
                    }
                }
            }
        }
    }

    private void setWalkingSound(AudioPart audioPart, MovingPart movingPart) {
        audioPart.setIsPlaying(false);

        if ((movingPart.isDown() || movingPart.isLeft() || movingPart.isRight() || movingPart.isUp())) {
            audioPart.setIsPlaying(true);
        }
    }

    private void setAnimation(AnimationPart animationPart, WeaponAnimationPart weaponAnimation) {
        if (animationPart.getAnimationByName("shoot") == null
                || !animationPart.getAnimationByName("shoot").getTextureFileName().equals(weaponAnimation.getAttackAnimationName())) {
            animationPart.addAnimation(
                    "shoot",
                    weaponAnimation.getAttackAnimationName(),
                    weaponAnimation.getAttackAnimationFrameCount(),
                    weaponAnimation.getAttackAnimationFrameDuration(),
                    false // Animation can't be interrupted
            );
        }

        if (animationPart.getAnimationByName("walkWithWeapon") == null
                || !animationPart.getAnimationByName("walkWithWeapon").getTextureFileName().equals(weaponAnimation.getWalkAnimationName())) {
            animationPart.addAnimation(
                    "walkWithWeapon",
                    weaponAnimation.getWalkAnimationName(),
                    weaponAnimation.getWalkAnimationFrameCount(),
                    weaponAnimation.getWalkAnimationFrameDuration(),
                    true // Animation can be interrupted
            );
        }
    }

    private void updateDirection(PositionPart positionPart, GameData gameData) {
        positionPart.setRadians((float) Math.atan2(gameData.getMouse().getY() - positionPart.getY(), gameData.getMouse().getX() - positionPart.getX()));
    }

}

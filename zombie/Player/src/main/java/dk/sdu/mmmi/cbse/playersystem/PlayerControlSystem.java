package dk.sdu.mmmi.cbse.playersystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.GameKeys;
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
    
    private int cooldown = 250;
    private long shootDelay = System.currentTimeMillis();
    //To be deleted: Testy
    private boolean testy = true;
    
    @Override
    public void process(GameData gameData, World world) {

        // all playerParts
        if (world.getMapByPart(PlayerPart.class.getSimpleName()) != null){
            for (Map.Entry<UUID,EntityPart> entry : world.getMapByPart(PlayerPart.class.getSimpleName()).entrySet()){

                // entity parts on player
                PositionPart positionPart = (PositionPart) world.getMapByPart("PositionPart").get(entry.getKey());
                MovingPart movingPart = (MovingPart) world.getMapByPart("MovingPart").get(entry.getKey());
                VisualPart visualPart = (VisualPart) world.getMapByPart("VisualPart").get(entry.getKey());
                AnimationPart animationPart = (AnimationPart) world.getMapByPart("AnimationPart").get(entry.getKey());
                CombatPart combatPart = (CombatPart) world.getMapByPart(CombatPart.class.getSimpleName()).get(entry.getKey());
                WeaponInventoryPart weaponInventoryPart = (WeaponInventoryPart) world.getMapByPart("WeaponInventoryPart").get(entry.getKey());
                CollectorPart collectorPart = (CollectorPart) world.getMapByPart(CollectorPart.class.getSimpleName()).get(entry.getKey()); 


                // Mouse event testing
                if (gameData.getMouse().isLeftClick() && shootDelay <= System.currentTimeMillis()){
                    shootDelay = System.currentTimeMillis()+cooldown;
                    combatPart.setAttacking(true);
                } else {
                    combatPart.setAttacking(false);
                }

                if (gameData.getMouse().isRightClick()){
                    //Inventory testing. to be deleted
                    if(weaponInventoryPart.getInventory().size()>0){
                    UUID removeWeaponId = weaponInventoryPart.getInventory().get(0);
                    weaponInventoryPart.removeWeapon(removeWeaponId);
                    }
                }
                if (gameData.getMouse().isMiddleClick()){
                }
                if (gameData.getMouse().getScroll() == -1){
                    gameData.getMouse().setScroll(0);
                }
                if (gameData.getMouse().getScroll() == 1){
                    gameData.getMouse().setScroll(0);
                }

                // movement
                movingPart.setLeft(gameData.getKeys().isDown(GameKeys.LEFT));
                movingPart.setRight(gameData.getKeys().isDown(GameKeys.RIGHT));
                movingPart.setUp(gameData.getKeys().isDown(GameKeys.UP));
                movingPart.setDown(gameData.getKeys().isDown(GameKeys.DOWN));
                //combatPart.setAttacking(gameData.getKeys().isPressed(GameKeys.SPACE));
                
                collectorPart.setCollecting(gameData.getKeys().isPressed(GameKeys.ENTER));

                //Weapon inventory testing. Delete

                if(gameData.getKeys().isDown(GameKeys.SHIFT) & testy){
                    for(int i = 0; i < 2;i++){
                        Entity weapon = new Entity();

                weaponInventoryPart.addToInventory(weapon.getUUID());
                world.addtoEntityPartMap(new VisualPart("sword_sprite",30,30), weapon);
                world.addtoEntityPartMap(new PositionPart(100*i,400,2), weapon);
                }
                testy = false;
                }

                
                // Animation processing
                if (!animationPart.hasCurrentAnimationLooped()) {
                    animationPart.setIsAnimated(true);
                } else {
                    animationPart.setIsAnimated(
                        (movingPart.isDown() || movingPart.isLeft() || movingPart.isRight() || movingPart.isUp() || combatPart.isAttacking())
                    );
                }
                
                if (combatPart != null && combatPart.getCurrentWeapon() != null) {
                    WeaponAnimationPart weaponAnimationPart = (WeaponAnimationPart) world.getMapByPart(
                        WeaponAnimationPart.class.getSimpleName()).get(combatPart.getCurrentWeapon()
                    );

                    setAnimation(animationPart, weaponAnimationPart);
                    
                    visualPart.setSpriteName(weaponAnimationPart.getIdleSpriteName());
                    if (combatPart.isAttacking()) {
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
    
    private void setAnimation(AnimationPart animationPart, WeaponAnimationPart weaponAnimation) {
        if (animationPart.getAnimationByName("shoot") == null ||
            !animationPart.getAnimationByName("shoot").getTextureFileName().equals(weaponAnimation.getAttackAnimationName())
        ) {
                animationPart.addAnimation(
                    "shoot",
                    weaponAnimation.getAttackAnimationName(),
                    weaponAnimation.getAttackAnimationFrameCount(),
                    weaponAnimation.getAttackAnimationFrameDuration()
                );
        }
        
        if (animationPart.getAnimationByName("walkWithWeapon") == null || 
                !animationPart.getAnimationByName("walkWithWeapon").getTextureFileName().equals(weaponAnimation.getWalkAnimationName())
            ) {
                animationPart.addAnimation(
                    "walkWithWeapon",
                    weaponAnimation.getWalkAnimationName(),
                    weaponAnimation.getWalkAnimationFrameCount(),
                    weaponAnimation.getWalkAnimationFrameDuration()
                );
        }   
    }
}

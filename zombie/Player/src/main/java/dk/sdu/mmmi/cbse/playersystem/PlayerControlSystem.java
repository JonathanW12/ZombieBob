package dk.sdu.mmmi.cbse.playersystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.GameKeys;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityammoparts.IWeaponAmmo;
import dk.sdu.mmmi.cbse.common.data.entityparts.AnimationPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.CombatPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.*;
import dk.sdu.mmmi.cbse.common.data.entitytypeparts.PlayerPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.WeaponInventoryPart;
import dk.sdu.mmmi.cbse.common.data.entitytypeparts.VisualPart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import org.openide.util.lookup.ServiceProvider;
import java.util.Map;
import java.util.UUID;


@ServiceProvider(service = IEntityProcessingService.class)
public class PlayerControlSystem implements IEntityProcessingService {
    
    private int cooldown = 250;
    private long shootDelay = System.currentTimeMillis();
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

                // Mouse event testing
                if (gameData.getMouse().isLeftClick() && shootDelay <= System.currentTimeMillis()){
                    shootDelay = System.currentTimeMillis()+cooldown;
                    System.out.println("Left Click");
                    //System.out.println("X: "+gameData.getMouse().getX()+" Y: "+gameData.getMouse().getY());
                }
                if (gameData.getMouse().isRightClick()){
                    System.out.println("Right Click");
                }
                if (gameData.getMouse().isMiddleClick()){
                    System.out.println("Middle Click");
                }
                if (gameData.getMouse().getScroll() == -1){
                    System.out.println("Scroll Up");
                    gameData.getMouse().setScroll(0);
                }
                if (gameData.getMouse().getScroll() == 1){
                    System.out.println("Scroll Down");
                    gameData.getMouse().setScroll(0);
                }

                // movement
                movingPart.setLeft(gameData.getKeys().isDown(GameKeys.LEFT));
                movingPart.setRight(gameData.getKeys().isDown(GameKeys.RIGHT));
                movingPart.setUp(gameData.getKeys().isDown(GameKeys.UP));
                movingPart.setDown(gameData.getKeys().isDown(GameKeys.DOWN));
                combatPart.setAttacking(gameData.getKeys().isPressed(GameKeys.SPACE));

                //Weapon inventory testing. Delete
                WeaponInventoryPart weaponInventoryPart = (WeaponInventoryPart) world.getMapByPart("WeaponInventoryPart").get(entry.getKey());
                if(gameData.getKeys().isDown(GameKeys.SHIFT) & testy){
                    for(int i = 0; i < 4;i++){
                        Entity weapon = new Entity();

                weaponInventoryPart.addToInventory(weapon.getUUID());
                world.addtoEntityPartMap(new VisualPart("sword_sprite",30,30), weapon);
                world.addtoEntityPartMap(new PositionPart(400,400,2), weapon);
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
                
                if (combatPart != null) {
                    IWeaponAmmo ammo = WeaponGenerator.getAmmoPart(combatPart.getCurrentWeapon());
                    setAnimation(animationPart, ammo);
                    
                    visualPart.setSpriteName(ammo.getIdleSpriteName());
                    if (combatPart.isAttacking()) {
                        animationPart.setCurrentAnimation("shoot");
                    } else if (movingPart.isDown() || movingPart.isLeft() || movingPart.isRight() || movingPart.isUp()) {
                        if (animationPart.hasCurrentAnimationLooped()) {
                            animationPart.setCurrentAnimation("walkWithWeapon");
                        }
                    }
                } else {
                    visualPart.setSpriteName("PlayerIdle");
                    if (movingPart.isDown() || movingPart.isLeft() || movingPart.isRight() || movingPart.isUp()) {
                        animationPart.setCurrentAnimation("walk");
                    }
                }    
            }
        }
    }
    
    private void setAnimation(AnimationPart animationPart, IWeaponAmmo currentWeaponAmmo) {
        if (!animationPart.getAnimationByName("shoot").getTextureFileName().equals(currentWeaponAmmo.getAttackAnimationName()) || 
            animationPart.getAnimationByName("shoot") == null) {
                animationPart.addAnimation("shoot", currentWeaponAmmo.getAttackAnimationName(), 2, 0.03f);
        }
        
        if (!animationPart.getAnimationByName("walkWithWeapon").getTextureFileName().equals(currentWeaponAmmo.getWalkAnimationName()) ||
            animationPart.getAnimationByName("walkWithWeapon") == null) {
                animationPart.addAnimation("walkWithWeapon", currentWeaponAmmo.getWalkAnimationName(), 2, 0.2f);
        }   
    }
}

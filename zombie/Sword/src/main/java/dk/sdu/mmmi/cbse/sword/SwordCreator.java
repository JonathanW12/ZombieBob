/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.sword;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LootablePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.VisualPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.WeaponAnimationPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.WeaponPart;
import dk.sdu.mmmi.cbse.common.services.IWeaponCreatorService;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author lake
 */
@ServiceProvider(service = IWeaponCreatorService.class)
public class SwordCreator implements IWeaponCreatorService {
  private static final SwordData swordData = SwordData.getInstance();

    @Override
    public Entity createWeapon(GameData gd, World world, Entity owner) {
        
         Entity sword = scaffoldSword(world);
        
        VisualPart visualPart = (VisualPart) world.getMapByPart(VisualPart.class.getSimpleName()).get(sword.getUUID());
        visualPart.setIsVisible(false);
        
        PositionPart positionPart = (PositionPart) world.getMapByPart(
            PositionPart.class.getSimpleName()).get(owner.getUUID()
        );
        
        world.addtoEntityPartMap(positionPart, sword);
        
        return sword;
    }

    @Override
    public void spawnGun(GameData gd, World world) {
        
         Entity sword = SwordCreator.scaffoldSword(world);

        VisualPart visualPart = (VisualPart) world.getMapByPart(VisualPart.class.getSimpleName()).get(sword.getUUID());
        visualPart.setIsVisible(true);

        float spawnX = gd.getDisplayWidth()/2;
        float spawnY = gd.getDisplayHeight()/2;
        float radians = 3.1415f / 2;

        PositionPart positionPart = new PositionPart(spawnX, spawnY, radians);

        world.addtoEntityPartMap(positionPart, sword);
        world.addtoEntityPartMap(new LootablePart(), sword);

        SwordProcessor.addToProcessingList(sword);

    }
    
   protected static Entity scaffoldSword(World world) {
       
        Entity sword = new Entity();
        
        float width = 10;
        float height = 10;
        
        WeaponPart weaponPart = new WeaponPart(
           swordData.getDamage(),
            swordData.getRange(),
            swordData.getFireRate(),
            1
        );
        WeaponAnimationPart weaponAnimationPart = new WeaponAnimationPart(
            swordData.getIdleSpriteName(),
            swordData.getAttackAnimationName(),
            swordData.getWalkAnimationName(),
            swordData.getAttackAnimationFrameCount(),
            swordData.getWalkAnimationFrameCount(),
            swordData.getAttackAnimationFrameDuration(),
            swordData.getWalkAnimationFrameDuration()
        );
        VisualPart visualPart = new VisualPart(
            swordData.getVisualPartName(),
            width,
            height
        );
        
        world.addtoEntityPartMap(weaponPart, sword);
        world.addtoEntityPartMap(weaponAnimationPart, sword);
        world.addtoEntityPartMap(visualPart, sword);
        
        SwordProcessor.addToProcessingList(sword);
        
        return sword;

 
    }
}

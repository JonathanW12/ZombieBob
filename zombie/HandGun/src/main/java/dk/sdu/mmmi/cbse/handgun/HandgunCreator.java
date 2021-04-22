package dk.sdu.mmmi.cbse.handgun;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.*;
import dk.sdu.mmmi.cbse.common.services.IWeaponCreatorService;
import org.openide.util.lookup.ServiceProvider;

@ServiceProvider(service = IWeaponCreatorService.class)
public class HandgunCreator implements IWeaponCreatorService {  
    
    private static final HandgunData gunData = HandgunData.getInstance();
   
    @Override
    public Entity createWeapon(GameData gameData, World world, Entity owner) {
        Entity gun = scaffoldGun(world);
        
        VisualPart visualPart = (VisualPart) world.getMapByPart(VisualPart.class.getSimpleName()).get(gun.getUUID());
        visualPart.setIsVisible(false);
        
        PositionPart positionPart = (PositionPart) world.getMapByPart(
            PositionPart.class.getSimpleName()).get(owner.getUUID()
        );
        
        world.addtoEntityPartMap(positionPart, gun);
        
        return gun;
    }
    @Override
    public void spawnGun(GameData gameData, World world) {
        Entity gun = HandgunCreator.scaffoldGun(world);

        VisualPart visualPart = (VisualPart) world.getMapByPart(VisualPart.class.getSimpleName()).get(gun.getUUID());
        visualPart.setIsVisible(true);

        float spawnX = gameData.getDisplayWidth()/2;
        float spawnY = gameData.getDisplayHeight()/2;
        float radians = 3.1415f / 2;

        PositionPart positionPart = new PositionPart(spawnX, spawnY, radians);

        world.addtoEntityPartMap(positionPart, gun);
        world.addtoEntityPartMap(new LootablePart(), gun);

        HandgunProcessor.addToProcessingList(gun);
    }
    
    protected static Entity scaffoldGun(World world) {
        Entity gun = new Entity();
        
        float width = 30;
        float height = 30;
        
        WeaponPart weaponPart = new WeaponPart(
            gunData.getDamage(),
            gunData.getRange(),
            gunData.getFireRate(),
            1
        );
        WeaponAnimationPart weaponAnimationPart = new WeaponAnimationPart(
            gunData.getIdleSpriteName(),
            gunData.getAttackAnimationName(),
            gunData.getWalkAnimationName(),
            gunData.getAttackAnimationFrameCount(),
            gunData.getWalkAnimationFrameCount(),
            gunData.getAttackAnimationFrameDuration(),
            gunData.getWalkAnimationFrameDuration()
        );
        VisualPart visualPart = new VisualPart(
            gunData.getVisualPartName(),
            width,
            height
        );
        
        world.addtoEntityPartMap(weaponPart, gun);
        world.addtoEntityPartMap(weaponAnimationPart, gun);
        world.addtoEntityPartMap(visualPart, gun);
        
        HandgunProcessor.addToProcessingList(gun);
        
        return gun;
    }

}

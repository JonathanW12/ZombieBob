package dk.sdu.mmmi.cbse.handgun;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.*;

public class HandgunCreator {
    
    private static final HandgunData gunData = HandgunData.getInstance();

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
        AudioPart audioPart = new AudioPart(
            gunData.getShootingSoundFileName()
        );
        
        world.addtoEntityPartMap(weaponPart, gun);
        world.addtoEntityPartMap(weaponAnimationPart, gun);
        world.addtoEntityPartMap(visualPart, gun);
        world.addtoEntityPartMap(audioPart, gun);
        
        // HandgunProcessor.addToProcessingList(gun);
        
        return gun;
    }

    
}

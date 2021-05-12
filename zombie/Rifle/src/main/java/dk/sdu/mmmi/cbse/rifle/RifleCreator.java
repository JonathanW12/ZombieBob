package dk.sdu.mmmi.cbse.rifle;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.*;
import dk.sdu.mmmi.cbse.common.data.Position;
import java.util.UUID;

public class RifleCreator {

    private static final RifleData rifleData = RifleData.getInstance();


    public UUID spawnRifleData(Position position, GameData gameData, World world) {
        Entity rifle = RifleCreator.scaffoldGun(world);

        VisualPart visualPart = (VisualPart) world.getMapByPart(VisualPart.class.getSimpleName()).get(rifle.getUUID());
        visualPart.setIsVisible(true);

        float spawnX = position.getX();
        float spawnY = position.getY();
        float radians = 3.1415f / 2;

        PositionPart positionPart = new PositionPart(spawnX, spawnY, radians);

        world.addtoEntityPartMap(positionPart, rifle);
        world.addtoEntityPartMap(new LootablePart(), rifle);

        RifleProcessor.addToProcessingList(rifle);
        
        return rifle.getUUID();
    }

    protected static Entity scaffoldGun(World world) {
        Entity rifle = new Entity();

        float width = 30;
        float height = 30;

        WeaponPart weaponPart = new WeaponPart(
                rifleData.getDamage(),
                rifleData.getRange(),
                rifleData.getFireRate(),
                1
        );
        WeaponAnimationPart weaponAnimationPart = new WeaponAnimationPart(
                rifleData.getIdleSpriteName(),
                rifleData.getAttackAnimationName(),
                rifleData.getWalkAnimationName(),
                rifleData.getAttackAnimationFrameCount(),
                rifleData.getWalkAnimationFrameCount(),
                rifleData.getAttackAnimationFrameDuration(),
                rifleData.getWalkAnimationFrameDuration()
        );
        VisualPart visualPart = new VisualPart(
                rifleData.getVisualPartName(),
                width,
                height
        );
        AudioPart audioPart = new AudioPart(
            rifleData.getShootingSoundFileName(),
            0.8f
        );

        world.addtoEntityPartMap(weaponPart, rifle);
        world.addtoEntityPartMap(weaponAnimationPart, rifle);
        world.addtoEntityPartMap(visualPart, rifle);
        world.addtoEntityPartMap(audioPart, rifle);

        return rifle;
    }
}

package dk.sdu.mmmi.cbse.rocketlauncher;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.*;
import dk.sdu.mmmi.cbse.common.data.Position;
import java.util.UUID;

public class RocketLauncherCreator {

    private static final RocketLauncherData rocketLauncherData = RocketLauncherData.getInstance();
    private static UUID rocketLauncherID;
    public UUID spawnRocketLauncher(Position position, GameData gameData, World world) {
        Entity rocketLauncher = RocketLauncherCreator.scaffoldGun(world);
        rocketLauncherID = rocketLauncher.getUUID();
        VisualPart visualPart = (VisualPart) world.getMapByPart(VisualPart.class.getSimpleName()).get(rocketLauncher.getUUID());
        visualPart.setIsVisible(true);

        float spawnX = position.getX();
        float spawnY = position.getY();
        float radians = 3.1415f / 2;

        PositionPart positionPart = new PositionPart(spawnX, spawnY, radians);

        world.addtoEntityPartMap(positionPart, rocketLauncher);
        world.addtoEntityPartMap(new LootablePart(), rocketLauncher);

        RocketLauncherProcessor.addToProcessingList(rocketLauncher);
        
        return rocketLauncher.getUUID();
    }

    protected static Entity scaffoldGun(World world) {
        Entity rocketLauncher = new Entity();

        float width = 30;
        float height = 30;

        WeaponPart weaponPart = new WeaponPart(
                rocketLauncherData.getDamage(),
                rocketLauncherData.getRange(),
                rocketLauncherData.getFireRate(),
                1
        );
        WeaponAnimationPart weaponAnimationPart = new WeaponAnimationPart(
                rocketLauncherData.getIdleSpriteName(),
                rocketLauncherData.getAttackAnimationName(),
                rocketLauncherData.getWalkAnimationName(),
                rocketLauncherData.getAttackAnimationFrameCount(),
                rocketLauncherData.getWalkAnimationFrameCount(),
                rocketLauncherData.getAttackAnimationFrameDuration(),
                rocketLauncherData.getWalkAnimationFrameDuration()
        );
        VisualPart visualPart = new VisualPart(
                rocketLauncherData.getVisualPartName(),
                width,
                height
        );
        AudioPart audioPart = new AudioPart(
            rocketLauncherData.getShootingSoundFileName(),
            0.12f
        );

        world.addtoEntityPartMap(weaponPart, rocketLauncher);
        world.addtoEntityPartMap(weaponAnimationPart, rocketLauncher);
        world.addtoEntityPartMap(visualPart, rocketLauncher);
        world.addtoEntityPartMap(audioPart, rocketLauncher);

        // RocketLauncherProcessor.addToProcessingList(rocketLauncher);

        return rocketLauncher;
    }
    public static UUID getRocketID(){
        return rocketLauncherID;
    }
}

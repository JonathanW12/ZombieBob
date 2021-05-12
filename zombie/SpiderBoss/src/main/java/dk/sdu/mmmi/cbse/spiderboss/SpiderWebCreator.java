package dk.sdu.mmmi.cbse.spiderboss;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.*;

public class SpiderWebCreator {
    private static final SpiderwebData spiderwebData = SpiderwebData.getInstance();

    protected Entity createWebShooter(World world) {
        Entity webShooter = new Entity();

        WeaponPart weaponPart = new WeaponPart(
                spiderwebData.getDamage(),
                spiderwebData.getRange(),
                spiderwebData.getFireRate(),
                1
        );
        WeaponAnimationPart weaponAnimationPart = new WeaponAnimationPart(
                spiderwebData.getIdleSpriteName(),
                spiderwebData.getAttackAnimationName(),
                spiderwebData.getWalkAnimationName(),
                spiderwebData.getAttackAnimationFrameCount(),
                spiderwebData.getWalkAnimationFrameCount(),
                spiderwebData.getAttackAnimationFrameDuration(),
                spiderwebData.getWalkAnimationFrameDuration()
        );

        AudioPart audioPart = new AudioPart(
                spiderwebData.getShootingSoundFileName()
        );


        world.addtoEntityPartMap(weaponPart, webShooter);
        world.addtoEntityPartMap(weaponAnimationPart, webShooter);
        world.addtoEntityPartMap(audioPart, webShooter);
        SpiderWebProcessor.addToProcessingList(webShooter);

        return webShooter;
    }
}

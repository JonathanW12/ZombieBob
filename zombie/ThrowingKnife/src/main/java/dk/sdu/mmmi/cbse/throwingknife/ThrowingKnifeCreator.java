/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.throwingknife;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.Position;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.AudioPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.LootablePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.VisualPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.WeaponAnimationPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.WeaponPart;
import java.util.UUID;

/**
 *
 * @author lake
 */
public class ThrowingKnifeCreator {

    private static final ThrowingKnifeData knifeData = ThrowingKnifeData.getInstance();
    private static UUID knifeID;

    public UUID spawnKnifeData(Position position, GameData gameData, World world) {
        Entity knife = ThrowingKnifeCreator.scaffoldGun(world);
        knifeID = knife.getUUID();
        VisualPart visualPart = (VisualPart) world.getMapByPart(VisualPart.class.getSimpleName()).get(knife.getUUID());
        visualPart.setIsVisible(true);

        float spawnX = position.getX();
        float spawnY = position.getY();
        float radians = 3.1415f / 2;

        PositionPart positionPart = new PositionPart(spawnX, spawnY, radians);

        world.addtoEntityPartMap(positionPart, knife);
        world.addtoEntityPartMap(new LootablePart(), knife);

        ThrowingKnifeProcessor.addToProcessingList(knife);

        return knife.getUUID();
    }

    protected static Entity scaffoldGun(World world) {
        Entity knife = new Entity();

        float width = 10;
        float height = 10;

        WeaponPart weaponPart = new WeaponPart(
                knifeData.getDamage(),
                knifeData.getRange(),
                knifeData.getFireRate(),
                1
        );
        WeaponAnimationPart weaponAnimationPart = new WeaponAnimationPart(
                knifeData.getIdleSpriteName(),
                knifeData.getAttackAnimationName(),
                knifeData.getWalkAnimationName(),
                knifeData.getAttackAnimationFrameCount(),
                knifeData.getWalkAnimationFrameCount(),
                knifeData.getAttackAnimationFrameDuration(),
                knifeData.getWalkAnimationFrameDuration()
        );
        VisualPart visualPart = new VisualPart(
                knifeData.getVisualPartName(),
                width,
                height
        );
        AudioPart audioPart = new AudioPart(
                knifeData.getShootingSoundFileName()
        );

        world.addtoEntityPartMap(weaponPart, knife);
        world.addtoEntityPartMap(weaponAnimationPart, knife);
        world.addtoEntityPartMap(visualPart, knife);
        world.addtoEntityPartMap(audioPart, knife);

        return knife;
    }

    public static UUID getKnifeID() {
        return knifeID;
    }
}

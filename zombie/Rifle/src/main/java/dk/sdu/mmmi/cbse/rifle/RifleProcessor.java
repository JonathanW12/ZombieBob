package dk.sdu.mmmi.cbse.rifle;


import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.*;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import org.openide.util.lookup.ServiceProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@ServiceProvider(service = IEntityProcessingService.class)
public class RifleProcessor implements IEntityProcessingService {

    private static List<Entity> rifles = new ArrayList<>();
    private Random rand = new Random();

    public void process(GameData gameData, World world) {
        for (Entity rifle: rifles) {
            WeaponPart weaponPart = (WeaponPart) world.getMapByPart(WeaponPart.class.getSimpleName()).get(rifle.getUUID());
            PositionPart weaponPosition = (PositionPart) world.getMapByPart(PositionPart.class.getSimpleName()).get(rifle.getUUID());
            if (weaponPart.isIsAttacking()) {
                float spawnDistanceFromAttacker = 50f;
                float spawnX = weaponPosition.getX() + spawnDistanceFromAttacker * (float) Math.cos(weaponPosition.getRadians());
                float spawnY = weaponPosition.getY() + spawnDistanceFromAttacker * (float) Math.sin(weaponPosition.getRadians());

                Entity bullet = new Entity();

                MovingPart movingPart = new MovingPart(12, 1000);
                world.addtoEntityPartMap(movingPart, bullet);
                world.addtoEntityPartMap(new PositionPart(spawnX, spawnY, weaponPosition.getRadians()+((rand.nextFloat()/2)-0.25f)), bullet);
                world.addtoEntityPartMap(new ProjectilePart(weaponPart.getRange()), bullet);
                world.addtoEntityPartMap(new ColliderPart(2,2), bullet);
                world.addtoEntityPartMap(new DamagePart(weaponPart.getDamage()), bullet);
                world.addtoEntityPartMap(new LifePart(1), bullet);
                world.addtoEntityPartMap(new VisualPart("RifleAmmo", 4, 4), bullet);

                movingPart.setUp(true);
            }
        }
    }

    protected static void addToProcessingList(Entity rifle) {
        rifles.add(rifle);
    }

}
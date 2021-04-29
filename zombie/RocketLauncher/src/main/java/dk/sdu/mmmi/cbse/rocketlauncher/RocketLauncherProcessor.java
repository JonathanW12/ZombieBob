package dk.sdu.mmmi.cbse.rocketlauncher;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.*;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import org.openide.util.lookup.ServiceProvider;

import java.util.ArrayList;
import java.util.List;

@ServiceProvider(service = IEntityProcessingService.class)
public class RocketLauncherProcessor implements IEntityProcessingService {

    private static List<Entity> rocketLaunchers = new ArrayList<>();

    public void process(GameData gameData, World world) {
        for (Entity rocketLauncher: rocketLaunchers) {
            WeaponPart weaponPart = (WeaponPart) world.getMapByPart(WeaponPart.class.getSimpleName()).get(rocketLauncher.getUUID());
            PositionPart weaponPosition = (PositionPart) world.getMapByPart(PositionPart.class.getSimpleName()).get(rocketLauncher.getUUID());
            if (weaponPart.isIsAttacking()) {
                float spawnDistanceFromAttacker = 50f;
                float spawnX = weaponPosition.getX() + spawnDistanceFromAttacker * (float) Math.cos(weaponPosition.getRadians());
                float spawnY = weaponPosition.getY() + spawnDistanceFromAttacker * (float) Math.sin(weaponPosition.getRadians());

                Entity bullet = new Entity();

                MovingPart movingPart = new MovingPart(12, 1000);
                world.addtoEntityPartMap(movingPart, bullet);
                
                float leftOffset = -27;
                
                float spawnXoff = spawnX + leftOffset* (float)Math.cos(Math.PI/2 + weaponPosition.getRadians());
                float spawnYoff = spawnY + leftOffset* (float)Math.sin(Math.PI/2 + weaponPosition.getRadians());
                
                world.addtoEntityPartMap(new PositionPart(spawnXoff, spawnYoff, weaponPosition.getRadians()), bullet);
                world.addtoEntityPartMap(new ProjectilePart(weaponPart.getRange()), bullet);
                world.addtoEntityPartMap(new ColliderPart(10,10), bullet);
                world.addtoEntityPartMap(new DamagePart(weaponPart.getDamage()), bullet);
                world.addtoEntityPartMap(new LifePart(1), bullet);
                world.addtoEntityPartMap(new VisualPart("Rocket", 20, 20), bullet);

                movingPart.setUp(true);
            }
        }
    }

    protected static void addToProcessingList(Entity rocketLauncher) {
        rocketLaunchers.add(rocketLauncher);
    }

}

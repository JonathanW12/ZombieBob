package dk.sdu.mmmi.cbse.spiderboss;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.*;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import org.openide.util.lookup.ServiceProvider;

import java.util.ArrayList;
import java.util.List;

@ServiceProvider(service = IEntityProcessingService.class)
public class SpiderWebProcessor implements IEntityProcessingService {

    private static List<Entity> webShooters = new ArrayList<>();

    public void process(GameData gameData, World world) {
        for (Entity webShooter : webShooters) {
            if (world.getMapByPart(WeaponPart.class.getSimpleName()) != null) {
                WeaponPart weaponPart = (WeaponPart) world.getMapByPart(WeaponPart.class.getSimpleName()).get(webShooter.getUUID());
                if (weaponPart != null) {
                    PositionPart weaponPosition = (PositionPart) world.getMapByPart(PositionPart.class.getSimpleName()).get(webShooter.getUUID());

                    if (weaponPart.isIsAttacking()) {
                        float spawnDistanceFromAttacker = 50f;
                        float spawnX = weaponPosition.getX() + spawnDistanceFromAttacker * (float) Math.cos(weaponPosition.getRadians());
                        float spawnY = weaponPosition.getY() + spawnDistanceFromAttacker * (float) Math.sin(weaponPosition.getRadians());

                        Entity web = new Entity();

                        MovingPart movingPart = new MovingPart(10, 1000);
                        world.addtoEntityPartMap(movingPart, web);
                        world.addtoEntityPartMap(new PositionPart(spawnX, spawnY, weaponPosition.getRadians()), web);
                        world.addtoEntityPartMap(new ProjectilePart(weaponPart.getRange()), web);
                        world.addtoEntityPartMap(new ColliderPart(10, 10), web);
                        world.addtoEntityPartMap(new DamagePart(weaponPart.getDamage()), web);
                        world.addtoEntityPartMap(new LifePart(1), web);
                        world.addtoEntityPartMap(new VisualPart("web", 20, 20), web);

                        movingPart.setUp(true);
                    }
                }
            }
        }
    }

    protected static void addToProcessingList(Entity gun) {
        webShooters.add(gun);
    }

    protected static void clearProcessingList() {
        webShooters.clear();
    }
}

package dk.sdu.mmmi.cbse.handgun;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.ColliderPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.DamagePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.ProjectilePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.WeaponPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.VisualPart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import java.util.ArrayList;
import java.util.List;
import org.openide.util.lookup.ServiceProvider;

@ServiceProvider(service = IEntityProcessingService.class)
public class HandgunProcessor implements IEntityProcessingService {
    
    private static List<Entity> guns = new ArrayList<>();
    
    public void process(GameData gameData, World world) {
        for (Entity gun: guns) {
            WeaponPart weaponPart = (WeaponPart) world.getMapByPart(WeaponPart.class.getSimpleName()).get(gun.getUUID());
            PositionPart weaponPosition = (PositionPart) world.getMapByPart(PositionPart.class.getSimpleName()).get(gun.getUUID());
            
            if (weaponPart.isIsAttacking()) {
                weaponPart.setIsAttacking(false);
                
                float spawnDistanceFromAttacker = 50f;
                float spawnX = weaponPosition.getX() + spawnDistanceFromAttacker * (float) Math.cos(weaponPosition.getRadians());
                float spawnY = weaponPosition.getY() + spawnDistanceFromAttacker * (float) Math.sin(weaponPosition.getRadians());
                
                Entity bullet = new Entity();
                
                world.addtoEntityPartMap(new PositionPart(spawnX, spawnY, weaponPosition.getRadians()), bullet);
                world.addtoEntityPartMap(new ProjectilePart(weaponPart.getRange()), bullet);
                MovingPart movingPart = new MovingPart(20, 1000);
                world.addtoEntityPartMap(movingPart, bullet);
                world.addtoEntityPartMap(new ColliderPart(5), bullet);
                world.addtoEntityPartMap(new DamagePart(weaponPart.getDamage()), bullet);
                world.addtoEntityPartMap(new LifePart(1), bullet);
                world.addtoEntityPartMap(new VisualPart("projectile", 10, 10), bullet);
                
                movingPart.setUp(true);
            }
        }
    }
    
    protected static void addToProcessingList(Entity gun) {
        guns.add(gun);
    }
    
}

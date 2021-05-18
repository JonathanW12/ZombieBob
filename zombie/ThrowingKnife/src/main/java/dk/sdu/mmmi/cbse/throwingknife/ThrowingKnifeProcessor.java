/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.throwingknife;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.ColliderPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.DamagePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.ProjectilePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.VisualPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.WeaponPart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author lake
 */
@ServiceProvider(service = IEntityProcessingService.class)
public class ThrowingKnifeProcessor implements IEntityProcessingService {

    private static List<Entity> knifes = new ArrayList<>();
    private Random rand = new Random();

    public void process(GameData gameData, World world) {
        for (Entity knife: knifes) {
            if(world.getMapByPart(WeaponPart.class.getSimpleName())!=null){
            WeaponPart weaponPart = (WeaponPart)world.getMapByPart(WeaponPart.class.getSimpleName()).get(knife.getUUID());
            if(weaponPart != null){
                
            
            PositionPart weaponPosition = (PositionPart) world.getMapByPart(PositionPart.class.getSimpleName()).get(knife.getUUID());
            if (weaponPart.isIsAttacking()) {
                float spawnDistanceFromAttacker = 50f;
                float spawnX = weaponPosition.getX() + spawnDistanceFromAttacker * (float) Math.cos(weaponPosition.getRadians());
                float spawnY = weaponPosition.getY() + spawnDistanceFromAttacker * (float) Math.sin(weaponPosition.getRadians());

                Entity bullet = new Entity();

                MovingPart movingPart = new MovingPart(20, 1000);
                world.addtoEntityPartMap(movingPart, bullet);
                world.addtoEntityPartMap(new PositionPart(spawnX, spawnY, weaponPosition.getRadians()), bullet);
                world.addtoEntityPartMap(new ProjectilePart(weaponPart.getRange()), bullet);
                world.addtoEntityPartMap(new ColliderPart(10, 10), bullet);
                world.addtoEntityPartMap(new DamagePart(weaponPart.getDamage()), bullet);
                world.addtoEntityPartMap(new LifePart(1), bullet);
                world.addtoEntityPartMap(new VisualPart("Sword",20, 20), bullet);

                movingPart.setUp(true);
            }
        }
        }
        }
    }

    protected static void addToProcessingList(Entity knife) {
        knifes.add(knife);
    }

}
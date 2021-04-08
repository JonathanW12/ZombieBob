/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.bulletsystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityammoparts.BulletAmmoPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.CollectorPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.DamagePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.EntityPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.ProjectilePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.WeaponPart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import java.util.Map;
import java.util.UUID;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

/**
 *
 * @author phili
 */
@ServiceProviders(value = {
    @ServiceProvider(service = IEntityProcessingService.class)})
public class BulletSpawnSystem implements IEntityProcessingService{

    @Override
    public void process(GameData gameData, World world) {
        for(Map.Entry<UUID,EntityPart> entry : world.getMapByPart(BulletAmmoPart.class.getSimpleName()).entrySet()){
            WeaponPart weaponPart = ((WeaponPart)world.getMapByPart(WeaponPart.class.getSimpleName()).get(entry.getKey()));
            if(weaponPart.isIsAttacking()){
                PositionPart weaponPosition = ((PositionPart)world.getMapByPart(PositionPart.class.getSimpleName()).get(entry.getKey()));
                float spawnDistanceFromAttacker = 10f;
                float weaponRightDisplacementOfAttacker = 3;//not used, but should be in cases when fx a weapon like  RocketLauncher is on the shoulder
                
                float spawnX = weaponPosition.getX() + spawnDistanceFromAttacker * (float)Math.cos(weaponPosition.getRadians());
                float spawnY = weaponPosition.getY() + spawnDistanceFromAttacker * (float)Math.sin(weaponPosition.getRadians());
                
                Entity bullet = new Entity();
                world.addtoEntityPartMap(new PositionPart(spawnX, spawnY, weaponPosition.getRadians()), bullet);
                world.addtoEntityPartMap(new ProjectilePart(weaponPart.getRange()), bullet);
                MovingPart movingPart = new MovingPart(20, 100, 120, 0);
                movingPart.setUp(true);
                world.addtoEntityPartMap(movingPart, bullet);
                world.addtoEntityPartMap(new DamagePart(weaponPart.getDamage()), bullet);
                world.addtoEntityPartMap(new LifePart(1), bullet);
                
                
                
                
                
            }
        }
    }
    
}

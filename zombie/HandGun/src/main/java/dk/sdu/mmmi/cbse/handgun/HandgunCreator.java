package dk.sdu.mmmi.cbse.handgun;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.WeaponAnimationPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.WeaponPart;
import dk.sdu.mmmi.cbse.common.services.IWeaponCreatorService;
import org.openide.util.lookup.ServiceProvider;

@ServiceProvider(service = IWeaponCreatorService.class)
public class HandgunCreator implements IWeaponCreatorService {  
   
    public Entity createWeapon(GameData gameData, World world, Entity owner) {
        Entity gun = new Entity();
        
        WeaponPart weaponPart = new WeaponPart(80, 50000, 0.3f, 1);
        WeaponAnimationPart weaponAnimationPart = new WeaponAnimationPart(
            "PlayerGun1",
            "PlayerShootGun",
            "PlayerWalkGun",
            2,
            2,
            0.03f,
            0.2f
        );
        PositionPart positionPart = (PositionPart) world.getMapByPart(
            PositionPart.class.getSimpleName()).get(owner.getUUID()
        );
        
        world.addtoEntityPartMap(weaponPart, gun);
        world.addtoEntityPartMap(weaponAnimationPart, gun);
        world.addtoEntityPartMap(positionPart, gun);
        
        HandgunProcessor.addToProcessingList(gun);
        
        return gun;
    }

}

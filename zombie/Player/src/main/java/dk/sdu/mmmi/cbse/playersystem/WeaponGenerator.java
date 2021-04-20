package dk.sdu.mmmi.cbse.playersystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityammoparts.IWeaponAmmo;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.WeaponPart;
import dk.sdu.mmmi.cbse.common.data.entityammoparts.BulletAmmoPart;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class WeaponGenerator {
    
    private static Map<UUID, IWeaponAmmo> weaponAmmoMap = new HashMap<>();
    
    public static IWeaponAmmo getAmmoPart(UUID weaponId) {
        return weaponAmmoMap.get(weaponId);
    }
    
    public static Entity createGun(Entity owner, World world){
        Entity gun = new Entity();
        BulletAmmoPart ammoPart = new BulletAmmoPart();
        
        world.addtoEntityPartMap(new WeaponPart(80, 50000, 0.3f, 1), gun);
        world.addtoEntityPartMap((PositionPart)world.getMapByPart(PositionPart.class.getSimpleName()).get(owner.getUUID()), gun);
        world.addtoEntityPartMap(ammoPart, gun);
        
        weaponAmmoMap.put(gun.getUUID(), (IWeaponAmmo) ammoPart);
        
        return gun;
    }
    
    public static Entity createRifle(Entity owner, World world){
        Entity rifle = new Entity();
        
        return rifle;
    }
    
    public static Entity createRPG(Entity owner, World world){
        Entity rpg = new Entity();
        
        return rpg;
    }
    
    public static Entity createFists(Entity owner, World world){
        Entity fists = new Entity();
        
        return fists;    
    }
}

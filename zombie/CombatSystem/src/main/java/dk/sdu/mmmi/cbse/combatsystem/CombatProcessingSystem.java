/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.combatsystem;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityammoparts.BulletAmmoPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.CombatPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.EntityPart;
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
public class CombatProcessingSystem implements IEntityProcessingService {

    @Override
    public void process(GameData gameData, World world) {
        System.out.println("processing combat system");
        for(Map.Entry<UUID,EntityPart> entry : world.getMapByPart(CombatPart.class.getSimpleName()).entrySet()){
            System.out.println("combat part registered ´- is attacking:"+ ((CombatPart)entry.getValue()).isAttacking());
            if(((CombatPart)entry.getValue()).isAttacking()){
                System.out.println("player attacking");
                UUID currentWeapon = ((CombatPart)entry.getValue()).getCurrentWeapon();
                ((WeaponPart)world.getMapByPart(WeaponPart.class.getSimpleName()).get(currentWeapon)).setIsAttacking(true);
            }
        }
    }
    
}

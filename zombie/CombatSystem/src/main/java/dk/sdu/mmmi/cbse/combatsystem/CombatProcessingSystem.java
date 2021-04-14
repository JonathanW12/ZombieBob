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
        for(Map.Entry<UUID,EntityPart> entry : world.getMapByPart(CombatPart.class.getSimpleName()).entrySet()){
            UUID currentWeapon = ((CombatPart)entry.getValue()).getCurrentWeapon();
            if(world.getMapByPart(WeaponPart.class.getSimpleName())!=null){
                    //System.out.println("found attached weapon to combat part");
                    WeaponPart weaponPart = ((WeaponPart)world.getMapByPart(WeaponPart.class.getSimpleName()).get(currentWeapon));
                weaponPart.setTimeSinceLastTrigger(weaponPart.getTimeSinceLastTrigger() + gameData.getDelta());
            
            if(((CombatPart)entry.getValue()).isAttacking()){
                    if(weaponPart.getTimeSinceLastTrigger() > weaponPart.getFireRate()){
                        weaponPart.setIsAttacking(true);
                        weaponPart.setTimeSinceLastTrigger(0);
                    }
                    
                } else {
                    System.out.println("no weaponPart equipped as currentWeapon");
                }
            }
        }
    }
    
}

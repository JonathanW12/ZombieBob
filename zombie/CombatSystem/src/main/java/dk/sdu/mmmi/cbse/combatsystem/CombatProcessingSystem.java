/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.combatsystem;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.CombatPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.EntityPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.WeaponPart;
import dk.sdu.mmmi.cbse.common.data.entitytypeparts.PlayerPart;
import dk.sdu.mmmi.cbse.common.data.entitytypeparts.ZombiePart;
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
            if(world.getMapByPart(WeaponPart.class.getSimpleName()) != null && currentWeapon != null){
                //System.out.println("found attached weapon to combat part");
                WeaponPart weaponPart = ((WeaponPart)world.getMapByPart(WeaponPart.class.getSimpleName()).get(currentWeapon));
                weaponPart.setTimeSinceLastTrigger(weaponPart.getTimeSinceLastTrigger() + gameData.getDelta());
            
                if(((CombatPart)entry.getValue()).isAttacking()){
                    if(weaponPart.getTimeSinceLastTrigger() > weaponPart.getFireRate()){
                        if(world.getMapByPart(ZombiePart.class.getSimpleName()) != null){
                            if(world.getMapByPart(ZombiePart.class.getSimpleName()).get(entry.getKey())!=null){
                                //processing of zombie attack
                                if (world.getMapByPart(PlayerPart.class.getSimpleName()) != null) {
                                    for (Map.Entry<UUID,EntityPart> player : world.getMapByPart(PlayerPart.class.getSimpleName()).entrySet()) {
                                        LifePart lifePart = (LifePart) world.getMapByPart(LifePart.class.getSimpleName()).get(player.getKey());

                                        PositionPart zombiePos = (PositionPart) world.getMapByPart(PositionPart.class.getSimpleName()).get(entry.getKey());
                                        PositionPart playerPos = (PositionPart) world.getMapByPart(PositionPart.class.getSimpleName()).get(player.getKey());
                                        float distance = Float.MAX_VALUE;
                                        if(playerPos != null){
                                        distance = (float) Math.sqrt(
                                        Math.pow(zombiePos.getX() - playerPos.getX(), 2) +
                                        Math.pow(zombiePos.getY() - playerPos.getY(), 2)
                                            );
                                        }
                                        if (distance <= weaponPart.getRange() && weaponPart.getTimeSinceLastTrigger() > weaponPart.getFireRate()) {

                                            // OBS
                                            lifePart.setLife(lifePart.getLife()-weaponPart.getDamage());
                                            weaponPart.setIsAttacking(true);
                                            weaponPart.setTimeSinceLastTrigger(0);
                                        }
                                    }
                                }
                            }
                        } else {
                            weaponPart.setIsAttacking(true);
                            weaponPart.setTimeSinceLastTrigger(0);
                        }
                    } else {
                        weaponPart.setIsAttacking(false);
                    }
                }
            }
        }
    }
    
}

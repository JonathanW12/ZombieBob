/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.common.data.entityparts;

import dk.sdu.mmmi.cbse.common.data.GameData;
import java.util.UUID;

/**
 *CombatPart describes the current weapon an entity is wielding.
 * As well as if the entity is attacking.
 */
public class CombatPart implements EntityPart{
    
    private UUID currentWeapon;
    private boolean isAttacking;
    
    public CombatPart(UUID currentWeapon) {
        this.currentWeapon = currentWeapon;
    }
    
    public void setCurrentWeapon(UUID currentWeapon) {
        this.currentWeapon = currentWeapon;
    }

    public void setIsAttacking(boolean isAttacking) {
        this.isAttacking = isAttacking;
    }

    public UUID getCurrentWeapon() {
        return currentWeapon;
    }

    public boolean isIsAttacking() {
        return isAttacking;
    }
}

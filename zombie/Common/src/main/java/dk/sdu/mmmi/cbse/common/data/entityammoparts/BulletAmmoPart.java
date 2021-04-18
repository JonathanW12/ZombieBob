/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.common.data.entityammoparts;

import dk.sdu.mmmi.cbse.common.data.entityparts.EntityPart;

/**
 *
 * @author jonaw
 */
public class BulletAmmoPart implements EntityPart, IWeaponAmmo {
    
    private final String idleSpriteName;
    private final String attackAnimationName;
    private final String walkAnimationName;
    
    public BulletAmmoPart() {
        idleSpriteName = "PlayerGun1";
        attackAnimationName = "PlayerShootGun";
        walkAnimationName = "PlayerWalkGun";
    }
    
    @Override
    public String getIdleSpriteName() {
        return idleSpriteName;
    }
    
    @Override
    public String getAttackAnimationName() {
        return attackAnimationName;
    }
    
    @Override
    public String getWalkAnimationName() {
        return walkAnimationName;
    }
}

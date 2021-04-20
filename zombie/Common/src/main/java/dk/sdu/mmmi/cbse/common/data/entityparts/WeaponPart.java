/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.common.data.entityparts;

/**
 *WeaponPart describes the damage and range of a weapon.
 */
public class WeaponPart implements EntityPart{
    
    private int damage;
    private float range;
    private boolean isAttacking;
    private float fireRate;
    private float timeSinceLastTrigger;
    private int level;


    public WeaponPart(int damage, float range, float fireRate, int level) {
        this.damage = damage;
        this.range = range;
        this.fireRate = fireRate;
        this.timeSinceLastTrigger = 0;
        this.level = level;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void setRange(float range) {
        this.range = range;
    }

    public void setIsAttacking(boolean isAttacking) {
        this.isAttacking = isAttacking;
    }

    public int getDamage() {
        return damage;
    }

    public float getRange() {
        return range;
    }
    
    public int getLevel() {
        return level;
    }

    public boolean isIsAttacking() {
        return isAttacking;
    }
    
    public float getFireRate(){
        return this.fireRate;
    }
    
    public float getTimeSinceLastTrigger(){
        return this.timeSinceLastTrigger;
    }
    
    public void setTimeSinceLastTrigger(float time){
        this.timeSinceLastTrigger = time;
    }

}

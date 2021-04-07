/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.common.data.entityparts;

import java.util.ArrayList;
import java.util.UUID;

/**
 *
 * @author phili
 */
public class WeaponInventoryPart implements EntityPart {
    private ArrayList<UUID> weapons;
    private int capacity;
    
    public WeaponInventoryPart(int capacity){
        this.weapons = new ArrayList<UUID>();
        this.capacity = capacity;
    }
    
    /**
     * 
     * @return 
     * if inventory is full, the method will return false, else true
     */
    public boolean addWeapon(UUID weaponId){
        if(this.weapons.size() < capacity){
            this.weapons.add(weaponId);
            return true;
        } return false;
    }
    
    public void removeWeapon(UUID weaponId){
        this.weapons.remove(weaponId);
    }
    
    public ArrayList<UUID> getWeapons(){
        return this.weapons;
    }
}


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
 * @author jonaw
 */
public class WeaponInventoryPart implements EntityPart{
    //inventory is supposed to contain wepons ?..
    private ArrayList<UUID> inventory;
    private int capacity;
    
    public WeaponInventoryPart(int capacity) {
        this.capacity = capacity;
        inventory = new ArrayList<UUID>();
    }
    
    public boolean addWeapon(UUID weaponId){
        if(this.inventory.size() < capacity){
            this.inventory.add(weaponId);
            System.out.println("Added at index "+inventory.toString());
            return true;
        } return false;
    }
    
    public ArrayList<UUID> getInventory() {
        return inventory;
    }

    public int getCapacity() {
        return capacity;
    }

    public void addToInventory(UUID item) {
        this.inventory.add(item);
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
   public void removeWeapon(UUID weaponId){
        this.inventory.remove(weaponId);
    }

}

 
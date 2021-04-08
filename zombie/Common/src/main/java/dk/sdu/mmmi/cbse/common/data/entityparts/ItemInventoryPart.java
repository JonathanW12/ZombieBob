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
public class ItemInventoryPart implements EntityPart {

    private ArrayList<UUID> items;
    private int capacity;
    
    public ItemInventoryPart(int capacity){
        this.capacity = capacity; 
        this.items = new ArrayList<UUID>();
    }
    
    public ArrayList<UUID> getItems(){
        return this.items;
    }
    
    public boolean addItem(UUID itemId){
        if(this.items.size() < capacity){
            items.add(itemId);
        }
        return false;
    }
    
    public void removeItem(UUID itemId){
        this.items.remove(itemId);
    }
}

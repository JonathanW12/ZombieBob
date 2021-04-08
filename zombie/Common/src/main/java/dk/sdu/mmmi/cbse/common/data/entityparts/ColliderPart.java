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
 * hele implementationen og definetionen på hvordan collisionen skal implementeres, skal afgøres af tobias,
 * men andre dele af systemet afhænger af "getCollidingEntities()", da man skal kunne reagere på en entites collision
 * HOW ITS DONE IT UP TO YOU, TOBI :)
 */
public class ColliderPart {
    private ArrayList<UUID> collidingEntities;
    
    
    public ColliderPart(){
        this.collidingEntities = new ArrayList<UUID>();
    }
    
    public ArrayList<UUID> getCollidingEntities(){
        return this.collidingEntities;
    }
    
}

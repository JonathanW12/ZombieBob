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
 * 
 */
public class ColliderPart implements EntityPart {
    private ArrayList<UUID> collidingEntities = new ArrayList<UUID>();
    private float width;
    private float height;
    private float radius;
    
    
    public ColliderPart(float width, float height){
        this.width = width;
        this.height = height;
    }
    public ColliderPart(float radius){
        this.radius = radius; 
    }
    
    public ArrayList<UUID> getCollidingEntities(){
        return this.collidingEntities;
    }

    public float getWidth() {
        return this.width; 
    }
    
    public float getHeight(){
        return this.height; 
    }

    public float getRadius() {
        return this.radius;
    }
    
}

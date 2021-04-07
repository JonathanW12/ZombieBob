/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.common.data.entityparts;


/**
 * LifePart describes the hit points an entity has.
 * And whether this entity is being hit.
 */
public class LifePart implements EntityPart{

    private int life;
    private boolean isHit;
    
    public LifePart(int life){
        this.life = life;
    }
    
    public int getLife(){
        return life;
    }
    
    public void setLife(int life){
        this.life = life;
    }
    
    public boolean getIsHit(){
        return isHit;
    }
    
    public void setIsHit(boolean isHit){
        this.isHit = isHit;
    }
   
}

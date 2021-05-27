/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.common.data.entityparts;

/**
 *AiMovementPart describes the AI level of an entity's movement.
 */
public class AiMovementPart implements EntityPart{
    private int level;
    private long delay;
    private long lastUpdate;
    
    public AiMovementPart(int level){
        this.level = level;
        delay = 500L;
        lastUpdate = System.currentTimeMillis();
    }
    
    public int getLevel(){
        return level;
    }
    
    public long getDelay() {
        return delay;
    }
    
    public long getLastUpdate() {
        return lastUpdate;
    }
    
    public void resetDelay() {
        lastUpdate = System.currentTimeMillis();
    }
}

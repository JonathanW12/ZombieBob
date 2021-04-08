/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.common.data.entityparts;

/**
 *
 * @author phili
 * 
 * add part, if owning entity should be able to collect and gather weapons
 */
public class CollectorPart {
    private boolean isCollecting;
    
    public CollectorPart(){
        
    }
    
    public void setCollecting(boolean b){
        this.isCollecting = b;
    }
    
    public boolean isCollecting(){
        return this.isCollecting;
    }
    
}
